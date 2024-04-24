package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.models.Logement;
import org.example.services.ServiceLogement;
import java.io.IOException;
import org.example.utils.MyDb;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private Button btnUpdate;
    @FXML
    private ListView<Logement> listViewLogements;
    @FXML
    private Button refreshButton;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField searchField;
    @FXML
    public void initialize() {
        MyDb myDb = MyDb.getInstance();
        ServiceLogement = new ServiceLogement(myDb);
        listViewLogements.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update the text fields with the values of the selected logement
                tfNom.setText(newValue.getNom());
                tfDescription.setText(newValue.getDescription());
                tfPlace.setText(newValue.getPlace());
                tfPrice.setText(String.valueOf(newValue.getPrix())); // Convert price to string

                // Set the selected category in the combo box
                try {
                    String categoryName = ServiceLogement.getCategoryName(newValue.getCategorieId());
                    categorieComboBox.setValue(categoryName);
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle exception appropriately
                }
            }
        });

        try {
            populateComboBox();
            setupInputValidation();
            loadLogements();
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
            Logement newLogement = new Logement(0,nom, description, place, prix, categorieId);

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
    @FXML
    void UpdateLogement(ActionEvent event) {
        // Récupérer les valeurs mises à jour depuis les champs du formulaire
        String category = categorieComboBox.getValue();
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String place = tfPlace.getText();
        int prix;

        // Vérifier si tous les champs sont remplis
        if (nom.isEmpty() || description.isEmpty() || place.isEmpty() || tfPrice.getText().isEmpty() || category == null) {
            showAlert("Error", "Please fill in all fields and select a category.");
            return;
        }

        try {
            // Convertir le prix en entier
            prix = Integer.parseInt(tfPrice.getText());
            if (prix <= 0) {
                showAlert("Error", "Price must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input for price. Please enter a valid integer.");
            return;
        }

        try {
            // Récupérer l'identifiant de la catégorie à partir du nom de la catégorie
            int categorieId = ServiceLogement.getCategorieId(category);

            // Récupérer l'ID du logement à partir de l'objet sélectionné dans la liste
            Logement selectedLogement = listViewLogements.getSelectionModel().getSelectedItem();
            if (selectedLogement == null) {
                showAlert("Error", "Please select a logement to update.");
                return;
            }
            int logementId = selectedLogement.getId();

            // Créer un nouvel objet Logement avec les valeurs mises à jour
            Logement updatedLogement = new Logement(logementId, nom, description, place, prix, categorieId);
            // Mettre à jour le logement dans la base de données en utilisant le service ServiceLogement
            ServiceLogement.update(updatedLogement);
            System.out.println("Logement updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions appropriately
        }
    }

    @FXML
    void deleteLogement(ActionEvent event) {
        Logement selectedLogement = listViewLogements.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            try {
                // Supprimer le logement à la fois de la liste et de la base de données
                ServiceLogement.delete(selectedLogement);
                System.out.println("Logement deleted successfully.");
                // Remove the selected logement from the list view
                listViewLogements.getItems().remove(selectedLogement);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exceptions appropriately
            }
        } else {
            showAlert("Error", "Please select a logement to delete.");
        }
    }
    @FXML
    void refreshList(ActionEvent event) {
        System.out.println("Refreshing list...");
        try {
            // Fetch the updated list of Logements from the database
            List<Logement> logements = ServiceLogement.afficher();
            System.out.println("Retrieved logements from the database: " + logements.size());

            // Populate the ListView with the updated Logements
            ObservableList<Logement> items = FXCollections.observableArrayList(logements);
            listViewLogements.setItems(items);
            System.out.println("List view refreshed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
    private void loadLogements() throws SQLException {
        List<Logement> logements = ServiceLogement.afficher();
        listViewLogements.getItems().addAll(logements);
    }
    @FXML
    void SearchLogement(ActionEvent event) {
        // Retrieve the search query from the search field
        String query = searchField.getText().toLowerCase(); // Assuming searchField is the TextField for entering search queries

        // Filter the items in the ListView based on the search query
        ObservableList<Logement> filteredItems = listViewLogements.getItems().filtered(logement ->
                logement.getNom().toLowerCase().contains(query)
        );

        // Update the ListView to display the filtered items
        listViewLogements.setItems(filteredItems);
    }



}
