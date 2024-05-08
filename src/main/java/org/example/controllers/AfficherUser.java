package org.example.controllers;



import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficherUser implements Initializable {

    @FXML
    private TableView<User> lbUsers;

    @FXML
    private TableColumn<User, String> affEmail;



    @FXML
    private TableColumn<User, String> affFullname;

    @FXML
    private TableColumn<User, String> affPhonenumber;

    @FXML
    private TableColumn<User, String> affrole;

    @FXML
    private TableColumn<User, String> affAdress;

    @FXML
    private TextField searchField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    private ObservableList<User> utilisateursData;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affFullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        affPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phone_numer"));
        affEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        affAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        affrole.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            UserRole role = cellData.getValue().getRole();
            if (role != null) {
                property.setValue(role.toString());
            }
            return property;
        });

        // Initialise la liste des utilisateurs
        utilisateursData = FXCollections.observableArrayList();

        // Récupère les utilisateurs depuis la base de données et les ajoute à la liste
        ServiceUser serviceUser = new ServiceUser();
        ArrayList<User> utilisateurs = serviceUser.getAll();
        utilisateursData.addAll(utilisateurs);

        // Configure les colonnes du tableau pour afficher les données des utilisateurs
        TableColumn<User, String> fullnameCol = new TableColumn<>("fullname");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));


        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> PhonenumberCol = new TableColumn<>("Phonenumber");
        PhonenumberCol.setCellValueFactory(new PropertyValueFactory<>("Phonenumber"));

        TableColumn<User, String> adressCol = new TableColumn<>("adress");
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));


        // HOW TO ADD MORE COLUMNS
        //lbUsers.getColumns().addAll(fullnameCol, emailCol);

        // Initialise le tableau avec les données des utilisateurs
        lbUsers.setItems(utilisateursData);

        // Écoute les changements de texte dans le champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
        deleteButton.setOnAction(event -> {
            User selectedUser = lbUsers.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Appeler la méthode de suppression de l'utilisateur
                boolean deleted = serviceUser.delete(selectedUser);
                if (deleted) {
                    lbUsers.getItems().remove(selectedUser);
                }
            }


        });

        updateButton.setOnAction(event -> {
            User selectedUser = lbUsers.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUser.fxml"));
                    Parent root = loader.load();
                    UpdateUser controller = loader.getController();
                    controller.setUserToUpdate(selectedUser);

                    // Remplacer la scène actuelle par la nouvelle scène
                    Stage stage = (Stage) updateButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }


        });


    }
    public void setUsersData(ObservableList<User> users) {
        lbUsers.setItems(users);
    }

    public void setLbUsers(ObservableList<User> users) {
        lbUsers.setItems(users);
    }
    private void filterTable(String searchText) {
        // Crée une liste filtrée à partir de la liste des utilisateurs
        FilteredList<User> filteredData = new FilteredList<>(utilisateursData, p -> true);

        // Applique le filtre en fonction du texte de recherche
        filteredData.setPredicate(user -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // Affiche toutes les lignes si la recherche est vide
            }
            // Filtre en fonction du nom ou de l'email
            String lowerCaseFilter = searchText.toLowerCase();
            if (user.getFullname().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Correspondance dans le nom
            } else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Correspondance dans l'email
            }
            return false; // Aucune correspondance
        });

        // Met à jour les données du tableau avec le contenu filtré
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(lbUsers.comparatorProperty());
        lbUsers.setItems(sortedData);
    }




}
