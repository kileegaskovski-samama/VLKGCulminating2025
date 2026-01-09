import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
* Graphical user interface for the connect four
*/
public class GUIDriver extends Application {
	
	boolean turnOver = true;
	Color tokenOneColor = Color.TRANSPARENT;
	Color tokenTwoColor = Color.TRANSPARENT;
	int chooseColorCount = 0;
	boolean playerWin = false; // true if a player won
	boolean canStart = false; // players must have chosen their token colors before the game can begin
	
	boolean testtest = false;
	
	String username1 = "";
	String username2 = "";
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// start screen
		Pane startRoot = new Pane();
		Label title = new Label("Welcome to Connect 4!");
		title.setFont(Font.font(40));
		title.setStyle("-fx-font-weight: bold;");
		title.layoutXProperty().bind(startRoot.widthProperty().subtract(title.widthProperty()).divide(2));
		title.setLayoutY(40);
		title.setVisible(true);
		
		Font btnFont = Font.font("Tahoma", FontWeight.BOLD, 20);
		
		Button tutorial = new Button("Tutorial");
		tutorial.setFont(btnFont);
		tutorial.layoutXProperty().bind(startRoot.widthProperty().subtract(tutorial.widthProperty()).divide(2));
		tutorial.setLayoutY(300);
		tutorial.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		
		Button start = new Button("Start New Game");
		start.setFont(btnFont);
		start.layoutXProperty().bind(startRoot.widthProperty().subtract(start.widthProperty()).divide(2));
		start.setLayoutY(350);
		start.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		
		// create background grid image
		GridPane startGrid = new GridPane();
		startGrid.setStyle("-fx-background-color: transparent;"); // makes background transparent so can see shapes behind
		startGrid.relocate(30, 70);
		startGrid.setPrefSize(600, 700); // does nothing?
		startGrid.setMaxSize(600, 700);
	    
		Grid startPlayingGrid = new Grid();
		Shape[][] arrStartShapes = new Shape[6][7];
		Circle[][] arrStartCircles = new Circle[6][7];
		
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
               arrStartShapes[r][c] = shape;
               Pane cell = new Pane(shape);
              
               // limits size of cell
               cell.setPrefSize(80, 80);
               cell.setMaxSize(80, 80);
             
