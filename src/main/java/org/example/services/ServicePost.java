package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.Post;
import org.example.models.User;
import org.example.utils.MyDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicePost implements IServices<Post> {
    public Connection con;
    public Statement ste;

    public ServicePost(){
        con= MyDb.getInstance().getCon();
    }
    @Override
    public void add(Post post) throws SQLException {
        String req = "INSERT INTO `post` (`post_id`, `creator_id`, `title`, `description`, `approved`, `big_post`, `up_vote_num`, `down_vote_num`, `created_at`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(req);
        if (post.getPost() == null){
            pst.setObject(1, null);
        }else{
            pst.setInt(1, post.getPost().getId());
        }
        pst.setInt(2, post.getCreator().getId());
        pst.setString(3, post.getTitle());
        pst.setString(4, post.getDescription());
        pst.setBoolean(5, post.getApproved());
        pst.setBoolean(6, post.getBigPost());
        pst.setInt(7, post.getUpVoteNum());
        pst.setInt(8, post.getDownVoteNum());
        pst.setTimestamp(9, Timestamp.valueOf(post.getCreatedAt()));

        pst.executeUpdate();

    }

    @Override
    public void update(Post post) throws SQLException {
        String req = "UPDATE `post` SET `post_id`=?, `creator_id`=?, `title`=?, `description`=?, `approved`=?, `big_post`=?, `up_vote_num`=?, `down_vote_num`=?, `created_at`=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(req);
        if (post.getPost() == null ){
            pst.setObject(1, null);
        }else {
            pst.setInt(1,post.getPost().getId());
        }
        pst.setInt(2, post.getCreator().getId());
        pst.setString(3, post.getTitle());
        pst.setString(4, post.getDescription());
        pst.setBoolean(5, post.getApproved());
        pst.setBoolean(6, post.getBigPost());
        pst.setInt(7, post.getUpVoteNum());
        pst.setInt(8, post.getDownVoteNum());
        pst.setTimestamp(9, Timestamp.valueOf(post.getCreatedAt()));
        pst.setInt(10,post.getId());

        pst.executeUpdate();
    }

    @Override
    public void delete(Post post) throws SQLException {
        String req = "delete from post where id=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,post.getId());
        pre.executeUpdate();
        System.out.println(post.getId());
    }

    @Override
    public List<Post> afficher() throws SQLException {
        ServiceUser sUser = new ServiceUser() ;
        List<Post> posts = new ArrayList<>();
        List<Post> comments = new ArrayList<>();
        String req = "select * from post";
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        User creator ;
        while (res.next()){
            int postid =res.getInt(2);
            int creatorid = res.getInt(3);
            if (creatorid != 0 ){
                creator = sUser.findUserById(creatorid);
            }else{
                creator = null ;
            }
            int id = res.getInt(1);
            String title  =res.getString(4);
            String description =res.getString(5);
            boolean approved =res.getBoolean(6);
            boolean bigpost =res.getBoolean(7);
            int downVote = res.getInt(8);
            int upVote = res.getInt(9);
            if (bigpost){
                comments =getComment(id);
            }else {
                comments = null;
            }
            LocalDateTime created = res.getTimestamp("created_at").toLocalDateTime();

            Post p = new Post(id,title,description,null,comments,approved,bigpost,upVote,downVote,created,creator);
            ServicePostImage servicePostImage = new ServicePostImage();
            p.setPostImages(servicePostImage.getPostImagesByPostId(p.getId()));
            posts.add(p);
        }
        return posts;
    }

    public List<Post> getComment(int postId) throws SQLException {
        List<Post> comments = new ArrayList<>();
        ServiceUser sUser = new ServiceUser() ;
        String req = "SELECT * FROM post WHERE post_id = ?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setInt(1, postId);
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    int postid = res.getInt(2);
                    int creatorid = res.getInt(3);
                    User creator = null;
                    if (creatorid != 0) {
                        creator = sUser.findUserById(creatorid);
                    }
                    int id = res.getInt(1);
                    String title  =res.getString(4);
                    String description =res.getString(5);
                    boolean approved =res.getBoolean(6);
                    boolean bigpost =res.getBoolean(7);
                    int downVote = res.getInt(8);
                    int upVote = res.getInt(9);
                    LocalDateTime created = res.getTimestamp("created_at").toLocalDateTime();

                    Post p = new Post(id, title, description, null, null, approved, bigpost, upVote, downVote, created, creator);
                    comments.add(p);
                }
            }
        }
        return comments;
    }
    public Post findPostById(int postSerachId) throws  SQLException{
        String req = "select * from post where id = "+postSerachId;
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        List<Post> comments = new ArrayList<>();
        ServiceUser sUser = new ServiceUser() ;
        User user ;
        while (res.next()){
            int id = res.getInt("id"); // Assuming 'id' is the name of the column in your database
            int postId = res.getInt("post_id");
            int creatorId = res.getInt("creator_id");
            String title = res.getString("title");
            String description = res.getString("description");
            boolean approved = res.getBoolean("approved");
            boolean bigPost = res.getBoolean("big_post");
            int upVoteNum = res.getInt("up_vote_num");
            int downVoteNum = res.getInt("down_vote_num");
            LocalDateTime createdAt = res.getTimestamp("created_at").toLocalDateTime();
            if (bigPost){
                comments =getComment(id);
            }else {
                comments = null;
            }
            if (creatorId != 0){
                user=sUser.findUserById(creatorId);
            }else{
                user=null;
            }
            // Create a Post object and add it to the list
            return new Post(id,title, description,comments ,approved, bigPost, upVoteNum, downVoteNum, createdAt,user);

        }
        return null ;
    }


}
