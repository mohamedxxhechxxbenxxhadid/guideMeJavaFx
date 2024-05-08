package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.example.models.Post;
import org.example.models.Reclamation;
import org.example.services.ServicePost;

import java.awt.event.InputMethodEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPosts implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private TextField searchTextId;

    @FXML
    private Button buttonAddPost;

    ServicePost sP = new ServicePost();

    ObservableList<Post> postObservableList ;
    List<Post> posts ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTextId.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                posts = sP.afficher() ;
                postObservableList = FXCollections.observableList(posts.stream().filter(post -> post.getTitle().contains(newValue)).toList()) ;
                this.updateData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
        int column = 1 ;
        int row = 1 ;
        try{
            posts = sP.afficher() ;
            postObservableList = FXCollections.observableList(posts);
            System.out.println(posts.size());
            for (Post post : posts){
                System.out.println("i m in posts boucle");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/postitem.fxml"));
                Pane anchorPane = fxmlLoader.load();

                PostItemcontroller pIC = fxmlLoader.getController();
                pIC.setData(post);

                if(column == 3){
                    column =1;
                    ++row ;
                }
                grid.add(anchorPane,column++,row);


                GridPane.setMargin(anchorPane, new Insets(0,150,50,0));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void updateData(){
        grid.getChildren().clear();
        int column = 1 ;
        int row = 1 ;
        try{
            for (Post post : postObservableList){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/postitem.fxml"));
                Pane anchorPane = fxmlLoader.load();

                PostItemcontroller pIC = fxmlLoader.getController();
                pIC.setData(post);

                if(column == 3){
                    column =1;
                    ++row ;
                }
                grid.add(anchorPane,column++,row);


                GridPane.setMargin(anchorPane, new Insets(0,150,50,0));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void goToAddPost(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        try{
            Parent root = loader.load();
            HomeController hC = loader.getController();
            searchTextId.getScene().setRoot(root);
            hC.changeToAddPostsFunction();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



}
