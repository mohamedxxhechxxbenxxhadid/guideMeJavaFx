package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("boom works pretty fine");
        int column = 1 ;
        int row = 1 ;
        try{
            List<Post> posts = sP.afficher() ;
            for (Post post : posts){
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
