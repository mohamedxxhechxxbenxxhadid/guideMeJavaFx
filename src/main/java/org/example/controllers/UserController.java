package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import org.example.models.Logement;
import org.example.services.ServiceLogement;
import org.example.utils.MyDb;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private GridPane logementGrid;

    private ServiceLogement serviceLogement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Instantiate the ServiceLogement service using your MyDb instance
            serviceLogement = new ServiceLogement(MyDb.getInstance());

            // Use the service to retrieve the accommodations from the database
            List<Logement> logements = serviceLogement.afficher();

            // Calculate the number of rows needed to display all accommodations
            int numRows = (int) Math.ceil((double) logements.size() / 3); // Assuming 3 accommodations per row

            // Clear existing row constraints
            logementGrid.getRowConstraints().clear();

            // Adjust the row constraints to accommodate the number of accommodations
            for (int i = 0; i < numRows; i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPrefHeight(Region.USE_COMPUTED_SIZE); // Use computed size
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

                // Set margins for the pane to create space between items
                Insets insets = new Insets(25); // Adjust the insets as needed
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
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
