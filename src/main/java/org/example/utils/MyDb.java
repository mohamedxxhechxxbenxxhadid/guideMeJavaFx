package org.example.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {

    public final String USERNAME = "root";
    public final String PWD = "";

    public final String URL = "jdbc:mysql://127.0.0.1:3306/pidevjavafx";

    private Connection con;

    public Connection getCon() {
        return con;
    }


    //3 creer une variable static pour stocker l'instance
    public static MyDb instance;


    //1 rendre le constructeur privee
    private MyDb() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PWD);
            System.out.println("connected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    //2 creer une methode intermediaire
    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

  /*  public MyDb() {
        // Constructor logic
    } */
}
