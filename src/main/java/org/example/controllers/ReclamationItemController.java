package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.example.models.Post;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;

public class ReclamationItemController {

    @FXML
    private Label dateId;

    @FXML
    private Label descriptionId;

    @FXML
    private Label emailId;

    @FXML
    private Label nameId;

    @FXML
    private Label titleId;

    public ShowReclamationController sRC ;

    private Reclamation reclamation;

    private ServiceReclamation sR ;

    public void  setData(Reclamation reclamation,ShowReclamationController sRC){
        this.reclamation = reclamation ;
        this.sRC = sRC ;
        dateId.setText(reclamation.getCreatedAt().toString());
        descriptionId.setText(reclamation.getMessage());
        emailId.setText(reclamation.getEmail());
        titleId.setText(reclamation.getTitre());
        nameId.setText(reclamation.getName());
    }

    public void DeleteItem(ActionEvent actionEvent) {
        try{
            this.sRC.remove(reclamation,this.sRC);
        }catch (Exception e){
            System.out.println("Delete item "+e.getMessage());
        }
    }

    public void SeeItemsDetails(ActionEvent actionEvent) {
    }
}
