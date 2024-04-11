package org.example.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.example.models.*;
import org.example.services.*;
import org.example.utils.MyDb;

public class Main {
    public static Connection con ;
    public static void main(String[] args) throws SQLException {
        /*ServiceReclamation sR = new ServiceReclamation();
        Reclamation reclamation = new Reclamation("name","123123123","email","titre","message");
        try{
            sR.add(reclamation);
            System.out.println(sR.afficher());
            reclamation = sR.afficher().get(0);
            reclamation.setMessage("updated");
            sR.update(reclamation);
            System.out.println(sR.afficher());
            try {
                Thread.sleep(5000); // 5000 milliseconds = 5 seconds
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Done waiting!");
            //sR.delete(reclamation);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }*/
        ServicePostImage sPi = new ServicePostImage() ;
        ServicePost sP = new ServicePost();
        ServiceUser sU = new ServiceUser();
        User u = new User() ;
        Post p1 = null ;
        try {
            p1 = sP.findPostById(7);
            u = sU.findUserById(1);
            System.out.println(u);
            System.out.println(p1);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        Post p = new Post("title123","description",null,true,true,0,0,u);
        Post comment = new Post("title122223","description",p1,true,true,0,0,u);
        try{
            sP.add(p);
            sP.add(comment);
            System.out.println(sP.afficher());
            try {
                Thread.sleep(5000); // 5000 milliseconds = 5 seconds
                System.out.println(111);
                Post uP = sP.findPostById(10);
                System.out.println(uP);
                uP.setTitle("uopdated");
                System.out.println(uP);
                sP.update(uP);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Post postWithImges = sP.findPostById(12);
            ServicePostImage sPI = new ServicePostImage();
            System.out.println(sPI.getPostImagesByPostId(postWithImges.getId()));
            postWithImges.setPostImages(sPI.getPostImagesByPostId(postWithImges.getId()));
            System.out.println(postWithImges);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        /*try {
            ServicePostImage sPI = new ServicePostImage();
            ServicePost sP = new ServicePost();
            try {
                File pic=new File(  Main.class.getResource( "/pict.jpg" ).toURI()  );
                InputStream in = new FileInputStream(pic);
                PostImage pI = new PostImage(sP.findPostById(12),in);
                pic=new File(  Main.class.getResource( "/pic1.png" ).toURI()  );
                in = new FileInputStream(pic);
                PostImage pI1 = new PostImage(1,sP.findPostById(12),in);
                sPI.update(pI1);
                System.out.println(pI);
                sPI.add(pI);
                sPI.delete(sPI.findPostImageById(1));
                System.out.println(sPI.afficher());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/
        /*try{
            ServiceVote sV = new ServiceVote();
            ServicePost sP = new ServicePost();
            Vote v = new Vote(true,false,sP.findPostById(12),"hech@gmail.com");
            sV.add(v);
            v = new Vote(false,true,sP.findPostById(12),"hech@gmail.com");
            sV.add(v);
            try {
                Thread.sleep(5000); // 5000 milliseconds = 5 seconds
                Vote newVote = sV.findVoteById(9);
                newVote.setUser("updated");
                sV.update(newVote);
                Thread.sleep(10000); // 5000 milliseconds = 5 seconds
                sV.delete(newVote);
                System.out.println(111);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(sV.afficher());

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }*/
    }

}