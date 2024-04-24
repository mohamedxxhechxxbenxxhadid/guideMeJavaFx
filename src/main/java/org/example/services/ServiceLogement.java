package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.Logement;
import org.example.utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceLogement implements IServices {
    private MyDb myDb;

    public ServiceLogement(MyDb myDb) {
        this.myDb = myDb;
    }

    // Method to get the category ID based on its name
    private int getIdCategorie(String categoryName) throws SQLException {
        String query = "SELECT id FROM categorie WHERE type = ?";
        try (PreparedStatement preparedStatement = myDb.getCon().prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Category not found: " + categoryName);
                }
            }
        }
    }


    @Override
    public void add(Object o) throws SQLException {
        if (o instanceof Logement) {
            Logement logement = (Logement) o;
            // Implement the logic to add the logement to the database
            // This could involve executing SQL queries to insert the logement data into the database
        } else {
            throw new IllegalArgumentException("Object must be of type Logement.");
        }
    }


    @Override
    public void add(Logement logement) throws SQLException {
        Connection con = myDb.getCon();
        String query = "INSERT INTO logement (categorie, nom, description, place, prix) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, logement.getCategorieId());
            preparedStatement.setString(2, logement.getNom());
            preparedStatement.setString(3, logement.getDescription());
            preparedStatement.setString(4, logement.getPlace());
            preparedStatement.setInt(5, logement.getPrix());

            // Execute the SQL statement
            preparedStatement.executeUpdate();
        }
    }



    @Override
    public void update(Object o) throws SQLException {
        if (o instanceof Logement) {
            update((Logement) o);
        } else {
            throw new IllegalArgumentException("Object must be of type Logement.");
        }
    }

    @Override
    public void delete(Object o) throws SQLException {
        if (o instanceof Logement) {
            Logement logement = (Logement) o;
            delete(logement.getId());
        } else {
            throw new IllegalArgumentException("Object is not an instance of Logement.");
        }
    }


    @Override
    public List<Logement> afficher() throws SQLException {
        List<Logement> logements = new ArrayList<>();
        Connection con = myDb.getCon();
        String query = "SELECT * FROM logement";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Logement logement = new Logement(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("place"),
                        resultSet.getInt("prix"),
                        resultSet.getInt("categorie")
                );
                logements.add(logement);
            }
        }
        return logements;
    }
    // Method to retrieve all categories from the "categorie" table
    public List<String> getAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        Connection con = myDb.getCon();
        String query = "SELECT type FROM categorie";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                categories.add(resultSet.getString("type"));
            }
        }
        return categories;
    }
    public int getCategorieId(String categorietype) throws SQLException {
        int categorieId = -1; // Default value if category ID is not found
        Connection con = myDb.getCon();
        String query = "SELECT id FROM categorie WHERE type = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, categorietype);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categorieId = resultSet.getInt("id");
                }
            }
        }
        return categorieId;
    }
    public String getCategoryName(int categoryId) throws SQLException {
        String categoryName = null;
        Connection con = myDb.getCon();
        String query = "SELECT type FROM categorie WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categoryName = resultSet.getString("type");
                }
            }
        }
        return categoryName;
    }

    public void update(Logement logement) throws SQLException {
        Connection con = myDb.getCon();
        String query = "UPDATE logement SET categorie=?, nom=?, description=?, place=?, prix=? WHERE id=?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            // Set parameters
            preparedStatement.setInt(1, logement.getCategorieId());
            preparedStatement.setString(2, logement.getNom());
            preparedStatement.setString(3, logement.getDescription());
            preparedStatement.setString(4, logement.getPlace());
            preparedStatement.setInt(5, logement.getPrix());
            preparedStatement.setInt(6, logement.getId());

            // Log the SQL query and parameters
            System.out.println("Executing SQL query: " + preparedStatement.toString());

            // Execute the query
            preparedStatement.executeUpdate();

            System.out.println("Logement updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions appropriately
        }
    }
    public void delete(Logement logement) throws SQLException {
        Connection con = myDb.getCon();
        String query = "DELETE FROM logement WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, logement.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Logement deleted successfully.");
            } else {
                System.out.println("Logement not found with ID: " + logement.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions appropriately
        }
    }



}
