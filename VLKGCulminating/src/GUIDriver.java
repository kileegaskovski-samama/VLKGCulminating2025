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

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root, 700, 700);
		
		
		
		
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
        
        Circle token = new Circle(30);
        token.setFill(Color.RED);
//        token.setCenterX(0);
//        token.setCenterY(0);
//        token.relocate(0, 0);
//        token.setCenterX(351);
        token.setCenterX(591);
        token.setCenterY(0);
        System.out.println(token.getCenterX());
        
        
        Button confirm = new Button("1 ");
        confirm.relocate(35, 235);
        
        scene.setOnKeyPressed(e-> {
        	double move = 80.0;
        	
        	if (e.getCode() == KeyCode.A) {
        		System.out.println(token.getCenterX());
        		token.setCenterX(token.getCenterX() - move);
        		System.out.println(token.getCenterX());
        	}
        	if (e.getCode() == KeyCode.D) {
        		System.out.println(token.getCenterX());
        		token.setCenterX(token.getCenterX() + move);
        		System.out.println(token.getCenterX());
        	}
        });
        
        Rectangle plat = new Rectangle(275, 525, 80, 15); 
        root.getChildren().add(plat);
//        Scene scene = new Scene(root, 600, 600);
        

//        scene.setOnKeyPressed(e-> {
//            double move = 30.0;
//            
//            System.out.println("Key pressed: " + e.getCode());
//
//            if (e.getCode() == KeyCode.A) {
//                plat.setX(plat.getX() - move);
////            	Path path = new Path();
////            	double x1 = token.getCenterX();
////            	double y1 = token.getCenterY();
////            	path.getElements().add(new MoveTo(x1, y1));
////            	path.getElements().add(new LineTo(x1, 151));
////            	
////            	PathTransition transition = new PathTransition();
////            	transition.setNode(token);
////            	transition.setDuration(Duration.seconds(2));
////            	transition.setPath(path);
////            	transition.setCycleCount(1);
////            	transition.play();
//            }
//
//            if (e.getCode() == KeyCode.D) {
//                plat.setX(plat.getX() + move);
//            }
//
//
//        });
        
        confirm.setOnAction(e-> {
//        	System.out.println(token.getCenterX());
//        	int x1 = playingGrid.getColumnNum(token.getCenterX());
//        	System.out.println(x1);
//        	int y1 = playingGrid.getRowNum(token.getCenterY());
//        	playingGrid.addToken();
//        	System.out.println("Button thing is working.");
        	
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
        
        
        stage.setScene(scene);
        stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
