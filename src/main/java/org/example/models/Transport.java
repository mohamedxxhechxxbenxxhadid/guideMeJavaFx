package org.example.models;

public class Transport {
    private int id;
    private String name;
    private int capacite;
    private int etat;
    private String image;
    private int prix;

    // Constructor
    public Transport(int id, String name, int capacite, int etat, String image, int prix) {
        this.id = id;
        this.name = name;
        this.capacite = capacite;
        this.etat = etat;
        this.image = image;
        this.prix = prix;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Capacite: " + capacite + "\n" +
                "Etat: " + etat + "\n" +
                "Prix: " + prix;
    }
}
