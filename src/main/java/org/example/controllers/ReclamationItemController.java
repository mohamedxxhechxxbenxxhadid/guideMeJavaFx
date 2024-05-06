package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.models.Post;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;

import java.io.IOException;

public class ReclamationItemController {
    @FXML
    private Button answearButtonId;
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

    @FXML
    void goToAnswearPage(ActionEvent event) {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/answear.fxml"));
        try {
            Parent root = loader.load();
            AnswearController aC = loader.getController();
            aC.reclamation = this.reclamation ;
            aC.rC = this.sRC ;
            aC.rIC = this ;
            Scene scene = new Scene(root );
            try{
                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            newStage.setTitle(reclamation.getTitre());
            newStage .setScene(scene);
            newStage.show();
            aC.stage = newStage ;



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
