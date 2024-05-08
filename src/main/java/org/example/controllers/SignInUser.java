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
import org.example.models.UserRole;
import org.example.services.ServiceUser;

import static org.example.models.UserRole.ROLE_ADMIN;


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
                // Assuming you have a method to get the user's role

                try {
                    FXMLLoader loader;
                    Parent root;
                    Scene scene;

                    System.out.println(user.getRole());
                    System.out.println(user.getAdress());

                    if (ROLE_ADMIN.equals(user.getRole())){
                        // Redirection vers le tableau de bord (Dashboard) pour l'administrateur
                        loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                        root = loader.load();
                        // Ajouter ici toute logique spécifique à l'administrateur si nécessaire
                    } else {
                        // Redirection vers la page d'accueil (Home) pour les autres utilisateurs
                        loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
                        root = loader.load();
                        // Ajouter ici toute logique spécifique aux utilisateurs ordinaires si nécessaire
                    }

                    // Configuration du contrôleur si nécessaire
                    // Exemple pour récupérer le contrôleur du Dashboard (à adapter selon votre besoin)
                    if (ROLE_ADMIN.equals(user.getRole())) {
                        dashboardController dashboardController = loader.getController();
                        dashboardController.initData(user); // Passer l'utilisateur connecté au contrôleur du tableau de bord
                    }

                    scene = new Scene(root);

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
