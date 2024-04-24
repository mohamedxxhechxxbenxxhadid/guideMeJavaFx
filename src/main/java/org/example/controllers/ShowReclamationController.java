package org.example.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.WritableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import org.example.models.Reclamation;
import org.example.services.ServiceReclamation;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowReclamationController implements Initializable {
    ServiceReclamation sR = new ServiceReclamation();
    List<Reclamation> reclamations1 ;
    ObservableList<Reclamation> reclamations ;
    @FXML
    private GridPane grid;
    @FXML
    private Text titleId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reclamations1 = sR.afficher() ;
            reclamations = FXCollections.observableList(reclamations1);
            this.Refresh();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        reclamations.addListener((javafx.beans.Observable observable)->{
            this.Refresh();
        });

    }

    public void Refresh() {
        int column = 1 ;
        int row = 0 ;
        grid.getChildren().clear();
        try{
            for (int i=0;i<reclamations.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/reclamationItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ReclamationItemController rIC = fxmlLoader.getController();
                rIC.setData(reclamations.get(i),this);

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
    public void  remove(Reclamation reclamation ,ShowReclamationController sRC){
        if (sRC == this){
            System.out.println("true");
        }else{
            System.out.println(sRC + " == " + this );
        }
        ServiceReclamation sR = new ServiceReclamation();
        try {
            sR.delete(reclamation);
            System.out.println(reclamations.size());
            //System.out.println(reclamation.getId()-reclamations.get(0).getId());
            //reclamations = reclamations.stream().filter(r->r.getId()-reclamation.getId()!=0).collect(Collectors.toCollection(FXCollections::observableArrayList)) ;
            for(int i=0;i<reclamations.size();i++){
                if (reclamations.get(i).getId()-reclamation.getId() ==0){
                    reclamations.remove(reclamations.get(i));
                }
            }
            System.out.println(reclamations1.size());
            System.out.println(reclamation);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        reclamations.remove(reclamation);
        System.out.println("remove works");
    }

}