               startGrid.add(cell, c, r); // adds cell to grid
              
              
               Circle circle = new Circle(30);
               double rowLocation = startPlayingGrid.getRowVal(r);
               circle.setCenterY(rowLocation);
               double columnLocation = startPlayingGrid.getColumnVal(c);
               circle.setCenterX(columnLocation);
               circle.setFill(Color.TRANSPARENT);
               arrStartCircles[r][c] = circle;
               startRoot.getChildren().add(circle);
           }
       }
		
		// fill background grid with tokens
		
		// row 5 tokens
		arrStartCircles[5][1].setFill(Color.RED);
		arrStartCircles[5][2].setFill(Color.YELLOW);
		arrStartCircles[5][3].setFill(Color.RED);
		arrStartCircles[5][4].setFill(Color.RED);
		arrStartCircles[5][5].setFill(Color.YELLOW);
		arrStartCircles[5][6].setFill(Color.RED);
		
		// row 4 tokens
		arrStartCircles[4][1].setFill(Color.YELLOW);
		arrStartCircles[4][2].setFill(Color.RED);
		arrStartCircles[4][3].setFill(Color.RED);
		arrStartCircles[4][4].setFill(Color.YELLOW);
		arrStartCircles[4][5].setFill(Color.RED);
		
		// row 3 tokens
		arrStartCircles[3][2].setFill(Color.RED);
		arrStartCircles[3][3].setFill(Color.YELLOW);
		arrStartCircles[3][4].setFill(Color.YELLOW);
		
		// row 2 tokens
		arrStartCircles[2][2].setFill(Color.YELLOW);
		arrStartCircles[2][3].setFill(Color.YELLOW);
		arrStartCircles[2][4].setFill(Color.YELLOW);
		
		// row 1 token
		arrStartCircles[1][4].setFill(Color.YELLOW);
		Rectangle layer = new Rectangle(0, 0, 700, 700);
		layer.setFill(Color.WHITE);
		layer.setOpacity(0.6);
		
		startRoot.getChildren().addAll(startGrid, layer, title, start, tutorial);
		Scene startScene = new Scene(startRoot, 700, 700);
		stage.setScene(startScene);
		stage.show();
			  
		
		// Pane for the instructions screen
		Pane instructionsScreen = new Pane();
		Scene instructions = new Scene(instructionsScreen, 700, 700);
				
		// Pane for feedback
		Pane feedbackScreen = new Pane();
		Scene feedbackScene = new Scene(feedbackScreen, 700, 700);
		
		// Pane for feedback
		Pane viewRRScreen = new Pane();
		Scene viewRRScene = new Scene(viewRRScreen, 700, 700);
		
		Font font2 = Font.font("Regular", FontWeight.BOLD, 36);
		
		Button addReview = new Button("Add a review!");
		addReview.setTextFill(Color.DARKBLUE);
		addReview.relocate(230, 50);
		addReview.setVisible(false);
		
		// Pane for the main playing screen
		Pane root = new Pane();
		Scene scene = new Scene(root, 700, 700);
		
		start.setOnAction(e-> { // goes to game
	       	stage.setScene(scene);
	       	stage.show();
	       });
		
		Label label1 = new Label("To start, Player 1 chooses their token color.");
		label1.relocate(100, 100);
		label1.setTextFill(Color.DARKBLUE);
		
		Label label2 = new Label("Next, Player 2 would choose their token color, and game play would begin.");
		label2.relocate(100, 150);
		label2.setTextFill(Color.DARKBLUE);
		label2.setVisible(false);
		
		Label label3 = new Label("Use the keys \"A\" and \"D\" to move your token.");
		label3.relocate(100, 250);
		label3.setTextFill(Color.DARKBLUE);
		label3.setVisible(false);
		
		Label label4 = new Label("Now press \"CONFIRM\" to place your token in the spot you want on the grid.");
		label4.relocate(100, 350);
		label4.setTextFill(Color.DARKBLUE);
		label4.setVisible(false);
		
		Label label5 = new Label("Press \"END TURN\" for the next player to be able to place their token.");
		label5.relocate(100, 400);
		label5.setTextFill(Color.DARKBLUE);
		label5.setVisible(false);
		
		Circle introToken = new Circle(31);
		introToken.setFill(Color.TRANSPARENT);
		introToken.relocate(450, 30);
		
		Font font = Font.font("Courier New", FontWeight.BOLD, 36);
		
		Button introRed = new Button("Red");
		introRed.relocate(100, 50);
		Button introYellow = new Button("Yellow");
		introYellow.relocate(170, 50);
		Button introOrange = new Button("Orange");
		introOrange.relocate(260, 50);
		Button introSalmon = new Button("Coral");
		introSalmon.relocate(350, 50);
		
		Button gotIt = new Button("Got it!");
		gotIt.relocate(100, 200);
		gotIt.setVisible(false);
		
		Button testedIt = new Button("I tested it!");
		testedIt.relocate(100, 300);
		testedIt.setVisible(false);
		
		Button introConfirm = new Button("CONFIRM");
		introConfirm.relocate(271, 630);
		introConfirm.setVisible(false);
		
		Button introNextPlayer = new Button("END TURN");
		introNextPlayer.relocate(351, 630);
		introNextPlayer.setVisible(false);
		
		Button play = new Button("START GAME");
		play.setTextFill(Color.DARKBLUE);
		play.setFont(font);
		play.setVisible(false);
		play.relocate(230, 450);
		
		Line arrowOne = new Line();
		arrowOne.setStartX(280);
		arrowOne.setStartY(375);
		arrowOne.setEndX(310);
		arrowOne.setEndY(620);
		arrowOne.setStroke(Color.RED);
		arrowOne.setVisible(false);
		
		Line arrowTwo = new Line();
		arrowTwo.setStartX(280);
		arrowTwo.setStartY(425);
		arrowTwo.setEndX(390);
		arrowTwo.setEndY(620);
		arrowTwo.setStroke(Color.RED);
		arrowTwo.setVisible(false);
		
		Text vOne = new Text("V");
		vOne.relocate(305.5, 610);
		vOne.setFill(Color.RED);
		vOne.setRotate(-6);
		vOne.setVisible(false);
		
		Text vTwo = new Text("V");
		vTwo.relocate(384.5, 610);
		vTwo.setFill(Color.RED);
		vTwo.setRotate(-30);
		vTwo.setVisible(false);
		
		
		instructionsScreen.getChildren().add(label1);
		instructionsScreen.getChildren().add(label2);
		instructionsScreen.getChildren().add(label3);
		instructionsScreen.getChildren().add(label4);
		instructionsScreen.getChildren().add(label5);
		
		instructionsScreen.getChildren().add(arrowOne);
		instructionsScreen.getChildren().add(arrowTwo);
		instructionsScreen.getChildren().add(vOne);
		instructionsScreen.getChildren().add(vTwo);
		
		instructionsScreen.getChildren().add(play);
		instructionsScreen.getChildren().add(gotIt);
		instructionsScreen.getChildren().add(testedIt);
		instructionsScreen.getChildren().add(introConfirm);
		instructionsScreen.getChildren().add(introNextPlayer);
		
		instructionsScreen.getChildren().add(introRed);
		instructionsScreen.getChildren().add(introYellow);
		instructionsScreen.getChildren().add(introOrange);
		instructionsScreen.getChildren().add(introSalmon);
		
		instructionsScreen.getChildren().add(introToken);
		
		tutorial.setOnAction(e-> { // goes to tutorial when pressed
			stage.setScene(instructions);
       		stage.show();
       	});		
		
        introRed.setOnAction(e-> {
        	introToken.setFill(Color.RED);
        	introToken.setStroke(Color.BLACK);
        	introRed.setDisable(true);
        	introYellow.setDisable(true);
        	introOrange.setDisable(true);
        	introSalmon.setDisable(true);
        	label1.setTextFill(Color.GREY);
        	gotIt.setVisible(true);
        	label2.setVisible(true);
        });
        
        introYellow.setOnAction(e-> {
        	introToken.setFill(Color.YELLOW);
        	introToken.setStroke(Color.BLACK);
        	introRed.setDisable(true);
        	introYellow.setDisable(true);
        	introOrange.setDisable(true);
        	introSalmon.setDisable(true);
        	label1.setTextFill(Color.GREY);
        	gotIt.setVisible(true);
        	label2.setVisible(true);
        });
        
        introOrange.setOnAction(e-> {
        	introToken.setFill(Color.ORANGE);
        	introToken.setStroke(Color.BLACK);
        	introRed.setDisable(true);
        	introYellow.setDisable(true);
        	introOrange.setDisable(true);
        	introSalmon.setDisable(true);
        	label1.setTextFill(Color.GREY);
        	gotIt.setVisible(true);
        	label2.setVisible(true);
        });
        
        introSalmon.setOnAction(e-> {
        	introToken.setFill(Color.SALMON);
        	introToken.setStroke(Color.BLACK);
        	introRed.setDisable(true);
        	introYellow.setDisable(true);
        	introOrange.setDisable(true);
        	introSalmon.setDisable(true);
        	label1.setTextFill(Color.GREY);
        	gotIt.setVisible(true);
        	label2.setVisible(true);
        });
        
        gotIt.setOnAction(e-> {
        	label2.setTextFill(Color.GREY);
        	gotIt.setDisable(true);
        	testedIt.setVisible(true);
        	label3.setVisible(true);
        });
        
        testedIt.setOnAction(e-> {
        	label3.setTextFill(Color.GREY);
        	testedIt.setDisable(true);
        	label4.setVisible(true);
        	arrowOne.setVisible(true);
        	vOne.setVisible(true);
        	introConfirm.setVisible(true);
        });
        
        introConfirm.setOnAction(e-> {
        	arrowOne.setVisible(false);
        	vOne.setVisible(false);
        	arrowTwo.setVisible(true);
        	vTwo.setVisible(true);
        	introNextPlayer.setVisible(true);
        	label4.setTextFill(Color.GREY);
        	label5.setVisible(true);
        });
        
        introNextPlayer.setOnAction(e-> {
        	arrowTwo.setVisible(false);
        	vTwo.setVisible(false);
        	play.setVisible(true);
        	label5.setTextFill(Color.GREY);
        });
        
        
        instructions.setOnKeyPressed(e-> {
        	double move = 80.0;
	        	introToken.setCenterY(0);
	        	
	        	if (e.getCode() == KeyCode.A) {
	        		introToken.setCenterX(introToken.getCenterX() - move);
	        	}
	        	if (e.getCode() == KeyCode.D) {
	        		introToken.setCenterX(introToken.getCenterX() + move);
	        	}
        });
		
		
     // screen in between instructions/start game and start new game/start game
        
        Pane settingsRoot = new Pane();
      
        // enter usernames
        Label usernamesTitle = new Label("Enter your names: ");
        usernamesTitle.setFont(Font.font(40));
 		usernamesTitle.setStyle("-fx-font-weight: bold;");
 		usernamesTitle.setLayoutX(20);
 		usernamesTitle.setLayoutY(20);
       
        Label p1 = new Label("Player 1: ");
        Label p2 = new Label("Player 2: ");
       
        p1.setTextFill(Color.DARKBLUE);
        p1.setLayoutX(30);
        p1.setLayoutY(100);
        p1.setFont(Font.font(25));
       
        p2.setTextFill(Color.DARKBLUE);
        p2.setLayoutX(30);
        p2.setLayoutY(200);
        p2.setFont(Font.font(25));
       
        TextField p1Name = new TextField();
        p1Name.setPrefWidth(100);
        p1Name.setPrefHeight(30);
        p1Name.setLayoutX(150);
        p1Name.setLayoutY(100);
       
        TextField p2Name = new TextField();
        p2Name.setPrefWidth(100);
        p2Name.setPrefHeight(30);
        p2Name.setLayoutX(150);
        p2Name.setLayoutY(200);
       
        Button next = new Button("Next");
       
        Label nameErr = new Label("");
        nameErr.setFont(Font.font(15));
        nameErr.setTextFill(Color.RED);
        nameErr.setLayoutY(250);
   
       
        next.setLayoutY(300);
        next.layoutXProperty().bind(settingsRoot.widthProperty().subtract(next.widthProperty()).divide(2));
        next.setFont(btnFont);
       
        settingsRoot.getChildren().addAll(usernamesTitle, p1, p2, next, p1Name, p2Name, nameErr);
       
        // choose token colours
  
        // creates colour options buttons
        Button tokenRed = new Button("Red"); // Color.RED
        tokenRed.relocate(70, 500);
        Button tokenYellow = new Button("Yellow"); // Color.YELLOW
        tokenYellow.relocate(160, 500);
        Button tokenOrange = new Button("Orange"); // Color.ORANGE
        tokenOrange.relocate(260, 500);
        Button tokenSalmon = new Button("Coral"); // Color.DARKSALMON
        tokenSalmon.relocate(365, 500);
        Button tokenPink = new Button("Light pink"); // Color.HOTPINK
        tokenPink.relocate(464, 500);
        Button tokenCyan = new Button("Turquoise"); // Color.MEDIUMSPRINGGREEN
        tokenCyan.relocate(560, 500);
       
        // invisible until next is pressed
        tokenRed.setVisible(false);
        tokenYellow.setVisible(false);
        tokenOrange.setVisible(false);
        tokenSalmon.setVisible(false);
        tokenPink.setVisible(false);
        tokenCyan.setVisible(false);
       
        Label chooseColour = new Label();
        chooseColour.setFont(Font.font(30));
 		chooseColour.setStyle("-fx-font-weight: bold;");
        chooseColour.setLayoutX(20);
        chooseColour.setLayoutY(350);
       
        settingsRoot.getChildren().add(chooseColour);
       
        next.setOnAction(e-> { // goes to choosing token colours
        	nameErr.setText("");
        	
        	username1 = p1Name.getText().trim();
 	       	username2 = p2Name.getText().trim();
 	       	
 	       	// checks if name is valid (between 1 and 10 characters)
 	       	if (username1.length() < 1 || username2.length() < 1 || username1.length() > 15 || username1.length() > 15) {
 	       		nameErr.setText("Username must be between 1 and 15 characters");
 	       		nameErr.layoutXProperty().bind(settingsRoot.widthProperty().subtract(nameErr.widthProperty()).divide(2));
 	       		
 	       		
 	       	} else {
 	       		next.setVisible(false);
 	       		
 	       		// choose colours
 	       		chooseColour.setText(username1 + ", please choose your token colour:");
 	  
 	       		
 	            // example tokens
 	            Circle red = new Circle(90, 450, 31, Color.RED);
 	            red.setStroke(Color.BLACK);
 	            Circle yellow = new Circle(191, 450, 31, Color.YELLOW);
 	            yellow.setStroke(Color.BLACK);
 	            Circle orange = new Circle(292, 450, 31, Color.ORANGE);
 	            orange.setStroke(Color.BLACK);
 	            Circle salmon = new Circle(393, 450, 31, Color.DARKSALMON);
 	            salmon.setStroke(Color.BLACK);
 	            Circle pink = new Circle(494, 450, 31, Color.HOTPINK);
 	            pink.setStroke(Color.BLACK);
 	            Circle cyan = new Circle(595, 450, 31, Color.MEDIUMSPRINGGREEN);
 	            cyan.setStroke(Color.BLACK);
 	           
 	            // set buttons to visible
 	            tokenRed.setVisible(true);
 	            tokenYellow.setVisible(true);
 	            tokenOrange.setVisible(true);
 	            tokenSalmon.setVisible(true);
 	            tokenPink.setVisible(true);
 	            tokenCyan.setVisible(true);
 	           
 	            // center buttons to circles
 	            tokenRed.layoutXProperty().bind(red.centerXProperty());
 	            tokenRed.translateXProperty().bind(tokenRed.widthProperty().divide(-2));
 	            tokenRed.setLayoutY(500);
 	           
 	            tokenYellow.layoutXProperty().bind(yellow.centerXProperty());
 	            tokenYellow.translateXProperty().bind(tokenYellow.widthProperty().divide(-2));
 	            tokenYellow.setLayoutY(500);
 	          
 	            tokenOrange.layoutXProperty().bind(orange.centerXProperty());
 	            tokenOrange.translateXProperty().bind(tokenOrange.widthProperty().divide(-2));
 	            tokenOrange.setLayoutY(500);
 	           
 	            tokenSalmon.layoutXProperty().bind(salmon.centerXProperty());
 	            tokenSalmon.translateXProperty().bind(tokenSalmon.widthProperty().divide(-2));
 	            tokenSalmon.setLayoutY(500);
 	           
 	            tokenPink.layoutXProperty().bind(pink.centerXProperty());
 	            tokenPink.translateXProperty().bind(tokenPink.widthProperty().divide(-2));
 	            tokenPink.setLayoutY(500);
 	           
 	            tokenCyan.layoutXProperty().bind(cyan.centerXProperty());
 	            tokenCyan.translateXProperty().bind(tokenCyan.widthProperty().divide(-2));
 	            tokenCyan.setLayoutY(500);
 	            settingsRoot.getChildren().addAll(red, yellow, orange, salmon, pink, cyan);
 	       	}
 	      
 	       });
       
        Scene settingsScene = new Scene(settingsRoot, 700, 700);
    
        		
        start.setOnAction(e-> { // goes to settings from start screen
 	       	stage.setScene(settingsScene);
 	       	stage.show();
 	       });
        
        
		////////////////////////////////////////
		// Makes the main screen appear
		play.setOnAction(e-> {
			stage.setScene(settingsScene);
		});		
		
		////////////////////////////////////////
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
//        });
        
        
        
        Button confirm = new Button("CONFIRM");
        confirm.layoutXProperty().bind(scene.widthProperty().subtract(confirm.widthProperty()).divide(2));
        confirm.setLayoutY(630);
        
        Button nextPlayer = new Button("CONTINUE");
        nextPlayer.layoutXProperty().bind(scene.widthProperty().subtract(nextPlayer.widthProperty()).divide(2));
        nextPlayer.setLayoutY(630);
        nextPlayer.setVisible(false);
        
        
        
        
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
	        	
	        	if (e.getCode() == KeyCode.S) {
	        		confirm.fire();
	        	}
        	}
        });
        
        Label pressEndTurn = new Label("              Outside of grid.");
        pressEndTurn.relocate(271, 660);
        pressEndTurn.setTextFill(Color.RED);
        pressEndTurn.setVisible(false);
        
        // Creates winner message
        Font winningFont = new Font("Times New Roman", 60.0);
        
        Text winningLabel = new Text("");
        winningLabel.setLayoutY(84);
        winningLabel.setFont(winningFont);
        winningLabel.setStroke(Color.BLACK);
        winningLabel.setVisible(false);
        
        
        
        
        confirm.setOnAction(e-> {
        	if (canStart) {
        		confirm.setDisable(true);
        		if (!testtest) {
	        	if (turnOver) { // prevents user from pressing button twice in a row
		        	int column = playingGrid.getColumnNum(token.getCenterX());
		        	if (column != -1) {
			        	double rowVal = playingGrid.addToken(column);
			        	int row = playingGrid.getCurrentRow();
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
			        			arrCircles[row][column].setFill(tokenOneColor);
			        			arrCircles[row][column].setStroke(Color.BLACK);
			        		}
			        		if (playerColor == 2) {
			        			arrCircles[row][column].setFill(tokenTwoColor);
			        			arrCircles[row][column].setStroke(Color.BLACK);
			        		}
			        		


			        		playerWin = playingGrid.checkForWin();
			        		
				        	if (playerWin) {
				        		String winnerName = "";
				        		int winner = 0;
				        		
				        		confirm.setDisable(true);
				        		nextPlayer.setDisable(true);
				        		token.setVisible(false);
				        		testtest = true;
				        		
				        		// sets winner message to winning player's user name and token color
				        		if (playingGrid.getTurnsNoModify() == 2) {
				        			winnerName = username1;
				        			winningLabel.setFill(tokenTwoColor);
				        			winner = 1;
				        			
				        		} else {
				        			winnerName = username2;
				        			winningLabel.setFill(tokenOneColor);
				        			winner = 2;
				        			
				        		}
				        		
				        		winningLabel.setText(winnerName + " won!");
				        		winningLabel.translateXProperty().bind(root.widthProperty().subtract(winningLabel.getBoundsInLocal().getWidth()).divide(2));
				        		winningLabel.setVisible(true);
				        		
				        		for (int i = 0; i < playingGrid.rowValues.size(); i++) {
				        			int rIndex = playingGrid.rowValues.get(i);
				        			int cIndex = playingGrid.colValues.get(i);
				        			
				        			if (winner == 1) {
				        				FillTransition changeColour = new FillTransition(Duration.millis(2000), arrCircles[rIndex][cIndex], tokenTwoColor, Color.WHITE);
					        			changeColour.setCycleCount(50);
					        			changeColour.setAutoReverse(true);
					        			
					        			changeColour.play();
				        			
				        			} else {
				        				FillTransition changeColour = new FillTransition(Duration.millis(2000), arrCircles[rIndex][cIndex], tokenOneColor, Color.WHITE);
				        				changeColour.setCycleCount(50);
					        			changeColour.setAutoReverse(true);
					        			
					        			changeColour.play();
				        			}
				        			
				        			
				        		
				        		}				        		
				        		addReview.setVisible(true);
				        	}
			        		
				        	// automatically presses end turn button
				        	Platform.runLater(() -> { // stops token from flickering
				        	    nextPlayer.fire();
				        	});

			        		
			        		nextPlayer.setDisable(false);
			        	});
			        	
			        	transition.play();
			        	playingGrid.print();
		        	
		        	}
		        	
		        	else {
		        		confirm.setDisable(true);
		        		nextPlayer.setVisible(true);
		        		nextPlayer.setDisable(false);
		        		pressEndTurn.setVisible(true);
		        	}
		        			        	 
	        	}
	        	turnOver = false;
        		}
        		else {
        			confirm.setDisable(true);
        		}
        	}
        	
        });
        
        nextPlayer.setOnAction(e-> {
        	if (canStart) {
        			nextPlayer.setVisible(false);
		        	pressEndTurn.setVisible(false);
		        	confirm.setDisable(true);
		        	confirm.setVisible(true);
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
		        		token.setFill(tokenTwoColor);
		    		}
		    		else {
		    			token.setFill(tokenOneColor);
		    		}
		        	
		        	transition.setOnFinished(g-> {
		        		confirm.setDisable(false);
		        	});
		        	
        		}
        	turnOver = true;
        });
                
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
			
			if (chooseColorCount == 1) {
				chooseColour.setText(username2 + ", please choose your token colour");
			}
			
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
				
				stage.setScene(scene);
				stage.show();
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
			
			if (chooseColorCount == 1) {
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			
			if (chooseColorCount == 2) {
				tokenRed.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
				stage.setScene(scene);
				stage.show();
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
			
			if (chooseColorCount == 1) {
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenRed.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
				stage.setScene(scene);
				stage.show();
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
			
			if (chooseColorCount == 1) {
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenRed.setDisable(true);
				tokenPink.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
				stage.setScene(scene);
				stage.show();
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
			
			if (chooseColorCount == 1) {
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenRed.setDisable(true);
				tokenCyan.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
				stage.setScene(scene);
				stage.show();
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
			
			if (chooseColorCount == 1) {
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) {
				tokenYellow.setDisable(true);
				tokenOrange.setDisable(true);
				tokenSalmon.setDisable(true);
				tokenPink.setDisable(true);
				tokenRed.setDisable(true);
				playingGrid.subtractTurn();
				playingGrid.subtractTurn();
				canStart = true;
				stage.setScene(scene);
				stage.show();
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
        
        // If the game is over, the player can add a review/rating
        addReview.setOnAction(e-> {
        	stage.setScene(feedbackScene);
        });
                
        
        RatingCollection rc = new RatingCollection();
        
        Text addReviewMessage = new Text("Add a review (1-45 characters, inclusive):");
        addReviewMessage.relocate(280, 280);
        TextField review = new TextField();
        review.relocate(280, 300);
        Button addRev = new Button("ADD");
        addRev.relocate(450, 300);
        
        Text notValidReview = new Text("Review must be between\n1-45 characters (inclusive)");
        notValidReview.setVisible(false);
        notValidReview.relocate(500, 300);
        notValidReview.setFill(Color.RED);
        
        Text addRatingMessage = new Text("Add a rating (1 - 5, inclusive):");
        addRatingMessage.relocate(280, 340);
        TextField rating = new TextField();
        rating.relocate(280, 360);
        Button addRating = new Button("ADD");
        addRating.relocate(450, 360);
        
        Text notValidRating = new Text("Rating must be between\n1-5 (inclusive)");
        notValidRating.setVisible(false);
        notValidRating.relocate(500, 360);
        notValidRating.setFill(Color.RED);
                
        Button viewRatings = new Button("VIEW RATINGS");
        viewRatings.relocate(313, 430);
        
        feedbackScreen.getChildren().add(review);
        feedbackScreen.getChildren().add(rating);
        
        feedbackScreen.getChildren().add(addReviewMessage);
        feedbackScreen.getChildren().add(addRatingMessage);
        
        feedbackScreen.getChildren().add(addRev);
        feedbackScreen.getChildren().add(addRating);
        feedbackScreen.getChildren().add(viewRatings);
        feedbackScreen.getChildren().addAll(notValidRating, notValidReview);
        
        addRev.setOnAction(e-> {
        	String tempReview = review.getText();
        	        	
        	try {
        		if (tempReview.length() > 0 && tempReview.length() <= 45) {
        			rc.addReviews(tempReview);
        		}
        		else {
        			notValidReview.setVisible(true);
        			Timeline hideMessage = new Timeline(new KeyFrame(Duration.seconds(4), g-> {
        				notValidReview.setVisible(false);
        			}));
        			hideMessage.play();
        		}
        	}
        	catch (Exception exception) {
        		System.out.println(exception.getMessage());
        	}
        	
        	review.setText("");
        });
        
        addRating.setOnAction(e-> {
        	int tempRating = Integer.parseInt(rating.getText());
        	try {
        		if (tempRating <= 5 && tempRating >= 1) {
        			rc.addRatings(tempRating);
        		}
        		else {
        			notValidRating.setVisible(true);
        			Timeline hideMessage = new Timeline(new KeyFrame(Duration.seconds(4), g-> {
        				notValidRating.setVisible(false);
        			}));
        			hideMessage.play();
        		}
        	}
        	catch (Exception exception) {
        		System.out.println(exception.getMessage());
        	}
        	
        	rating.setText("");
        });
        
        Font rrPageFont = Font.font("Regular", FontWeight.BOLD, 30);
        Font rrPageFontOne = Font.font("Regular", FontWeight.BOLD, 25);
        Font rrPageFontTwo = Font.font("Regular", 20);
        
        Text gameRatings = new Text("Game ratings");
        gameRatings.relocate(270, 50);
        gameRatings.setFont(rrPageFont);
        gameRatings.setFill(Color.DARKOLIVEGREEN);
        
        Text ratings = new Text("Average Rating:");
        ratings.relocate(100, 150);
        ratings.setFont(rrPageFontOne);
        ratings.setFill(Color.DARKGREEN);
        Text reviews = new Text("Reviews:");
        reviews.relocate(100, 280);
        reviews.setFont(rrPageFontOne);
        reviews.setFill(Color.DARKGREEN);
        Text latestRatings = new Text("No ratings yet"); // visible in viewRRScreen Pane
        latestRatings.setVisible(false);
        latestRatings.relocate(100, 180);
        latestRatings.setFont(rrPageFontTwo);
        Text latestReviews = new Text("No reviews yet"); // visible in viewRRScreen Pane
        latestReviews.setVisible(false);
        latestReviews.relocate(100, 310);
        latestReviews.setFont(rrPageFontTwo);
        
        Line line1 = new Line(5, 100, 695, 100);
        Line line2 = new Line(5, 230, 695, 230);
        Line line3 = new Line(5, 440, 695, 440);
        
        Button addReviewAgain = new Button("Add another rating");
        addReviewAgain.setFont(rrPageFontTwo);
        addReviewAgain.relocate(100, 490);
        
        Button done = new Button("Done?");
        done.setFont(rrPageFontTwo);
        done.relocate(100, 570);
        
        addReviewAgain.setOnAction(e-> {
        	stage.setScene(feedbackScene);
        });
        
        // In the feedbackScreen Pane
        viewRatings.setOnAction(e-> {
        	String labelMessage = rc.getRatings() + " / 5";
        	latestRatings.setText(labelMessage);
        	latestRatings.setVisible(true);
        	
        	labelMessage = "" + rc.getLatestReviews();
        	latestReviews.setText(labelMessage);
        	latestReviews.setVisible(true);
        	        	
        	stage.setScene(viewRRScene);
        });
        
        // In the viewRRScreen
        ///
        ///
        ///
        ///
        ///
        //
        
        viewRRScreen.getChildren().addAll(latestRatings, latestReviews, gameRatings);
        viewRRScreen.getChildren().addAll(ratings, reviews);
        viewRRScreen.getChildren().add(addReviewAgain);
        viewRRScreen.getChildren().add(done);
        viewRRScreen.getChildren().addAll(line1, line2, line3);
        


        root.getChildren().add(grid); // adds rectangle behind grid
                
        root.getChildren().add(token);
        root.getChildren().add(confirm);
        root.getChildren().add(nextPlayer);
        root.getChildren().add(pressEndTurn);
        
        root.getChildren().addAll(grey, green, blue, purple, orange, pink);
        
        settingsRoot.getChildren().addAll(tokenRed, tokenYellow, tokenOrange, tokenSalmon, tokenPink, tokenCyan);
                
        root.getChildren().add(winningLabel);
        
        root.getChildren().add(addReview);
        
        Label thanks = new Label("Thanks for playing Connect 4!");
		thanks.setFont(Font.font(40));
		thanks.setStyle("-fx-font-weight: bold;");
		thanks.layoutXProperty().bind(root.widthProperty().subtract(thanks.widthProperty()).divide(2));
		thanks.setLayoutY(300);
        
        done.setOnAction(e-> {
        	root.getChildren().removeAll(confirm, grey, green, blue, purple, orange, pink, addReview, winningLabel);
        	root.getChildren().addAll(layer, thanks);
        	stage.setScene(scene);
        	
		});
        
        stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
