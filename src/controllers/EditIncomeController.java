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
import objects.Income;
import utils.DialogManager;

import static controllers.MainController.oldInc;

public class EditIncomeController {

    @FXML
    public Button btnIncOk;
    @FXML
    public Button btnIncCancel;
    @FXML
    public TextField txtIncSum;
    @FXML
    public TextField txtIncName;
    @FXML
    private DatePicker dataPickerInc;
    @FXML
    private ChoiceBox<Account> choiceBoxIncAccount;

    private Income income;

    // fill choiceBox account
    public void fillChoiceBox() {
        choiceBoxIncAccount.getItems().clear();
        for (Account account : Account.getAccounts()) {
            choiceBoxIncAccount.getItems().add(account);
        }
    }

    public void setIncome(Income income) {
        this.income = income;
        txtIncName.setText(income.getName());
        txtIncSum.setText(String.valueOf(income.getSum()));
        dataPickerInc.setValue(income.getDate());
        choiceBoxIncAccount.setValue(income.getAccount());
    }

    public Income getIncome() {
        return income;
    }

    public void actionSaveInc(ActionEvent actionEvent) {
        if(!checkInputAllValues()){
            return;
        }

        // set new value
        income.setName(txtIncName.getText());
        income.setSum(Double.parseDouble(txtIncSum.getText()));
        income.setDate(dataPickerInc.getValue());
        income.setAccount(choiceBoxIncAccount.getValue());

        // account movement
        income.move(income.getAccount());

        /* when changing the account, we delete the movement on the old account
        value if the change was in the amount or account
         */
        if(oldInc.getAccount() != null && income.getAccount() != null) {
            if (!(oldInc.getAccount().equals(income.getAccount()))
                    || oldInc.getSum() != income.getSum()) {
                oldInc.deleteMove(oldInc.getAccount());
            }
        }
        actionCloseInc(actionEvent);
    }

    public void actionCloseInc(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private boolean checkInputAllValues() {
        if (txtIncName.getText().trim().length()==0 ||
                txtIncSum.getText().trim().length()==0 ||
                choiceBoxIncAccount.getValue() == null ||
                dataPickerInc.getValue() == null){
            DialogManager.showInfoDialog();
            return false;
        }
        return true;
    }
}
