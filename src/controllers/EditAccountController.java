package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Account;
import utils.TypeOfAccount;
import utils.DialogManager;

public class EditAccountController {

    @FXML private ChoiceBox<TypeOfAccount> choiceBoxTypeAccount;
    @FXML private TextField txtNameAccount;
    @FXML private TextField txtSumAccount;

    public TextField getTxtSumAccount() {
        return txtSumAccount;
    }

    private Account account;

    public void setAccount(Account account){
        this.account = account;
        txtNameAccount.setText(account.getName());
        txtSumAccount.setText(String.valueOf(account.getBalance()));
        choiceBoxTypeAccount.setValue(account.getType());
    }

    @FXML
    public void actionClose(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void actionSave(ActionEvent event) {
        if(!checkInputAllValues()){
            return;
        }
        account.setBalance(Double.parseDouble(txtSumAccount.getText()));
        account.setName(txtNameAccount.getText());
        account.setType(choiceBoxTypeAccount.getValue());

        actionClose(event);
    }

    public void fillChoiceBoxTypeAccount(){
        choiceBoxTypeAccount.getItems().clear();
        choiceBoxTypeAccount.getItems().addAll(TypeOfAccount.CASH, TypeOfAccount.NON_CASH);
    }

    private boolean checkInputAllValues() {
        if (txtSumAccount.getText().trim().length()==0 ||
                txtNameAccount.getText().trim().length()==0 ||
            choiceBoxTypeAccount.getValue() == null){
            DialogManager.showInfoDialog();
            return false;
        }
        return true;
    }

}
