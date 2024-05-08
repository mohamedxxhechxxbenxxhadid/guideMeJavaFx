package org.example.test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.CategorieController;


public class MainFx extends Application {
    @Override
    public void start( Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/dashboard.fxml"));
     primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(new Scene(root,  1198 ,  700));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}
