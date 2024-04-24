package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AjouterReclamationController {
    ServiceReclamation sR = new ServiceReclamation();

    @FXML
    private TextArea descriptionId;

    @FXML
    private Label descriptionLabelId;

    @FXML
    private TextField emailId;

    @FXML
    private Label emailLabelId;

    @FXML
    private TextField nameId;

    @FXML
    private Label nameLabelId;

    @FXML
    private TextField phoneId;

    @FXML
    private Label phoneNumberId;

    @FXML
    private TextField titleId;

    @FXML
    private Label titleLabelId;

    boolean nameB = false;
    boolean phoneB = false ;
    boolean emailB = false ;
    boolean titleB = false ;
    boolean descriptionB = false ;

    @FXML
    void AjouterReclamation(ActionEvent event) {
        if (emailId.getText().isBlank() || !this.validateEmail(emailId.getText())){
            emailLabelId.setVisible(true);
            emailB = false ;
            emailId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() && this.validateEmail(oldValue)) {
                    emailLabelId.setVisible(false);
                    emailB = true ;
                }
            }));
        }
        if(phoneId.getText().isBlank() || this.validateNumber(this.phoneId.getText())){
            phoneNumberId.setVisible(true);
            phoneB = false ;
            phoneId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() && this.validateNumber(oldValue)) {
                    phoneNumberId.setVisible(false);
                    phoneB = true ;
                }
            }));
        }
        if(nameId.getText().isBlank()){
            nameLabelId.setVisible(true);
            nameB = false ;
            nameId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() ) {
                    nameLabelId.setVisible(false);
                    nameB = true ;
                }
            }));
        }
        if(titleId.getText().isBlank()){
            titleLabelId.setVisible(true);
            titleB = true ;
            titleId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() ) {
                    titleLabelId.setVisible(false);
                    titleB = true ;
                }
            }));
        }
        if(descriptionId.getText().isBlank()){
            descriptionLabelId.setVisible(true);
            descriptionB = true ;
            descriptionId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() ) {
                    descriptionLabelId.setVisible(false);
                    descriptionB = true ;
                }
            }));
        }
        if(phoneB && emailB && descriptionB && titleB && nameB){
            Reclamation reclamation = new Reclamation();
            reclamation.setEmail(emailId.getText());
            reclamation.setMessage(descriptionId.getText());
            reclamation.setphon_number(phoneId.getText());
            reclamation.setTitre(titleId.getText());
            reclamation.setName(nameId.getText());
            reclamation.setCreatedAt();
            try{
                sR.add(reclamation);
                this.AfficherReclamation();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

    }

    public void AfficherReclamation() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        try {
            Parent root = loader.load();
            HomeController hC = loader.getController();
            phoneId.getScene().setRoot(root);
            hC.changeToShowReclamationFunction();

        } catch (IOException e) {
            System.out.println("affiche"+e.getMessage());
        }
    }
    public  boolean validateNumber(String input) {
        if (input != null && input.length() == 12) {
            if (input.startsWith("00216")) {
                for (int i = 5; i < input.length(); i++) {
                    if (!Character.isDigit(input.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    public boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
