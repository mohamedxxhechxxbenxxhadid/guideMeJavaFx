package org.example.models;

import java.util.Arrays;

public class User {
    private int id;
    private String email;
    private String[] roles;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;
    private boolean isVerified;
    private boolean isActivated;
    private String image;

    // Constructors
    public User() {
    }

    public User(String email, String[] roles, String password, String fullName, String address,
                String phoneNumber, boolean isVerified, boolean isActivated, String image) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isVerified = isVerified;
        this.isActivated = isActivated;
        this.image = image;
    }
    public User(int id ,String email, String password, String fullName, String address,
                String phoneNumber, boolean isVerified, boolean isActivated) {
        this.id = id ;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isVerified = isVerified;
        this.isActivated = isActivated;
    }
    public User(int id ,String email, String[] roles, String password, String fullName, String address,
                String phoneNumber, boolean isVerified, boolean isActivated, String image) {
        this.id = id ;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isVerified = isVerified;
        this.isActivated = isActivated;
        this.image = image;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isVerified=" + isVerified +
                ", isActivated=" + isActivated +
                ", image='" + image + '\'' +
                '}';
    }
}
