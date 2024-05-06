package org.example.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.models.categorie;
import org.example.services.ServiceCategorie;
import java.io.IOException;
import java.sql.*;


public class CategorieController  {
    ServiceCategorie sR = new ServiceCategorie();
Connection con = null;
PreparedStatement st = null;
ResultSet rs = null;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button BtnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;
    @FXML
    private ListView<String> listViewCategories;
    @FXML
    private Button refreshButton;
    @FXML
    private Button btnUpdate;

    @FXML
    private Button categorieScene;

    @FXML
    public ComboBox<String> ComboB;

    @FXML
    private TextField tfType;
//Crud categorie
    @FXML
    void ClearField(ActionEvent event) {

    }
    @FXML
    void CreatCategorie(ActionEvent event) {
        String type = tfType.getText();
        categorie categorie = new categorie(type);
        try {
            sR.add(categorie);
            System.out.println("Category added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void DeleteCategorie(ActionEvent event) {
    }
    @FXML
    void UpdateCategorie(ActionEvent event) {
    }

    //switch from categorie to dashboard
    @FXML
    void OnSwitchBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void populateComboBox(ActionEvent event) {

    }

    @FXML
    void refreshList(ActionEvent event) {

    }
}






