package org.example.models;

public class categorie {
    public int id;
    public String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public categorie(int identifiant, String categorieType) {
        id = identifiant;
        type = categorieType;
    }
    public categorie(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "categorie{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

}


