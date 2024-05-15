package org.example.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.example.models.Post;
import org.example.models.PostImage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CommentItemController {


    @FXML
    private HBox commentBox;

    @FXML
    private Text descriptionId;

    @FXML
    private Button updateButtonId;

    @FXML
    private Label usernameId;

    Post post;

    PostDetailsController pDC ;

    @FXML
    void updateComment(ActionEvent event) {
        pDC.updateComment(this.post);
    }
    @FXML
    void deleteComment(ActionEvent event) {
        pDC.deleteComment(this.post);
    }
    public void setData(Post post,PostDetailsController postDetailsController){
        this.post = post ;
        this.pDC = postDetailsController ;
        descriptionId.setText(post.getDescription());
        usernameId.setText(post.getCreator().getFullname());
        if(this.post.getPostImages()!= null){
            ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
            if(postImages.isEmpty()){
                System.out.println("Comment with no pictures");
            }else {
                for (PostImage postImage : postImages){
                    try{
                        File pic=new File("/opt/lampp/htdocs/uploads/postimages/"+ postImage.getUrl()) ;
                        InputStream in = new FileInputStream(pic);
                        Image image = new Image(in);
                        ImageView img = new ImageView();
                        img.setFitHeight(100);
                        img.setFitWidth(100);
                        img.setImage(image);
                        commentBox.getChildren().add(img);
                    }catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }

                }
            }
        }

    }
}
