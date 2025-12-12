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
//		playingGrid.print();
//		playingGrid.addToken(0);
//		playingGrid.print();
//		playingGrid.addToken(5);
//		playingGrid.print();
//		playingGrid.addToken(5);
//		playingGrid.print();
//		playingGrid.addToken(6);
		playingGrid.print();
		
		
//        root.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: transparent;"); // makes background transparent so can see shapes behind 
//        grid.setAlignment(Pos.CENTER);
        grid.relocate(30, 70);
        grid.setPrefSize(600, 700); // does nothing?
        grid.setMaxSize(600, 700);
        
        Circle token = new Circle(30);
//        token.setFill(Color.RED);
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
//                System.out.println(r + "Row: " + rowLocation);
                double columnLocation = playingGrid.getColumnVal(c);
                circle.setCenterX(columnLocation);
//                System.out.println(c + "Column: " + columnLocation + "\n");
                circle.setFill(Color.TRANSPARENT);
                arrCircles[r][c] = circle;
                root.getChildren().add(circle);
            }
        }
        
//        System.out.println(token.getCenterX());
        
        
        Button confirm = new Button("CONFIRM");
        confirm.relocate(271, 630);
        
        Button nextPlayer = new Button("END TURN");
        nextPlayer.relocate(351, 630);
        
//        int player = playingGrid.getTurnsNoModify();
//        
//        if (player == 1) {
//        	token.setFill(Color.YELLOW);
//        }
//        else {
//        	token.setFill(Color.RED);
//        }
        
        scene.setOnKeyPressed(e-> {
        	double move = 80.0;
        	double temp = 50;
        	
//        	int playerColor = playingGrid.getTurnsNoModify();
        	
        	
        	token.setCenterY(50);
        	
        	if (e.getCode() == KeyCode.A) {
        		if (turnOver) {
	//        		token.requestFocus();
	//        		token.setCenterY(150);
	//        		System.out.println(token.getCenterY());
	//        		System.out.println(token.getCenterX());
	        		token.setCenterX(token.getCenterX() - move);
//	        		if (playerColor == 1) {
//	        			token.setFill(Color.YELLOW);
//	        		}
//	        		else {
//	        			token.setFill(Color.RED);
//	        		}
        		}
        	}
        	if (e.getCode() == KeyCode.D) {
        		if (turnOver) {
	        		token.setCenterY(temp);
	//        		System.out.println(token.getCenterX());
	        		token.setCenterX(token.getCenterX() + move);
	//        		System.out.println(token.getCenterX());
	//        		token.setCenterY(token.getCenterY() - token.getCenterY() + 50);
//	        		if (playerColor == 1) {
//	        			token.setFill(Color.YELLOW);
//	        		}
//	        		else {
//	        			token.setFill(Color.RED);
//	        		}
        		}
        	}
//        	if (e.getCode() == KeyCode.W) {
//        		token.setCenterY(token.getCenterY() + 1);
//        		
//        		int column = playingGrid.getColumnNum(token.getCenterX());
//            	double rowVal = playingGrid.addToken(column);
//            	int row = playingGrid.getRowNum(column);
//        		
//        		Path path = new Path();
//            	double x1 = token.getCenterX();
//            	double y1 = token.getCenterY();
//            	path.getElements().add(new MoveTo(x1, y1));
////            	path.getElements().add(new LineTo(x1, rowVal));
//            	
//            	PathTransition transition = new PathTransition();
//            	transition.setNode(token);
//            	transition.setDuration(Duration.seconds(2));
//            	transition.setPath(path);
//            	transition.setCycleCount(1);
//            	transition.play();
//        	}
        	
        	
        });
                
        confirm.setOnAction(e-> {
        	if (turnOver) {
	        	int column = playingGrid.getColumnNum(token.getCenterX());
	        	double rowVal = playingGrid.addToken(column);
	        	int row = playingGrid.getRowNum(column);
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
	        			arrCircles[row + 1][column].setFill(Color.RED);
	        			arrCircles[row + 1][column].setStroke(Color.BLACK);
	//        			token.setFill(Color.RED);
	//        			
	//        			token.relocate(0, 0);
	//        			token.setCenterY(50);
	//        			System.out.println(token.getCenterY());
	//        			System.out.println("X: " + token.getCenterX());
	        		}
	        		if (playerColor == 2) {
	//        			System.out.println("PLAYER: " + playerColor);
	        			arrCircles[row + 1][column].setFill(Color.YELLOW);
	        			arrCircles[row + 1][column].setStroke(Color.BLACK);
	//        			token.setFill(Color.YELLOW);
	        		}
	        	});
	        	
	        	transition.play();
	        	
	        	playingGrid.print();
	        
        	}
        	
        	turnOver = false;
        	
        });
        
        nextPlayer.setOnAction(e-> {
        	int playerColor = playingGrid.getTurnsNoModify();
        	
        	Path path = new Path();
        	double x1 = token.getCenterX();
        	double y1 = token.getCenterY();
        	path.getElements().add(new MoveTo(x1, y1));
//        	path.getElements().add(new LineTo(x1, 50));
        	path.getElements().add(new LineTo(x1, y1 + 10));
        	
        	PathTransition transition = new PathTransition();
        	transition.setNode(token);
        	transition.setDuration(Duration.millis(1));
        	transition.setCycleCount(1);
        	transition.setPath(path);
        	transition.play();
        	
//        	token.setFill(Color.TRANSPARENT);
        	
//        	transition.setOnFinished(g-> {
        	if (playerColor == 1) {
    			token.setFill(Color.YELLOW);
    		}
    		else {
    			token.setFill(Color.RED);
    		}
        	
        	turnOver = true;
        	playingGrid.check();
//        	});
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
