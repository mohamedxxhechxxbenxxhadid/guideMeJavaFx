package org.example.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.models.Logement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;

public class Logement2Controller {

    @FXML
    private Label nomHotel;
    @FXML
    private Pane pane;
    @FXML
    private Label prix;
    @FXML
    private ImageView imageView;
    //File imageFile = new File(Logement.getImage());
    private Logement logement; // Instance variable to store the Logement object
    String imagePath = "file:///C:/path/to/image.jpg";
    Image image = new Image(imagePath);

    public void setData(Logement logement) {
        nomHotel.setText(logement.getNom());
        prix.setText(String.valueOf(logement.getPrix()));
        String imagePath = logement.getImage(); // Access getImage() from the Logement instance
        loadImage(imagePath);
    }

    // Function to load and set an image
    public void loadImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            Image imageObject = new Image(imageFile.toURI().toString());
            imageView.setImage(imageObject); // NullPointerException may occur here
        } else {
            // Handle the case where the image path is null or empty
            // For example, you could set a default image
            // imageView.setImage(defaultImage);
        }
    }
    @FXML
    private void handlePaneClick(MouseEvent event) {
        // You can put your logic here to handle the click event
        try {
            // Load the FXML file for the new page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/LogementUser.fxml"));
            Parent root = fxmlLoader.load();

            // Access the controller of the new FXML file
            LogementUserController controller = fxmlLoader.getController();

            // Pass the data to the controller and initialize it
            controller.initData(logement); // Assuming 'logement' is available in this context

            // Create a new scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = new Stage();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}






