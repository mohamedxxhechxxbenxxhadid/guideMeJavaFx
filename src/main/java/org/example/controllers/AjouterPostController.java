package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.PostImage;
import org.example.services.ServicePost;
import org.example.services.ServicePostImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

public class AjouterPostController  {

    Set<InputStream> setImages = new HashSet<>(); ;
    FileChooser fileChooser = new FileChooser();
    @FXML
    private TextArea descriptionId;

    @FXML
    private TextArea imageAreaId;

    @FXML
    private Button saveId;

    @FXML
    private TextField titleId;

    @FXML
    private Button uploadId;

    public void UploadImage(javafx.event.ActionEvent actionEvent) {
        System.out.println("function works");
        ServicePostImage sPI = new ServicePostImage();
        ServicePost sP = new ServicePost();
        try{
            File file = fileChooser.showOpenDialog(new Stage());
            InputStream in = new FileInputStream(file);
            PostImage pI = new PostImage(sP.findPostById(12),in);
            imageAreaId.appendText(file+"\n");
            setImages.add(in);
            System.out.println(setImages.size());
            //sPI.add(pI);
            try
            {
                Image picture = ImageIO.read(file);
            }
            catch (Exception e)
            {
                String workingDir = System.getProperty("user.dir");
                System.out.println("Current working directory : " + workingDir);
                e.printStackTrace();
            }
            System.out.println(file);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
