package com.example.music.dao;

import com.example.music.config.MySQLConnection;
import com.example.music.model.Album;
import com.example.music.model.Artista;

import java.sql.*;
import java.util.HashSet;
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
        Album album = null;
        Artista artista = null;
        String query = "select titolo,data_uscita,genere,album.artista_id,album.data_inserimento,album.data_aggiornamento, artisti.artista_id,artisti.nome,artisti.nazione,artisti.anno_inizio,artisti.data_inserimento,artisti.data_aggiornamento " +
                       " from album join artisti on album.artista_id = artisti.artista_id" +
                        " where album_id=?";
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = creaPerFindByID(connection,query,albumId);
            ResultSet risultati = statement.executeQuery()){

            if(risultati.next()) {
                artista = new Artista();
                artista.setArtistaid(risultati.getInt(7));
                artista.setNome(risultati.getString(8));
                artista.setNazione(risultati.getString(9));
                artista.setAnno_inizio(risultati.getInt(10));
                artista.setData_inserimento(risultati.getTimestamp(11).toLocalDateTime());
                Timestamp t2 = risultati.getTimestamp(12);
                if(t2 != null){
                    artista.setData_aggiornamento(risultati.getTimestamp(12).toLocalDateTime());
                }

                album = new Album();
                album.setTitolo(risultati.getString(1));
                album.setDataUscita(risultati.getDate(2).toLocalDate());
                album.setGenere(risultati.getString(3));
                album.setArtistaid(artista);
                album.setDataInserimento(risultati.getTimestamp(5).toLocalDateTime());
                Timestamp timestamp = risultati.getTimestamp(6);
                if(timestamp != null){
                    album.setDataAggiornamento(risultati.getTimestamp(6).toLocalDateTime());
                }



                log.info("Album trovato" + album.getTitolo());
            }else{
                log.warning("Album non trovato");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
    }

    public PreparedStatement creaPerFindByID(Connection connection, String query, Integer albumId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, albumId);
        return statement;
    }

    @Override
    public Album update(Integer albumId, Album album) {
        Album inserito = null;
        String query = "update album set titolo = ?,data_uscita = ?,genere = ?,artista_id = ? where album_id = ?";
        try(Connection connection = MySQLConnection.open(); PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, album.getTitolo());
            statement.setDate(2, Date.valueOf(album.getDataUscita()));
            statement.setString(3, album.getGenere());
            statement.setInt(4, album.getArtistaid().getArtistaid());
            statement.setInt(5, albumId);
            int righeCoinvolte = statement.executeUpdate();
            if(righeCoinvolte>0) {
                inserito = new Album();
                inserito.setAlbumid(albumId);
                inserito.setTitolo(album.getTitolo());
                inserito.setGenere(album.getGenere());
                inserito.setArtistaid(album.getArtistaid());
                inserito.setDataInserimento(album.getDataInserimento());
                inserito.setDataUscita(album.getDataUscita());
                log.info("Inserito album " + albumId);
            }else {
                log.warning("Nessun inserimento");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Album deleteById(Integer albumId) {
        Album eliminato = null;
        Artista artista = null;
        String query1 = "select titolo,data_uscita,genere,album.artista_id,album.data_inserimento,album.data_aggiornamento album  where album_id =?";
        String query2 = "delete from album where album_id=?";
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement1 = creaPerFindByID(connection, query1, albumId);
            PreparedStatement statement2 = creaPerdeleteByID(connection, query2, albumId);
            ResultSet result = statement1.executeQuery();){
            statement1.setInt(1, albumId);
            if(result.next()) {
                eliminato = new Album();
                eliminato.setAlbumid(albumId);
                eliminato.setTitolo(result.getString(1));
                eliminato.setGenere(result.getString(2));
                artista = new Artista();
                artista.setArtistaid(result.getInt(3));
                eliminato.setArtistaid(artista);
                eliminato.setDataInserimento(result.getTimestamp(4).toLocalDateTime());
                Timestamp timestamp = result.getTimestamp(5);
                if(timestamp != null){
                    eliminato.setDataAggiornamento(result.getTimestamp(6).toLocalDateTime());
                }
                statement2.executeUpdate();

                log.info("Eliminato " + albumId);
            }else {
                log.warning("Eliminato non trovato");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement creaPerdeleteByID(Connection connection, String query, Integer albumId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, albumId);
        return statement;
    }

    @Override
    public Set<Album> findAll() {
        Set<Album> albums = new HashSet<>();
        Album album = null;
        Artista artista = null;

        String query = "select titolo,data_uscita,genere,album.artista_id,album.data_inserimento,album.data_aggiornamento, artisti.artista_id,artisti.nome,artisti.nazione,artisti.anno_inizio,artisti.data_inserimento,artisti.data_aggiornamento, album.album_id " +
                        " from album join artisti on album.artista_id = artisti.artista_id";
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet risultati= statement.executeQuery()){

            while(risultati.next()) {
                artista = new Artista();
                artista.setArtistaid(risultati.getInt(7));
                artista.setNome(risultati.getString(8));
                artista.setNazione(risultati.getString(9));
                artista.setAnno_inizio(risultati.getInt(10));
                artista.setData_inserimento(risultati.getTimestamp(11).toLocalDateTime());
                Timestamp t2 = risultati.getTimestamp(12);
                if(t2 != null){
                    artista.setData_aggiornamento(risultati.getTimestamp(12).toLocalDateTime());
                }

                album = new Album();
                album.setAlbumid(risultati.getInt(13));
                album.setTitolo(risultati.getString(1));
                album.setDataUscita(risultati.getDate(2).toLocalDate());
                album.setGenere(risultati.getString(3));
                album.setArtistaid(artista);
                album.setDataInserimento(risultati.getTimestamp(5).toLocalDateTime());
                Timestamp timestamp = risultati.getTimestamp(6);
                if(timestamp != null){
                    album.setDataAggiornamento(risultati.getTimestamp(6).toLocalDateTime());
                }
                albums.add(album);
            }
            System.out.println(albums);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return albums;
    }
}
