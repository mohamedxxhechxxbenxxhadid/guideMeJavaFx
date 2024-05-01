package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Logement;

public class DetailedViewController {

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea labelDescription;

    @FXML
    private TextField labelNom;

    @FXML
    private TextField labelPlace;

    @FXML
    private TextField labelPrix;
    public void initData(Logement logement) {
        // Afficher les détails de l'élément sélectionné dans les éléments graphiques correspondants
        labelNom.setText(logement.getNom());
        labelDescription.setText(logement.getDescription());
        labelPlace.setText(logement.getPlace());
        labelPrix.setText(String.valueOf(logement.getPrix())); // Convertir int en String pour l'afficher dans un TextField

        // Charger et afficher l'image
        if (logement.getImage() != null && !logement.getImage().isEmpty()) {
            try {
                Image image = new Image(logement.getImage());
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer les exceptions si l'image ne peut pas être chargée
            }
        }
    }


}
