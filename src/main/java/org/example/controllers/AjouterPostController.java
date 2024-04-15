package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Post;
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

    Set<File> setImages = new HashSet<>(); ;
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

    @FXML
    void savePost(ActionEvent event) {
        Post post = new Post();
        post.setTitle(titleId.getText());
        post.setDescription(descriptionId.getText());
    }
    public void UploadImage(javafx.event.ActionEvent actionEvent) {
        String fileExtention ;
        System.out.println("function works");
        ServicePostImage sPI = new ServicePostImage();
        ServicePost sP = new ServicePost();
        try{
            File file = fileChooser.showOpenDialog(new Stage());
            fileExtention = getFileExtension(file.getName()) ;
            if(fileExtention.equals("jpeg") || fileExtention.equals("jpg") || fileExtention.equals("png")){
                InputStream in = new FileInputStream(file);
                PostImage pI = new PostImage(sP.findPostById(12),in);
                imageAreaId.appendText(file+"\n");
                setImages.add(file);
                System.out.println(setImages.size());
                System.out.println(setImages);
                System.out.println(file);
            }else{
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                showErrorPopup("the file is not an image",stage);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    public static void showErrorPopup(String message,Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured");
        alert.setContentText(message);//ww  w . j  a  va2s  .  co  m
        alert.show();
    }
}
