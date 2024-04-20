package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.models.Reclamation;
import javafx.fxml.FXML;
import org.example.test.MainFx;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PostItemcontroller {

    @FXML
    private Pane paneId;
    @FXML
    private Button detailsButton;
    @FXML
    private Label creatorId;

    @FXML
    private Label postDateId;

    @FXML
    private ImageView postImageId;

    @FXML
    private Label postTitleId;

    Post post ;
    public void  setData(Post post){
        this.post = post ;
        postTitleId.setText(post.getTitle());
        creatorId.setText(post.getCreator().getFullName());
        postDateId.setText(post.getCreatedAt().toString());
        ArrayList<PostImage> postImages  = new ArrayList<>(post.getPostImages()) ;
        if(!postImages.isEmpty()){
            InputStream blobImage = postImages.get(0).getImage_blob();
            Image image = new Image(blobImage);
            System.out.println("width="+image.getWidth() +"||||"+"height="+image.getHeight());
            postImageId.setScaleY(1.185);
            postImageId.setImage(image);
        }else {
            try{
                File pic=new File(  MainFx.class.getResource( "/pic1.png" ).toURI()  );
                InputStream in = new FileInputStream(pic);
                Image image = new Image(in);
                postImageId.setImage(image);
            }catch (URISyntaxException| FileNotFoundException e ){
                System.out.println(e.getMessage());
            }
        }
    }
    @FXML
    void goToDetailsPost(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        try{
            Parent root = loader.load();
            HomeController hC = loader.getController();
            detailsButton.getScene().setRoot(root);
            hC.changeToPostDeatils(post);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
