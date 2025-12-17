import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//import java.util.Scanner;

public class GUIDriver extends Application {
	
	boolean turnOver = true;
//	Color tokenTwoColor = Color.YELLOW;
	Color tokenOneColor = Color.TRANSPARENT;
//	Color tokenTwoColor = Color.RED;
	Color tokenTwoColor = Color.TRANSPARENT;
	int chooseColorCount = 0;
	boolean playerWin = false; // true if a player won
	boolean canStart = false; // players must have chosen their token colors before the game can begin

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
        token.setFill(tokenOneColor);
        
        ////////////////
        ////////////////
        ////////////////
        ////////////////
        ////////////////
        ////////////////
        Button startGame = new Button("START GAME");
        startGame.setDisable(false);
        
        Shape[][] arrShapes = new Shape[6][7];
        Circle[][] arrCircles = new Circle[6][7];
        
//        startGame.setOnAction(e-> {
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
//        });
        
        
        
        Button confirm = new Button("CONFIRM");
        confirm.relocate(271, 630);
        
        Button nextPlayer = new Button("END TURN");
        nextPlayer.relocate(351, 630);
        
        
        
        
        scene.setOnKeyPressed(e-> {
        	if (canStart) {
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
        	}
//        	else {
//        		token.setCenterX(0);
//        	}
        });
        
        Label pressEndTurn = new Label("              Outside of grid.\nPress END TURN to continue");
        pressEndTurn.relocate(271, 660);
        pressEndTurn.setTextFill(Color.RED);
        pressEndTurn.setVisible(false);
        
        Font winningFont = new Font("Times New Roman", 60.0);
        
        Text winningLabel = new Text("PLAYER " + playingGrid.getTurnsNoModify() + " WON!");
        winningLabel.relocate(125, 350);
        winningLabel.setFont(winningFont);
        winningLabel.setFill(Color.GOLD);
        winningLabel.setStroke(Color.BLACK);
        winningLabel.setVisible(false);
        
        confirm.setOnAction(e-> {
        	if (canStart) {
	        	nextPlayer.setDisable(true);
	        	if (turnOver) { // prevents user from pressing button twice in a row
		        	int column = playingGrid.getColumnNum(token.getCenterX());
		        	if (column != -1) {
//			        	System.out.println("COLUMN " + column);
			        	double rowVal = playingGrid.addToken(column);
//			        	System.out.println("Check 1");
			        	int row = playingGrid.getCurrentRow();
//			        	System.out.println("Check 2");
//			        	System.out.println("Number you get: " + row);
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
	//		        			arrCircles[row][column].setFill(Color.RED);
			        			arrCircles[row][column].setFill(tokenOneColor);
	//		        			arrCircles[row][column].setFill(tokenTwoColor);
			        			arrCircles[row][column].setStroke(Color.BLACK);
			        		}
			        		if (playerColor == 2) {
	//		        			arrCircles[row][column].setFill(Color.YELLOW);
			        			arrCircles[row][column].setFill(tokenTwoColor);
	//		        			arrCircles[row][column].setFill(tokenOneColor);
			        			arrCircles[row][column].setStroke(Color.BLACK);
			        		}
			        		nextPlayer.setDisable(false);
			        	});
			        	
			        	transition.play();
			        	playingGrid.print();
		        	
		        	}
		        	
		        	else {
		        		nextPlayer.setDisable(false);
		        		pressEndTurn.setVisible(true);
		        	}
		        	
		        	playerWin = playingGrid.checkForWin();
		        	if (playerWin) {
		        		winningLabel.setVisible(true);
		        	}
		        	
	        	}
	        	
	        	turnOver = false;
        	}
        	
        });
        
        nextPlayer.setOnAction(e-> {
        	if (canStart) {
	        	pressEndTurn.setVisible(false);
	        	confirm.setDisable(true);
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
	//    			token.setFill(tokenOneColor);
	        		token.setFill(tokenTwoColor);
	    		}
	    		else {
	//    			token.setFill(tokenTwoColor);
	    			token.setFill(tokenOneColor);
	    		}
	        	
	        	transition.setOnFinished(g-> {
	        		confirm.setDisable(false);
	        	});
	        	
	        	turnOver = true;
        	}
        });
        
        
        Button tokenRed = new Button("Red"); // Color.RED
        tokenRed.relocate(100, 50);
        Button tokenYellow = new Button("Yellow"); // Color.YELLOW
        tokenYellow.relocate(170, 50);
        Button tokenOrange = new Button("Orange"); // Color.ORANGE
        tokenOrange.relocate(260, 50);
        Button tokenSalmon = new Button("Coral"); // Color.DARKSALMON
        tokenSalmon.relocate(350, 50);
        Button tokenPink = new Button("Light pink"); // Color.LAVENDERBLUSH
        tokenPink.relocate(430, 50);
        Button tokenCyan = new Button("Turquoise"); // Color.MEDIUMSPRINGGREEN
        tokenCyan.relocate(530, 50);
        
        tokenRed.setOnAction(e-> {
        	chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.RED;
				token.setFill(Color.RED);
			}
			else {
				tokenOneColor = Color.RED;
			}
			tokenRed.setDisable(true);
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
			}
        });
        
        tokenYellow.setOnAction(e-> {
        	chooseColorCount++;
        	int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.YELLOW;
				token.setFill(Color.YELLOW);
			}
			else {
				tokenOneColor = Color.YELLOW;
			}
			tokenYellow.setDisable(true);
			if (chooseColorCount == 2) {
				tokenRed.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
			}
        });
        
        tokenOrange.setOnAction(e-> {
        	chooseColorCount++;
        	int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.ORANGE;
				token.setFill(Color.ORANGE);
			}
			else {
				tokenOneColor = Color.ORANGE;
			}
			tokenOrange.setDisable(true);
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenRed.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
			}
        });
        
        tokenSalmon.setOnAction(e-> {
        	chooseColorCount++;
        	int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.DARKSALMON;
				token.setFill(Color.DARKSALMON);
			}
			else {
				tokenOneColor = Color.DARKSALMON;
			}
			tokenSalmon.setDisable(true);
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenRed.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
			}
        });
        
        tokenPink.setOnAction(e-> {
        	chooseColorCount++;
        	int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.HOTPINK;
				token.setFill(Color.HOTPINK);
			}
			else {
				tokenOneColor = Color.HOTPINK;
			}
			tokenPink.setDisable(true);
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenRed.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
			}
        });
        
        tokenCyan.setOnAction(e-> {
        	chooseColorCount++;
        	int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.MEDIUMSPRINGGREEN;
				token.setFill(Color.MEDIUMSPRINGGREEN);
			}
			else {
				tokenOneColor = Color.MEDIUMSPRINGGREEN;
			}
			tokenCyan.setDisable(true);
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenRed.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
			}
        });
        
        
        // Buttons to change the grid colors
        
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
        root.getChildren().add(pressEndTurn);
        
        root.getChildren().add(grey);
        root.getChildren().add(green);
        root.getChildren().add(blue);
        root.getChildren().add(purple);
        root.getChildren().add(orange);
        root.getChildren().add(pink);
        
        root.getChildren().add(tokenRed);
        root.getChildren().add(tokenYellow);
        root.getChildren().add(tokenOrange);
        root.getChildren().add(tokenSalmon);
        root.getChildren().add(tokenPink);
        root.getChildren().add(tokenCyan);
        
        root.getChildren().add(startGame);
        
        root.getChildren().add(winningLabel);
        
        
        stage.setScene(scene);
        stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
