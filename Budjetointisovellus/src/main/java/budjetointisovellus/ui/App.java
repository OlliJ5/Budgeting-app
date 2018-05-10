package budjetointisovellus.ui;

import budjetointisovellus.dao.*;
import budjetointisovellus.domain.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ogrousu
 */
public class App {

    private BudgetService budgetService;
    private Stage primaryStage;
    private User user;
    private Scene scene;
    private Budget budget;

    /**
     *
     * @param primaryStage Stage-olio, jota halutaan käyttää
     */
    public App(Stage primaryStage) throws ClassNotFoundException {
        this.primaryStage = primaryStage;

        Database database = new Database("jdbc:sqlite:budget.db");
        UserDao userDao = new SqlUserDao(database);
        ExpenseDao expenseDao = new SqlExpenseDao(database);
        BudgetDao budgetDao = new SqlBudgetDao(database);
        budgetService = new BudgetService(expenseDao, userDao, budgetDao);
    }

    /**
     * Kaynnistaa sovelluksen
     */
    public void start() {
        loginScene();
    }

    /**
     * Luo sisäänkirjautumisnäkymän ja asettaa sen Stage-olioon
     */
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

    /**
     * Luo näkymän, jossa uusi käyttäjä luodaan ja asettaa sen Stageen
     */
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

            if (newUsername.length() < 3 || newName.length() < 2 || newPassword.length() < 3) {
                messageCreate.setText("Käyttäjätunnus tai salasana liian lyhyt");
            } else {
                try {
                    if (budgetService.createUser(newUsername, newName, newPassword)) {
                        messageCreate.setText("Käyttäjä " + newUsername + " luotu");
                    } else {
                        messageCreate.setText("Käyttäjätunnus " + newUsername + " on jo olemassa");
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            usernameField.setText("");
            nameField.setText("");
            passwordField.setText("");
        });
    }

