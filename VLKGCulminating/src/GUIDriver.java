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

	// allows "Confirm" button to be pressed only when previous token is in place
	boolean turnOver = true;

	// Player token colors
	Color tokenOneColor = Color.TRANSPARENT;
	Color tokenTwoColor = Color.TRANSPARENT;
	int chooseColorCount = 0; // counts if both (2) players have chosen their token colors

	boolean playerWin = false; // true if a player has won

	boolean playerWinStopTokens = false; // prevents more tokens from being placed if a player has won

	// Player user names
	String username1 = "";
	String username2 = "";

	@Override
	public void start(Stage stage) throws Exception {
		/// All fonts
		// General fonts
		Font btnFont = Font.font("Tahoma", FontWeight.BOLD, 20);
		Font font = Font.font("Courier New", FontWeight.BOLD, 36);

		// Font for winning message
		Font winningFont = new Font("Times New Roman", 60.0);

		// review/rating page fonts
		Font rrPageFont = Font.font("Regular", FontWeight.BOLD, 30);
		Font rrPageFontOne = Font.font("Regular", FontWeight.BOLD, 25);
		Font rrPageFontTwo = Font.font("Regular", 20);

		/// start screen
		// start screen pane
		Pane startRoot = new Pane();
		Label title = new Label("Welcome to Connect 4!");
		title.setFont(Font.font(40));
		title.setStyle("-fx-font-weight: bold;");
		title.layoutXProperty().bind(startRoot.widthProperty().subtract(title.widthProperty()).divide(2));
		title.setLayoutY(40);
		title.setVisible(true);

		// "Tutorial" button
		Button tutorial = new Button("Tutorial");
		tutorial.setFont(btnFont);
		tutorial.layoutXProperty().bind(startRoot.widthProperty().subtract(tutorial.widthProperty()).divide(2));
		tutorial.setLayoutY(300);
		tutorial.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		// "Start New Game" button
		Button start = new Button("Start New Game");
		start.setFont(btnFont);
		start.layoutXProperty().bind(startRoot.widthProperty().subtract(start.widthProperty()).divide(2));
		start.setLayoutY(350);
		start.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		/// Background image of a Connect Four grid
		GridPane startGrid = new GridPane();
		startGrid.setStyle("-fx-background-color: transparent;"); // makes background transparent so you can see the
																	// shapes behind it
		startGrid.relocate(30, 70);
		startGrid.setMaxSize(600, 700);

		// Demo Connect Four grid in background
		Grid startPlayingGrid = new Grid();
		Shape[][] arrStartShapes = new Shape[6][7];
		Circle[][] arrStartCircles = new Circle[6][7];

		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				Rectangle sq = new Rectangle(31, 31, 100, 100);
				Circle circ = new Circle(30);

				// Aligns circle to middle of square
				circ.setCenterX(sq.getX() + sq.getWidth() / 2);
				circ.setCenterY(sq.getY() + sq.getWidth() / 2);

				// Takes area of the circle away from middle of square
				Shape shape = Shape.subtract(sq, circ);
				shape.setFill(Color.CORNFLOWERBLUE);
				arrStartShapes[r][c] = shape;
				Pane cell = new Pane(shape);

				// Limits size of cell
				cell.setPrefSize(80, 80);
				cell.setMaxSize(80, 80);

				startGrid.add(cell, c, r); // adds cell to grid

				// Temporarily transparent circles (colors them below)
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

		/// colors demo grid tokens
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

		// Semi-transparent layer on top of grid for start and end screen
		Rectangle layer = new Rectangle(0, 0, 700, 700);
		layer.setFill(Color.WHITE);
		layer.setOpacity(0.6);

		startRoot.getChildren().addAll(startGrid, layer, title, start, tutorial);
		Scene startScene = new Scene(startRoot, 700, 700);
		stage.setScene(startScene);
		stage.show();

		/// Panes
		// Pane for the instructions screen
		Pane instructionsScreen = new Pane();
		Scene instructions = new Scene(instructionsScreen, 700, 700);

		// Pane for feedback
		Pane feedbackScreen = new Pane();
		Scene feedbackScene = new Scene(feedbackScreen, 700, 700);

		// Pane for feedback
		Pane viewRRScreen = new Pane();
		Scene viewRRScene = new Scene(viewRRScreen, 700, 700);

		// Pane for the main playing screen
		Pane root = new Pane();
		Scene scene = new Scene(root, 700, 700);

		/// Starts game
		start.setOnAction(e -> {
			stage.setScene(scene);
			stage.show();
		});

		/// Button for adding a review when a player has won
		Button addReview = new Button("Add a review!");
		addReview.setTextFill(Color.DARKBLUE);
		addReview.relocate(300, 10);
		addReview.setVisible(false);

		/// Tutorial instructions
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

		Label label4 = new Label(
				"Now press \"CONFIRM\" to place your token in the spot you want on the grid\n(in the actual game, the \"S\" key works too)!");
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

		/// Tutorial buttons
		// Buttons to choose demo token color
		Button introRed = new Button("Red");
		introRed.relocate(100, 50);
		Button introYellow = new Button("Yellow");
		introYellow.relocate(170, 50);
		Button introOrange = new Button("Orange");
		introOrange.relocate(260, 50);
		Button introSalmon = new Button("Coral");
		introSalmon.relocate(350, 50);

		// Buttons to continue through instructions
		Button gotIt = new Button("Got it!");
		gotIt.relocate(100, 200);
		gotIt.setVisible(false);
		Button testedIt = new Button("I tested it!");
		testedIt.relocate(100, 300);
		testedIt.setVisible(false);
		Button introConfirm = new Button("CONFIRM");
		introConfirm.relocate(326, 630);
		introConfirm.setVisible(false);

		// Button to start game
		Button play = new Button("START GAME");
		play.setTextFill(Color.DARKBLUE);
		play.setFont(font);
		play.setVisible(false);
		play.relocate(230, 450);

		/// Tutorial arrow pointing to "Confirm" button
		// The line of the array
		Line arrowOne = new Line();
		arrowOne.setStartX(280);
		arrowOne.setStartY(385);
		arrowOne.setEndX(362);
		arrowOne.setEndY(620);
		arrowOne.setStroke(Color.RED);
		arrowOne.setVisible(false);
		// V-shape at the end of arrow
		Text vOne = new Text("V");
		vOne.relocate(357, 610);
		vOne.setFill(Color.RED);
		vOne.setRotate(-17);
		vOne.setVisible(false);

		/// Adding tutorial components to instructionsScreen
		// Instructions
		instructionsScreen.getChildren().add(label1);
		instructionsScreen.getChildren().add(label2);
		instructionsScreen.getChildren().add(label3);
		instructionsScreen.getChildren().add(label4);
		instructionsScreen.getChildren().add(label5);

		// Buttons
		instructionsScreen.getChildren().add(play);
		instructionsScreen.getChildren().add(gotIt);
		instructionsScreen.getChildren().add(testedIt);
		instructionsScreen.getChildren().add(introConfirm);

		// Demo token
		instructionsScreen.getChildren().add(introToken);

		// Token demo color buttons
		instructionsScreen.getChildren().add(introRed);
		instructionsScreen.getChildren().add(introYellow);
		instructionsScreen.getChildren().add(introOrange);
		instructionsScreen.getChildren().add(introSalmon);

		// Arrow
		instructionsScreen.getChildren().add(arrowOne);
		instructionsScreen.getChildren().add(vOne);

		// Goes to tutorial
		tutorial.setOnAction(e -> {
			stage.setScene(instructions);
			stage.show();
		});

		/// Buttons in tutorial to change demo token color
		// Red
		introRed.setOnAction(e -> {
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

		// Yellow
		introYellow.setOnAction(e -> {
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

		// Orange
		introOrange.setOnAction(e -> {
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

		// Salmon/pink
		introSalmon.setOnAction(e -> {
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

		/// Buttons to continue through tutorial instructions
		gotIt.setOnAction(e -> {
			label2.setTextFill(Color.GREY);
			gotIt.setDisable(true);
			testedIt.setVisible(true);
			label3.setVisible(true);
		});

		testedIt.setOnAction(e -> {
			label3.setTextFill(Color.GREY);
			testedIt.setDisable(true);
			label4.setVisible(true);
			arrowOne.setVisible(true);
			vOne.setVisible(true);
			introConfirm.setVisible(true);
		});

		introConfirm.setOnAction(e -> {
			arrowOne.setVisible(false);
			vOne.setVisible(false);
			label4.setTextFill(Color.GREY);
			play.setVisible(true);
		});

		// Allows the user to test moving the demo token with A and D keys
		instructions.setOnKeyPressed(e -> {
			double move = 80.0;
			introToken.setCenterY(0);

			// A key movie it left
			if (e.getCode() == KeyCode.A) {
				introToken.setCenterX(introToken.getCenterX() - move);
			}
			// D key moves it right
			if (e.getCode() == KeyCode.D) {
				introToken.setCenterX(introToken.getCenterX() + move);
			}
		});

		/// Settings screen where players choose their user names and token colors
		// Settings screen Pane
		Pane settingsRoot = new Pane();

		/// Enter user names
		Label usernamesTitle = new Label("Enter your names: ");
		usernamesTitle.setFont(Font.font(40));
		usernamesTitle.setStyle("-fx-font-weight: bold;");
		usernamesTitle.setLayoutX(20);
		usernamesTitle.setLayoutY(20);

		Label p1 = new Label("Player 1: ");
		p1.setTextFill(Color.DARKBLUE);
		p1.setLayoutX(30);
		p1.setLayoutY(100);
		p1.setFont(Font.font(25));

		Label p2 = new Label("Player 2: ");
		p2.setTextFill(Color.DARKBLUE);
		p2.setLayoutX(30);
		p2.setLayoutY(200);
		p2.setFont(Font.font(25));

		/// Text fields to enter user names
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

		// Button to continue to choosing token colors
		Button next = new Button("Next");
		next.setLayoutY(300);
		next.layoutXProperty().bind(settingsRoot.widthProperty().subtract(next.widthProperty()).divide(2));
		next.setFont(btnFont);

		// Error message is no user name was chosen
		Label nameErr = new Label("");
		nameErr.setFont(Font.font(15));
		nameErr.setTextFill(Color.RED);
		nameErr.setLayoutY(250);

		settingsRoot.getChildren().addAll(usernamesTitle, p1, p2, next, p1Name, p2Name, nameErr);

		/// Choose token colors
		// Color option buttons
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

		// Choose-color label
		Label chooseColour = new Label();
		chooseColour.setFont(Font.font(30));
		chooseColour.setStyle("-fx-font-weight: bold;");
		chooseColour.setLayoutX(20);
		chooseColour.setLayoutY(350);

		settingsRoot.getChildren().add(chooseColour);
		settingsRoot.getChildren().addAll(tokenRed, tokenYellow, tokenOrange, tokenSalmon, tokenPink, tokenCyan);

		// Goes to choosing token colors
		next.setOnAction(e -> {
			/// Checks if a valid user name was given
			// Sets error message to blank if a valid user name was given
			nameErr.setText("");

			// Gets user names
			username1 = p1Name.getText().trim();
			username2 = p2Name.getText().trim();

			// Checks if name is valid (between 1 and 15 characters)
			if (username1.length() < 1 || username2.length() < 1 || username1.length() > 15
					|| username1.length() > 15) {
				nameErr.setText("Username must be between 1 and 15 characters"); // Shows error message if an invalid
																					// user name was given
				nameErr.layoutXProperty()
						.bind(settingsRoot.widthProperty().subtract(nameErr.widthProperty()).divide(2));

			} else { // valid user name was give

				// "Next" button disappears
				next.setVisible(false);

				// Indicates players to choose their color
				chooseColour.setText(username1 + ", please choose your token colour:");

				// Example tokens
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

				// Set buttons to choose player colors to visible
				tokenRed.setVisible(true);
				tokenYellow.setVisible(true);
				tokenOrange.setVisible(true);
				tokenSalmon.setVisible(true);
				tokenPink.setVisible(true);
				tokenCyan.setVisible(true);

				/// Aligns tokens with their respective buttons (buttons to pick the color)
				// Red token
				tokenRed.layoutXProperty().bind(red.centerXProperty());
				tokenRed.translateXProperty().bind(tokenRed.widthProperty().divide(-2));
				tokenRed.setLayoutY(500);
				// Yellow token
				tokenYellow.layoutXProperty().bind(yellow.centerXProperty());
				tokenYellow.translateXProperty().bind(tokenYellow.widthProperty().divide(-2));
				tokenYellow.setLayoutY(500);
				// Orange token
				tokenOrange.layoutXProperty().bind(orange.centerXProperty());
				tokenOrange.translateXProperty().bind(tokenOrange.widthProperty().divide(-2));
				tokenOrange.setLayoutY(500);
				// Salmon token
				tokenSalmon.layoutXProperty().bind(salmon.centerXProperty());
				tokenSalmon.translateXProperty().bind(tokenSalmon.widthProperty().divide(-2));
				tokenSalmon.setLayoutY(500);
				// Pink token
				tokenPink.layoutXProperty().bind(pink.centerXProperty());
				tokenPink.translateXProperty().bind(tokenPink.widthProperty().divide(-2));
				tokenPink.setLayoutY(500);
				// Cyan token
				tokenCyan.layoutXProperty().bind(cyan.centerXProperty());
				tokenCyan.translateXProperty().bind(tokenCyan.widthProperty().divide(-2));
				tokenCyan.setLayoutY(500);
				settingsRoot.getChildren().addAll(red, yellow, orange, salmon, pink, cyan);
			}

		});

		// Scene for choosing user names and player colors
		Scene settingsScene = new Scene(settingsRoot, 700, 700);

		// Goes to settings from start screen
		start.setOnAction(e -> {
			stage.setScene(settingsScene);
			stage.show();
		});

		// Starts game play
		play.setOnAction(e -> {
			stage.setScene(settingsScene);
		});

		// Creates a Grid
		Grid playingGrid = new Grid();
		playingGrid.print();

		// GridPane that holds the playing grid on the screen
		GridPane grid = new GridPane();
		grid.relocate(30, 70);

		// The main token that the players move at the top
		Circle token = new Circle(30);
		token.setCenterX(31);
		token.setCenterY(50);

		/// Buttons for players to choose their colors in the settings screen (happens
		/// before game play)
		tokenRed.setOnAction(e -> {
			chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.RED;
				token.setFill(Color.RED);
			} else {
				tokenOneColor = Color.RED;
			}
			tokenRed.setDisable(true);

			if (chooseColorCount == 1) { // Only one player has chosen their token color
				chooseColour.setText(username2 + ", please choose your token colour");
			}

			if (chooseColorCount == 2) { // Both players have chosen their token colors
				// Begin game play
				stage.setScene(scene);
				stage.show();
			}
		});

		tokenYellow.setOnAction(e -> {
			chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.YELLOW;
				token.setFill(Color.YELLOW);
			} else {
				tokenOneColor = Color.YELLOW;
			}
			tokenYellow.setDisable(true);

			if (chooseColorCount == 1) { // Only one player has chosen their token color
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) { // Both players have chosen their token colors
				// Begin game play
				stage.setScene(scene);
				stage.show();
			}
		});

		tokenOrange.setOnAction(e -> {
			chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.ORANGE;
				token.setFill(Color.ORANGE);
			} else {
				tokenOneColor = Color.ORANGE;
			}
			tokenOrange.setDisable(true);

			if (chooseColorCount == 1) { // Only one player has chosen their token color
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) { // Both players have chosen their token colors
				// Begin game play
				stage.setScene(scene);
				stage.show();
			}
		});

		tokenSalmon.setOnAction(e -> {
			chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.DARKSALMON;
				token.setFill(Color.DARKSALMON);
			} else {
				tokenOneColor = Color.DARKSALMON;
			}
			tokenSalmon.setDisable(true);

			if (chooseColorCount == 1) { // Only one player has chosen their token color
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) { // Both players have chosen their token colors
				// Begin game play
				stage.setScene(scene);
				stage.show();
			}
		});

		tokenPink.setOnAction(e -> {
			chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.HOTPINK;
				token.setFill(Color.HOTPINK);
			} else {
				tokenOneColor = Color.HOTPINK;
			}
			tokenPink.setDisable(true);

			if (chooseColorCount == 1) { // Only one player has chosen their token color
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) { // Both players have chosen their token colors
				// Begin game play
				stage.setScene(scene);
				stage.show();
			}
		});

		tokenCyan.setOnAction(e -> {
			chooseColorCount++;
			int turn = playingGrid.getTurn();
			if (turn == 1) {
				tokenTwoColor = Color.MEDIUMSPRINGGREEN;
				token.setFill(Color.MEDIUMSPRINGGREEN);
			} else {
				tokenOneColor = Color.MEDIUMSPRINGGREEN;
			}
			tokenCyan.setDisable(true);

			if (chooseColorCount == 1) { // Only one player has chosen their token color
				chooseColour.setText(username2 + ", please choose your token colour:");
			}
			if (chooseColorCount == 2) { // Both players have chosen their token colors
				// Begin game play
				stage.setScene(scene);
				stage.show();
			}
		});

		// Creates an array of Shapes and Circles
		Shape[][] arrShapes = new Shape[6][7];
		Circle[][] arrCircles = new Circle[6][7];

		// Creates the empty grid on the screen
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				Rectangle sq = new Rectangle(31, 31, 100, 100);
				Circle circ = new Circle(30);

				// Aligns circle to middle of square
				circ.setCenterX(sq.getX() + sq.getWidth() / 2);
				circ.setCenterY(sq.getY() + sq.getWidth() / 2);

				// Takes the area of circle away from middle of square
				Shape shape = Shape.subtract(sq, circ);
				shape.setFill(Color.CORNFLOWERBLUE);
				arrShapes[r][c] = shape;

				// Playing grid on the screen
				Pane cell = new Pane(shape);

				// Limits the size of cell
				cell.setPrefSize(80, 80);
				cell.setMaxSize(80, 80);

				grid.add(cell, c, r); // adds cell to grid

				// Temporarily transparent circles (colors them when a player places a token in
				// their spot)
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

		// Button for player to drop their token
		Button confirm = new Button("CONFIRM");
		confirm.layoutXProperty().bind(scene.widthProperty().subtract(confirm.widthProperty()).divide(2));
		confirm.setLayoutY(630);

		// If players try to drop a token outside the grid, token is locked, and players
		// must press this button to continue
		Button outsideGridButton = new Button("CONTINUE");
		outsideGridButton.layoutXProperty()
				.bind(scene.widthProperty().subtract(outsideGridButton.widthProperty()).divide(2));
		outsideGridButton.setLayoutY(630);
		outsideGridButton.setVisible(false);

		// To move the token left, right, and drop the token
		scene.setOnKeyPressed(e -> {
			double move = 80.0;
			double temp = 50;

			token.setCenterY(50);

			// Moves the token to the right
			if (e.getCode() == KeyCode.A) {
				if (turnOver) {
					token.setCenterX(token.getCenterX() - move);
				}
			}
			// Moves the token to the left
			if (e.getCode() == KeyCode.D) {
				if (turnOver) {
					token.setCenterY(temp);
					token.setCenterX(token.getCenterX() + move);
				}
			}
			// Drops the token
			if (e.getCode() == KeyCode.S) {
				confirm.fire();
			}
		});

		// Warning label when the player tries to place the token outside of the grid
		Label outsideGrid = new Label("              Outside of grid.");
		outsideGrid.relocate(271, 660);
		outsideGrid.setTextFill(Color.RED);
		outsideGrid.setVisible(false);

		// Winner message
		Text winningLabel = new Text("");
		winningLabel.setLayoutY(84);
		winningLabel.setFont(winningFont);
		winningLabel.setStroke(Color.BLACK);
		winningLabel.setVisible(false);

		// Drops the token
		confirm.setOnAction(e -> {
			confirm.setDisable(true); // Prevents players from pressing CONFIRM again before the token has reached the
										// bottom
			if (!playerWinStopTokens) {
				int column = playingGrid.getColumnNum(token.getCenterX()); // gets the column the token is to be placed
																			// in based on the tokens location

				if (column != -1) { // the token is not outside the grid
					double rowVal = playingGrid.addToken(column);
					int row = playingGrid.getCurrentRow();
					int playerColor = playingGrid.getTurnsNoModify();

					// Creates the path for the token from where it is, to its the spot in the grid
					Path path = new Path();
					double x1 = token.getCenterX();
					double y1 = token.getCenterY();
					path.getElements().add(new MoveTo(x1, y1));
					path.getElements().add(new LineTo(x1, rowVal));

					// Creates the transition of the token being dropped
					PathTransition transition = new PathTransition();
					transition.setNode(token);
					transition.setDuration(Duration.seconds(2)); // The transition takes 2 seconds
					transition.setPath(path);
					transition.setCycleCount(1); // The transition happens once

					// When the transition is finished, check if any players won
					transition.setOnFinished(g -> {
						if (playerColor == 1) {
							arrCircles[row][column].setFill(Color.RED);
							arrCircles[row][column].setFill(tokenOneColor);
							arrCircles[row][column].setStroke(Color.BLACK);
						}
						if (playerColor == 2) {
							arrCircles[row][column].setFill(tokenTwoColor);
							arrCircles[row][column].setStroke(Color.BLACK);
						}

						// checks if a player has four-in-a-row
						playerWin = playingGrid.checkForWin();

						if (playerWin) {
							String winnerName = "";
							int winner = 0;

							token.setVisible(false);
							playerWinStopTokens = true; // Stops more tokens from being placed

							// Sets the winner message to the winning player's user name and token color
							if (playingGrid.getTurnsNoModify() == 2) {
								winnerName = username1;
								winningLabel.setFill(tokenTwoColor);
								winner = 1;
							} else {
								winnerName = username2;
								winningLabel.setFill(tokenOneColor);
								winner = 2;
							}

							// Displays the user name of the winner
							winningLabel.setText(winnerName + " won!");
							winningLabel.translateXProperty().bind(root.widthProperty()
									.subtract(winningLabel.getBoundsInLocal().getWidth()).divide(2));
							winningLabel.setVisible(true);

							for (int i = 0; i < playingGrid.rowValues.size(); i++) {
								int rIndex = playingGrid.rowValues.get(i);
								int cIndex = playingGrid.colValues.get(i);

								// The four winning tokens flash to indicate the four-in-a-row
								if (winner == 1) {
									FillTransition changeColour = new FillTransition(Duration.millis(2000),
											arrCircles[rIndex][cIndex], tokenTwoColor, Color.WHITE);
									changeColour.setCycleCount(50);
									changeColour.setAutoReverse(true);

									// winning tokens flash
									changeColour.play();

								} else {
									FillTransition changeColour = new FillTransition(Duration.millis(2000),
											arrCircles[rIndex][cIndex], tokenOneColor, Color.WHITE);
									changeColour.setCycleCount(50);
									changeColour.setAutoReverse(true);

									// winning tokens flash
									changeColour.play();
								}
							}

							// The addReview button is visible for players to add a review of the game
							addReview.setVisible(true);
						}

						// stops the token from flickering
						Platform.runLater(() -> {
							outsideGridButton.fire();
						});

					});

					transition.play(); // runs the transition
					playingGrid.print(); // prints grid in console

				} else { // token is outside the grid
					confirm.setDisable(true);
					outsideGridButton.setVisible(true); // player must press CONTINUE (the outsideGridButton) to unlock
														// their token and keep playing
					outsideGridButton.setDisable(false);
					outsideGrid.setVisible(true);
				}
				turnOver = false; // Prevents the token at the top from being moved with the A and D keys
			}

		});

		// Must be pressed to continue if the player tried to drop token outside of grid
		outsideGridButton.setOnAction(e -> {
			// Make CONFIRM button visible and CONTINUE button (outsideGridButton) invisible
			outsideGridButton.setVisible(false);
			outsideGrid.setVisible(false);
			confirm.setDisable(true);
			confirm.setVisible(true);

			// gets the current player's turn
			int playerColor = playingGrid.getTurnsNoModify();

			// Unlocks the token (the player can move their token again)
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

			// Sets the token to the current player's color
			if (playerColor == 1) {
				token.setFill(tokenTwoColor);
			} else {
				token.setFill(tokenOneColor);
			}

			// CONFIRM button re-enabled
			transition.setOnFinished(g -> {
				confirm.setDisable(false);
			});

			turnOver = true; // Token at the top can be moved with the A and D keys again
		});

		/// Buttons to change the grid colors (all grid colors are different shades than
		/// the token colors)
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

		// Changes grid color to grey
		grey.setOnAction(e -> {
			for (int r = 0; r < arrShapes.length; r++) {
				for (int c = 0; c < arrShapes[r].length; c++) {
					arrShapes[r][c].setFill(Color.GREY);
				}
			}
		});

		// Changes grid color to green
		green.setOnAction(e -> {
			for (int r = 0; r < arrShapes.length; r++) {
				for (int c = 0; c < arrShapes[r].length; c++) {
					arrShapes[r][c].setFill(Color.GREEN);
				}
			}
		});

		// Changes grid color to blue (default is also blue)
		blue.setOnAction(e -> {
			for (int r = 0; r < arrShapes.length; r++) {
				for (int c = 0; c < arrShapes[r].length; c++) {
					arrShapes[r][c].setFill(Color.CORNFLOWERBLUE);
				}
			}
		});

		// Changes grid color to purple
		purple.setOnAction(e -> {
			for (int r = 0; r < arrShapes.length; r++) {
				for (int c = 0; c < arrShapes[r].length; c++) {
					arrShapes[r][c].setFill(Color.PURPLE);
				}
			}
		});

		// Changes grid color to orange
		orange.setOnAction(e -> {
			for (int r = 0; r < arrShapes.length; r++) {
				for (int c = 0; c < arrShapes[r].length; c++) {
					arrShapes[r][c].setFill(Color.ORANGE);
				}
			}
		});

		// Changes grid color to pink
		pink.setOnAction(e -> {
			for (int r = 0; r < arrShapes.length; r++) {
				for (int c = 0; c < arrShapes[r].length; c++) {
					arrShapes[r][c].setFill(Color.PINK);
				}
			}
		});

		/// Once the game is over, the player can add a review/rating
		addReview.setOnAction(e -> {
			stage.setScene(feedbackScene);
		});

		/// RatingCollection contains both the reviews and the ratings
		RatingCollection rc = new RatingCollection();

		/// Adding a review
		// Add a review instructions
		Text addReviewMessage = new Text("Add a review (1-45 characters, inclusive):");
		addReviewMessage.relocate(280, 280);
		// TextField where the review can be written
		TextField review = new TextField();
		review.relocate(280, 300);
		// ADD button to lock in the review
		Button addRev = new Button("ADD");
		addRev.relocate(450, 300);

		// Review error message (if the review is not valid)
		Text notValidReview = new Text("Review must be between\n1-45 characters (inclusive)");
		notValidReview.setVisible(false);
		notValidReview.relocate(500, 300);
		notValidReview.setFill(Color.RED);

		// Adding a rating
		Text addRatingMessage = new Text("Add a rating (1 - 5, inclusive):"); // Add a rating instructions
		addRatingMessage.relocate(280, 340);
		TextField rating = new TextField(); // TextField where the rating can be written
		rating.relocate(280, 360);
		Button addRating = new Button("ADD"); // ADD button to lock in the rating
		addRating.relocate(450, 360);

		// Review error message (if the review is not valid)
		Text notValidRating = new Text("Rating must be between\n1-5 (inclusive)");
		notValidRating.setVisible(false);
		notValidRating.relocate(500, 360);
		notValidRating.setFill(Color.RED);

		// Button to see the average game rating and latest reviews
		Button viewRatings = new Button("VIEW RATINGS");
		viewRatings.relocate(313, 430);

		// review and rating TextFields
		feedbackScreen.getChildren().add(review);
		feedbackScreen.getChildren().add(rating);

		// Review and rating instructions
		feedbackScreen.getChildren().add(addReviewMessage);
		feedbackScreen.getChildren().add(addRatingMessage);

		// ADD review/rating buttons and VIEW RATINGS button
		feedbackScreen.getChildren().add(addRev);
		feedbackScreen.getChildren().add(addRating);
		feedbackScreen.getChildren().add(viewRatings);

		// Error messages for invalid review/rating
		feedbackScreen.getChildren().addAll(notValidRating, notValidReview);

		// Adds a review to RatingCollection
		addRev.setOnAction(e -> {
			String tempReview = review.getText(); // add the review in the TextField to tempReview

			try {
				// Checks if the review is valid
				if (tempReview.length() > 0 && tempReview.length() <= 45) { // The review is valid

					// Adds the review to RatingCollection
					rc.addReviews(tempReview);

				} else { // The review is not valid

					// Display error message for four seconds
					notValidReview.setVisible(true);
					Timeline hideMessage = new Timeline(new KeyFrame(Duration.seconds(4), g -> {
						notValidReview.setVisible(false);
					}));
					hideMessage.play();
				}
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
			}

			review.setText(""); // Clears the TextField
		});

		addRating.setOnAction(e -> {
			int tempRating = Integer.parseInt(rating.getText()); // add the rating in the TextField to tempRating

			try {
				// Checks if the rating is valid
				if (tempRating <= 5 && tempRating >= 1) { // The rating is valid

					// Adds the rating to RatingCollection
					rc.addRatings(tempRating);

				} else { // The rating is not valid

					// Display error message for four seconds
					notValidRating.setVisible(true);
					Timeline hideMessage = new Timeline(new KeyFrame(Duration.seconds(4), g -> {
						notValidRating.setVisible(false);
					}));
					hideMessage.play();
				}
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
			}

			rating.setText(""); // Clears the TextField
		});

		/// in the viewRRScreen (the screen that displays the latest reviews and average
		/// rating)
		//
		Text gameRatings = new Text("Game reviews & ratings");
		gameRatings.relocate(200, 50);
		gameRatings.setFont(rrPageFont);
		gameRatings.setFill(Color.DARKOLIVEGREEN);

		/// Displays the ratings and reviews
		// Rating label
		Text ratings = new Text("Average Rating:");
		ratings.relocate(100, 150);
		ratings.setFont(rrPageFontOne);
		ratings.setFill(Color.DARKGREEN);
		// Reviews label
		Text reviews = new Text("Reviews:");
		reviews.relocate(100, 280);
		reviews.setFont(rrPageFontOne);
		reviews.setFill(Color.DARKGREEN);
		// Average rating
		Text averageRatings = new Text("No ratings yet"); // visible in viewRRScreen Pane
		averageRatings.setVisible(false);
		averageRatings.relocate(100, 180);
		averageRatings.setFont(rrPageFontTwo);
		// Latest reviews
		Text latestReviews = new Text("No reviews yet"); // visible in viewRRScreen Pane
		latestReviews.setVisible(false);
		latestReviews.relocate(100, 310);
		latestReviews.setFont(rrPageFontTwo);

		// Lines separating the three sections (ratings, reviews, and buttons to go to
		// end screen or add another rating)
		Line line1 = new Line(5, 100, 695, 100);
		Line line2 = new Line(5, 230, 695, 230);
		Line line3 = new Line(5, 440, 695, 440);

		// Button to add another rating
		Button addReviewAgain = new Button("Add another rating");
		addReviewAgain.setFont(rrPageFontTwo);
		addReviewAgain.relocate(100, 490);

		// Button to go to end screen
		Button done = new Button("Done?");
		done.setFont(rrPageFontTwo);
		done.relocate(100, 570);

		// Takes you back to the screen that allows you to add another rating/review
		addReviewAgain.setOnAction(e -> {
			stage.setScene(feedbackScene);
		});

		/// In the feedbackScreen Pane
		// Button to get to viewRRScreen (to view the average rating/reviews)
		viewRatings.setOnAction(e -> {
			// Updates the rating average
			String labelMessage = rc.getRatings() + " / 5";
			averageRatings.setText(labelMessage);
			averageRatings.setVisible(true);

			// Updates the latest reviews
			labelMessage = "" + rc.getLatestReviews();
			latestReviews.setText(labelMessage);
			latestReviews.setVisible(true);

			// Goes to viewRRScreen
			stage.setScene(viewRRScene);
		});

		viewRRScreen.getChildren().addAll(averageRatings, latestReviews, gameRatings);
		viewRRScreen.getChildren().addAll(ratings, reviews);
		viewRRScreen.getChildren().add(addReviewAgain);
		viewRRScreen.getChildren().add(done);
		viewRRScreen.getChildren().addAll(line1, line2, line3);

		// Components of game play (grid, token, CONFIRM button, etc.)
		root.getChildren().add(grid);
		root.getChildren().add(token);
		root.getChildren().add(confirm);
		root.getChildren().add(outsideGridButton);
		root.getChildren().add(outsideGrid);
		root.getChildren().addAll(grey, green, blue, purple, orange, pink);
		root.getChildren().add(winningLabel);
		root.getChildren().add(addReview);

		// End screen label
		Label thanks = new Label("Thanks for playing Connect 4!");
		thanks.setFont(Font.font(40));
		thanks.setStyle("-fx-font-weight: bold;");
		thanks.layoutXProperty().bind(root.widthProperty().subtract(thanks.widthProperty()).divide(2));
		thanks.setLayoutY(300);

		// Takes players to end screen
		done.setOnAction(e -> {
			// Removes unnecessary components from the game-play screen for the end screen
			root.getChildren().removeAll(confirm, grey, green, blue, purple, orange, pink, addReview, winningLabel);
			// Adds the end screen message and semi-transparent layer
			root.getChildren().addAll(layer, thanks);
			stage.setScene(scene);
		});

		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
