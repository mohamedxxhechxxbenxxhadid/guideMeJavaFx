package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class TransportController {

    private Stage stage;
    private File selectedFile;
    private String imagePath;
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea capacite;

    @FXML
    private TextField etat;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<?> listViewTransports;

    @FXML
    private TextField name;

    @FXML
    private TextField prix;

    @FXML
    private TextField searchField;

    @FXML
    void addTransport(ActionEvent event) {
        // Implement logic to add a transport
        String transportName = name.getText();
        String description = capacite.getText();
        String priceText = prix.getText();
        String state = etat.getText();

        // Validate input
        if (transportName.isEmpty() || description.isEmpty() || priceText.isEmpty() || state.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            // Add the transport using the provided information
            // Example: transportService.addTransport(new Transport(transportName, description, price, state));
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input for price. Please enter a valid number.");
        }
    }



    @FXML
    void deleteTransport(ActionEvent event) {
        // Implement logic to delete a transport
        Object selectedTransport = listViewTransports.getSelectionModel().getSelectedItem();
        if (selectedTransport != null) {

        } else {
            showAlert("Error", "Please select a transport to delete.");
        }
    }

    @FXML
    void getImage(MouseEvent event) {
        // Implement logic to get an image for the transport
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            imageView.setImage(new javafx.scene.image.Image(imagePath));
        }
    }

    @FXML
    void onItemSelected(MouseEvent event) {
        // Implement logic when an item is selected in the list view
    }

    @FXML
    void refreshList(ActionEvent event) {
        // Implement logic to refresh the list of transports

}
    @FXML
    void searchTransport(ActionEvent event) {
        // Implement logic to search for a transport
        String searchTerm = searchField.getText();

    }

    @FXML
    void updateTransport(ActionEvent event) {
        // Implement logic to update the selected transport
        Object selectedTransport = listViewTransports.getSelectionModel().getSelectedItem();
        if (selectedTransport != null) {
            // Get updated information from input fields
            String updatedName = name.getText();
            String updatedDescription = capacite.getText();
            String updatedPriceText = prix.getText();
            String updatedState = etat.getText();

            // Validate input
            if (updatedName.isEmpty() || updatedDescription.isEmpty() || updatedPriceText.isEmpty() || updatedState.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            try {
                double updatedPrice = Double.parseDouble(updatedPriceText);
                // Update the selected transport with the new information
                // Example: transportService.updateTransport(selectedTransport.getId(), new Transport(updatedName, updatedDescription, updatedPrice, updatedState));
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input for price. Please enter a valid number.");
            }
        } else {
            showAlert("Error", "Please select a transport to update.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
