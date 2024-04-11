package org.example.services;

import org.example.interfaces.IServices;
import org.example.models.User;
import org.example.utils.MyDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ServiceUser implements IServices<User> {
    public Connection con;
    public Statement ste;

    public ServiceUser(){
        con= MyDb.getInstance().getCon();
    }
    @Override
    public void add(User user) throws SQLException {

    }

    @Override
    public void update(User user) throws SQLException {

    }

    @Override
    public void delete(User user) throws SQLException {

    }

    @Override
    public List<User> afficher() throws SQLException {
        return null;
    }

    public User findUserById(int userId) throws  SQLException{
        String req = "select * from user where id = "+userId;
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            int id = res.getInt(1);
            String email  =res.getString(2);
            String password =res.getString(4);
            String fullname =res.getString(5);
            String adress =res.getString(6);
            String phoneNumber =res.getString(7);
            boolean  verif =res.getBoolean(8);
            boolean actif =res.getBoolean(9);

            return new User(id,email,password,fullname,adress,phoneNumber,verif,actif);
        }
        return null ;
    }
}
