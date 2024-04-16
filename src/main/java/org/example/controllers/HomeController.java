package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button changeToPosts;

    @FXML
    private Button changeToReclamation;

    @FXML
    private Text homeTitleId;

    @FXML
    private StackPane contentArea;

    @FXML
    void changeToPostsFunction(ActionEvent event) {
        System.out.println("boom");
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/addPost.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
