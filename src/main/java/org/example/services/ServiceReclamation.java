package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.Reclamation;
import org.example.utils.MyDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IServices<Reclamation> {
    public Connection con;
    public Statement ste;
    public ServiceReclamation(){
        con= MyDb.getInstance().getCon();
    }
    @Override
    public void add(Reclamation reclamation) throws SQLException {
        String req = "insert into reclamation (name,phon_number,email,titre,message,created_at) values('"+reclamation.getName()+"','"+reclamation.getphon_number()+"','"+reclamation.getEmail()+"','"+reclamation.getTitre()+"','"+reclamation.getMessage()+"','"+reclamation.getCreatedAt()+"')";
        ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void update(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET name=?, phon_number=?, email=?, titre=?, message=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,reclamation.getName());
        pre.setString(2,reclamation.getphon_number());
        pre.setString(3,reclamation.getEmail());
        pre.setString(4,reclamation.getTitre());
        pre.setString(5,reclamation.getMessage());
        pre.setInt(6,reclamation.getId());
        pre.executeUpdate();

    }

    @Override
    public void delete(Reclamation reclamation) throws SQLException {
        String req = "delete from reclamation where id=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,reclamation.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Reclamation> afficher() throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "select * from reclamation";
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            int id = res.getInt(1);
            String name =res.getString("name");
            String phone =res.getString(3);
            String email =res.getString("email");
            String titre =res.getString("titre");
            String message =res.getString("message");
            LocalDateTime created = res.getTimestamp("created_at").toLocalDateTime();
            Reclamation p = new Reclamation(id,name,phone,email,titre,message,created);
            reclamations.add(p);
        }
        return reclamations;
    }
}
