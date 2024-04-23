package org.example.models;

public class Logement {
    private int id;
    private categorie categorie;
    private String nom;
    private String description;
    private String place;
    private int prix;
    private int categorieId;

    // Constructor
    public Logement(String nom, String description, String place, int prix, int categorieId) {
        this.nom = nom;
        this.description = description;
        this.place = place;
        this.prix = prix;
        this.categorieId = categorieId;
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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Logement{" +
                "id=" + id +
                ", categorie=" + categorie +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                ", prix=" + prix +
                '}';
    }
}
