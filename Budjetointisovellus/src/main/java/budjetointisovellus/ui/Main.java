
package budjetointisovellus.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Budjetointisovellus");
        Label nameText = new Label("Nimi: ");
        Label usernameText = new Label("Käyttäjätunnus: ");
        TextField nameField = new TextField();
        TextField usernameField = new TextField();
        
        Button addButton = new Button("Luo käyttäjä");
        
        GridPane pane = new GridPane();
        pane.add(nameText, 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(usernameText, 0, 1);
        pane.add(usernameField, 1, 1);
        pane.add(addButton, 1, 2);
        
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(50, 50, 50, 10));
        
        Scene createUser = new Scene(pane);
        primaryStage.setScene(createUser);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
