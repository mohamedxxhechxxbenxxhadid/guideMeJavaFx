package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.models.User;
import org.example.services.ServicePost;
import org.example.services.ServicePostImage;
import org.example.services.ServiceUser;
import org.example.utils.SentimentAnalysis;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AjouterPostController  {

    private ArrayList<File> arrayImages= new ArrayList<>(); ;
    private FileChooser fileChooser = new FileChooser();


    @FXML
    private TextArea descriptionId;

    @FXML
    private Label descriptionLabelId;

    @FXML
    private HBox imagesHbox;

    @FXML
    private Button saveId;

    @FXML
    private Label textLabelId;

    @FXML
    private TextField titleId;

    @FXML
    private Button uploadId;

    private boolean loading=false ;

    public void savePost(javafx.event.ActionEvent actionEvent) {
        if (titleId.getText().isBlank()){
            textLabelId.setVisible(true);
            textLabelId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    textLabelId.setVisible(false);
                }
            }));
        }else if (descriptionId.getText().isBlank()){
            descriptionLabelId.setVisible(true);
            descriptionLabelId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    descriptionLabelId.setVisible(false);
                }
            }));
        }else if(SentimentAnalysis.sentimentType(descriptionId.getText(),this) <0){
            this.setLoading(false);
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            showNegativePopup("Your Description has a negative meaning please try to be more" +
                    " positive",stage);
        }else {
            ServiceUser sU = new ServiceUser();
            User u = new User() ;
            try{
                u = sU.findUserById(1);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            Post post = new Post();
            post.setTitle(titleId.getText());
            post.setDescription(descriptionId.getText());
            post.setBigPost(true);
            post.setApproved(false);
            post.setUpVoteNum(0);
            post.setDownVoteNum(0);
            post.setCreator(u);
            post.setCreatedAt();
            ServicePost sP = new ServicePost();
            try{
                sP.add(post);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            if(arrayImages.isEmpty()){
                System.out.println("empty array");
            }else{
                try{
                    post = sP.getTheLastPost() ;
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
                for(File file : arrayImages){
                    ServicePostImage sPI = new ServicePostImage();
                    try {
                        InputStream in = new FileInputStream(file);
                        PostImage postImage = new PostImage();
                        postImage.setImage_blob(in);
                        postImage.setPost(post);
                        sPI.add(postImage);
                        System.out.println("file"+in);
                    }catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            try{
                Parent root = loader.load();
                HomeController hC = loader.getController();
                textLabelId.getScene().setRoot(root);
                hC.changeToPostsFunction(actionEvent);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
    public void UploadImage(javafx.event.ActionEvent actionEvent) {
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
                    System.out.println("it contains it this image");
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
    public void showNegativePopup(String message,Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);//ww  w . j  a  va2s  .  co  m
        alert.show();
    }
    public void disableAll(){
        descriptionId.setDisable(this.loading);
        titleId.setDisable(this.loading);
        saveId.setDisable(this.loading);
        uploadId.setDisable(this.loading);
    }
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        if (this.loading != loading) {
            this.loading = loading;
            // Call your function here
            disableAll();
        }
    }


}
