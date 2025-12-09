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
		StackPane root = new StackPane();
//        root.setAlignment(Pos.CENTER);

//        Circle disc = new Circle(20, 100, 100);
//        disc.setFill(Color.YELLOW);
//        root.getChildren().add(disc);

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: transparent;"); // makes background transparent so can see shapes behind 
        grid.setAlignment(Pos.CENTER);
        grid.setPrefSize(600, 700); // does nothing?
        grid.setMaxSize(600, 700);
        
        Circle token = new Circle(30);
        token.setFill(Color.RED);
        token.setCenterX(0);
        token.setCenterY(100);
        token.relocate(0, 0);
//        token.setCenterX(50);
//        token.setCenterY(250);        

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Rectangle sq = new Rectangle(31, 31, 100, 100);
                Circle circ = new Circle(30);

                // align circle to middle of square
                circ.setCenterX(sq.getX() + sq.getWidth() / 2);
                circ.setCenterY(sq.getY() + sq.getWidth() / 2); 
                
                // takes area of circle away from middle of square
                Shape shape = Shape.subtract(sq, circ); 
                shape.setFill(Color.CORNFLOWERBLUE);

                Pane cell = new Pane(shape);
                
                // limits size of cell
                cell.setPrefSize(80, 80);
                cell.setMaxSize(80, 80);
               
                grid.add(cell, j, i); // adds cell to grid
            }
        }

//        Rectangle rectangle = new Rectangle(100, 100, 100, 100);
//        rectangle.setFill(Color.BLACK);

        root.getChildren().addAll(grid); // adds rectangle behind grid
//        StackPane.setAlignment(grid, Pos.CENTER);
        
        root.getChildren().add(token);
        
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
