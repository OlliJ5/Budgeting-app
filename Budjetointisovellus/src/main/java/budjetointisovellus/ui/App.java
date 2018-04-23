package budjetointisovellus.ui;

import budjetointisovellus.dao.*;
import budjetointisovellus.domain.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        SqlBudgetDao budgetDao = new SqlBudgetDao(database);
        budgetService = new BudgetService(expenseDao, userDao, budgetDao);
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
                    loggedInScene(null);
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

    public void loggedInScene(String budgetName) {
        String time = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

        Label loggedInAs = new Label("Tervetuloa " + this.user.getName());
        Label timeLabel = new Label(time);
        Button logOutButton = new Button("Kirjaudu ulos");
        Button createABudget = new Button("Luo uusi budjetti");
        Label edit = new Label("Valitse muokattava budjetti: ");

        ToolBar toprow = new ToolBar();
        toprow.getItems().addAll(loggedInAs, timeLabel);

        ChoiceBox cb = new ChoiceBox();

        ArrayList<String> budgets = budgetService.findBudgets(user)
                .stream()
                .map(budget -> budget.getName())
                .collect(Collectors.toCollection(ArrayList::new));

        cb.setItems(FXCollections.observableArrayList(budgets));
        
        if(budgetName == null) {
            cb.getSelectionModel().select(0);
        } else {
            cb.setValue(budgetName);
        }

        ToolBar sidePanel = new ToolBar();
        sidePanel.setOrientation(Orientation.VERTICAL);
        sidePanel.getItems().addAll(logOutButton, createABudget, edit, cb);

        Label expenseText = new Label("Nimi: ");
        Label priceText = new Label("Hinta: ");
        TextField expenseName = new TextField();
        TextField expensePrice = new TextField();
        Button addButton = new Button("Lisää kulu");
        Label notification = new Label();

        GridPane expenseCreation = new GridPane();
        expenseCreation.add(expenseText, 0, 0);
        expenseCreation.add(expenseName, 1, 0);
        expenseCreation.add(priceText, 0, 1);
        expenseCreation.add(expensePrice, 1, 1);
        expenseCreation.add(addButton, 1, 2);
        expenseCreation.add(notification, 1, 3);

        expenseCreation.setHgap(10);
        expenseCreation.setVgap(10);

        TableView table = new TableView();
        table.setEditable(true);

        TableColumn<Expense, String> nameCol = new TableColumn<>("Kulu");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, Double> priceCol = new TableColumn<>("Hinta");
        priceCol.setMinWidth(180);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        Label tableHeader = new Label();
        if (cb.getValue() != null) {
            tableHeader.setText(cb.getValue().toString());
        }

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(tableHeader, table);

        List<Expense> expenses = new ArrayList<>();
        if(cb.getValue() != null) {
            expenses = budgetService.findBudgetsExpenses(cb.getValue().toString(), user.getUsername());
        }

        cb.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                loggedInScene(newValue.toString());
            }
        });

        table.getColumns().addAll(nameCol, priceCol);
        table.setItems(FXCollections.observableArrayList(expenses));

        BorderPane pane = new BorderPane();
        pane.setTop(toprow);
        pane.setLeft(sidePanel);
        pane.setRight(expenseCreation);
        pane.setCenter(vbox);

        scene = new Scene(pane, 800, 500);
        primaryStage.setScene(scene);

        logOutButton.setOnAction((event) -> {
            this.user = null;
            loginScene();
        });

        createABudget.setOnAction((event) -> {
            createABudgetScene();
        });

        addButton.setOnAction((event) -> {
            if (cb.getValue() == null) {
                notification.setText("Valitse muokattava budjetti!");
                return;
            }
            if (budgetService.createExpense(user.getUsername(), cb.getValue().toString(), expenseName.getText(), Double.parseDouble(expensePrice.getText()))) {
                notification.setText("Kulu lisätty!");
                loggedInScene(cb.getValue().toString());
            }
            expensePrice.setText("");
            expenseName.setText("");
        });
    }

    public void createABudgetScene() {
        Label budgetName = new Label("Budjetin nimi: ");
        TextField budget = new TextField();
        Label budgetAmount = new Label("Budjetin määrä: ");
        TextField amount = new TextField();
        Button submit = new Button("Luo uusi");
        Button changeScene = new Button("Palaa");
        Label notification = new Label("");

        GridPane pane = new GridPane();
        pane.add(budgetName, 0, 0);
        pane.add(budget, 1, 0);
        pane.add(budgetAmount, 0, 1);
        pane.add(amount, 1, 1);
        pane.add(submit, 1, 2);
        pane.add(changeScene, 1, 3);
        pane.add(notification, 1, 4);

        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(50, 50, 50, 10));

        scene = new Scene(pane, 400, 250);
        primaryStage.setScene(scene);

        submit.setOnAction((event) -> {
            Budget newBudget = new Budget(budget.getText(), Double.parseDouble(amount.getText()));
            try {
                if (budgetService.createBudget(newBudget, user)) {
                    loggedInScene(null);
                } else {
                    notification.setText("Saman niminen budjetti on jo olemassa");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        });

        changeScene.setOnAction((event) -> {
            loggedInScene(null);
        });
    }

}
