package org.example.models;
import java.time.LocalDateTime;

public class Reclamation {
    private Integer id;
    private String name;
    private String phon_number;
    private String email;
    private String titre;
    private String message;
    private LocalDateTime createdAt;

    public Reclamation(){

    }
    public Reclamation(int id,String name, String phon_number, String email, String titre, String message,LocalDateTime created_at) {
        this.id = id ;
        this.name = name;
        this.phon_number = phon_number;
        this.email = email;
        this.titre = titre;
        this.message = message;
        this.createdAt = created_at ;
    }
    public Reclamation(String name, String phon_number, String email, String titre, String message) {

        this.name = name;
        this.phon_number = phon_number;
        this.email = email;
        this.titre = titre;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getphon_number() {
        return phon_number;
    }

    public void setphon_number(String phon_number) {
        this.phon_number = phon_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phon_number='" + phon_number + '\'' +
                ", email='" + email + '\'' +
                ", titre='" + titre + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}' +'\'';
    }
}
