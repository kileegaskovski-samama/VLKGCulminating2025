import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

//import javafx.geometry.Pos;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

//import javafx.scene.Scene;

import javafx.scene.paint.Color;

//import javafx.scene.text.*;

import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

//import java.util.Scanner;

public class GUIDriver extends Application {
	
	boolean turnOver = true;

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root, 700, 700);
		
		
		
		
		Grid playingGrid = new Grid();
		playingGrid.print();
		
		

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: transparent;"); // makes background transparent so can see shapes behind 
        grid.relocate(30, 70);
        grid.setPrefSize(600, 700); // does nothing?
        grid.setMaxSize(600, 700);
        
        Circle token = new Circle(30);
        token.setCenterX(31);
        token.setCenterY(50);
        token.setFill(Color.YELLOW);
        
        Shape[][] arrShapes = new Shape[6][7];
        Circle[][] arrCircles = new Circle[6][7];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                Rectangle sq = new Rectangle(31, 31, 100, 100);
                Circle circ = new Circle(30);

                // align circle to middle of square
                circ.setCenterX(sq.getX() + sq.getWidth() / 2);
                circ.setCenterY(sq.getY() + sq.getWidth() / 2); 
                
                // takes area of circle away from middle of square
                Shape shape = Shape.subtract(sq, circ);
                shape.setFill(Color.CORNFLOWERBLUE);
                arrShapes[r][c] = shape;

                Pane cell = new Pane(shape);
                
                // limits size of cell
                cell.setPrefSize(80, 80);
                cell.setMaxSize(80, 80);
               
                grid.add(cell, c, r); // adds cell to grid
                
                
                Circle circle = new Circle(30);
                double rowLocation = playingGrid.getRowVal(r);
                circle.setCenterY(rowLocation);
                double columnLocation = playingGrid.getColumnVal(c);
                circle.setCenterX(columnLocation);
                circle.setFill(Color.TRANSPARENT);
                arrCircles[r][c] = circle;
                root.getChildren().add(circle);
            }
        }
        
        
        
        Button confirm = new Button("CONFIRM");
        confirm.relocate(271, 630);
        
        Button nextPlayer = new Button("END TURN");
        nextPlayer.relocate(351, 630);
        
        
        scene.setOnKeyPressed(e-> {
        	double move = 80.0;
        	double temp = 50;        	
        	
        	token.setCenterY(50);
        	
        	if (e.getCode() == KeyCode.A) {
        		if (turnOver) {
	        		token.setCenterX(token.getCenterX() - move);
        		}
        	}
        	if (e.getCode() == KeyCode.D) {
        		if (turnOver) {
	        		token.setCenterY(temp);
	        		token.setCenterX(token.getCenterX() + move);
        		}
        	}
        	
        	
        });
                
        confirm.setOnAction(e-> {
        	if (turnOver) { // prevents user from pressing button twice in a row
	        	int column = playingGrid.getColumnNum(token.getCenterX());
	        	double rowVal = playingGrid.addToken(column);
	        	System.out.println("Check 1");
	        	int row = playingGrid.getCurrentRow();
	        	System.out.println("Check 2");
	        	System.out.println("Number you get: " + row);
	        	int playerColor = playingGrid.getTurnsNoModify();
	        	
	        	Path path = new Path();
	        	double x1 = token.getCenterX();
	        	double y1 = token.getCenterY();
	        	path.getElements().add(new MoveTo(x1, y1));
	        	path.getElements().add(new LineTo(x1, rowVal));
	        	
	        	PathTransition transition = new PathTransition();
	        	transition.setNode(token);
	        	transition.setDuration(Duration.seconds(2));
	        	transition.setPath(path);
	        	transition.setCycleCount(1);
	        	
	        	transition.setOnFinished(g-> {
	        		if (playerColor == 1) {
	        			arrCircles[row][column].setFill(Color.RED);
	        			arrCircles[row][column].setStroke(Color.BLACK);
	        		}
	        		if (playerColor == 2) {
	        			arrCircles[row][column].setFill(Color.YELLOW);
	        			arrCircles[row][column].setStroke(Color.BLACK);
	        		}
	        	});
	        	
	        	transition.play();
	        	
	        	playingGrid.print();
	        	playingGrid.checkForWin();
	        	
        	}
        	
        	turnOver = false;
        	
        });
        
        nextPlayer.setOnAction(e-> {
        	int playerColor = playingGrid.getTurnsNoModify();
        	
        	Path path = new Path();
        	double x1 = token.getCenterX();
        	double y1 = token.getCenterY();
        	path.getElements().add(new MoveTo(x1, y1));
        	path.getElements().add(new LineTo(x1, y1 + 10));
        	
        	PathTransition transition = new PathTransition();
        	transition.setNode(token);
        	transition.setDuration(Duration.millis(1));
        	transition.setCycleCount(1);
        	transition.setPath(path);
        	transition.play();
        	
        	if (playerColor == 1) {
    			token.setFill(Color.YELLOW);
    		}
    		else {
    			token.setFill(Color.RED);
    		}
        	
        	turnOver = true;
        	
        });
        
        Button grey = new Button("Grey");
        grey.relocate(641.5, 111);
        Button green = new Button("Green");
        green.relocate(641.5, 191);
        Button blue = new Button("Blue");
        blue.relocate(641.5, 271);
        Button purple = new Button("Purple");
        purple.relocate(641.5, 351);
        Button orange = new Button("Orange");
        orange.relocate(641.5, 431);
        Button pink = new Button("Pink");
        pink.relocate(641.5, 511);
        
        grey.setOnAction(e-> {
        	for (int r = 0; r < arrShapes.length; r++) {
        		for (int c = 0; c < arrShapes[r].length; c++) {
        			arrShapes[r][c].setFill(Color.GREY);
        		}
        	}
        });
        
        green.setOnAction(e-> {
        	for (int r = 0; r < arrShapes.length; r++) {
        		for (int c = 0; c < arrShapes[r].length; c++) {
        			arrShapes[r][c].setFill(Color.GREEN);
        		}
        	}
        });
        
        blue.setOnAction(e-> {
        	for (int r = 0; r < arrShapes.length; r++) {
        		for (int c = 0; c < arrShapes[r].length; c++) {
        			arrShapes[r][c].setFill(Color.CORNFLOWERBLUE);
        		}
        	}
        });
        
        purple.setOnAction(e-> {
        	for (int r = 0; r < arrShapes.length; r++) {
        		for (int c = 0; c < arrShapes[r].length; c++) {
        			arrShapes[r][c].setFill(Color.PURPLE);
        		}
        	}
        });
        
        orange.setOnAction(e-> {
        	for (int r = 0; r < arrShapes.length; r++) {
        		for (int c = 0; c < arrShapes[r].length; c++) {
        			arrShapes[r][c].setFill(Color.ORANGE);
        		}
        	}
        });
        
        pink.setOnAction(e-> {
        	for (int r = 0; r < arrShapes.length; r++) {
        		for (int c = 0; c < arrShapes[r].length; c++) {
        			arrShapes[r][c].setFill(Color.PINK);
        		}
        	}
        });
        


        root.getChildren().addAll(grid); // adds rectangle behind grid
        
        root.getChildren().add(token);
        root.getChildren().add(confirm);
        root.getChildren().add(nextPlayer);
        
        root.getChildren().add(grey);
        root.getChildren().add(green);
        root.getChildren().add(blue);
        root.getChildren().add(purple);
        root.getChildren().add(orange);
        root.getChildren().add(pink);
        
        
        stage.setScene(scene);
        stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
