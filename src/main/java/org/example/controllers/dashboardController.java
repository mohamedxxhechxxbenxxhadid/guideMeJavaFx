package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class dashboardController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button SwitchToTransport;
    @FXML
    private Button SwitchToCategorie;
    @FXML
    private Button SwitchToHome;
    @FXML
    private Button SwitchToLogement;
    //switch from dashboard to categorie
    @FXML
    void OnSwitchToCategorie(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/categorie.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //switch from dashboard to logement
    @FXML
    void OnSwitchToLogement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/logement.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void OnSwitchToHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/User.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void OnSwitchToTransport(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Transport.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}