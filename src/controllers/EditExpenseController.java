package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Account;
import objects.Expense;
import utils.DialogManager;

import static controllers.MainController.oldExp;

public class EditExpenseController {

    @FXML public Button btnOk;
    @FXML public Button btnCancel;
    @FXML public TextField txtSum;
    @FXML public TextField txtName;
    @FXML private DatePicker dataPicker;
    @FXML private ChoiceBox<Account> choiceBoxExpAccount;

    private Expense expense;

    public void setExpense(Expense expense){
        this.expense = expense;
        txtName.setText(expense.getName());
        txtSum.setText(String.valueOf(expense.getSum()));
        dataPicker.setValue(expense.getDate());
        choiceBoxExpAccount.setValue(expense.getAccount());
    }

    public Expense getExpense() {
        return expense;
    }

    // fill choiceBox account
    public void fillChoiceBox(){
        choiceBoxExpAccount.getItems().clear();
        for ( Account account: Account.getAccounts()) {
            choiceBoxExpAccount.getItems().add(account);
        }
    }

    public void actionSave(ActionEvent actionEvent){
        if(!checkInputAllValues()){
            return;
        }
        // set new value
        expense.setName(txtName.getText());
        expense.setSum(Double.parseDouble(txtSum.getText()));
        expense.setDate(dataPicker.getValue());
        expense.setAccount(choiceBoxExpAccount.getValue());

        // account movement
        // fix new income on the account
        expense.move(expense.getAccount());

        /* when changing the account, we delete the movement on the old account
        value if the change was in the amount or account
         */
        if(oldExp.getAccount() != null && expense.getAccount() != null ) {
            if (!(oldExp.getAccount().equals(expense.getAccount()))
                    || oldExp.getSum() != expense.getSum()) {
                oldExp.deleteMove(oldExp.getAccount());
            }
        }
        actionClose(actionEvent);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node)actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private boolean checkInputAllValues() {
        if (txtSum.getText().trim().length()==0 ||
                txtName.getText().trim().length()==0 ||
                choiceBoxExpAccount.getValue() == null ||
                dataPicker.getValue() == null){
            DialogManager.showInfoDialog();
            return false;
        }
        return true;
    }

}
