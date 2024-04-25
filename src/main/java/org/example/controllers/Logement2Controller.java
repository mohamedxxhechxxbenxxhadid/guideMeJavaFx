package org.example.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.models.Logement;

public class Logement2Controller {

    @FXML
    private Label nomHotel;

    @FXML
    private Label prix;

    public void setData(Logement logement) {
        nomHotel.setText(logement.getNom());
      //  categorie.setText(logement.getCategorieId());
        prix.setText(String.valueOf(logement.getPrix()));
    }

}
