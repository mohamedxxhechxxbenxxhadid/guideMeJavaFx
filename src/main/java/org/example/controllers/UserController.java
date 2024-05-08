package org.example.controllers;


import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.example.models.Logement;
import org.example.models.User;
import org.example.services.ServiceLogement;
import org.example.utils.MyDb;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class UserController implements Initializable {
    private Stage stage;
    private Scene scene;
    private User user;

    @FXML
    private GridPane logementGrid;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchField;
    private ServiceLogement serviceLogement;
    private List<Logement> logements;

    // Method to filter logements based on the search query
    private List<Logement> filterLogements(String query) {
        List<Logement> filteredLogements = new ArrayList<>();
        for (Logement logement : logements) {
            // Check if the logement name contains the search query
            if (logement.getNom().toLowerCase().contains(query.toLowerCase())) {
                filteredLogements.add(logement);
            }
        }
        return filteredLogements;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {


            // Instantiate the ServiceLogement service using your MyDb instance
            serviceLogement = new ServiceLogement(MyDb.getInstance());

            // Use the service to retrieve the accommodations from the database
            logements = serviceLogement.afficher();
            // Add listener to the text property of the search field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Filter logements based on the search query
                List<Logement> filteredLogements = filterLogements(newValue);

                // Update displayed content with filtered logements
                updateDisplay(filteredLogements);
            });

            // Instantiate the ServiceLogement service using your MyDb instance
            serviceLogement = new ServiceLogement(MyDb.getInstance());

            // Use the service to retrieve the accommodations from the database
            List<Logement> logements = serviceLogement.afficher();

            // Calculate the number of rows needed to display all accommodations
            int numRows = (int) Math.ceil((double) logements.size() / 3); // Assuming 3 accommodations per row

            // Clear existing row constraints
            logementGrid.getRowConstraints().clear();

            // Adjust the row constraints to accommodate the content
            for (int i = 0; i < numRows; i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(Priority.ALWAYS); // Allow rows to grow vertically
                logementGrid.getRowConstraints().add(rowConstraints);
            }

            // Variables to track column and row indices
            int column = 0;
            int row = 0;

            // Iterate through the retrieved accommodations and display them in your user interface
            for (Logement logement : logements) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Logement2.fxml"));
                Pane pane = fxmlLoader.load();

                Logement2Controller logement2Controller = fxmlLoader.getController();
                logement2Controller.setData(logement);

                // Set constraints for the pane to grow and fill the available space
                GridPane.setHgrow(pane, Priority.ALWAYS);
                GridPane.setVgrow(pane, Priority.ALWAYS);

                // Set margins for the pane to create space between items
                Insets insets = new Insets(15); // Adjust the insets as needed
                GridPane.setMargin(pane, insets);

                // Add the pane to the grid with appropriate column and row indices
                logementGrid.add(pane, column, row);

                // Increment column index
                column++;

                // Check if we need to move to the next row
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }

            // Set properties of the ScrollPane to fill available space
            scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
            scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void setUser(User user) {
        this.user = user;
        System.out.println(user.getFullname());
    }

    @FXML
    void OnSwitchToUpdate(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UpdateUser.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


            // Method to update displayed content with filtered logements
// Method to update displayed content with filtered logements
            private void updateDisplay (List < Logement > filteredLogements) {
                // Clear existing content from the grid pane
                logementGrid.getChildren().clear();

                // Add filtered logements to the grid pane
                int column = 0;
                int row = 0;

                // Reset grid gaps to ensure proper spacing
                logementGrid.setHgap(10); // Adjust as needed
                logementGrid.setVgap(10); // Adjust as needed

                for (Logement logement : filteredLogements) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Logement2.fxml"));
                    try {
                        Pane pane = fxmlLoader.load();
                        Logement2Controller logement2Controller = fxmlLoader.getController();
                        logement2Controller.setData(logement); // Pass Logement object here

                        // Add the pane to the grid pane
                        logementGrid.add(pane, column, row);

                        // Increment column index
                        column++;

                        // Check if we need to move to the next row
                        if (column == 3) {
                            column = 0;
                            row++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @FXML
            private void handleImageClick (MouseEvent event){
                // Handle image click event
                System.out.println("Image clicked");

                try {
                    // Load the FXML file for the new page
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/LogementUser.fxml"));
                    Parent root = fxmlLoader.load();

                    // Create a new scene with the loaded FXML content
                    Scene scene = new Scene(root);

                    // Get the stage information
                    Stage stage = (Stage) logementGrid.getScene().getWindow();

                    // Set the new scene on the stage
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



