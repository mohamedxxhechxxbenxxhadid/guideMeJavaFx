package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.services.ServicePost;
import org.example.services.ServicePostImage;

import java.io.*;
import java.util.*;

public class AjouterPostController  {

    private ArrayList<File> arrayImages= new ArrayList<>(); ;
    private FileChooser fileChooser = new FileChooser();


    @FXML
    private TextArea descriptionId;

    @FXML
    private TextField titleId;

    @FXML
    private HBox imagesHbox;

    public void savePost(javafx.event.ActionEvent actionEvent) {
        Post post = new Post();
        post.setTitle(titleId.getText());
        post.setDescription(descriptionId.getText());
    }
    public void UploadImage(javafx.event.ActionEvent actionEvent) {
        System.out.println("boom working this button");
        String fileExtention ;
        ServicePostImage sPI = new ServicePostImage();
        ServicePost sP = new ServicePost();
        try{
            File file = fileChooser.showOpenDialog(new Stage());
            fileExtention = getFileExtension(file.getName()) ;
            if(fileExtention.equals("jpeg") || fileExtention.equals("jpg") || fileExtention.equals("png")){
                InputStream in = new FileInputStream(file);
                PostImage pI = new PostImage(sP.findPostById(12),in);
                if(arrayImages.contains(file)){
                    System.out.println("it contains it ");
                }else{
                    arrayImages.add(file);
                    VBox imageBox = new VBox();
                    Button imageButton = new Button();
                    imageButton.setText("X");
                    imageButton.setPrefWidth(20);
                    imageButton.setPrefHeight(20);
                    imageBox.setPrefWidth(140);
                    imageBox.setPrefHeight(140);
                    ImageView imageShown = new ImageView();
                    String imagePath = file.toURI().toString();
                    Image image = new Image(imagePath);
                    imageShown.setImage(image);
                    imageShown.setFitWidth(100);
                    imageShown.setFitHeight(100);
                    imageBox.getChildren().add(imageButton);
                    imageBox.getChildren().add(imageShown);
                    imageButton.setId(file.toString());
                    imageButton.setOnMouseClicked(event -> this.removePicture(event,imagesHbox,imageBox,file));
                    imagesHbox.getChildren().add(imageBox);
                    //imageAreaId.getItems().add(arrayImages.get(arrayImages.size()-1)+"\n");
                    //changePicture(file);
                }

            }else{
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                showErrorPopup("the file is not an image",stage);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void removePicture(MouseEvent Event,HBox ImagesHbox,VBox imageBox,File file) {
        ImagesHbox.getChildren().remove(imageBox);
        arrayImages.remove(file);
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
