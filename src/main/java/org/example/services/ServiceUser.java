package org.example.services;



import org.example.interfaces.IService;

import org.example.models.User;
import org.example.models.UserRole;
import org.example.utils.MyDb;
import org.example.models.Logement;

import java.sql.*;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

public class ServiceUser implements IService<User> {

    private Connection con ;
    public Statement ste;

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

    public void updatePassword(String email, String newPassword) {
        try {
            // Prepare the SQL update statement
            String updateQuery = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);

            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();
            /*if (rowsAffected > 0) {
                System.out.println("Password updated successfully for email: " + email);
            } else {
                System.out.println("Email not found in the database or password not updated.");
                // Handle cases where the email is not found or the password is not updated
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle
        }
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
            String role = res.getString(3) ;
            boolean  verif =res.getBoolean(8);
            boolean actif =res.getBoolean(9);

            return new User(id,fullname,adress,phoneNumber,email,password, UserRole.ROLE_ADMIN,verif,actif);
        }
        return null ;
    }
}


