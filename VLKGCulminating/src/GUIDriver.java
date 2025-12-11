import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Scanner;

public class GUIDriver extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Grid playingGrid = new Grid();
		playingGrid.print();
		playingGrid.addToken(0, 0);
		playingGrid.print();
		playingGrid.addToken(5, 5);
		playingGrid.print();
		playingGrid.addToken(5, 5);
		playingGrid.print();
		playingGrid.addToken(5, 6);
		playingGrid.print();
		
		Pane root = new Pane();
//        root.setAlignment(Pos.CENTER);

//        Circle disc = new Circle(20, 100, 100);
//        disc.setFill(Color.YELLOW);
//        root.getChildren().add(disc);

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: transparent;"); // makes background transparent so can see shapes behind 
//        grid.setAlignment(Pos.CENTER);
        grid.relocate(30, 70);
        grid.setPrefSize(600, 700); // does nothing?
        grid.setMaxSize(600, 700);
        
        Circle token = new Circle(30);
        token.setFill(Color.RED);
//        token.setCenterX(0);
//        token.setCenterY(0);
//        token.relocate(0, 0);
//        token.setCenterX(351);
        token.setCenterX(591);
        token.setCenterY(0);
        
        
        
        Shape[][] arrShapes = new Shape[6][7];
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
            }
        }
        
        Button confirm = new Button("1 ");
        confirm.relocate(35, 235);
        
        confirm.setOnAction(e-> {
//        	System.out.println(token.getCenterX());
//        	int x1 = playingGrid.getColumnNum(token.getCenterX());
//        	System.out.println(x1);
//        	int y1 = playingGrid.getRowNum(token.getCenterY());
//        	playingGrid.addToken();
        	
        	Path path = new Path();
        	double x1 = token.getCenterX();
        	double y1 = token.getCenterY();
        	path.getElements().add(new MoveTo(x1, y1));
        	path.getElements().add(new LineTo(x1, 151));
        	
        	PathTransition transition = new PathTransition();
        	transition.setNode(token);
        	transition.setDuration(Duration.seconds(2));
        	transition.setPath(path);
        	transition.setCycleCount(1);
        	transition.play();
        });

//        Rectangle rectangle = new Rectangle(100, 100, 100, 100);
//        rectangle.setFill(Color.BLACK);

        root.getChildren().addAll(grid); // adds rectangle behind grid
//        StackPane.setAlignment(grid, Pos.CENTER);
        
        root.getChildren().add(token);
        root.getChildren().add(confirm);
        
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
