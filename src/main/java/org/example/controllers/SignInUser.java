package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.example.models.User;
import org.example.services.ServiceUser;



public class SignInUser {

    @FXML
    private Button btnsignin;

    @FXML
    private Button btnsignup;

    @FXML
    private TextField tfemail;

    @FXML
    private PasswordField tfmdp;

    @FXML
    private Button ForgetPassword;

    @FXML
    public void initialize() {

        // Action du bouton de connexion
        btnsignin.setOnAction(event -> {
            String email = tfemail.getText();
            String password = tfmdp.getText();

            // Vérifier les informations de connexion
            ServiceUser sp = new ServiceUser();
            User user = sp.getByEmailAndPassword(email, password);

            if (user != null) {
                // Authentification réussie
                // Redirection vers la page AjouterPersonne après l'authentification réussie
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
                    Parent root = loader.load();
                    AjouterUser ajoutUser = loader.getController();
                    // Vous pouvez passer l'utilisateur à ajouterPersonneController si nécessaire
                    Scene scene = new Scene(root);

                    // Obtention de la fenêtre actuelle (Stage)
                    Stage stage = (Stage) btnsignin.getScene().getWindow();

                    // Changement de la scène de la fenêtre actuelle
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                // Authentification échouée
                // Affichage d'un message d'erreur
                System.out.println("Echec de l'authentification. Vérifiez vos identifiants.");
            }
        });

        // Action du bouton d'inscription
        btnsignup.setOnAction(event -> {
            // Redirection vers la page d'inscription
            // Ouverture d'une nouvelle fenêtre ou chargement d'une nouvelle vue
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUpUser.fxml"));
                Parent root = loader.load();
                SignUpUser signUpUser = loader.getController();
                // Vous pouvez passer l'utilisateur à ajouterPersonneController si nécessaire
                Scene scene = new Scene(root);

                // Obtention de la fenêtre actuelle (Stage)
                Stage stage = (Stage) btnsignin.getScene().getWindow();

                // Changement de la scène de la fenêtre actuelle
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        ForgetPassword.setOnAction(event -> {
            // Redirection vers la page d'inscription
            // Ouverture d'une nouvelle fenêtre ou chargement d'une nouvelle vue
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassword.fxml"));
                Parent root = loader.load();
                ResetPassword resetPassword = loader.getController();
                // Vous pouvez passer l'utilisateur à ajouterPersonneController si nécessaire
                Scene scene = new Scene(root);

                // Obtention de la fenêtre actuelle (Stage)
                Stage stage = (Stage) ForgetPassword.getScene().getWindow();

                // Changement de la scène de la fenêtre actuelle
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }




}
