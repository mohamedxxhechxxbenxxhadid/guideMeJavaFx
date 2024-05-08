package org.example.controllers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.HttpResponse;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Logement;
import javafx.scene.control.Label;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.util.Locale;
import org.javamoney.moneta.Money;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;

public class DetailedViewController {
    private ArrayList<String> currencyList;
    private String apiKey;
    private final String CURRENCY_DATA_API_URL = "https://api.apilayer.com/currency_data/change?start_date=2024-05-01&end_date=2024-05-31";
    private final String API_KEY = "5x4Do1JejAYGNNrimlzGWNjTkk9xx5g8";
    @FXML
    private Button convertButton;

    @FXML
    private ComboBox<String> currencyOneBox;
    @FXML
    private ComboBox<String> currencyTwoBox;
    @FXML
    private Label resultLabel;
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

    @FXML
    void handleConvertButtonAction(ActionEvent event) {
        // Retrieve price and selected currencies
        double price = Double.parseDouble(labelPrix.getText());
        String currencyFrom = currencyOneBox.getValue();
        String currencyTo = currencyTwoBox.getValue();

        // Perform currency conversion
        double convertedPrice = convertCurrency(price, currencyFrom, currencyTo);

        // Display converted price
        resultLabel.setText(String.format(Locale.ENGLISH, "%.2f %s = %.2f %s", price, currencyFrom, convertedPrice, currencyTo));
    }

    public double convertCurrency(double amount, String currencyFrom, String currencyTo) {
        // Convert amount to Money object
        CurrencyUnit currencyUnitFrom = Monetary.getCurrency(currencyFrom);
        Money moneyFrom = Money.of(amount, currencyUnitFrom);

        // Get ExchangeRateProvider and perform currency conversion
        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        CurrencyUnit currencyUnitTo = Monetary.getCurrency(currencyTo);
        CurrencyConversion conversion = exchangeRateProvider.getCurrencyConversion(currencyUnitTo);
        MonetaryAmount moneyConverted = moneyFrom.with(conversion);

        // Return converted amount
        return moneyConverted.getNumber().doubleValueExact();
    }

    public List<String> parseCurrencyData(String responseBody) {
        List<String> currencyCodes = new ArrayList<>();

        try {
            // Parse the JSON response
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject symbolsObject = jsonObject.getAsJsonObject("symbols");

            // Extract currency codes from the "symbols" object
            for (Map.Entry<String, JsonElement> entry : symbolsObject.entrySet()) {
                String currencyCode = entry.getKey();
                currencyCodes.add(currencyCode);
            }
        } catch (JsonSyntaxException e) {
            // Handle JSON parsing exception
            e.printStackTrace();
            // You can log the exception or handle it as appropriate for your application
        }

        // Inside the parseCurrencyData method
        System.out.println("Response Body: " + responseBody);

        // Inside the parseCurrencyData method after the currencyCodes list is retrieved
        System.out.println("Currency Codes: " + currencyCodes);

        return currencyCodes;
    }
    public void initialize() {
        new Thread(() -> {
            try {
                // Make HTTP request to retrieve currency data
                URL url = new URL(CURRENCY_DATA_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("apikey", API_KEY);

                // Check if the request was successful (status code 200)
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the response body to extract the list of currency codes
                    List<String> currencyCodes = parseCurrencyData(response.toString());

                    // Update the ComboBoxes on the JavaFX application thread
                    Platform.runLater(() -> {
                        currencyOneBox.setItems(FXCollections.observableArrayList(currencyCodes));
                        currencyTwoBox.setItems(FXCollections.observableArrayList(currencyCodes));
                    });
                } else {
                    System.err.println("Failed to retrieve currency data: " + connection.getResponseCode());
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



}