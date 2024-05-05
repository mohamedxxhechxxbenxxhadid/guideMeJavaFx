package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Logement;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class DetailedViewController {
    //private String currencyOneBox, currencyTwoBox, apiKey;
    private String apiKey;
    private ArrayList<String> currencyList;
    @FXML
    private Button convertButton;

    @FXML
    private ComboBox<String> currencyOneBox;
    @FXML
    private Label resultLabel;

    @FXML
    private ComboBox<String> currencyTwoBox;
    @FXML
    private ImageView imageView;

    @FXML
    private TextArea labelDescription;

    @FXML
    private TextField labelNom;

    @FXML
    private TextField labelPlace;

    @FXML
    private TextField labelPrix;
    public void initData(Logement logement) {
        // Afficher les détails de l'élément sélectionné dans les éléments graphiques correspondants
        labelNom.setText(logement.getNom());
        labelDescription.setText(logement.getDescription());
        labelPlace.setText(logement.getPlace());
        labelPrix.setText(String.valueOf(logement.getPrix())); // Convertir int en String pour l'afficher dans un TextField

        // Charger et afficher l'image
        if (logement.getImage() != null && !logement.getImage().isEmpty()) {
            try {
                Image image = new Image(logement.getImage());
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer les exceptions si l'image ne peut pas être chargée
            }
        }
    }
    public void initialize(){
        // retrieve api key
        getApiKey();

        // load logo
      //  loadLogo();

       /* try{
            // retrieve and store list of currencies
            currencyList = loadCurrencyList();

            // store the list in the combo boxes
            ObservableList<String> options = FXCollections.observableArrayList(currencyList);
            currencyOneBox.setItems(options);
            currencyTwoBox.setItems(options);

        }catch(IOException e){
            System.out.println("Error: Failed to load currency list " + e);
        }
*/
    }

    //public void setCurrencyOne(){currencyOne = currencyOneBox.getValue();}
   // public void setCurrencyTwo(){currencyTwo = currencyTwoBox.getValue();}

    private void getApiKey() {
        InputStream inputStream = getClass().getResourceAsStream("/resources/apikey.txt");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                apiKey = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading API key: " + e.getMessage());
            }
        } else {
            System.out.println("API key resource not found.");
        }
    }




}
