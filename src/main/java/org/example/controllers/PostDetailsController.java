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
    private Label postCreatedId;

    @FXML
    private Label postCreatorId;

    @FXML
    private Text postDescriptionId;

    @FXML
    private ImageView postImageId;

    @FXML
    private Label postTitleId;


    Post post ;

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
            Image image = new Image(blobImage);
            postImageId.setImage(image);
            System.out.println(isCorrupted(blobImage));
            System.out.println("*****"+image.getException());
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
    public boolean isCorrupted(InputStream inputStream) {
        try {
            // Attempt to read from the InputStream
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);

            // Check for unexpected EOF (End of File)
            if (bytesRead == -1) {
                // EOF reached unexpectedly, indicating corruption
                return true;
            }

            // Optional: Perform additional checks based on the read data

            // No corruption detected
            return false;
        } catch (IOException e) {
            // Exception occurred during read operation
            // It may indicate corruption or other issues
            e.printStackTrace();
            return true; // Assume corrupted
        }
    }

}
