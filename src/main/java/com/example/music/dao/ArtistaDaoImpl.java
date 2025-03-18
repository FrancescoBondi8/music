package com.example.music.dao;

import com.example.music.config.MySQLConnection;
import com.example.music.model.Artista;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ArtistaDaoImpl implements ArtistaDao {
    // implementazione di un logger fornito da java che prende in input un nome per stringa che può essere scelto da noi
    // oppure di solito si usa la dicitura di seguito (lo stesso nome della classe).
    private static final Logger log = Logger.getLogger(ArtistaDaoImpl.class.getName());
    @Override
    public Artista save(final Artista artista) throws SQLException {
        Artista inserito = null;
        String query = "Insert into artisti (nome,nazione,anno_inizio,data_inserimento,data_aggiornamento) values (?,?,?,?,?)";

        //try with resource
        //con questa struttura quando il blocco try finirà la connessione verrà automaticamente chiusa
        // non devo specificare quindi la close().
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,artista.getNome());
            statement.setString(2,artista.getNazione());
            statement.setInt(3,artista.getAnno_inizio());
            statement.setTimestamp(4, Timestamp.valueOf(artista.getData_inserimento()));
            statement.setTimestamp(5, null);

            int righeCoinvolte = statement.executeUpdate();
            Integer artistaId = -1;
            if(righeCoinvolte>0){
                //potenzialmente ci possono essere più chiavi perchè si possono fare più insert contemporaneamente
                ResultSet chiavi = statement.getGeneratedKeys();
                if(chiavi.next()) {
                    artistaId = chiavi.getInt(1);

                    inserito = new Artista();
                    inserito.setArtistaid(artistaId);
                    inserito.setNome(artista.getNome());
                    inserito.setNazione(artista.getNazione());
                    inserito.setAnno_inizio(artista.getAnno_inizio());
                    inserito.setData_inserimento(artista.getData_inserimento());

                    log.info("Creato artista con il seguente ID: " + artistaId);
                }else{
                    log.warning("Non è stato possibile recuperare la chiave primaria");
                    }
                chiavi.close();
            }else{
                log.severe("Non è stato possibile inserire l'artista");
            }
        }
        return inserito;
    }

    @Override
    public Artista findById(Integer artistaId) throws SQLException {
        Artista trovato = null;

        String query = "select nome,nazione,anno_inizio,data_inserimento,data_aggiornamento from artisti where artista_id = ?";
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = creaPerFindById(connection,query,artistaId);
            ResultSet result = statement.executeQuery();){
            if(result.next()){
                trovato = new Artista();
                trovato.setArtistaid(artistaId);
                trovato.setNome(result.getString(1));
                trovato.setNazione(result.getString(2));
                trovato.setAnno_inizio(result.getInt(3));
                trovato.setData_inserimento(result.getTimestamp(4).toLocalDateTime());
                trovato.setData_aggiornamento(result.getTimestamp(5).toLocalDateTime());

                log.info("Trovato artista con ID: " + artistaId);
            }else{
                log.warning("Non è stato possibile trovare artista con ID: " + artistaId);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return trovato;
    }

    private PreparedStatement creaPerFindById(Connection connection,String query, Integer artistaId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, artistaId);

        return statement;
    }

    @Override
    public Artista update(Integer artistaid, Artista artista) throws SQLException {
        Artista aggiornato = null;

        String query = "update artisti set nome = ?, nazione = ?, anno_inizio = ?, data_inserimento = ?, data_aggiornamento = ? where artista_id = ?";
        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,artista.getNome());
            statement.setString(2,artista.getNazione());
            statement.setInt(3,artista.getAnno_inizio());
            statement.setTimestamp(4, Timestamp.valueOf(artista.getData_inserimento()));
            statement.setTimestamp(5, Timestamp.valueOf(artista.getData_aggiornamento()));
            statement.setInt(6, artistaid);

            int righeCoinvolte = statement.executeUpdate();
            if(righeCoinvolte>0){
                aggiornato = new Artista();
                aggiornato.setArtistaid(artistaid);
                aggiornato.setNome(artista.getNome());
                aggiornato.setNazione(artista.getNazione());
                aggiornato.setAnno_inizio(artista.getAnno_inizio());
                aggiornato.setData_inserimento(artista.getData_inserimento());
                aggiornato.setData_aggiornamento(artista.getData_aggiornamento());

                log.info("Aggiornato artista con ID: " + artistaid);
            }else{
                log.warning("Non è stato possibili aggiornare artista con id: " + artistaid);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return aggiornato;
    }

    @Override
    public Artista deleteById(Integer artistaid) throws SQLException {
        Artista eliminato = null;

        String query1 = "select nome,nazione,anno_inizio,data_inserimento,data_aggiornamento from artisti where artista_id = ?";
        String query2 = "delete from artisti where artista_id = ?";

        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement1 = creaPerFindById(connection,query1,artistaid);
            PreparedStatement statement2 = creaPerDeleteById(connection,query2,artistaid);
            ResultSet result = statement1.executeQuery();
            ){
            if(result.next()){
                eliminato = new Artista();
                eliminato.setArtistaid(artistaid);
                eliminato.setNome(result.getString(1));
                eliminato.setNazione(result.getString(2));
                eliminato.setAnno_inizio(result.getInt(3));
                eliminato.setData_inserimento(result.getTimestamp(4).toLocalDateTime());
                eliminato.setData_aggiornamento(result.getTimestamp(5).toLocalDateTime());

                statement2.executeUpdate();

                log.info("Eliminato artista con ID: " + artistaid);
            }else{
                log.warning("Non è stato possibili eliminare artista con id: " + artistaid);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return eliminato;
    }

    private PreparedStatement creaPerDeleteById(Connection connection, String query, Integer artistaid) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,artistaid);
            return statement;
    }

    @Override
    public Set<Artista> findAll() throws SQLException {
        Set<Artista> artisti = new LinkedHashSet<Artista>();

        String query = "select * from artisti order by anno_inizio";

        try(Connection connection = MySQLConnection.open();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();){

            while(result.next()){
                Artista artista = new Artista();
                artista.setArtistaid(result.getInt(1));
                artista.setNome(result.getString(2));
                artista.setNazione(result.getString(3));
                artista.setAnno_inizio(result.getInt(4));
                artista.setData_inserimento(result.getTimestamp(5).toLocalDateTime());
                Timestamp timestamp = result.getTimestamp(6);
                if(timestamp != null){
                    artista.setData_aggiornamento(result.getTimestamp(6).toLocalDateTime());
                }
                artisti.add(artista);
            }

            log.info("Sono stati trovati" + artisti.size() + " artisti");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return artisti;
    }
}
