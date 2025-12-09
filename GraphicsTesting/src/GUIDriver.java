import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class GUIDriver {
	public void start (Stage stage) throws Exception {
		
		Font orig = new Font ("Arial", 15);
		Label text = new Label("HI");
		text.setFont(orig);
		
		HBox test = new HBox(10);
		test.setAlignment(Pos.CENTER);
		
		test.getChildren().add(text);
		
		Scene scene = new Scene(test, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
