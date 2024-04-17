package org.example.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView postImageId;

    @FXML
    private Text postTitleId;

    Post post ;
    public void  setData(Post post){
        this.post = post ;
        postTitleId.setText(post.getTitle());
        ArrayList<PostImage> postImages  = new ArrayList<>(post.getPostImages()) ;
        if(!postImages.isEmpty()){
            InputStream blobImage = postImages.get(1).getImage_blob();
            Image image = new Image(blobImage);
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
}
