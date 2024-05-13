package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.models.Logement;
import javafx.scene.image.Image;

public class LogementUserController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField searchField;
    // Method to initialize data
    // Method to receive Logement data from Logement2Controller
    public void initData(Logement logement) {
        // Check if logement is not null
        if (logement != null) {
            // Update UI elements with Logement data
            nameLabel.setText(logement.getNom());
            priceLabel.setText(String.valueOf(logement.getPrix()));
            descriptionLabel.setText(logement.getDescription());

            // Load and set the image
            loadImage(logement.getImage());
        } else {
            // Handle the case where logement is null
            // For example, display an error message or set default values for UI elements
        }
    }

    public void setData(Logement logement) {
        // Set the data to the UI elements
        nameLabel.setText(logement.getNom());
        priceLabel.setText(String.valueOf(logement.getPrix()));
        loadImage(logement.getImage()); // Load image
    }
    // Function to load and set an image
    private void loadImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Image imageObject = new Image(imagePath);
            imageView.setImage(imageObject);
        } else {
            // Handle the case where the image path is null or empty
            // For example, you could set a default image
            // imageView.setImage(defaultImage);
        }
    }


}

