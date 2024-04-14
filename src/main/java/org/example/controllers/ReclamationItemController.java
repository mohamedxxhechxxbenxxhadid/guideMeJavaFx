package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.models.Reclamation;

public class ReclamationItemController {

    @FXML
    private Label createdId;

    @FXML
    private Label descriptionId;

    @FXML
    private Label emailId;

    @FXML
    private Label nameId;

    @FXML
    private Label numberId;

    @FXML
    private Label titleId;

    private Reclamation reclamation;

    public void  setData(Reclamation reclamation){
        this.reclamation = reclamation ;
        titleId.setText(reclamation.getTitre());
        numberId.setText(reclamation.getId().toString());
        nameId.setText(reclamation.getName());
    }
}
