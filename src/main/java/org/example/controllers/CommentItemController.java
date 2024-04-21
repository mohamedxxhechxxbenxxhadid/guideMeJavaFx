package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.example.models.Post;

public class CommentItemController {
    @FXML
    private Text descriptionId;

    @FXML
    private Label usernameId;

    Post post;

    public void setData(Post post){

        this.post = post ;
        descriptionId.setText(post.getDescription());
        usernameId.setText(post.getCreator().getFullName());
    }
}
