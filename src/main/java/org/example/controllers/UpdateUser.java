package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUser {

    private Scene scene;

    @FXML
    private TextField tfFulname;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAdress;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfnumtel;

    @FXML
    private ComboBox<UserRole> tfRole;

    @FXML
    private ImageView imageView;

    ServiceUser sp  = new ServiceUser();
    private Image selectedImage;
    private String imageUrl;


    private Stage stage;
    FileChooser fileChooser = new FileChooser();



    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the stage object
        stage = new Stage();

    }

    private  User user;

    public void setUserToUpdate(User user) {
        this.user = user;



        // Afficher les détails de l'utilisateur dans les champs
        tfAdress.setText(user.getAdress());
        tfnumtel.setText(user.getPhone_numer());
        tfFulname.setText(user.getFullname());
        tfEmail.setText(user.getEmail());

        Image image = new Image(user.getImage());
        imageView.setImage(image);


        //tfRole.setValue(user.getRole());
    }


    @FXML
    void updatePersonne(ActionEvent event) {

        // Mettre à jour les détails de l'utilisateur avec les valeurs des champs
        user.setFullname(tfFulname.getText());
        user.setPhone_numer(tfnumtel.getText());
        user.setAdress(tfAdress.getText());
        user.setEmail(tfEmail.getText());
        user.setPassword(tfPassword.getText());
        System.out.println(imageUrl);
        user.setImage(imageUrl);


        //    user.setRole(tfRole.getValue());

        // Appeler la méthode de mise à jour de l'utilisateur dans votre service

        sp.update(user);

    }
    @FXML
    void OnSwitchBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherUser.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void getImage(ActionEvent event) {
        try {
            // Configure file chooser to only allow PNG and JPEG files
            fileChooser.setTitle("Choose Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );


            // Show file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                imageUrl = selectedFile.toURI().toString(); // Store image URL
                Image image = new Image(imageUrl);
                imageView.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}