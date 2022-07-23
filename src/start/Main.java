package start;

import controllers.MainController;
import impls.ExpenseBook;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("Financial Accounting System");

        primaryStage.setMaxHeight(440);
        primaryStage.setMaxWidth(700);
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(700);

        primaryStage.setScene(new Scene(root, 700, 440));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }

}
