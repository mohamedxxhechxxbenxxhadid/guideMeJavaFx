package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Transport;

import java.io.*;

public class DetailTController {
    private String apiKey;

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

    public void initData(Transport transport) {
        labelNom.setText(transport.getName());
        labelDescription.setText(String.valueOf(transport.getCapacite()));
        labelPlace.setText(String.valueOf(transport.getEtat()));
        labelPrix.setText(String.valueOf(transport.getPrix()));

        if (transport.getImage() != null && !transport.getImage().isEmpty()) {
            try {
                Image image = new Image(transport.getImage());
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        getApiKey();
    }

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
