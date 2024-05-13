package org.example.models;

import com.itextpdf.layout.element.Cell;

public class Logement {
    private int id;
    private categorie categorie;
    private String nom;
    private String description;
    private String place;
    private int prix;
    private int categorieId;
    private String image;
    // Constructor
    public Logement(int id,String nom, String description, String place, int prix, int categorieId, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.place = place;
        this.prix = prix;
        this.categorieId = categorieId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public org.example.models.categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(org.example.models.categorie categorie) {
        this.categorie = categorie;
    }
    public int getCategorieId() {
        return categorieId;
    }
    public void setCategorieId(int categorieId) {this.categorieId = categorieId;}
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public void setPrix(int prix) {
        this.prix = prix;
    }
    public int getPrix() {
        return prix;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Nom: " + nom + "\n" +
                "Description: " + description + "\n" +
                "Place: " + place + "\n" +
                "Prix: " + prix;
    }
}