    /**
     * Luo näkymän sisäänkirjautuneelle käyttäjälle ja asettaa sen Stageen
     */
    public void loggedInScene() {
        String time = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

        Label loggedInAs = new Label("Tervetuloa " + this.user.getName());
        Label timeLabel = new Label(time);

        Button logOutButton = new Button("Kirjaudu ulos");
        Button editUserInfo = new Button("Käyttäjän tiedot");
        Button createABudgetButton = new Button("Luo uusi budjetti");

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
            if (budgets.size() > 0) {
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
        leftSide.getChildren().addAll(logOutButton, editUserInfo, createABudgetButton);
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

        Label addExpenses = new Label("Lisää kuluja:");
        TextField expenseName = new TextField();
        expenseName.setPromptText("Kulun nimi");
        expenseName.setMinWidth(nameCol.getPrefWidth());
        TextField expensePrice = new TextField();
        expensePrice.setPromptText("Kulun hinta");
        expensePrice.setMinWidth(priceCol.getPrefWidth());
        Button addExpenseButton = new Button("Lisää");

        HBox expenseForm = new HBox();
        expenseForm.getChildren().addAll(expenseName, expensePrice, addExpenseButton);
        expenseForm.setSpacing(5);

        Label notification = new Label();

        Label tableName = new Label();
        Button showBudgetInfo = new Button("Näytä lisätietoja");
        tableName.setPadding(new Insets(5, 0, 0, 0));
        if (cb.getValue() != null) {
            tableName.setText(cb.getValue().toString() + " (" + budgetService.getBudgetByName(cb.getValue().toString(), user).getAmount() + ")");
        }

        HBox tableHeader = new HBox();
        if (!budgets.isEmpty()) {
            tableHeader.getChildren().addAll(tableName, showBudgetInfo);
        }
        tableHeader.setSpacing(10);
        tableHeader.setPadding(new Insets(10, 10, 10, 10));

        List<Expense> expenses = new ArrayList<>();
        if (cb.getValue() != null) {
            expenses = budgetService.findBudgetsExpenses(cb.getValue().toString(), user.getUsername());
        }

        double sumOfExpenses = budgetService.totalExpenses(expenses);

        Button deleteBudgetButton = new Button("Poista bujetti");
        Button deleteExpense = new Button("Poista kulu");
        Label expensesInTotal = new Label("Kulut yhteensä: " + sumOfExpenses);
        Label leftOfBudget = new Label();

        if (budget != null) {
            if (sumOfExpenses > budget.getAmount()) {
                double howMuchOver = sumOfExpenses - budget.getAmount();
                leftOfBudget.setText("Olet ylittänyt budjettisi " + howMuchOver + " eurolla!");
            } else {
                double budgetLeft = budget.getAmount() - sumOfExpenses;
                leftOfBudget.setText("Budjettia jäljellä " + budgetLeft + " euroa!");
            }
        }

        HBox budgetInfo = new HBox();
        budgetInfo.setSpacing(10);
        budgetInfo.setPadding(new Insets(15, 0, 0, 0));

        if (!budgets.isEmpty()) {
            budgetInfo.getChildren().addAll(leftOfBudget, deleteBudgetButton);
        }

        VBox middle = new VBox();
        middle.setPadding(new Insets(10, 5, 15, 15));
        middle.setSpacing(10);
        middle.getChildren().addAll(tableHeader, table, expensesInTotal, budgetInfo, addExpenses, expenseForm, deleteExpense, notification);

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
        pane.setCenter(middle);
        pane.setRight(rightSide);

        scene = new Scene(pane, 820, 600);
        primaryStage.setScene(scene);

        logOutButton.setOnAction((event) -> {
            this.user = null;
            loginScene();
        });

        createABudgetButton.setOnAction((event) -> {
            createABudgetScene();
        });

        showBudgetInfo.setOnAction((event) -> {
            budgetInfoScene();
        });

        addExpenseButton.setOnAction((event) -> {
            if (cb.getValue() == null) {
                notification.setText("Valitse muokattava budjetti!");
                return;
            } else if (!budgetService.isDouble(expensePrice, expensePrice.getText())) {
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
            if (cb.getValue() == null) {
                notification.setText("Valitse muokattava budjetti!");
                return;
            }
            Expense expense = table.getSelectionModel().getSelectedItem();
            budgetService.deleteExpense(budgetService.getBudgetByName(cb.getValue().toString(), user), user, expense);
            table.getItems().remove(expense);
            loggedInScene();
        });

        editUserInfo.setOnAction((event) -> {
            userInfoScene();
        });
    }

    /**
     * Luo näkymän budjetin luomiselle ja asettaa sen Stageen
     */
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
            if (!budgetService.isDouble(amount, amount.getText())) {
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

    public void userInfoScene() {
        Label header = new Label("Käyttäjän tiedot:");
        Label name = new Label("Nimi: " + this.user.getName());
        Label username = new Label("Käyttäjätunnus: " + this.user.getUsername());
        Button deleteUser = new Button("Poista käyttäjä");
        Button returnButton = new Button("Palaa takaisin");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(header, name, username, deleteUser, returnButton);
        vbox.setPadding(new Insets(200, 210, 200, 230));
        vbox.setSpacing(15);

        scene = new Scene(vbox, 820, 600);
        primaryStage.setScene(scene);

        returnButton.setOnAction((event) -> {
            loggedInScene();
        });

        deleteUser.setOnAction((event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Poisto");
            alert.setHeaderText("Haluatko poistaa?");
            alert.setContentText("Valitse ok poistaaksesi.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                budgetService.deleteUser(user);
                budget = null;
                user = null;
                this.loginScene();
            }

        });
    }

    public void budgetInfoScene() {
        Label header = new Label("Budjetin tiedot:");
        Label nameInfo = new Label("Nimi: " + budget.getName());
        Label amountInfo = new Label("Määrä: " + budget.getAmount());

        List<Expense> expenses = budgetService.findBudgetsExpenses(budget.getName(), user.getUsername());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        for(int i = 0; i < expenses.size(); i++) {
            pieChartData.add(new PieChart.Data(expenses.get(i).getName(), expenses.get(i).getPrice()));
        }
        
        PieChart chart = new PieChart(pieChartData);
        chart.setLabelLineLength(25);
        chart.setLegendSide(Side.RIGHT);
        chart.setTitle("Budjetin kulut");

        Pane pane = new Pane();
        chart.setLayoutX(280);
        chart.setLayoutY(-230);
        pane.getChildren().add(chart);

        GridPane info = new GridPane();
        info.add(header, 0, 0);
        info.add(nameInfo, 0, 1);
        info.add(amountInfo, 0, 2);
        info.setVgap(30);

        Label editInfo = new Label("Muokkaa budjetin tietoja: ");
        Label name = new Label("Nimi: ");
        TextField newName = new TextField();
        Button updateName = new Button("Muuta nimi");
        Label amount = new Label("Määrä: ");
        TextField newAmount = new TextField();
        Button updateAmount = new Button("Muuta määrää");

        Label notification = new Label();
        Button returnButton = new Button("Palaa takaisin");

        GridPane form = new GridPane();
        form.add(name, 0, 0);
        form.add(newName, 1, 0);
        form.add(updateName, 2, 0);
        form.add(amount, 0, 1);
        form.add(newAmount, 1, 1);
        form.add(updateAmount, 2, 1);

        form.setHgap(10);
        form.setVgap(10);

        VBox formWithHeader = new VBox();
        formWithHeader.getChildren().addAll(editInfo, form);
        formWithHeader.setSpacing(20);
        formWithHeader.setPadding(new Insets(30, 0, 0, 0));

        VBox view = new VBox();
        view.getChildren().addAll(info, pane, formWithHeader, notification, returnButton);
        view.setPadding(new Insets(90, 210, 200, 20));
        view.setSpacing(50);

        scene = new Scene(view, 820, 600);
        primaryStage.setScene(scene);

        returnButton.setOnAction((event) -> {
            loggedInScene();
        });

        updateName.setOnAction((event) -> {
            if (budgetService.updateBudgetName(newName.getText(), user, budget.getName())) {
                budget = budgetService.getBudgetByName(newName.getText(), user);
                budgetInfoScene();
            }
        });

        updateAmount.setOnAction((event) -> {
            if (!budgetService.isDouble(newAmount, newAmount.getText())) {
                notification.setText(newAmount.getText() + " ei ole luku");
                return;
            }
            if (budgetService.updateBudgetAmount(Double.parseDouble(newAmount.getText()), user, budget.getName())) {
                budget = budgetService.getBudgetByName(budget.getName(), user);
                budgetInfoScene();
            }
        });
    }

}
