package com.example.music.dao;

import com.example.music.model.Artista;

import java.sql.SQLException;
import java.util.Set;

public interface ArtistaDao {
    Artista save(Artista artista) throws SQLException;

    Artista findById(Integer artistaid) throws SQLException;

    Artista update(Integer artistaid, Artista artista) throws SQLException;

    Artista deleteById(Integer artistaid) throws SQLException;

    Set<Artista> findAll() throws SQLException;
}
