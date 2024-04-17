package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.example.models.Post;
import org.example.models.Reclamation;
import org.example.services.ServicePost;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPosts implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private TextField searchTextId;

    ServicePost sP = new ServicePost();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("boom works pretty fine");
        int column = 1 ;
        int row = 0 ;
        try{
            List<Post> posts = sP.afficher() ;
            for (int i=0;i<posts.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/postitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PostItemcontroller pIC = fxmlLoader.getController();
                pIC.setData(posts.get(i));

                if(column == 2){
                    column =0;
                    row++ ;
                }
                grid.add(anchorPane,column++,row);
                //set grid width


                GridPane.setMargin(anchorPane, new Insets(10));         }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
