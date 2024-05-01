package org.example.services;



import org.example.interfaces.IService;

import org.example.models.User;
import org.example.models.UserRole;
import org.example.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

public class ServiceUser implements IService<User> {

    private Connection con ;

    public ServiceUser(){
        con = MyDb.getInstance().getCon();
    }
    @Override
    public void add(User user) {
        String qry = "INSERT INTO `user`(`email`, `roles`, `password`, `fullname`, `adress`, `phone_numer`, `is_verified`, `is_activated`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(qry);

            pstm.setString(1, user.getEmail());
            pstm.setString(2, new ObjectMapper().writeValueAsString(user.getRole()));
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getFullname());
            pstm.setString(5, user.getAdress());
            pstm.setString(6, user.getPhone_numer());
            pstm.setBoolean(7, user.isIs_verified());
            pstm.setBoolean(8, user.isIs_activated());



            pstm.executeUpdate();
        } catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String qry ="SELECT * FROM `user`";
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id")); // Récupération de l'ID
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setPhone_numer(rs.getString("phone_numer"));
                user.setEmail(rs.getString("email"));
                user.setIs_activated(rs.getBoolean("is_activated"));
                user.setIs_verified(rs.getBoolean("is_verified"));
               /* String rolesJson = rs.getString("roles");
                ObjectMapper objectMapper = new ObjectMapper();
                List<UserRole> roles = objectMapper.readValue(rolesJson, new TypeReference<List<UserRole>>() {});
                user.setRole(roles);*/

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    @Override
    public void update(User user) {
        String qry ="UPDATE `user` SET `fullname`=?,  `phone_numer`=?, `email`=?,`adress`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = con.prepareStatement(qry);

            pstm.setString(1, user.getFullname());
            pstm.setString(2, user.getPhone_numer());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getAdress());
            //pstm.setBoolean(5, user.isIs_activated());  add attribut
            pstm.setInt(5, user.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean delete(User user) {
        String qry ="DELETE FROM `user` WHERE `id`=?";
        try {
            PreparedStatement pstm = con.prepareStatement(qry);

            pstm.setInt(1, user.getId());

            int rowsDeleted = pstm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public User getByEmailAndPassword(String email, String password) {
        String qry = "SELECT * FROM `user` WHERE `email`=? AND `password`=?";
        try {
            PreparedStatement pstm = con.prepareStatement(qry);
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id")); // Récupération de l'ID
                user.setFullname(rs.getString("fullname"));
                user.setAdress(rs.getString("adress"));
                user.setPhone_numer(rs.getString("phone_numer"));
                user.setEmail(rs.getString("email"));
                user.setIs_activated(rs.getBoolean("is_activated"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                // Récupération du rôle
                String rolesJson = rs.getString("roles");
                ObjectMapper objectMapper = new ObjectMapper();
                //   List<UserRole> roles = objectMapper.readValue(rolesJson, new TypeReference<List<UserRole>>() {});
                //user.setRole(roles);

                return user;
            }
        } catch (SQLException /*| JsonProcessingException*/ e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

