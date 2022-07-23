package utils;

import javafx.scene.control.Alert;

public class DialogManager {

    public static void showInfoDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Внимание!");
        alert.setContentText("Все поля обязательны для заполнение");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText("Выберите запись");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static void showInfoDialog(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Внимание!");
        alert.setContentText(string);
        alert.setHeaderText("");
        alert.showAndWait();
    }

}