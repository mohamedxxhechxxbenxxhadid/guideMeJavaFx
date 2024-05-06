package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.Logement;
import org.example.models.categorie;
import org.example.utils.MyDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class ServiceCategorie implements IServices <categorie> {
    public Connection con;
    public Statement ste;

    public ServiceCategorie(){
        con= MyDb.getInstance().getCon();
    }

    @Override
    public void add(categorie categorie) throws SQLException {
        String req = "INSERT INTO categorie (type) VALUES (?)";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setString(1, categorie.getType());
            pst.executeUpdate();
        }
    }

    @Override
    public void add(Logement logement) throws SQLException {

    }


    @Override
    public void update(categorie categorie) throws SQLException {
        String query = "UPDATE categorie SET type = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, categorie.getType());
            pst.setInt(2, categorie.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(categorie categorie) throws SQLException {
        String query = "DELETE FROM categorie WHERE type = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, categorie.getType());
            pst.executeUpdate();
        }
    }


    @Override
    public List<categorie> afficher() throws SQLException {
        List<categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";

        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                categorie cat = new categorie(id, type);
                categories.add(cat); // Add category object to the list
            }
        }

        return categories;
    }
    public List<String> populateComboBox() throws SQLException {
        List<String> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";

        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("type");
                categories.add(type); // Add category name to the list
            }
        }

        return categories;
    }


    // Other methods...
}


