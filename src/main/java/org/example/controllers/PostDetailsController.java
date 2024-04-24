package org.example.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
import java.util.Date;
import java.util.ResourceBundle;

public class PostDetailsController implements Initializable {

    @FXML
    private TextArea Description;

    @FXML
    private Label blankDescriptionId;

    @FXML
    private Label blankTitleId;

    @FXML
    private GridPane commentContainerId;

    @FXML
    private Button deleteButtonPostId;

    @FXML
    private Button donePostId;

    @FXML
    private Button editPostButton;

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
    private Button returnToCommentId;

    @FXML
    private Button saveId;

    @FXML
    private TextField titleEditFieldId;

    @FXML
    private Button updateId;

    @FXML
    private Button uploadId;


    public Post postDetails ;
    ArrayList<Post> comments = new ArrayList<Post>();
    Post updatedComment ;
    Post post ;
    ImageView img = new ImageView();
    private ArrayList<File> arrayImages= new ArrayList<>();
    private ArrayList<InputStream> arrayBlobImages= new ArrayList<>(); ;
    private FileChooser fileChooser = new FileChooser();
    int currentImage;
    boolean update = false ;
    ArrayList<PostImage> postImagesUpdate ;
    ArrayList<PostImage> postImagesAdd =  new ArrayList<PostImage>() ;
    ArrayList<PostImage> postImagesDelete = new ArrayList<PostImage>() ;
    ObservableList<Post> commentList =  FXCollections.observableList(comments); ;
    boolean  followList = false ;
    ArrayList<Image> images = new ArrayList<Image>();
    ServicePost sP = new ServicePost();
    ServicePostImage sPI = new ServicePostImage();
    boolean updatePost = false ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void setData(Post post){
        followList = true ;
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
        ArrayList<PostImage> postImages  = new ArrayList<>(this.post.getPostImages()) ;
        if(!postImages.isEmpty()){
            InputStream blobImage = postImages.get(0).getImage_blob();
            currentImage = 0 ;
            Image image = new Image(blobImage);
            img.setFitHeight(373);
            img.setFitWidth(565);
            img.setImage(image);
            imageContainerId.getChildren().add(0,img);
            for (PostImage postImage : postImages){
                images.add(new Image(postImage.getImage_blob()));
            }
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
            System.out.println("no comments"+commentList.size());
        }else{
            comments = new ArrayList<>(this.post.getComments()) ;
            commentList = FXCollections.observableList(comments);
            int column = 0 ;
            int row = 0 ;
            for(int i=0 ;i<comments.size();i++){
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
        commentList.addListener((javafx.beans.Observable observable)->{
            this.setData();
        });


    }
    @FXML
    public void setData(){
        commentContainerId.getChildren().clear();
        ServicePostImage sPI = new ServicePostImage() ;
        if(commentList.isEmpty()){
            System.out.println("no comments");
        }else{
            int column = 0 ;
            int row = 0 ;
            for(int i=0;i<commentList.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/commentItem.fxml"));
                try {
                    for (Post comment:commentList){
                        comment.setPostImages(sPI.getPostImagesByPostId(comment.getId()));
                    }
                    AnchorPane anchorPane = fxmlLoader.load();
                    CommentItemController cIR = fxmlLoader.getController();
                    cIR.setData(commentList.get(i),this);

                    commentContainerId.add(anchorPane,column,row++);
                }catch (IOException | SQLException e){
                    System.out.println(e.getMessage());
                }

            }


        }

    }
    @FXML
    void goToNextImage(ActionEvent event) {
        if(images.size()-1==currentImage){
            System.out.println("there is no more picture postDetailsController");
        }else{
            currentImage++;
            img.setImage(images.get(currentImage));
        }
    }

    @FXML
    void goToPreviousImage(ActionEvent event) {
        currentImage--;
        if(currentImage <=0  ){
            try {
                ArrayList<PostImage> firstPostImage = new ArrayList<>(sPI.getPostImagesByPostId(this.post.getId()));
                Image image = new Image(firstPostImage.get(0).getImage_blob());
                img.setImage(image);
                System.out.println("there is no more picture postDetailsController");
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }else {
            img.setImage(images.get(currentImage));
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
                    System.out.println("111111111111111111111111111111111111111111111");
                    if(update == true){
                        PostImage p111 = new PostImage();
                        p111.setPost(this.updatedComment);
                        p111.setImage_blob(in);
                        p111.setUrl(null);
                        postImagesAdd.add(p111);
                        System.out.println("111111111111111111111111111111111111111111111");
                    }
                    if(updatePost == true){
                        PostImage p111 = new PostImage();
                        p111.setPost(this.post);
                        p111.setImage_blob(in);
                        p111.setUrl(null);
                        postImagesAdd.add(p111);
                        System.out.println("111111111111111111111111111111111111111111111");
                    }
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
    public void returnToComment(ActionEvent event) {
        saveId.setVisible(true);
        updateId.setVisible(false);
        Description.setText("");
        imagesHbox.getChildren().clear();
    }
    @FXML
    public void removePicture(MouseEvent Event, HBox ImagesHbox, VBox imageBox, File file) {
        ImagesHbox.getChildren().remove(imageBox);
        arrayImages.remove(file);
    }
    @FXML
    public void removePictureBlob(MouseEvent Event, HBox ImagesHbox, VBox imageBox, PostImage postImage) {
        ImagesHbox.getChildren().remove(imageBox);
        System.out.println("removePi");
        postImagesDelete.add(postImage);
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
        if(Description.getText().isBlank()){
            blankDescriptionId.setVisible(true);
            Description.textProperty().addListener(((observable, oldValue, newValue) -> {
                if(!oldValue.isBlank()){
                    blankDescriptionId.setVisible(false);
                }
            }));
        }else{
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
                    }catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            commentList.add(comment);
        }

    }
    @FXML
    void updateCommentGui(ActionEvent event) {

        if(Description.getText().isBlank()){
            blankDescriptionId.setVisible(true);
            Description.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    blankDescriptionId.setVisible(false);
                }
            }));
        }else{
            blankDescriptionId.setVisible(false);
            Post oldComment = updatedComment ;
            this.updatedComment.setDescription(Description.getText());
            this.updatedComment.setPost(this.post);
            ServicePost sP = new ServicePost();
            try {
                sP.update(this.updatedComment);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            if(!this.postImagesAdd.isEmpty()){
                for(PostImage postImage: this.postImagesAdd){
                    ServicePostImage sPI = new ServicePostImage();
                    try {
                        sPI.add(postImage);
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            if(!this.postImagesDelete.isEmpty()){
                for(PostImage postImage: this.postImagesDelete){
                    ServicePostImage sPI = new ServicePostImage();
                    try {
                        sPI.delete(postImage);
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            int placeOfOldComment = commentList.indexOf(oldComment);
            commentList.set(placeOfOldComment,updatedComment);
            Description.setText("");
            updateId.setVisible(false);
            saveId.setVisible(true);
            returnToCommentId.setVisible(false);
        }

    }

    public void updateComment(Post comment){
        if(Description.getText().isBlank()) {
            blankDescriptionId.setVisible(true);
            Description.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    blankDescriptionId.setVisible(false);
                }
            }));
        }else if (titleEditFieldId.getText().isBlank()){
            titleEditFieldId.setVisible(true);
            titleEditFieldId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    titleEditFieldId.setVisible(false);
                }
            }));
        }else{
            blankTitleId.setVisible(false);
            blankDescriptionId.setVisible(false);
            this.updatedComment = comment ;
            Description.setText(comment.getDescription());
            this.update = true ;
            this.updatePost = false;
            updateId.setVisible(true);
            saveId.setVisible(false);
            donePostId.setVisible(false);
            ServicePostImage sPI = new ServicePostImage();
            if(comment.getPostImages()!= null){
                imagesHbox.getChildren().clear();
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
                            imageButton.setOnMouseClicked(event -> this.removePictureBlob(event,imagesHbox,imageBox,postImage));
                            imagesHbox.getChildren().add(imageBox);

                        }
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }else {
                imagesHbox.getChildren().clear();

            }
        }

    }

    public void deleteComment(Post comment){

        try{
            ArrayList<PostImage> postImages = new ArrayList<>(comment.getPostImages()) ;
            if(!postImages.isEmpty()){
                for(PostImage postImage : postImages){
                    sPI.delete(postImage);
                }
            }
            sP.delete(comment);
            commentList.remove(comment);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void deletePost(ActionEvent event) {
        commentList = FXCollections.observableList(comments);
        if(!commentList.isEmpty()){
            for(Post comment : commentList){
                try{
                    ArrayList<PostImage> postImagesComment = new ArrayList<>(comment.getPostImages()) ;
                    if(!postImagesComment.isEmpty()){
                        for(PostImage postImage :postImagesComment){
                            sPI.delete(postImage);
                        }
                    }
                    sP.delete(comment);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }

        try{
            ArrayList<PostImage> postImages = new ArrayList<>(this.post.getPostImages()) ;
            if(!postImages.isEmpty()){
                for(PostImage postImage : postImages){
                    sPI.delete(postImage);
                }
            }
            sP.delete(this.post);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        try{
            Parent root = loader.load();
            HomeController hC = loader.getController();
            Description.getScene().setRoot(root);
            hC.changeToPostsFunction(event);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void editPostFunction(ActionEvent event) {
        this.updatePost = true;
        this.update = false ;
        titleEditFieldId.setVisible(true);
        Description.setPromptText("update Post");
        saveId.setVisible(false);
        updateId.setVisible(false);
        donePostId.setVisible(true);
        Description.setText(this.post.getDescription());
        titleEditFieldId.setText(this.post.getTitle());
        updatePostImages();
    }
    @FXML
    void updatePost(ActionEvent event) {
        if(Description.getText().isBlank()) {
            blankDescriptionId.setVisible(true);
            Description.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    blankDescriptionId.setVisible(false);
                }
            }));
        }else if (titleEditFieldId.getText().isBlank()){
            titleEditFieldId.setVisible(true);
            titleEditFieldId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank()) {
                    titleEditFieldId.setVisible(false);
                }
            }));
        }else{
            this.post.setDescription(Description.getText());
            this.post.setTitle(titleEditFieldId.getText());
            ServicePost sP = new ServicePost();
            try {
                sP.update(this.post);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            if(!this.postImagesAdd.isEmpty()){
                for(PostImage postImage: this.postImagesAdd){
                    ServicePostImage sPI = new ServicePostImage();
                    try {
                        sPI.add(postImage);
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            if(!this.postImagesDelete.isEmpty()){
                for(PostImage postImage: this.postImagesDelete){
                    ServicePostImage sPI = new ServicePostImage();
                    try {
                        sPI.delete(postImage);
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        Description.setText("");
        titleEditFieldId.setVisible(false);
        imagesHbox.getChildren().clear();
        donePostId.setVisible(false);
        saveId.setVisible(true);
        returnToCommentId.setVisible(false);
        setData(this.post);
    }
    public void  updatePostImages(){
        ServicePostImage sPI = new ServicePostImage();
        if(this.post.getPostImages()!= null){
            imagesHbox.getChildren().clear();
            try {
                this.postImagesUpdate = new ArrayList<>(sPI.getPostImagesByPostId(this.post.getId())) ;
                if(postImagesUpdate.isEmpty()){
                    System.out.println("Post with no pictures");
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
                        imageButton.setOnMouseClicked(event -> this.removePictureBlob(event,imagesHbox,imageBox,postImage));
                        imagesHbox.getChildren().add(imageBox);
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }else {
            imagesHbox.getChildren().clear();

        }
    }
}
