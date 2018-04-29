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
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App {

    private BudgetService budgetService;
    private Stage primaryStage;
    private User user;
    private Scene scene;
    private Budget budget;

    public App(Stage primaryStage) throws ClassNotFoundException {
        this.primaryStage = primaryStage;

        Database database = new Database("jdbc:sqlite:budget.db");
        UserDao userDao = new SqlUserDao(database);
        ExpenseDao expenseDao = new SqlExpenseDao(database);
        BudgetDao budgetDao = new SqlBudgetDao(database);
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
        loginPane.setPadding(new Insets(200, 210, 200, 230));

        scene = new Scene(loginPane, 820, 600);
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
        createUserPane.setPadding(new Insets(200, 210, 200, 230));

        scene = new Scene(createUserPane, 820, 600);
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
        toprow.setPadding(new Insets(10, 30, 10, 30));

        Label edit = new Label("Valitse muokattava budjetti: ");
        ChoiceBox cb = new ChoiceBox();

        ArrayList<String> budgets = budgetService.findBudgets(user)
                .stream()
                .map(b -> b.getName())
                .collect(Collectors.toCollection(ArrayList::new));

        cb.setItems(FXCollections.observableArrayList(budgets));

        if (budget == null) {
            cb.getSelectionModel().select(0);
            if(budgets.size() > 0) {
                budget = budgetService.getBudgetByName(cb.getValue().toString(), user);
            }
        } else {
            cb.setValue(budget.getName());
        }

        VBox rightSide = new VBox();
        rightSide.getChildren().addAll(edit, cb);
        rightSide.setSpacing(10);
        rightSide.setPadding(new Insets(30, 20, 10, 20));

        VBox leftSide = new VBox();
        leftSide.getChildren().addAll(logOutButton, createABudget);
        leftSide.setSpacing(15);
        leftSide.setPadding(new Insets(10, 10, 10, 10));

        ToolBar sidePanel = new ToolBar();
        sidePanel.setOrientation(Orientation.VERTICAL);
        sidePanel.getItems().add(leftSide);
        sidePanel.setPadding(new Insets(10, 10, 10, 10));

        TableView<Expense> table = new TableView();
        table.setEditable(true);

        TableColumn<Expense, String> nameCol = new TableColumn<>("Kulu");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Expense, Double> priceCol = new TableColumn<>("Hinta");
        priceCol.setMinWidth(180);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TextField expenseName = new TextField();
        expenseName.setPromptText("Kulun nimi");
        expenseName.setMinWidth(nameCol.getPrefWidth());
        TextField expensePrice = new TextField();
        expensePrice.setPromptText("Kulun hinta");
        expensePrice.setMinWidth(priceCol.getPrefWidth());
        Button addButton = new Button("Lisää");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(expenseName, expensePrice, addButton);
        hbox.setSpacing(5);

        Label notification = new Label();

        Button deleteBudgetButton = new Button("Poista bujetti");
        Label tableName = new Label();
        tableName.setPadding(new Insets(5, 0, 0, 0));
        if (cb.getValue() != null) {
            tableName.setText(cb.getValue().toString() + " (" + budgetService.getBudgetByName(cb.getValue().toString(), user).getAmount() + ")");
        }

        HBox tableHeader = new HBox();
        if (!budgets.isEmpty()) {
            tableHeader.getChildren().addAll(tableName, deleteBudgetButton);
        }
        tableHeader.setSpacing(10);
        tableHeader.setPadding(new Insets(10, 10, 10, 10));
        
        List<Expense> expenses = new ArrayList<>();
        if (cb.getValue() != null) {
            expenses = budgetService.findBudgetsExpenses(cb.getValue().toString(), user.getUsername());
        }
        
        double sumOfExpenses = budgetService.totalExpenses(expenses);
        
        Button deleteExpense = new Button("Poista kulu");
        Label expensesInTotal = new Label("Kulut yhteensä: " + sumOfExpenses);
        Label leftOfBudget = new Label();
        
        if(budget != null) {
            if (sumOfExpenses > budget.getAmount()) {
                double howMuchOver = sumOfExpenses - budget.getAmount();
                leftOfBudget.setText("Olet ylittänyt budjettisi " + howMuchOver + " eurolla!");
            } else {
                double budgetLeft = budget.getAmount() - sumOfExpenses;
                leftOfBudget.setText("Budjettia jäljellä " + budgetLeft + " euroa!");
            }
        }
        

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 5, 15, 15));
        vbox.setSpacing(10);
        vbox.getChildren().addAll(tableHeader, table, expensesInTotal, leftOfBudget, hbox, deleteExpense, notification);


        cb.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                this.budget = budgetService.getBudgetByName(newValue.toString(), user);
                loggedInScene();
            }
        });

        table.getColumns().addAll(nameCol, priceCol);
        table.setItems(FXCollections.observableArrayList(expenses));

        BorderPane pane = new BorderPane();
        pane.setTop(toprow);
        pane.setLeft(sidePanel);
        pane.setCenter(vbox);
        pane.setRight(rightSide);

        scene = new Scene(pane, 820, 600);
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
            } else if (!isDouble(expensePrice, expensePrice.getText())) {
                notification.setText(expensePrice.getText() + " ei ole luku!");
                return;
            }

            if (budgetService.createExpense(user.getUsername(), cb.getValue().toString(), expenseName.getText(), Double.parseDouble(expensePrice.getText()))) {
                notification.setText("Kulu lisätty!");
                budget = budgetService.getBudgetByName(cb.getValue().toString(), user);
                loggedInScene();
            }
            expensePrice.setText("");
            expenseName.setText("");
        });

        deleteBudgetButton.setOnAction((event) -> {
            budgetService.deleteBudget(budget, user);
            budgets.remove(cb.getValue().toString());
            cb.getSelectionModel().select(0);
            this.budget = budgetService.getBudgetByName(cb.getValue().toString(), user);
            loggedInScene();
        });
        
        deleteExpense.setOnAction((event) -> {
            Expense expense = table.getSelectionModel().getSelectedItem();
            budgetService.deleteExpense(budgetService.getBudgetByName(cb.getValue().toString(), user), user, expense);
            table.getItems().remove(expense);
            loggedInScene();
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
        pane.setPadding(new Insets(200, 210, 200, 230));

        scene = new Scene(pane, 820, 600);
        primaryStage.setScene(scene);

        submit.setOnAction((event) -> {
            if (!isDouble(amount, amount.getText())) {
                notification.setText(amount.getText() + " ei ole luku");
                return;
            }

            Budget newBudget = new Budget(budget.getText(), Double.parseDouble(amount.getText()));
            try {
                if (budgetService.createBudget(newBudget, user)) {
                    this.budget = newBudget;
                    loggedInScene();
                } else {
                    notification.setText("Saman niminen budjetti on jo olemassa");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        });

        changeScene.setOnAction((event) -> {
            loggedInScene();
        });
    }

    public boolean isDouble(TextField input, String message) {
        try {
            Double number = Double.parseDouble(input.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
