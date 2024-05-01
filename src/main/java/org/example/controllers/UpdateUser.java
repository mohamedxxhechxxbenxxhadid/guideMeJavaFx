package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.services.ServiceUser;

import java.io.IOException;

public class UpdateUser {

    @FXML
    private TextField tfFulname;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAdress;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfnumtel;

    @FXML
    private ComboBox<UserRole> tfRole;

    ServiceUser sp  = new ServiceUser();


    private  User user;
    public void setUserToUpdate(User user) {
        this.user = user;

        // Afficher les détails de l'utilisateur dans les champs
        tfAdress.setText(user.getAdress());
        tfnumtel.setText(user.getPhone_numer());
        tfFulname.setText(user.getFullname());
        tfEmail.setText(user.getEmail());
        //tfRole.setValue(user.getRole());
    }

    @FXML
    void afficherUser(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));

        try {
            Parent root = loader.load();
            AfficherUser ap = loader.getController();

            ap.setLbUsers(FXCollections.observableArrayList(sp.getAll()));
            tfFulname.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }

    @FXML
    void updatePersonne(ActionEvent event) {

        // Mettre à jour les détails de l'utilisateur avec les valeurs des champs
        user.setFullname(tfFulname.getText());
        user.setPhone_numer(tfnumtel.getText());
        user.setAdress(tfAdress.getText());
        user.setEmail(tfEmail.getText());
        user.setPassword(tfPassword.getText());
        //    user.setRole(tfRole.getValue());

        // Appeler la méthode de mise à jour de l'utilisateur dans votre service

        sp.update(user);

    }

}