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
        String req = "insert into categorie (type) values('" + categorie.getType() + "')";
        ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void add(Logement logement) throws SQLException {

    }


    @Override
    public void update(categorie categorie) throws SQLException {

    }

    @Override
    public void delete(categorie categorie) throws SQLException {

    }

    @Override
    public List<categorie> afficher() throws SQLException {
        return null;
    }
    public List<categorie> populateComboBox() throws SQLException {
        List<categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";

        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("type");
                categorie category = new categorie(type);
                categories.add(category);
            }
        }

        return categories;
    }

    // Other methods...
}


