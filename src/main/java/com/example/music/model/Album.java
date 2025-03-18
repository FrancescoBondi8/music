package com.example.music.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Questo è un javadoc che serve a creare una documentazione
 *  creo una classe che rappresente la tabella album e lo faccio sfuttando la convenzione POJO
 *  che sarebbe la convenzione Java Beans
 *  Una classe è detta beans se ha almeno il costruttore di default e i getter e setter
 */
public class Album {
    private Integer albumid;
    private String titolo;
    private LocalDate dataUscita;
    private String genere;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataAggiornamento;
    private Artista artistaid;

    public Album(){

    }

    public Album(Integer albumid, String titolo, LocalDate dataUscita, String genere, LocalDateTime dataInserimento, LocalDateTime dataAggiornamento, Artista artistaid) {
        this.albumid = albumid;
        this.titolo = titolo;
        this.dataUscita = dataUscita;
        this.genere = genere;
        this.dataInserimento = dataInserimento;
        this.dataAggiornamento = dataAggiornamento;
        this.artistaid = artistaid;
    }

    public Integer getAlbumid() {
        return albumid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public LocalDate getDataUscita() {
        return dataUscita;
    }

    public void setDataUscita(LocalDate dataUscita) {
        this.dataUscita = dataUscita;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Artista getArtistaid() {
        return artistaid;
    }

    public void setArtistaid(Artista artistaid) {
        this.artistaid = artistaid;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {

        return "ID: " + albumid + " Titolo: " + titolo;
    }

    public LocalDateTime getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(LocalDateTime dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public LocalDateTime getDataAggiornamento() {
        return dataAggiornamento;
    }

    public void setDataAggiornamento(LocalDateTime dataAggiornamento) {
        this.dataAggiornamento = dataAggiornamento;
    }
}
