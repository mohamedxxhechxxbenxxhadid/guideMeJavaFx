package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.services.ServiceUser;


import java.io.IOException;

public class SignUpUser {
    @FXML
    private ComboBox<String> crole;

    @FXML
    private Button signupbutton;

    @FXML
    private TextField tfemail;

    @FXML
    private PasswordField tfpassword;

    @FXML
    private TextField tffullname;

    @FXML
    private TextField tfadress;

    @FXML
    private TextField tftel;






    private ServiceUser sp= new ServiceUser();

    @FXML
    public void initialize() {
        crole.setItems(FXCollections.observableArrayList( "ROLE_ADMIN", "ROLE_USER"));
        crole.setValue("ROLE_ADMIN"); // Set default value

    }

    @FXML
    public void SignupButton(javafx.event.ActionEvent actionEvent)  {
        String role = crole.getValue();
        String email = tfemail.getText();
        String password = this.tfpassword.getText();
        String fullName = tffullname.getText();
        String adress = tfadress.getText();
        String phoneNumber = tftel.getText();

        // Crée un nouvel utilisateur avec les valeurs des champs
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAdress(adress);
        newUser.setFullname(fullName);
        newUser.setPhone_numer(phoneNumber);
        newUser.setRole(UserRole.valueOf(role));

        // Ajoute le nouvel utilisateur à la base de données
        sp.add(newUser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignInUser.fxml"));
        try {
            Parent root = loader.load();
            SignInUser signinController = loader.getController();
            // Utilisez le contrôleur signinController si nécessaire
            Scene scene = new Scene(root);

            // Obtention de la fenêtre actuelle (Stage)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changement de la scène de la fenêtre actuelle
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }

    @FXML
    public void SigninButton(javafx.event.ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignInUser.fxml"));
        try {
            Parent root = loader.load();
            SignInUser signinController = loader.getController();
            // Utilisez le contrôleur signinController si nécessaire
            Scene scene = new Scene(root);

            // Obtention de la fenêtre actuelle (Stage)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changement de la scène de la fenêtre actuelle
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


}
