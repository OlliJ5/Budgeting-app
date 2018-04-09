
package budjetointisovellus.ui;

import budjetointisovellus.dao.Database;
import budjetointisovellus.dao.SqlExpenseDao;
import budjetointisovellus.dao.SqlUserDao;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.domain.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{
    private BudgetService budgetService;
    private User user;
    
    @Override
    public void init() throws Exception{
        Database database = new Database("jdbc:sqlite:budget.db");
        SqlUserDao userDao = new SqlUserDao(database);
        SqlExpenseDao expenseDao = new SqlExpenseDao(database);
        
        budgetService = new BudgetService(expenseDao, userDao);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Budjetointisovellus");

        //Create a user Scene
        Label nameText = new Label("Nimi: ");
        Label usernameText = new Label("Käyttäjätunnus: ");
        TextField nameField = new TextField();
        TextField usernameField = new TextField();
        Button addButton = new Button("Luo käyttäjä");
        Button changeToLogin = new Button("Takaisin kirjautumiseen");
        Label messageCreate = new Label("");
        
        GridPane createUserPane = new GridPane();
        createUserPane.add(usernameText, 0, 0);
        createUserPane.add(usernameField, 1, 0);
        createUserPane.add(nameText, 0, 1);
        createUserPane.add(nameField, 1, 1);
        createUserPane.add(addButton, 1, 2);
        createUserPane.add(changeToLogin, 1, 3);
        createUserPane.add(messageCreate, 1, 4);
        
        createUserPane.setHgap(10);
        createUserPane.setVgap(10);
        createUserPane.setPadding(new Insets(50, 50, 50, 10));
        
        Scene createUser = new Scene(createUserPane, 400, 250);
        
        //Login Scene
        Label usernameTextLogin = new Label("Käyttäjätunnus: ");
        TextField usernameFieldLogin = new TextField();
        Button loginButton = new Button("Login");
        Button changeToCreate = new Button("Luo uusi käyttäjä");
        Label messageLogin = new Label();
        
        GridPane loginPane = new GridPane();
        loginPane.add(usernameTextLogin, 0, 0);
        loginPane.add(usernameFieldLogin, 1, 0);
        loginPane.add(loginButton, 1, 2);
        loginPane.add(changeToCreate, 1, 3);
        loginPane.add(messageLogin, 1, 4);
        
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(50, 50, 50, 10));
        
        Scene login = new Scene(loginPane, 400, 250);
        
        //Logged in Scene
        Label loggedInAs = new Label();
        Pane pane = new Pane();
        pane.getChildren().add(loggedInAs);
        Scene loggedIn = new Scene(pane, 400, 250);
        
        //Event handlers
        
        changeToCreate.setOnAction((event)-> {
            messageLogin.setText("");
            primaryStage.setScene(createUser);
        });
        
        changeToLogin.setOnAction((event) -> {
            messageCreate.setText("");
            primaryStage.setScene(login);
        });

        addButton.setOnAction((event) ->{
            String newUsername = usernameField.getText();
            String newName = nameField.getText();
            usernameField.setText("");
            
            try {
                if(budgetService.createUser(newUsername, newName, 0)) {
                    nameField.setText("");
                    messageCreate.setText("Käyttäjä " + newUsername + " luotu");
                } else {
                    messageCreate.setText("Käyttäjätunnus " + newUsername + " on jo olemassa");
                }
                
            } catch(Exception e) {
                System.out.println(e);
            }
            
        });
        
        loginButton.setOnAction((event) -> {
            String username = usernameFieldLogin.getText();
            usernameFieldLogin.setText("");
            try {
                if(budgetService.login(username)) {
                    this.user = budgetService.getUser(username);
                    loggedInAs.setText("Logged in as " + this.user.getUsername());
                    primaryStage.setScene(loggedIn);
                } else {
                    messageLogin.setText("Virheellinen käyttäjätunnus");
                }
            } catch(Exception e) {
                System.out.println(e);
            }
        });
        
        primaryStage.setScene(login);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
