package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Post;
import org.example.models.PostImage;
import org.example.models.User;
import org.example.services.ServicePost;
import org.example.services.ServicePostImage;
import org.example.services.ServiceUser;
import org.example.test.MainFx;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PostDetailsController implements Initializable {


    @FXML
    private TextArea Description;

    @FXML
    private GridPane commentContainerId;

    @FXML
    private AnchorPane imageContainerId;

    @FXML
    private HBox imagesHbox;

    @FXML
    private Button nextImage;

    @FXML
    private Label postCreatedId;

    @FXML
    private Label postCreatorId;

    @FXML
    private Text postDescriptionId;

    @FXML
    private ImageView postImageId;

    @FXML
    private Label postTitleId;

    @FXML
    private Button previousImage;

    @FXML
    private Button saveId;

    @FXML
    private Button uploadId;


    @FXML
    private Button updateId;

    ArrayList<Post> comments;
    Post updatedComment ;
    Post post ;
    ImageView img = new ImageView();
    private ArrayList<File> arrayImages= new ArrayList<>();
    private ArrayList<InputStream> arrayBlobImages= new ArrayList<>(); ;
    private FileChooser fileChooser = new FileChooser();
    int currentImage;
    boolean update = false ;
    ArrayList<PostImage> postImagesUpdate ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void setData(Post post){
        ServicePost sP = new ServicePost();
        ServicePostImage sPI = new ServicePostImage();
        try {
            this.post = sP.findPostById(post.getId()) ;
            this.post.setPostImages(sPI.getPostImagesByPostId(post.getId()));
        }catch (SQLException e){
            System.out.println("============"+e.getMessage());
        }
        postTitleId.setText(this.post.getTitle());
        postCreatorId.setText(this.post.getCreator().getFullName());
        postCreatedId.setText(this.post.getCreatedAt().toString());
        postDescriptionId.setText(this.post.getDescription());
        System.out.println(this.post.getPostImages());
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(!postImages.isEmpty()){
            InputStream blobImage = postImages.get(0).getImage_blob();
            currentImage = 0 ;
            Image image = new Image(blobImage);
            img.setFitHeight(373);
            img.setFitWidth(565);
            img.setImage(image);
            imageContainerId.getChildren().add(0,img);
        }else {
            previousImage.setDisable(true);
            nextImage.setDisable(true);
            try{
                File pic=new File(  MainFx.class.getResource( "/pic1.png" ).toURI()  );
                InputStream in = new FileInputStream(pic);
                Image image = new Image(in);
                img.setFitHeight(373);
                img.setFitWidth(565);
                img.setImage(image);
                imageContainerId.getChildren().add(0,img);
            }catch (URISyntaxException | FileNotFoundException e ){
                System.out.println(e.getMessage());
            }
        }
        if(this.post.getComments().isEmpty()){
            System.out.println("no comments");
        }else{
            comments = new ArrayList<>(this.post.getComments()) ;
            int column = 0 ;
            int row = 0 ;
            for(int i=0;i<comments.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/commentItem.fxml"));
                try {
                    for (Post comment:comments){
                        comment.setPostImages(sPI.getPostImagesByPostId(comment.getId()));
                    }
                    AnchorPane anchorPane = fxmlLoader.load();
                    CommentItemController cIR = fxmlLoader.getController();
                    cIR.setData(comments.get(i),this);

                    commentContainerId.add(anchorPane,column,row++);
                }catch (IOException | SQLException e){
                    System.out.println(e.getMessage());
                }


            }
        }

    }
    @FXML
    void goToNextImage(ActionEvent event) {
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(postImages.size()-1==currentImage){
            System.out.println("there is no more picture postDetailsController");
        }else{
            currentImage++;
            img.setImage(new Image(postImages.get(currentImage).getImage_blob()));
        }
    }

    @FXML
    void goToPreviousImage(ActionEvent event) {
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(currentImage ==0  ){
            System.out.println("there is no more picture postDetailsController");
        }else {
            System.out.println(currentImage);
            currentImage--;
            img.setImage(new Image(postImages.get(currentImage).getImage_blob()));
        }
    }

    @FXML
    void UploadImage(ActionEvent actionEvent) {
        String fileExtention ;
        ServicePostImage sPI = new ServicePostImage();
        ServicePost sP = new ServicePost();
        try{
            File file = fileChooser.showOpenDialog(new Stage());
            fileExtention = getFileExtension(file.getName()) ;
            if(fileExtention.equals("jpeg") || fileExtention.equals("jpg") || fileExtention.equals("png")){
                InputStream in = new FileInputStream(file);
                PostImage pI = new PostImage(sP.findPostById(12),in);
                if(arrayImages.contains(file)){
                    System.out.println("it contains it this image");
                }else{
                    arrayImages.add(file);
                    VBox imageBox = new VBox();
                    Button imageButton = new Button();
                    imageButton.setText("X");
                    imageButton.setPrefWidth(20);
                    imageButton.setPrefHeight(20);
                    imageBox.setPrefWidth(140);
                    imageBox.setPrefHeight(140);
                    ImageView imageShown = new ImageView();
                    String imagePath = file.toURI().toString();
                    Image image = new Image(imagePath);
                    imageShown.setImage(image);
                    imageShown.setFitWidth(100);
                    imageShown.setFitHeight(100);
                    imageBox.getChildren().add(imageButton);
                    imageBox.getChildren().add(imageShown);
                    imageButton.setId(file.toString());
                    imageButton.setOnMouseClicked(event -> this.removePicture(event,imagesHbox,imageBox,file));
                    imagesHbox.getChildren().add(imageBox);

                }

            }else{
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                showErrorPopup("the file is not an image",stage);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void removePicture(MouseEvent Event, HBox ImagesHbox, VBox imageBox, File file) {
        ImagesHbox.getChildren().remove(imageBox);
        arrayImages.remove(file);
    }
    @FXML
    public void removePictureBlob(MouseEvent Event, HBox ImagesHbox, VBox imageBox, InputStream blob) {
        ImagesHbox.getChildren().remove(imageBox);
        arrayBlobImages.remove(blob);
        for(post)
    }

    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    public static void showErrorPopup(String message,Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured");
        alert.setContentText(message);//ww  w . j  a  va2s  .  co  m
        alert.show();
    }
    @FXML
    public void saveComment(javafx.event.ActionEvent actionEvent) {
        ServiceUser sU = new ServiceUser();
        User u = new User() ;
        try{
            u = sU.findUserById(1);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        Post comment = new Post();
        comment.setTitle("C");
        comment.setDescription(Description.getText());
        comment.setPost(this.post);
        comment.setBigPost(false);
        comment.setApproved(true);
        comment.setUpVoteNum(0);
        comment.setDownVoteNum(0);
        comment.setCreator(u);
        comment.setCreatedAt();
        ServicePost sP = new ServicePost();
        try{
            sP.add(comment);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        if(arrayImages.isEmpty()){
            System.out.println("empty array");
        }else{
            try{
                comment = sP.getTheLastPost() ;
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            for(File file : arrayImages){
                ServicePostImage sPI = new ServicePostImage();
                try {
                    InputStream in = new FileInputStream(file);
                    PostImage postImage = new PostImage();
                    postImage.setImage_blob(in);
                    postImage.setPost(comment);
                    sPI.add(postImage);
                    System.out.println("file"+in);
                }catch (FileNotFoundException e){
                    System.out.println(e.getMessage());
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    @FXML
    void updateCommentGui(ActionEvent event) {
        this.updatedComment.setDescription(Description.getText());
        ServicePost sP = new ServicePost();
        System.out.println(updatedComment);
        try {
            sP.update(this.updatedComment);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        for(InputStream inputStream : arrayBlobImages){
            ServicePostImage sPI = new ServicePostImage();
            try {
                PostImage postImage = new PostImage();
                postImage.setImage_blob(inputStream);
                postImage.setPost(updatedComment);
                sPI.add(postImage);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void updateComment(Post comment){
        this.updatedComment = comment ;
        Description.setText(comment.getDescription());
        this.update = true ;
        updateId.setVisible(true);
        saveId.setVisible(false);
        ServicePostImage sPI = new ServicePostImage();
        if(comment.getPostImages()!= null){
            try {
                this.postImagesUpdate = new ArrayList<>(sPI.getPostImagesByPostId(comment.getId())) ;
                if(postImagesUpdate.isEmpty()){
                    System.out.println("Comment with no pictures");
                }else {
                    for (PostImage postImage : postImagesUpdate){
                        VBox imageBox = new VBox();
                        Button imageButton = new Button();
                        imageButton.setText("X");
                        imageButton.setPrefWidth(20);
                        imageButton.setPrefHeight(20);
                        imageBox.setPrefWidth(140);
                        imageBox.setPrefHeight(140);
                        ImageView imageShown = new ImageView();
                        InputStream blobImage = postImage.getImage_blob();
                        Image image = new Image(blobImage);
                        imageShown.setImage(image);
                        imageShown.setFitWidth(100);
                        imageShown.setFitHeight(100);
                        imageBox.getChildren().add(imageButton);
                        imageBox.getChildren().add(imageShown);
                        imageButton.setId(postImage.toString());
                        imageButton.setOnMouseClicked(event -> this.removePictureBlob(event,imagesHbox,imageBox,blobImage));
                        imagesHbox.getChildren().add(imageBox);
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
