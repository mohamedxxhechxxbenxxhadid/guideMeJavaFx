package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowReclamationController implements Initializable {
    ServiceReclamation sR = new ServiceReclamation();
    @FXML
    private GridPane grid;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0 ;
        int row = 0 ;
        try{
            List<Reclamation> reclamations = sR.afficher() ;
            for (int i=0;i<reclamations.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/reclamationItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ReclamationItemController rIC = fxmlLoader.getController();
                rIC.setData(reclamations.get(i));

                if(column == 3){
                    column =0;
                    row++ ;
                }
                grid.add(anchorPane,column++,row);
                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
