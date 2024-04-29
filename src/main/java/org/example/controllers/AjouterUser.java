package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.services.ServiceUser;


import java.io.IOException;

public class AjouterUser {
    ServiceUser su  = new ServiceUser();
    @FXML
    private TextField tfFullname;

    @FXML
    private TextField tfPhonenumber;

    @FXML
    private TextField tfAdress;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private ComboBox<String> tfRole;

    @FXML
    void afficherUser(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));

        try {
            Parent root = loader.load();
            AfficherUser au = loader.getController();

            au.setLbUsers(FXCollections.observableArrayList(su.getAll()));
            tfEmail.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
    @FXML
    void ajouterUser(ActionEvent event) {

        User u = new User();

        String fullname = tfFullname.getText().trim();
        if (fullname.isEmpty()) {
            // Afficher un message d'erreur
            System.out.println("Fullname cannot be empty");
            return; // Arrêter le traitement car le fullname est invalide
        } else if (fullname.length() > 20) {
            // Afficher un message d'erreur pour une longueur de fullname dépassée
            System.out.println("Fullname cannot exceed 20 characters");
            return; // Arrêter le traitement car le fullname est invalide
        } else if (!Character.isLetter(fullname.charAt(0))) {
            // Afficher un message d'erreur pour un fullname ne commençant pas par une lettre
            System.out.println("Fullname must start with a letter");
            return; // Arrêter le traitement car le fullname est invalide
        }
        u.setFullname(fullname);

        // Valider le numéro de téléphone
        String phoneNumber = tfPhonenumber.getText().trim();
        if (phoneNumber.isEmpty()) {
            // Afficher un message d'erreur
            System.out.println("Phone number cannot be empty");
            return; // Arrêter le traitement car le numéro de téléphone est invalide
        } else if (!phoneNumber.matches("[0-9]{8}")) {
            // Afficher un message d'erreur pour un numéro de téléphone invalide
            System.out.println("Phone number must contain 8 digits");
            return; // Arrêter le traitement car le numéro de téléphone est invalide
        }
        u.setPhone_numer(phoneNumber);

        // Valider l'adresse email
        String email = tfEmail.getText().trim();
        if (email.isEmpty()) {

            // Afficher un message d'erreur
            System.out.println("Email cannot be empty");
            return; // Arrêter le traitement car l'email est invalide
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            // Afficher un message d'erreur pour un email invalide
            System.out.println("Invalid email format");
            return; // Arrêter le traitement car l'email est invalide
        }

        u.setEmail(email);

        // Valider le mot de passe
        String password = tfPassword.getText().trim();
        if (password.isEmpty()) {
            // Afficher un message d'erreur
            System.out.println("Password cannot be empty");
            return; // Arrêter le traitement car le mot de passe est invalide
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$")) {
            // Afficher un message d'erreur pour un mot de passe invalide
            System.out.println("Password must contain at least one digit, one letter, and be at least 8 characters long");
            return; // Arrêter le traitement car le mot de passe est invalide
        }
        u.setPassword(password);
        // Valider le rôle
        String role = tfRole.getValue();
        if (role == null || role.isEmpty()) {
            // Afficher un message d'erreur
            System.out.println("Role cannot be empty");
            return; // Arrêter le traitement car le rôle est invalide
        }
        u.setRole(UserRole.valueOf(role));

        // Valider l'adresse
        String address = tfAdress.getText().trim();
        if (address.isEmpty()) {
            // Afficher un message d'erreur
            System.out.println("Address cannot be empty");
            return; // Arrêter le traitement car l'adresse est invalide
        }
        u.setAdress(address);

        // Définir les autres propriétés de l'utilisateur
        u.setIs_verified(true);
        u.setIs_activated(true);

        // Si toutes les vérifications passent, ajouter l'utilisateur
        su.add(u);

    }
    @FXML
    public void initialize() {
        tfRole.setItems(FXCollections.observableArrayList("ROLE_ADMIN", "ROLE_USER"));
        tfRole.setValue("ROLE_ADMIN"); // Set default value

    }

}
