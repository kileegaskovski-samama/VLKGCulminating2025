import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIDriver extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox(10); // 10 is distance between each node
		Label text = new Label("HELLO HIHI");
		Label text2 = new Label("hiii");
		root.getChildren().add(text2);
		root.getChildren().add(text);
		
		Scene scene = new Scene(root, 600, 600);
		stage.setScene(scene);
		stage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
