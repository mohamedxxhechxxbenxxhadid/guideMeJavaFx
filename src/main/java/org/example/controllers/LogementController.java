package org.example.controllers;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Logement;
import org.example.services.ServiceLogement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import org.example.utils.MyDb;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogementController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private File selectedFile;
    private String imagePath ;
    FileChooser fileChooser = new FileChooser();
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
    private ImageView imageView;

    @FXML
    void getImage(MouseEvent event) {
        // Configurer le FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            try {
                // Stocker le chemin de l'image dans la variable de classe
                imagePath = selectedFile.toURI().toString();

                // Charger l'image dans un objet Image
                Image image = new Image(imagePath);

                // Afficher l'image dans l'ImageView
                imageView.setImage(image);

                // Optionnel : redimensionner l'image pour l'adapter à votre ImageView si nécessaire
                imageView.setFitWidth(100); // Modifier la largeur de l'ImageView selon vos besoins
                imageView.setFitHeight(100); // Modifier la hauteur de l'ImageView selon vos besoins
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer les exceptions si l'image ne peut pas être chargée
            }
        } else {
            // Si aucun fichier n'a été sélectionné, afficher un message à l'utilisateur
            System.out.println("Aucune image sélectionnée.");
        }
    }


    @FXML
    public void initialize() {
        MyDb myDb = MyDb.getInstance();
        ServiceLogement = new ServiceLogement(myDb);
        listViewLogements.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/DetailedView.fxml"));
                try {
                    Parent detailedViewParent = loader.load();
                    DetailedViewController detailedViewController = loader.getController();
                    detailedViewController.initData(newValue);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(detailedViewParent));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer les exceptions si la vue détaillée ne peut pas être chargée
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
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        // Récupérer le chemin de l'image sélectionnée
        String imagePath = imageView.getImage() != null ? imageView.getImage().getUrl() : null;

        // Récupérer les autres valeurs des champs
        String category = categorieComboBox.getValue();
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String place = tfPlace.getText();
        String priceText = tfPrice.getText();

        // Vérifier si tous les champs sont remplis
        if (category == null || nom.isEmpty() || description.isEmpty() || place.isEmpty() || priceText.isEmpty()) {
            // Afficher une alerte à l'utilisateur indiquant que tous les champs doivent être remplis
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information incomplète");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Quitter la méthode
        }

        // Valider le nom et la description pour s'assurer qu'ils ne contiennent pas de chiffres
        if (!isValidInput(nom) || !isValidInput(description)) {
            // Afficher une alerte à l'utilisateur indiquant que le nom et la description ne doivent pas contenir de chiffres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Saisie invalide");
            alert.setHeaderText(null);
            alert.setContentText("Le nom et la description ne doivent pas contenir de chiffres.");
            alert.showAndWait();
            return; // Quitter la méthode
        }

        try {
            // Convertir le prix en entier
            int prix = Integer.parseInt(priceText);

            // Obtenir l'identifiant de catégorie correspondant au nom de catégorie sélectionné
            int categorieId = ServiceLogement.getCategorieId(category);

            // Créer un nouvel objet Logement avec les valeurs récupérées
            Logement newLogement = new Logement(0, nom, description, place, prix, categorieId, imagePath);

            // Ajouter le nouveau Logement à la base de données en utilisant la classe ServiceLogement
            ServiceLogement.add(newLogement);
            System.out.println("Logement ajouté avec succès.");

            // Optionnellement, effacer les champs du formulaire après avoir ajouté le Logement
            clearFormFields();
        } catch (NumberFormatException e) {
            // Gérer le cas où la saisie du prix n'est pas un nombre valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prix invalide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un prix valide.");
            alert.showAndWait();
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
            // Optionnellement, afficher un message d'erreur à l'utilisateur
        }
    }


    private String selectImageFile() {
        // Configurer le FileChooser
        fileChooser.setTitle("Choisir une image");

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath(); // Retourner le chemin absolu du fichier sélectionné
        } else {
            return null; // Retourner null si aucun fichier n'a été sélectionné
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

       // String imagePath = getImagePath(); // Appel de la méthode pour récupérer le chemin de l'image

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
            Logement updatedLogement = new Logement(logementId, nom, description, place, prix, categorieId, imagePath);

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

    // Dans votre contrôleur de vue principale
    @FXML
    void onItemSelected(MouseEvent event) throws IOException {
        Logement selectedLogement = listViewLogements.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/DetailedView.fxml"));
            Parent detailedViewParent = loader.load();
            DetailedViewController detailedViewController = loader.getController();
            detailedViewController.initData(selectedLogement);

            Stage stage = new Stage();
            stage.setScene(new Scene(detailedViewParent));
            stage.show();
        }
    }


}
