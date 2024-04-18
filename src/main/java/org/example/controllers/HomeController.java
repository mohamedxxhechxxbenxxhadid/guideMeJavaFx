package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import org.example.models.Reclamation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button changeToPosts;

    @FXML
    private Button changeToReclamation;

    @FXML
    private VBox generalId;

    @FXML
    private StackPane contentArea;



    @FXML
    void changeToPostsFunction(ActionEvent event) {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/showposts.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("not working");
        }

    }
    @FXML
    public void changeToReclamationFunction(ActionEvent event) {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/ajouterreclamation.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
            System.out.println(contentArea);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void changeToAddPostsFunction() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/addPost.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
            System.out.println(contentArea);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("not working changeToAddPostsFunction" );
        }

    }
    public void changeToPostDeatils(){
        System.out.println("this function also workss");
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/postdetails.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
            System.out.println(contentArea);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
