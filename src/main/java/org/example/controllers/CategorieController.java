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
import org.example.models.categorie;
import org.example.services.ServiceCategorie;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategorieController  {
    ServiceCategorie sR = new ServiceCategorie();
    private List<Integer> categoryIds = new ArrayList<>();

    Connection con = null;
PreparedStatement st = null;
ResultSet rs = null;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label CategorieLabel;
    @FXML
    private ListView<String> listViewCategories;
    @FXML
    private Button BtnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;
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
        // Get the selected item from the ListView
        String selectedCategory = listViewCategories.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            categorie category = new categorie(selectedCategory); // Create a categorie object
            try {
                // Delete the selected category
                ServiceCategorie serviceCategorie = new ServiceCategorie();
                serviceCategorie.delete(category);
                System.out.println("Category deleted successfully.");

                // Remove the selected item from the ListView
                listViewCategories.getItems().remove(selectedCategory);
            } catch (SQLException e) {
                System.err.println("Error deleting category: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No category selected.");
        }
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
       try {
            // Fetch the updated list of category names from the database
            List<String> categories = sR.populateComboBox();

            // Populate the ListView with the updated category names
            ObservableList<String> items = FXCollections.observableArrayList(categories);
            listViewCategories.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

    @FXML
    public void initialize() {
        ServiceCategorie serviceCategorie = new ServiceCategorie();
        // Add a listener to the ListView to update the text field when an item is selected
        listViewCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfType.setText(newValue); // Update the text field with the selected type
            }
        });
        try {
            // Fetch the list of categories from the database
            List<categorie> categories = serviceCategorie.afficher();

            if (categories != null) {
                // Print the categories retrieved from the database
                for (categorie cat : categories) {
                    System.out.println(cat.getType());
                }

                // Populate the ListView with the category names and populate categoryIds list
                ObservableList<String> items = FXCollections.observableArrayList();
                for (categorie category : categories) {
                    items.add(category.getType()); // Add category name to the items list
                    categoryIds.add(category.getId()); // Add category ID to the categoryIds list
                }
                listViewCategories.setItems(items);
            } else {
                System.out.println("No categories found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }


    @FXML
    void UpdateCategorie(ActionEvent event) {
        // Get the selected item from the ListView
        String selectedCategory = listViewCategories.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            try {
                // Get the new category name from the TextField
                String newCategoryName = tfType.getText();

                // Retrieve the ID of the selected category
                int selectedIndex = listViewCategories.getSelectionModel().getSelectedIndex();
                int selectedCategoryId = categoryIds.get(selectedIndex);

                // Update the category in the database
                sR.update(new categorie(selectedCategoryId, newCategoryName));
                System.out.println("Category updated successfully.");

                // Refresh the ListView to reflect the changes
                refreshList(null); // You can pass null since the event parameter is not used in refreshList
            } catch (SQLException e) {
                System.err.println("Error updating category: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No category selected.");
        }
    }

}






