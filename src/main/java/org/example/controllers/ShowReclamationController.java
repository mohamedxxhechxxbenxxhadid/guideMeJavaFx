package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowReclamationController implements Initializable {
    ServiceReclamation sR = new ServiceReclamation();
    @FXML
    private GridPane grid;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 1 ;
        int row = 0 ;
        try{
            List<Reclamation> reclamations = sR.afficher() ;
            for (int i=0;i<reclamations.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/reclamationItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ReclamationItemController rIC = fxmlLoader.getController();
                rIC.setData(reclamations.get(i));

                if(column == 4){
                    column =1;
                    row++ ;
                }
                grid.add(anchorPane,column++,row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));         }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
