package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.PostImage;
import org.example.utils.MyDb;
import org.example.models.Logement;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePostImage implements IServices<PostImage> {
    public Connection con;
    public Statement ste;

    public ServicePostImage(){
        con= MyDb.getInstance().getCon();
    }

    @Override
    public void add(Logement logement) throws SQLException {

    }
    @Override
    public void add(PostImage postImage) throws SQLException {
        String req = "INSERT INTO `postimage` (`post_id`, `url`, `blobimage`) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(req);
        pst.setInt(1, postImage.getPost().getId());
        pst.setString(2, postImage.getUrl()); // Assuming 'url' is a string column and you want to insert 'Null'
        pst.setBlob(3, postImage.getImage_blob());
        pst.executeUpdate();

    }

    @Override
    public void update(PostImage postImage) throws SQLException {
        String req = "UPDATE postimage SET post_id=?, url=?, blobimage=? WHERE  id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,postImage.getId());
        pre.setString(2,"11");
        pre.setBlob(3,postImage.getImage_blob());
        pre.setInt(4,postImage.getId());
        pre.executeUpdate();
    }

    @Override
    public void delete(PostImage postImage) throws SQLException {
        String req = "delete from postimage where id=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,postImage.getId());
        pre.executeUpdate();
    }

    @Override
    public List<PostImage> afficher() throws SQLException {
        List<PostImage> postimages = new ArrayList<>();
        ServicePost sP = new ServicePost();
        String req = "select * from postimage";
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            int id = res.getInt(1);
            int postid =res.getInt(2);
            String url = res.getString(3) ;
            if( res.getBlob(4) != null){
                InputStream imageblob =res.getBlob(4).getBinaryStream();
            }
            InputStream imageblob = null ;
            PostImage p = new PostImage(id,sP.findPostById(postid),url,imageblob);
            postimages.add(p);
        }
        return postimages;
    }

    public PostImage findPostImageById(int postImageId) throws SQLException {
        ServicePost sP = new ServicePost();
        String req = "select * from postimage where id=?";
        ste = con.createStatement();
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,postImageId);
        ResultSet res = pre.executeQuery();
        while (res.next()){
            int id = res.getInt(1);
            int postid =res.getInt(2);
            String url = res.getString(3) ;
            System.out.println("22222222222222");
            if( res.getBlob(4) != null){
                InputStream imageblob =res.getBlob(4).getBinaryStream();
            }
            InputStream imageblob = null ;
            return new PostImage(id,sP.findPostById(postid),url,imageblob);
        }
        return null;
    }
    /*public List<PostImage> getPostImagesByPostId(int post) throws SQLException {
        List<PostImage> postimages = new ArrayList<>();
        ServicePost sP = new ServicePost();
        String req = "select * from postimage where post_id=?";
        ste = con.createStatement();
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,post);
        ResultSet res = pre.executeQuery();
        while (res.next()){
            int id = res.getInt(1);
            int postid =res.getInt(2);
            InputStream imageblob =res.getBlob(4).getBinaryStream();
            PostImage p = new PostImage(id,sP.findPostById(postid),imageblob);
            postimages.add(p);
        }
        return postimages;
    }*/
    public List<PostImage> getPostImagesByPostId(int post) throws SQLException {
        List<PostImage> postimages = new ArrayList<>();
        ServicePost sP = new ServicePost();
        String req = "select * from postimage where post_id=?";
        ste = con.createStatement();
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,post);
        ResultSet res = pre.executeQuery();
        while (res.next()){
            int id = res.getInt(1);
            int postid =res.getInt(2);
            String url = res.getString(3) ;
            if( res.getBlob(4) != null){
                InputStream imageblob =res.getBlob(4).getBinaryStream();
            }
            InputStream imageblob = null ;
            PostImage p = new PostImage(id,sP.findPostById(postid),url,imageblob);
            postimages.add(p);
        }
        return postimages;
    }



}
