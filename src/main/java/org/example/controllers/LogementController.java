package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.models.Logement;
import org.example.services.ServiceLogement;
import java.io.IOException;
import org.example.utils.MyDb;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;

public class LogementController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ServiceLogement ServiceLogement;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private Button LogementScene;
    @FXML
    private TextArea tfDescription;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPlace;

    @FXML
    private TextField tfPrice;
    @FXML
    public void initialize() {
        MyDb myDb = MyDb.getInstance();
        ServiceLogement = new ServiceLogement(myDb);
        try {
            populateComboBox();
            setupInputValidation();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

    }
    @FXML
    void OnSwitchBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void populateComboBox() throws SQLException {
        List<String> categories = ServiceLogement.getAllCategories();
        categorieComboBox.getItems().addAll(categories);
    }
   /* @FXML
    void addLogement(ActionEvent event) {
        // Get the values from the input fields
        String category = categorieComboBox.getValue();
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String place = tfPlace.getText();
        String priceText = tfPrice.getText();

        // Check if any of the fields are empty
        if (category == null || nom.isEmpty() || description.isEmpty() || place.isEmpty() || priceText.isEmpty()) {
            // Display an alert message to the user indicating that all fields must be filled
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        try {
            int prix = Integer.parseInt(priceText);
            int categorieId = ServiceLogement.getCategorieId(category);
            Logement newLogement = new Logement(nom, description, place, prix, categorieId);
            ServiceLogement.add(newLogement);
            System.out.println("Logement added successfully.");
            clearFormFields();
        } catch (NumberFormatException e) {
            // Handle the case where the price input is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Price");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid price.");
            alert.showAndWait();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            // Optionally, display an error message to the user
        }
    }
*/

    // Method to clear the form fields after adding a Logement
    private void clearFormFields() {
        categorieComboBox.setValue(null);
        tfNom.clear();
        tfDescription.clear();
        tfPlace.clear();
        tfPrice.clear();
    }
    //controle de saisie
    private void setupInputValidation() {
        tfNom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateTextField(tfNom);
            }
        });
        tfPlace.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateTextField(tfPlace);
            }
        });
        tfPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePriceField(tfPrice);
            }
        });
    }

    private void validateTextField(TextField textField) {
        if (textField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
        }
    }
    private void validatePriceField(TextField textField) {
        try {
            int price = Integer.parseInt(textField.getText());
            if (price <= 0) {
                showAlert("Error", "Price must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input for price. Please enter a valid integer.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void addLogement(ActionEvent event) {
        // Get the values from the input fields
        String category = categorieComboBox.getValue();
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String place = tfPlace.getText();
        String priceText = tfPrice.getText();

        // Check if any of the fields are empty
        if (category == null || nom.isEmpty() || description.isEmpty() || place.isEmpty() || priceText.isEmpty()) {
            // Display an alert message to the user indicating that all fields must be filled
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return; // Exit the method early
        }

        // Validate nom and description to ensure they don't contain numbers
        if (!isValidInput(nom) || !isValidInput(description)) {
            // Display an alert message to the user indicating that nom and description should not contain numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Name and description should not contain numbers.");
            alert.showAndWait();
            return; // Exit the method early
        }

        try {
            // Parse the price input to an integer
            int prix = Integer.parseInt(priceText);

            // Get the category ID corresponding to the selected category name
            int categorieId = ServiceLogement.getCategorieId(category);

            // Create a new Logement object with the retrieved category ID and other parameters
            Logement newLogement = new Logement(nom, description, place, prix, categorieId);

            // Add the new Logement to the database using the ServiceLogement class
            ServiceLogement.add(newLogement);
            System.out.println("Logement added successfully.");

            // Optionally, clear the form fields after adding the Logement
            clearFormFields();
        } catch (NumberFormatException e) {
            // Handle the case where the price input is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Price");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid price.");
            alert.showAndWait();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            // Optionally, display an error message to the user
        }
    }

    // Method to validate input string to ensure it doesn't contain numbers
    private boolean isValidInput(String input) {
        // Regular expression to match any numeric characters
        String regex = ".*\\d.*";
        // Return false if the input contains any numeric characters, true otherwise
        return !input.matches(regex);
    }

}
