package org.example.models;

import java.util.Date;

public class Reservation_Transport {
    private int id;
    private Date date_reservation;
    private String duree_reservation;

    public Reservation_Transport() {
    }

    public Reservation_Transport(int id, Date date_reservation, String duree_reservation) {
        this.id = id;
        this.date_reservation = date_reservation;
        this.duree_reservation = duree_reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public String getDuree_reservation() {
        return duree_reservation;
    }

    public void setDuree_reservation(String duree_reservation) {
        this.duree_reservation = duree_reservation;
    }
}