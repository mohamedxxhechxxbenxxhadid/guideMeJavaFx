package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.example.models.Post;
import org.example.models.Reclamation;
import org.example.models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button changeToPosts;

    @FXML
    private Button changeToReclamation;
    @FXML
    private Button changeToLogement;

    @FXML
    private Button changeToProfileId;
    @FXML
    private VBox generalId;

    @FXML
    private StackPane contentArea;

    private User user;

    public void initData(User user) {
        System.out.println(user);
        this.user = user;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void changeToPostsFunction(ActionEvent event) {
        try{

            Parent fxml = FXMLLoader.load(getClass().getResource("/showposts.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("not working");
        }

    }
    @FXML
    public void changeToProfile(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUser.fxml"));
            Parent root = loader.load();
            UpdateUser controller = loader.getController();
            controller.setUserToUpdate(user);
            root.prefWidth(contentArea.getWidth());
            root.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(root);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("not working");
        }
        //System.out.println(user.getEmail());

    }
    @FXML
    public void changeToReclamationFunction(ActionEvent event) {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/ajouterreclamation.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public void changeToAddPostsFunction() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/addPost.fxml"));
            fxml.prefWidth(contentArea.getWidth());
            fxml.prefHeight(contentArea.getHeight());
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void changeToPostDeatils(Post post){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/postdetails.fxml"));
            Parent fxml = loader.load();
            PostDetailsController pDC = loader.getController();
            if (pDC != null) {
                fxml.prefWidth(contentArea.getWidth());
                fxml.prefHeight(contentArea.getHeight());
                contentArea.getChildren().removeAll();
                contentArea.getChildren().setAll(fxml);
                pDC.setData(post);
            } else {
                System.out.println("Error: Could not get PostDetailsController");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void changeToShowReclamationFunction() {
        

    }
    @FXML
    public void changeToBackShowPosts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/dashboard.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void changeToLogement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/user.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
