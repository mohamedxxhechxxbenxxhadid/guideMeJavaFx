package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root );
            try{
                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            primaryStage.setTitle("Gestion Personne");
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

}
/*package org.example.test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.CategorieController;

import java.io.IOException;


public class MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/dashboard.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
*/