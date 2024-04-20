package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.test.MainFx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PostDetailsController implements Initializable {
    Post post ;

    @FXML
    private Text postDescriptionId;

    @FXML
    private ImageView postImageId;

    @FXML
    private Text postTitleId;

    @FXML
    private Text creatorId;

    @FXML
    private Text postDateId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setData(Post post){
        this.post = post ;
        postTitleId.setText(post.getTitle());
        creatorId.setText(post.getCreator().getFullName());
        postDateId.setText(post.getCreatedAt().toString());
        postDescriptionId.setText(post.getDescription());
        System.out.println(post.getPostImages());
        ArrayList<PostImage> postImages  = new ArrayList<>(post.getPostImages()) ;
        if(!postImages.isEmpty()){
            InputStream blobImage = postImages.get(0).getImage_blob();
            Image image = new Image(blobImage);
            postImageId.setImage(image);
        }else {
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


}
