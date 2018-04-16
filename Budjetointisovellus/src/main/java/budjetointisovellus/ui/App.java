package budjetointisovellus.ui;

import budjetointisovellus.dao.Database;
import budjetointisovellus.dao.SqlExpenseDao;
import budjetointisovellus.dao.SqlUserDao;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.domain.User;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App {

    private BudgetService budgetService;
    private Stage primaryStage;
    private User user;
    private Scene scene;

    public App(Stage primaryStage) throws ClassNotFoundException {
        this.primaryStage = primaryStage;

        Database database = new Database("jdbc:sqlite:budget.db");
        SqlUserDao userDao = new SqlUserDao(database);
        SqlExpenseDao expenseDao = new SqlExpenseDao(database);
        budgetService = new BudgetService(expenseDao, userDao);
    }

    public void start() {
        loginScene();
    }

    public void loginScene() {
        Label usernameTextLogin = new Label("Käyttäjätunnus: ");
        TextField usernameFieldLogin = new TextField();
        Label passwordTextLogin = new Label("Salasana: ");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button changeToCreate = new Button("Luo uusi käyttäjä");
        Label messageLogin = new Label();

        GridPane loginPane = new GridPane();
        loginPane.add(usernameTextLogin, 0, 0);
        loginPane.add(usernameFieldLogin, 1, 0);
        loginPane.add(passwordTextLogin, 0, 1);
        loginPane.add(passwordField, 1, 1);
        loginPane.add(loginButton, 1, 2);
        loginPane.add(changeToCreate, 1, 3);
        loginPane.add(messageLogin, 1, 4);

        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(50, 50, 50, 10));

        scene = new Scene(loginPane, 400, 250);
        primaryStage.setScene(scene);

        changeToCreate.setOnAction((event) -> {
            messageLogin.setText("");
            userCreationScene();
        });

        loginButton.setOnAction((event) -> {
            String username = usernameFieldLogin.getText();
            String password = passwordField.getText();
            usernameFieldLogin.setText("");
            passwordField.setText("");
            try {
                if (budgetService.login(username, password)) {
                    this.user = budgetService.getUser(username);
                    loggedInScene();
                } else {
                    messageLogin.setText("Virheellinen käyttäjätunnus tai salasana");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    public void userCreationScene() {
        Label nameText = new Label("Nimi: ");
        Label usernameText = new Label("Käyttäjätunnus: ");
        Label passWordText = new Label("Salasana: ");
        TextField nameField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button addButton = new Button("Luo käyttäjä");
        Button changeToLogin = new Button("Takaisin kirjautumiseen");
        Label messageCreate = new Label("");

        GridPane createUserPane = new GridPane();
        createUserPane.add(usernameText, 0, 0);
        createUserPane.add(usernameField, 1, 0);
        createUserPane.add(nameText, 0, 1);
        createUserPane.add(nameField, 1, 1);
        createUserPane.add(passWordText, 0, 2);
        createUserPane.add(passwordField, 1, 2);
        createUserPane.add(addButton, 1, 3);
        createUserPane.add(changeToLogin, 1, 4);
        createUserPane.add(messageCreate, 1, 5);

        createUserPane.setHgap(10);
        createUserPane.setVgap(10);
        createUserPane.setPadding(new Insets(50, 50, 50, 10));

        scene = new Scene(createUserPane, 400, 250);
        primaryStage.setScene(scene);

        changeToLogin.setOnAction((event) -> {
            messageCreate.setText("");
            loginScene();
        });

        addButton.setOnAction((event) -> {
            String newUsername = usernameField.getText();
            String newName = nameField.getText();
            String newPassword = passwordField.getText();
            usernameField.setText("");

            try {
                if (budgetService.createUser(newUsername, newName, newPassword)) {
                    nameField.setText("");
                    passwordField.setText("");
                    messageCreate.setText("Käyttäjä " + newUsername + " luotu");
                } else {
                    messageCreate.setText("Käyttäjätunnus " + newUsername + " on jo olemassa");
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        });
    }

    public void loggedInScene() {
        String time = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

        Label loggedInAs = new Label("Tervetuloa " + this.user.getName());
        Label timeLabel = new Label(time);
        Button logOutButton = new Button("Kirjaudu ulos");
        Button createABudget = new Button("Luo uusi budjetti");
        
        ToolBar toprow = new ToolBar();
        toprow.getItems().addAll(loggedInAs, timeLabel);
        
        ToolBar side = new ToolBar();
        side.setOrientation(Orientation.VERTICAL);
        side.getItems().addAll(logOutButton, createABudget);
        
        BorderPane pane = new BorderPane();
        pane.setTop(toprow);
        pane.setLeft(side);
        scene = new Scene(pane, 600, 400);
        primaryStage.setScene(scene);

        logOutButton.setOnAction((event) -> {
            this.user = null;
            loginScene();
        });
        
        createABudget.setOnAction((event) -> {
            Stage stage = new Stage();
            stage.setTitle("Luo uusi budjetti");
            Pane createNew = new Pane();
            stage.setScene(new Scene(createNew));
            stage.show();
        });
    }

}
