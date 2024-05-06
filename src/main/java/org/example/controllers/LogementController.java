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
    @FXML
    void addLogement(ActionEvent event) {
        String category = categorieComboBox.getValue(); // Get selected category from combo box
        String nom = tfNom.getText(); // Get name from text field
        String description = tfDescription.getText(); // Get description from text field
        String place = tfPlace.getText(); // Get place from text field
        int prix = Integer.parseInt(tfPrice.getText()); // Get price from text field

        try {
            // Get the category ID corresponding to the selected category name
            int categorieId = ServiceLogement.getCategorieId(category);

            // Create a new Logement object with the retrieved category ID and other parameters
            Logement newLogement = new Logement(nom, description, place, prix, categorieId);

            // Add the new Logement to the database using the ServiceLogement class
            ServiceLogement.add(newLogement);
            System.out.println("Logement added successfully.");

            // Optionally, clear the form fields after adding the Logement
            clearFormFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    // Method to clear the form fields after adding a Logement
    private void clearFormFields() {
        categorieComboBox.setValue(null);
        tfNom.clear();
        tfDescription.clear();
        tfPlace.clear();
        tfPrice.clear();
    }

}
