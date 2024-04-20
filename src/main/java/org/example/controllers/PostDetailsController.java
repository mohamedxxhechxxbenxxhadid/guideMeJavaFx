package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.services.ServicePost;
import org.example.services.ServicePostImage;
import org.example.test.MainFx;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PostDetailsController implements Initializable {


    @FXML
    private Button nextImage;

    @FXML
    private Label postCreatedId;

    @FXML
    private Label postCreatorId;

    @FXML
    private Text postDescriptionId;

    @FXML
    private ImageView postImageId;

    @FXML
    private Label postTitleId;

    @FXML
    private Button previousImage;

    Post post ;

    int currentImage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void setData(Post post){
        ServicePost sP = new ServicePost();
        ServicePostImage sPI = new ServicePostImage();
        try {
            this.post = sP.findPostById(post.getId()) ;
            this.post.setPostImages(sPI.getPostImagesByPostId(post.getId()));
        }catch (SQLException e){
            System.out.println("============"+e.getMessage());
        }
        postTitleId.setText(this.post.getTitle());
        postCreatorId.setText(this.post.getCreator().getFullName());
        postCreatedId.setText(this.post.getCreatedAt().toString());
        postDescriptionId.setText(this.post.getDescription());
        System.out.println(this.post.getPostImages());
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(!postImages.isEmpty()){
            InputStream blobImage = postImages.get(0).getImage_blob();
            currentImage = 0 ;
            Image image = new Image(blobImage);
            postImageId.setImage(image);
            System.out.println("*****"+image.getException());
        }else {
            previousImage.setDisable(true);
            previousImage.setDisable(true);
            try{
                File pic=new File(  MainFx.class.getResource( "/pic1.png" ).toURI()  );
                InputStream in = new FileInputStream(pic);
                Image image = new Image(in);
                postImageId.setImage(image);
            }catch (URISyntaxException | FileNotFoundException e ){
                System.out.println(e.getMessage());
            }
        }

    }
    @FXML
    void goToNextImage(ActionEvent event) {
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(postImages.size()-1==currentImage){
            System.out.println("there is no more picture postDetailsController");
        }else{
            postImageId.setImage(new Image(postImages.get(currentImage+1).getImage_blob()));
        }
    }

    @FXML
    void goToPreviousImage(ActionEvent event) {
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(currentImage ==0  ){
            System.out.println("there is no more picture postDetailsController");
        }else {
            postImageId.setImage(new Image(postImages.get(currentImage-1).getImage_blob()));
        }
    }
}
