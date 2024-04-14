package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AjouterPostController implements Initializable {


    FileChooser fileChooser = new FileChooser();
    @FXML
    private TextArea descriptionId;

    @FXML
    private Button saveId;

    @FXML
    private TextField titleId;

    @FXML
    private Button uploadId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File("/"));
    }

    public void UploadImage(javafx.event.ActionEvent actionEvent) {
        System.out.println("function works");
        File file = fileChooser.showOpenDialog(new Stage());
        try{
            Scanner scanner = new Scanner(file);
            System.out.println(scanner);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
