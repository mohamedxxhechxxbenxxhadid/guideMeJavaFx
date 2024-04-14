package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.awt.*;
import java.sql.SQLException;

public class ReclamationController {
    ServiceReclamation sR = new ServiceReclamation();

    @FXML
    private TextArea descriptionId;

    @FXML
    private TextField emailId;

    @FXML
    private TextField nameId;

    @FXML
    private TextField phoneId;


    @FXML
    private TextField titleId;

    @FXML
    void AjouterReclamation(ActionEvent event) {
        Reclamation reclamation = new Reclamation();
        reclamation.setEmail(emailId.getText());
        reclamation.setMessage(descriptionId.getText());
        reclamation.setphon_number(phoneId.getText());
        reclamation.setTitre(titleId.getText());
        reclamation.setName(nameId.getText());
        reclamation.setCreatedAt();
        System.out.println(reclamation);
        try{
            sR.add(reclamation);
            this.AfficherReclamation();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AfficherReclamation() {

    }
}
