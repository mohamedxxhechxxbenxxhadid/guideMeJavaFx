package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.services.ServicePost;
import org.example.services.ServicePostImage;

import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class backOfficePostController implements Initializable {

    @FXML
    private TableColumn<Post, ?> approvedId;

    @FXML
    private TableColumn<Post, ?> bigPostId;

    @FXML
    private TableColumn<Post, ?> createdId;

    @FXML
    private TableColumn<Post, ?> descriptionId;

    @FXML
    private TableColumn<Post, ?> downId;


    @FXML
    private TableView<Post> tableViewid;

    @FXML
    private TableColumn<Post, ?> titleId;

    @FXML
    private TableColumn<Post, ?> upId;

    ObservableList<Post> posts ;
    ServicePost sP = new ServicePost();
    ServicePostImage sPI = new ServicePostImage();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        approvedId.setCellValueFactory(new PropertyValueFactory<>("approved"));
        bigPostId.setCellValueFactory(new PropertyValueFactory<>("bigPost"));
        createdId.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        descriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));
        downId.setCellValueFactory(new PropertyValueFactory<>("downVoteNum"));
        titleId.setCellValueFactory(new PropertyValueFactory<>("title"));
        upId.setCellValueFactory(new PropertyValueFactory<>("upVoteNum"));
        try{
            List<Post> listposts = sP.afficherBack();
            posts = FXCollections.observableArrayList(listposts);
            tableViewid.setItems(posts);
            posts.addListener((javafx.beans.Observable observable)->{
                tableViewid.setItems(posts);
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void approvePost(ActionEvent event) {
        try{
            Post p = tableViewid.getSelectionModel().getSelectedItem();
            p.setApproved(true);
            sP.update(p);
            posts.set(tableViewid.getSelectionModel().getSelectedIndex(),p);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void deletePost(ActionEvent event) {
        try{
            ArrayList<PostImage> postImages = new ArrayList<>(sPI.getPostImagesByPostId(tableViewid.getSelectionModel().getSelectedItem().getId()));
            for (PostImage p : postImages){
                sPI.delete(p);
            }
            ArrayList<Post> comments = new ArrayList<>(sP.getComment(tableViewid.getSelectionModel().getSelectedItem().getId()));
            for (Post comment:comments){
                sP.delete(comment);
            }
            sP.delete(tableViewid.getSelectionModel().getSelectedItem());
            posts.remove(tableViewid.getSelectionModel().getSelectedItem());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
