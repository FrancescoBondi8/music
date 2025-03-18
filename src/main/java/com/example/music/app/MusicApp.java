package com.example.music.app;

import com.example.music.dao.AlbumDao;
import com.example.music.dao.AlbumDaoImpl;
import com.example.music.dao.ArtistaDao;
import com.example.music.dao.ArtistaDaoImpl;
import com.example.music.model.Album;
import com.example.music.model.Artista;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class MusicApp {
    public static void main(String[] args) {
        /*
        Artista inserito = null;

        Artista artista = new Artista();
        artista.setNome("Beatles");
        artista.setNazione("Regno Unito");
        artista.setAnno_inizio(1962);
        artista.setData_inserimento(LocalDateTime.now());
        try{
            ArtistaDao artistaDao = new ArtistaDaoImpl();
            inserito = artistaDao.save(artista);
            System.out.println(inserito.getArtistaid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Album album = new Album();
        album.setTitolo("titolo");
        album.setArtistaid(inserito);
        album.setGenere("POP");;
        album.setDataUscita(LocalDate.of(2025, 3, 17));
        album.setDataInserimento(LocalDateTime.now());

        try{
            AlbumDao albumDao = new AlbumDaoImpl();
            albumDao.save(album);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


        AlbumDao albumDao = new AlbumDaoImpl();
        albumDao.findAll();
    }
}
