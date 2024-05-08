package org.example.interfaces;

import java.sql.SQLException;
import java.util.List;
import org.example.models.Logement;

public interface IServices<T>{
    void add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
    void add(Logement logement) throws SQLException;

    List<T> afficher() throws SQLException;
}