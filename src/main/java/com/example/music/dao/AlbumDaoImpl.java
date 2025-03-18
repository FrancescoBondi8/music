package com.example.music.dao;

import com.example.music.config.MySQLConnection;
import com.example.music.model.Album;

import java.sql.*;
import java.util.Set;
import java.util.logging.Logger;

public class AlbumDaoImpl implements AlbumDao {

    Logger log = Logger.getLogger(AlbumDaoImpl.class.getName());

    @Override
    public Album save(Album album) throws SQLException{

        Album inserito = null;
        Integer albumId = -1;
        String query = "insert into album(titolo,data_uscita,genere,artista_id,data_inserimento) values(?,?,?,?,?)";
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ){

            statement.setString(1, album.getTitolo());
            statement.setDate(2, Date.valueOf(album.getDataUscita()));
            statement.setString(3, album.getGenere());
            statement.setInt(4,album.getArtistaid().getArtistaid());
            statement.setTimestamp(5,Timestamp.valueOf(album.getDataInserimento()));

            int righeCoinvolte = statement.executeUpdate();
            if(righeCoinvolte>0) {
                ResultSet result = statement.getGeneratedKeys();

                if (result.next()) {
                    albumId = result.getInt(1);
                    inserito = new Album();
                    inserito.setAlbumid(albumId);
                    inserito.setTitolo(album.getTitolo());
                    inserito.setDataUscita(album.getDataUscita());
                    inserito.setGenere(album.getGenere());
                    inserito.setArtistaid(album.getArtistaid());
                    inserito.setDataInserimento(album.getDataInserimento());

                    log.info("Inserito album " + albumId);
                } else {
                    log.info("Niente");
                }
                result.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inserito;
    }

    @Override
    public Album findById(Integer albumId) {
        return null;
    }

    @Override
    public Album update(Integer albumId, Album album) {
        return null;
    }

    @Override
    public Album deleteById(Integer albumId) {
        return null;
    }

    @Override
    public Set<Album> findAll() {
        return null;
    }
}
