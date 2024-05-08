package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.Transport;
import org.example.utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceTransport implements IServices<Transport> { // Updated interface implementation

    private MyDb myDb;

    public ServiceTransport(MyDb myDb) {
        this.myDb = myDb;
    }

    @Override
    public void add(Transport transport) throws SQLException {
        Connection con = myDb.getCon();
        String query = "INSERT INTO Transport (name, capacite, etat, image, prix) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, transport.getName());
            preparedStatement.setInt(2, transport.getCapacite());
            preparedStatement.setInt(3, transport.getEtat());
            preparedStatement.setString(4, transport.getImage());
            preparedStatement.setInt(5, transport.getPrix());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Transport transport) throws SQLException {
        Connection con = myDb.getCon();
        String query = "UPDATE Transport SET name=?, capacite=?, etat=?, image=?, prix=? WHERE id=?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, transport.getName());
            preparedStatement.setInt(2, transport.getCapacite());
            preparedStatement.setInt(3, transport.getEtat());
            preparedStatement.setString(4, transport.getImage());
            preparedStatement.setInt(5, transport.getPrix());
            preparedStatement.setInt(6, transport.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Transport transport) throws SQLException {
        delete(transport.getId());
    }

    public void delete(int id) throws SQLException {
        Connection con = myDb.getCon();
        String query = "DELETE FROM Transport WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
    public void deleteTransport(Transport transport) throws SQLException {
        delete(transport);
    }

    public void updateTransport(Transport transport) throws SQLException {
        update(transport);
    }

    public void addTransport(Transport transport) throws SQLException {
        add(transport);
    }

    public List<Transport> getAllTransports() throws SQLException {
        return afficher();
    }


    @Override
    public List<Transport> afficher() throws SQLException {
        List<Transport> transports = new ArrayList<>();
        Connection con = myDb.getCon();
        String query = "SELECT * FROM Transport";

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Transport transport = new Transport(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("capacite"),
                        resultSet.getInt("etat"),
                        resultSet.getString("image"),
                        resultSet.getInt("prix")
                );
                transports.add(transport);
            }
        }
        return transports;
    }
}
