package budjetointisovellus.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author ogrousu
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        App budget = new App(primaryStage);
        budget.start();

        primaryStage.show();
        primaryStage.setTitle("Budjetointisovellus");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
