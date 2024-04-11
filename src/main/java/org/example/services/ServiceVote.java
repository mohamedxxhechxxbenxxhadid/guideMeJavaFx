package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.Vote;
import org.example.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceVote implements IServices<Vote> {
    public Connection con;
    public Statement ste;

    public ServiceVote(){
        con= MyDb.getInstance().getCon();
    }
    @Override
    public void add(Vote vote) throws SQLException {
        String req = "INSERT INTO `vote` (`post_id`, `up`, `down`, `user`) VALUES (?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(req);
        pst.setInt(1, vote.getPost().getId());
        pst.setBoolean(2,vote.getUp()); // Assuming 'url' is a string column and you want to insert 'Null'
        pst.setBoolean(3,vote.getDown());
        pst.setString(4,vote.getUser());
        pst.executeUpdate();

    }

    @Override
    public void update(Vote vote) throws SQLException {
        String req = "UPDATE vote SET post_id=?, up=?, down=? ,user=? WHERE  id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,vote.getPost().getId());
        pre.setBoolean(2,vote.getUp());
        pre.setBoolean(3,vote.getDown());
        pre.setString(4,vote.getUser());
        pre.setInt(5,vote.getId());
        pre.executeUpdate();
    }

    @Override
    public void delete(Vote vote) throws SQLException {
        String req = "delete from vote where id=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,vote.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Vote> afficher() throws SQLException {
        List<Vote> votes = new ArrayList<>();
        String req = "select * from vote";
        ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        ServicePost sP = new ServicePost();
        while(res.next()){
            int id = res.getInt(1);
            int post_id = res.getInt(2);
            boolean up = res.getBoolean(3);
            boolean down = res.getBoolean(4);
            String user = res.getString(5);
            Vote v = new Vote(id,up,down,sP.findPostById(post_id),user);
            votes.add(v);
        }
        return votes;
    }

    public Vote findVoteById(int voteId) throws SQLException {
        ServicePost sP = new ServicePost();
        String req = "select * from vote where id=?";
        ste = con.createStatement();
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,voteId);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            int id = res.getInt(1);
            int post_id = res.getInt(2);
            boolean up = res.getBoolean(3);
            boolean down = res.getBoolean(4);
            String user = res.getString(5);
            return new Vote(id,up,down,sP.findPostById(post_id),user);
        }
        return null;
    }
}
