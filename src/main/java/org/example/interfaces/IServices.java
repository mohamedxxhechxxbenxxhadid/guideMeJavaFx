package org.example.interfaces;

import org.example.models.Logement;

import java.sql.SQLException;
import java.util.List;

public interface IServices<T>{
    void add(T t) throws SQLException;

    void add(Logement logement) throws SQLException;

    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
    List<T> afficher() throws SQLException;
}