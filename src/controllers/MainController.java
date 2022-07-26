package controllers;

import impls.ExpenseBook;
import impls.IncomeBook;
import impls.PlanBook;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.*;
import impls.AccountBook;
import utils.TypeOfAccount;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainController implements Initializable {

    private ExpenseBook expenseBook = new ExpenseBook();
    private AccountBook accountBook = new AccountBook();
    private IncomeBook incomeBook = new IncomeBook();
    private PlanBook planBook = new PlanBook();

    // Item table Expense
    @FXML
    private TableView tableExpenseBook;
    @FXML
    private TableColumn<Expense, String> columnNameExpense;
    @FXML
    private TableColumn<Expense, Double> columnSumExpense;
    @FXML
    private TableColumn<Expense, LocalDate> columnDateExpense;
    @FXML
    private TableColumn<Expense, Account> columnAccountExpense;
    @FXML
    private Label resultExpense;

    // Item table Income
    @FXML
    private TableView tableIncomeBook;
    @FXML
    private TableColumn<Income, String> columnNameIncome;
    @FXML
    private TableColumn<Income, Double> columnSumIncome;
    @FXML
    private TableColumn<Income, LocalDate> columnDateIncome;
    @FXML
    private TableColumn<Income, Account> columnAccountIncome;
    @FXML
    private Label resultIncome;

    // Item all accounts block balance
    @FXML private CheckBox cashAccounts;
    @FXML private CheckBox nonCashAccounts;
    @FXML private Label structureAllBalance;
    @FXML private Label addAccountLabel;

    // Item change and remove account
    @FXML
    private ChoiceBox<Account> choiceBoxAccount;
    @FXML
    private Label choiceBoxAccountLabel;
    @FXML
    private Button btnChangeAccount;
    @FXML
    private Button btnRemoveAccount;

    // Item addAccount
    @FXML
    private ChoiceBox<TypeOfAccount> choiceBoxType;
    @FXML
    private TextField textNameAccount;
    @FXML
    private TextField textBalanceAccount;

    // Item Plan tables
    @FXML
    private TextField textNamePlanExp;
    @FXML
    private TextField textNamePlanInc;
    @FXML
    private TextField textSumPlanExp;
    @FXML
    private TextField textSumPlanInc;
    @FXML
    private TableView tablePlanExpense;
    @FXML
    private TableView tablePlanIncome;
    @FXML
    private Label resultPlanExp;
    @FXML
    private Label resultPlanInc;
    @FXML
    private TableColumn<PlanExpense, String> columnPlanExpName;
    @FXML
    private TableColumn<PlanExpense, Double> columnPlanExpSum;
    @FXML
    private TableColumn<PlanIncome, String> columnPlanIncName;
    @FXML
    private TableColumn<PlanIncome, Double> columnPlanIncSum;

    // Item search income / expense
    @FXML private CustomTextField txtSearchInc;
    @FXML private CustomTextField txtSearchExp;


    // Item test fill system
    @FXML
    private Button btnTestFill;

    // fields for expense change window
    private Parent fxmlEditExp;
    private FXMLLoader fxmlLoaderExp = new FXMLLoader();
    private EditExpenseController editExpenseController;
    private Stage editDialogStageExp;
    public static Expense oldExp = new Expense();

    // fields for income change window
    private Parent fxmlEditInc;
    private FXMLLoader fxmlLoaderInc = new FXMLLoader();
    private EditIncomeController editIncomeController;
    private Stage editDialogStageInc;
    public static Income oldInc = new Income();

    // fields for account change window
    private Parent fxmlEditAccount;
    private FXMLLoader fxmlLoaderAcc = new FXMLLoader();
    private EditAccountController editAccountController;
    private Stage editDialogStageAcc;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Stage mainStage;

    private ObservableList<Income> backupListIncome = FXCollections.observableArrayList();
    private ObservableList<Expense> backupListExpense = FXCollections.observableArrayList();

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    // open window editExpense
    public void actionBtnExpense(ActionEvent actionEvent) {
        // get event
        Object source = actionEvent.getSource();

        // checked event (button or not)
        if (!(source instanceof Button btnClick)) {
            return;
        }

        // get string in table
        Expense selectedExpense = (Expense) tableExpenseBook.getSelectionModel().getSelectedItem();

        // input value validation
        inputValidation(editExpenseController.txtSum);

        // action on selected button
        switch (btnClick.getId()) {
            case "btnAddExpense" -> {
                editExpenseController.fillChoiceBox();
                editExpenseController.setExpense(new Expense());
                showDialogExp();
                expenseBook.add(editExpenseController.getExpense());
                backupListExpense.add(editExpenseController.getExpense());
            }
            case "btnEditExpense" -> {
                if (!checkedExpenseIsSelected(selectedExpense)) {
                    return;
                }
                editExpenseController.setExpense(selectedExpense);
                setValueOldExp(selectedExpense);
                editExpenseController.fillChoiceBox();
                showDialogExp();
            }
            case "btnDeleteExpense" -> {
                if (!checkedExpenseIsSelected(selectedExpense)) {
                    return;
                }
                expenseBook.delete(selectedExpense);
                backupListExpense.remove(selectedExpense);
            }
        }
    }

    // open window editIncome
    public void actionBtnIncome(ActionEvent actionEvent){

        Object source = actionEvent.getSource();

        if (!(source instanceof Button btnClick)) {
            return;
        }

        Income selectedIncome = (Income) tableIncomeBook.getSelectionModel().getSelectedItem();

        inputValidation(editIncomeController.txtIncSum);

        switch (btnClick.getId()) {
            case "btnAddIncome" -> {
                editIncomeController.fillChoiceBox();
                editIncomeController.setIncome(new Income());
                showDialogInc();
                incomeBook.add(editIncomeController.getIncome());
                backupListIncome.add(editIncomeController.getIncome());
            }
            case "btnEditIncome" -> {
                if (!checkedIncomeIsSelected(selectedIncome)) {
                    return;
                }
                editIncomeController.fillChoiceBox();
                editIncomeController.setIncome(selectedIncome);
                setValueOldInc(selectedIncome);
                showDialogInc();
            }
            case "btnDeleteIncome" -> {
                if (!checkedIncomeIsSelected(selectedIncome)) {
                    return;
                }
                incomeBook.delete(selectedIncome);
                backupListIncome.remove(selectedIncome);
            }
        }

    }

    // add new account in system
    public void actionBtnAddNewAccount() {
        if (!checkedInputAccValue()) {
            return;
        }
        String nameAccount = textNameAccount.getText();
        double setBalance = Double.parseDouble(textBalanceAccount.getText());
        TypeOfAccount type = choiceBoxType.getValue();
        Account acc = new Account(nameAccount, setBalance, type);

        addAccountLabel.setText("Создан новый счет/кошелек:\n" + acc + ",  баланс: " + acc.getBalance());

        // add new account in choiceBox
        choiceBoxAccount.getItems().add(acc);

    }

    // method change and remove account
    public void actionBtnAccount(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button btnClick)) {
            return;
        }

        Account selectedAccount = choiceBoxAccount.getValue();

        inputValidation(editAccountController.getTxtSumAccount());

        // remove account
        if (btnClick.getId().equals(btnRemoveAccount.getId())) {
            if (!checkedAccountIsSelected(selectedAccount)) {
                return;
            }
            if (!checkedAccountActivity(selectedAccount)) {
                return;
            }
            choiceBoxAccount.getItems().remove(selectedAccount);
            Account.getAccounts().remove(selectedAccount);
            choiceBoxAccountLabel.setText("Счет/кошелек " + selectedAccount + " удален");
        }
        // change account
        else if (btnClick.getId().equals(btnChangeAccount.getId())) {
            if (!checkedAccountIsSelected(selectedAccount)) {
                return;
            }
            editAccountController.setAccount(selectedAccount);
            editAccountController.fillChoiceBoxTypeAccount();
            showDialogChangeAccount();
            choiceBoxAccount.getItems().clear();
            choiceBoxAccount.getItems().addAll(Account.getAccounts());
        }
    }

    // method show balance all account
    public void actionBtnRefreshBalance() {
        StringBuilder res = new StringBuilder();
        if (cashAccounts.isSelected()) {
            res.append(accountBook.showBalanceCashAccount());
        } else if (nonCashAccounts.isSelected()) {
            res.append(accountBook.showBalanceNonCashAccount());
        } else {
            res.append(accountBook.showBalanceStartup());
        }
        structureAllBalance.setText(res.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        creatingTables();
        loaderModalWindow();

        // set start value
        choiceBoxAccountLabel.setText("");
        addAccountLabel.setText("");

        // fill choiceBoxAccount
        choiceBoxAccount.getItems().addAll(Account.getAccounts());

        // fill choiceBoxType
        choiceBoxType.getItems().addAll(TypeOfAccount.NON_CASH, TypeOfAccount.CASH);
        choiceBoxType.setValue(TypeOfAccount.CASH);

        // fill structureAllBalance label
        structureAllBalance.setText(accountBook.showBalanceStartup());

        // checking character input in the amount field
        inputValidation(textBalanceAccount);
        inputValidation(textSumPlanExp);
        inputValidation(textSumPlanInc);

        // fill label results
        updateLabelResultPlanIncome();
        updateLabelResultPlanExpense();
        updateLabelResultIncome();
        updateLabelResultExpense();

        // search income/expense
        setupClearButtonField(txtSearchInc);
        setupClearButtonField(txtSearchExp);

        listenerSystem();

    }

    // show dialog windows
    private void showDialogExp() {
        // initialized once
        if (editDialogStageExp == null) {
            editDialogStageExp = new Stage();
            editDialogStageExp.setTitle("Редактирование записи");
            editDialogStageExp.setMinWidth(467);
            editDialogStageExp.setMinHeight(199);
            editDialogStageExp.setResizable(false);
            editDialogStageExp.setScene(new Scene(fxmlEditExp));
            editDialogStageExp.initModality(Modality.WINDOW_MODAL);
            editDialogStageExp.initOwner(mainStage);
        }

        editDialogStageExp.showAndWait();
    }

    private void showDialogInc() {
        // initialized once
        if (editDialogStageInc == null) {
            editDialogStageInc = new Stage();
            editDialogStageInc.setTitle("Редактирование записи");
            editDialogStageInc.setMinWidth(467);
            editDialogStageInc.setMinHeight(199);
            editDialogStageInc.setResizable(false);
            editDialogStageInc.setScene(new Scene(fxmlEditInc));
            editDialogStageInc.initModality(Modality.WINDOW_MODAL);
            editDialogStageInc.initOwner(mainStage);
        }

        editDialogStageInc.showAndWait();
    }

    private void showDialogChangeAccount() {
        // initialized once
        if (editDialogStageAcc == null) {
            editDialogStageAcc = new Stage();
            editDialogStageAcc.setTitle("Редактирование счета/кошелька");
            editDialogStageAcc.setMinWidth(467);
            editDialogStageAcc.setMinHeight(144);
            editDialogStageAcc.setResizable(false);
            editDialogStageAcc.setScene(new Scene(fxmlEditAccount));
            editDialogStageAcc.initModality(Modality.WINDOW_MODAL);
            editDialogStageAcc.initOwner(mainStage);
        }
        editDialogStageAcc.showAndWait();
    }

    // fill label result
    private void updateLabelResultExpense() {
        resultExpense.setText("Сумма расходов: " + String.format("%10.2f", expenseBook.resultSumExpense()));
    }

    private void updateLabelResultIncome() {
        resultIncome.setText("Сумма доходов: " + String.format("%10.2f", incomeBook.resultSumIncome()));
    }

    private void updateLabelResultPlanIncome() {
        resultPlanInc.setText("Итого: " + planBook.resultSumPlanIncome());
    }

    private void updateLabelResultPlanExpense() {
        resultPlanExp.setText("Итого: " + planBook.resultSumPlanExpense());
    }

    // save fields Income and Expense
    private void setValueOldInc(Income income) {
        oldInc.setDate(income.getDate());
        oldInc.setName(income.getName());
        oldInc.setSum(income.getSum());
        oldInc.setAccount(income.getAccount());
    }

    private void setValueOldExp(Expense expense) {
        oldExp.setDate(expense.getDate());
        oldExp.setName(expense.getName());
        oldExp.setSum(expense.getSum());
        oldExp.setAccount(expense.getAccount());
    }

    // add and remove PlanIncome, PlanExpense
    public void actionBtnAddNewPlanIncome() {
        if (!checkedInputPlanIncValue()) {
            return;
        }
        planBook.getPlanIncomes().add(new PlanIncome(Double.parseDouble(textSumPlanInc.getText()),
                textNamePlanInc.getText()));
    }

    public void actionBtnAddNewPlanExpense() {
        if (!checkedInputPlanExpValue()) {
            return;
        }
        planBook.getPlanExpenses().add(new PlanExpense(Double.parseDouble(textSumPlanExp.getText()),
                textNamePlanExp.getText()));
    }

    public void actionBtnDeletePlanIncome() {
        if (!checkedPlanIncomeIsSelected((PlanIncome) tablePlanIncome.getSelectionModel().getSelectedItem())) {
            return;
        }
        int selectID = tablePlanIncome.getSelectionModel().getSelectedIndex();
        planBook.getPlanIncomes().remove(selectID);
    }

    public void actionBtnDeletePlanExpense() {
        if (!checkedPlanExpenseIsSelected((PlanExpense) tablePlanExpense.getSelectionModel().getSelectedItem())) {
            return;
        }

        int selectID = tablePlanExpense.getSelectionModel().getSelectedIndex();
        planBook.getPlanExpenses().remove(selectID);
    }

    // input validation textField sum regex
    private void inputValidation(TextField textField) {
        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches())
                textField.setText(oldValue);
        });
    }

    // modal window loader
    private void loaderModalWindow() {
        // account
        /*
           load the file at the beginning once with load and get the controller
         */
        try {
            fxmlLoaderAcc.setLocation(getClass().getResource("/fxml/editAccount.fxml"));
            fxmlEditAccount = fxmlLoaderAcc.load();
            editAccountController = fxmlLoaderAcc.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // income
        try {
            fxmlLoaderInc.setLocation(getClass().getResource("/fxml/editIncome.fxml"));
            fxmlEditInc = fxmlLoaderInc.load();
            editIncomeController = fxmlLoaderInc.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // expense
        try {
            fxmlLoaderExp.setLocation(getClass().getResource("/fxml/editExpense.fxml"));
            fxmlEditExp = fxmlLoaderExp.load();
            editExpenseController = fxmlLoaderExp.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // creating tables
    private void creatingTables() {

        // creating table income
        columnNameIncome.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSumIncome.setCellValueFactory(new PropertyValueFactory<>("sum"));
        columnDateIncome.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnAccountIncome.setCellValueFactory(new PropertyValueFactory<>("account"));

        // set format date in table income
        columnDateIncome.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean dateIsEmpty) {
                    super.updateItem(date, dateIsEmpty);
                    if ( date == null || dateIsEmpty) {
                        setText(null);
                    } else {
                        setText(formatter.format(date));
                    }
            }
        });

        // creating table expense
        columnNameExpense.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSumExpense.setCellValueFactory(new PropertyValueFactory<>("sum"));
        columnDateExpense.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnAccountExpense.setCellValueFactory(new PropertyValueFactory<>("account"));

        // set format date in table expense
        columnDateExpense.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean dateIsEmpty) {
                super.updateItem(date, dateIsEmpty);
                if ( date == null || dateIsEmpty) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });

        // creating tables plan income and plan expense
        columnPlanIncName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPlanIncSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        columnPlanExpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPlanExpSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        tablePlanIncome.setItems(planBook.getPlanIncomes());
        tablePlanExpense.setItems(planBook.getPlanExpenses());
        try {
            tableExpenseBook.setItems(expenseBook.getExpenseList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tableIncomeBook.setItems(incomeBook.getIncomeList());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void listenerSystem() {
        // fill result sum all expense
        expenseBook.getExpenseList().addListener((ListChangeListener<Expense>) c -> updateLabelResultExpense());

        // fill result sum all income
        incomeBook.getIncomeList().addListener((ListChangeListener<Income>) c -> updateLabelResultIncome());

        // fill result sum all planExpense and planIncome
        planBook.getPlanExpenses().addListener((ListChangeListener<PlanExpense>) c -> updateLabelResultPlanExpense());
        planBook.getPlanIncomes().addListener((ListChangeListener<PlanIncome>) c -> updateLabelResultPlanIncome());

        // doubleClick in table
        tableIncomeBook.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    editIncomeController.fillChoiceBox();
                    editIncomeController.setIncome((Income) tableIncomeBook.getSelectionModel().getSelectedItem());
                    setValueOldInc((Income) tableIncomeBook.getSelectionModel().getSelectedItem());
                    showDialogInc();
                }
            }
        });

        tableExpenseBook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    editExpenseController.fillChoiceBox();
                    editExpenseController.setExpense((Expense) tableExpenseBook.getSelectionModel().getSelectedItem());
                    setValueOldExp((Expense) tableExpenseBook.getSelectionModel().getSelectedItem());
                    showDialogExp();
                }
            }
        });

    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionBtnSearchInc() {
        incomeBook.getIncomeList().clear();

        for (Income income : backupListIncome) {
            if (income.getName().toLowerCase().contains(txtSearchInc.getText().toLowerCase()) ||
                    String.valueOf(income.getSum()).toLowerCase().contains(txtSearchInc.getText().toLowerCase()) ||
                    income.getAccount().getName().toLowerCase().contains(txtSearchInc.getText().toLowerCase())) {
                incomeBook.getIncomeList().add(income);
            }
        }
    }

    public void actionBtnSearchExp() {
        expenseBook.getExpenseList().clear();

        for (Expense expense : backupListExpense) {
            if (expense.getName().toLowerCase().contains(txtSearchExp.getText().toLowerCase()) ||
                    String.valueOf(expense.getSum()).toLowerCase().contains(txtSearchExp.getText().toLowerCase()) ||
                    expense.getAccount().getName().toLowerCase().contains(txtSearchExp.getText().toLowerCase())) {
                expenseBook.getExpenseList().add(expense);
            }
        }
    }

    // verification methods
    private boolean checkedAccountIsSelected(Account selectedAccount) {
        if (selectedAccount == null) {
            DialogManager.showErrorDialog();
            return false;
        }
        return true;
    }

    private boolean checkedAccountActivity(Account selectedAccount) {
        int occurrences = 0;
        for (Expense exp : expenseBook.getExpenseList()) {
            if (exp.getAccount().equals(selectedAccount)) {
                occurrences++;
            }
        }
        for (Income inc : incomeBook.getIncomeList()) {
            if (inc.getAccount().equals(selectedAccount)) {
                occurrences++;
            }
        }
        if (occurrences > 0) {
            DialogManager.showInfoDialog("По " + selectedAccount + " есть доходы/расходы. Удалить не возможно.");
            return false;
        }
        return true;
    }

    private boolean checkedIncomeIsSelected(Income selectedIncome) {
        if (selectedIncome == null) {
            DialogManager.showErrorDialog();
            return false;
        }
        return true;
    }

    private boolean checkedExpenseIsSelected(Expense selectedExp) {
        if (selectedExp == null) {
            DialogManager.showErrorDialog();
            return false;
        }
        return true;
    }

    private boolean checkedPlanIncomeIsSelected(PlanIncome selectedPlanInc) {
        if (selectedPlanInc == null) {
            DialogManager.showErrorDialog();
            return false;
        }
        return true;
    }

    private boolean checkedPlanExpenseIsSelected(PlanExpense selectedPlanExp) {
        if (selectedPlanExp == null) {
            DialogManager.showErrorDialog();
            return false;
        }
        return true;
    }

    private boolean checkedInputAccValue() {
        if (textNameAccount.getText().trim().length() == 0 ||
                textBalanceAccount.getText().trim().length() == 0 ||
                choiceBoxType.getValue() == null) {
            DialogManager.showInfoDialog();
            return false;
        }
        return true;
    }

    private boolean checkedInputPlanIncValue() {
        if (textNamePlanInc.getText().trim().length() == 0 ||
                textSumPlanInc.getText().trim().length() == 0) {
            DialogManager.showInfoDialog();
            return false;
        }
        return true;
    }

    private boolean checkedInputPlanExpValue() {
        if (textNamePlanExp.getText().trim().length() == 0 ||
                textSumPlanExp.getText().trim().length() == 0) {
            DialogManager.showInfoDialog();
            return false;
        }
        return true;
    }

    public void actionBtnTestFill() {
        Account cash = new Account("Наличные", 1000, TypeOfAccount.CASH);
        Account debitCard = new Account("Тинькофф деб", 2000.0, TypeOfAccount.NON_CASH);
        Account creditCard = new Account("Альфа кредитка", 20000, TypeOfAccount.NON_CASH);

        choiceBoxAccount.getItems().addAll(Account.getAccounts());

        Income salary = new Income("Зарплата", 12000, LocalDate.of(2022, 6, 10),
                debitCard);
        Income gift = new Income("Подарок", 1000, LocalDate.of(2022, 6, 25),
                cash);
        Income premium = new Income("Премия", 5000, LocalDate.of(2022, 7, 1),
                creditCard);
        Income salary2 = new Income("Зарплата", 12500, LocalDate.of(2022, 7, 10),
                debitCard);

        incomeBook.getIncomeList().addAll(salary, gift, premium, salary2);

        // account movement
        for (Income inc : incomeBook.getIncomeList()) {
            for (Account acc : Account.getAccounts()) {
                if (acc.equals(inc.getAccount())) {
                    inc.move(acc);
                }
            }
        }

        Expense milk = new Expense("Молоко", 87.2, LocalDate.of(2022, 6, 12), cash);
        Expense fruit = new Expense("Фрукты", 780, LocalDate.of(2022, 6, 12), debitCard);
        Expense jeans = new Expense("Джинсы", 3890, LocalDate.of(2022, 6, 22), debitCard);
        Expense food = new Expense("Продукты", 1070, LocalDate.of(2022, 6, 28), creditCard);
        Expense tea = new Expense("Чай", 255.7, LocalDate.of(2022, 7, 6), cash);
        Expense stinks = new Expense("Бытовая химия", 1200, LocalDate.of(2022, 7, 7), debitCard);
        Expense cinema = new Expense("Кино", 750, LocalDate.of(2022, 7, 9), debitCard);
        Expense farmersMarket = new Expense("Продукты, фермерский рынок", 945.3, LocalDate.of(2022, 7, 10), cash);
        Expense benzine = new Expense("Бензин", 2100, LocalDate.of(2022, 7, 12), debitCard);
        Expense carWash = new Expense("Автомойка, полная без багажника", 1500, LocalDate.of(2022, 7, 12), debitCard);
        Expense electricity = new Expense("Электричество", 1000.2,
                LocalDate.of(2022, 6, 12), creditCard);

        expenseBook.getExpenseList().addAll(milk, fruit, tea,stinks,cinema,farmersMarket, electricity, benzine,jeans,food,carWash);

        // account movement
        for (Expense exp : expenseBook.getExpenseList()) {
            for (Account acc : Account.getAccounts()) {
                if (acc.equals(exp.getAccount())) {
                    exp.move(acc);
                }
            }
        }

        planBook.getPlanIncomes().add(new PlanIncome(100000.0, "дивиденды Газпром"));
        planBook.getPlanIncomes().add(new PlanIncome(500.0, "продажа стульев"));
        planBook.getPlanIncomes().add(new PlanIncome(10000.0, "зарплата"));

        planBook.getPlanExpenses().add(new PlanExpense(150000.0,"хочу на Бали"));
        planBook.getPlanExpenses().add(new PlanExpense(900.0,"страхование от НС"));

        backupListIncome.addAll(incomeBook.getIncomeList());
        backupListExpense.addAll(expenseBook.getExpenseList());

        btnTestFill.setDisable(true);

    }
}




