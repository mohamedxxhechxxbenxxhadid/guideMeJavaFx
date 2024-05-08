package org.example.models;


import javafx.scene.image.Image;

public class User {

    private int id;
    private String fullname, adress, phone_numer, email, password, image;
    private UserRole role;
    private boolean is_verified = false;
    private boolean is_activated = true;


    public User() {
    }

    public User(int id, String fullname, String adress, String phone_numer, String email, String password, UserRole role, boolean is_verified, boolean is_activated, String image) {
        this.id = id;
        this.fullname = fullname;
        this.image= image;
        this.adress = adress;
        this.phone_numer = phone_numer;
        this.email = email;
        this.password = password;
        this.role = role;
        this.is_verified = is_verified;
        this.is_activated = is_activated;
    }

    public User(int id, String fullname, String adress, String phone_numer, String email, String password, String role, boolean is_verified, boolean is_activated) {
        this.id = id;
        this.fullname = fullname;
        this.adress = adress;
        this.phone_numer = phone_numer;
        this.email = email;
        this.password = password;
        if(role.equals(UserRole.ROLE_ADMIN)){
            this.role = UserRole.ROLE_ADMIN;
        }else{
            this.role = UserRole.ROLE_USER ;
        }
        this.is_verified = is_verified;
        this.is_activated = is_activated;
    }

    public User( String fullname, String adress, String phone_numer, String email, String password, UserRole role, boolean is_verified, boolean is_activated) {
        this.fullname = fullname;
        this.adress = adress;
        this.phone_numer = phone_numer;
        this.email = email;
        this.password = password;
        this.role = role;
        this.is_verified = is_verified;
        this.is_activated = is_activated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone_numer() {
        return phone_numer;
    }

    public void setPhone_numer(String phone_numer) {
        this.phone_numer = phone_numer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public boolean isIs_activated() {
        return is_activated;
    }

    public void setIs_activated(boolean is_activated) {
        this.is_activated = is_activated;
    }


    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z]\\w*@[\\w-]+(\\.[\\w-]+)*$");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", adress='" + adress + '\'' +
                ", phone_numer='" + phone_numer + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", role=" + role +
                ", is_verified=" + is_verified +
                ", is_activated=" + is_activated +
                '}';
    }
}
