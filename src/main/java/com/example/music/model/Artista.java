package com.example.music.model;

import java.time.LocalDateTime;

/**
 * Questo è un javadoc che serve a creare una documentazione
 *  creo una classe che rappresente la tabella artisti e lo faccio sfuttando la convenzione POJO
 *  che sarebbe la convenzione Java Beans
 *  Una classe è detta beans se ha almeno il costruttore di default e i getter e setter
 */
public class Artista {
    // qui bisogna riportare come attributi i nomi delle colonne della tabella SQL
    // si usa la wrapper solo perchè così possiamo usare anche i valori null.
    private Integer artistaid;
    private String nome;
    private String nazione;
    private Integer anno_inizio;
    private LocalDateTime data_inserimento;
    private LocalDateTime data_aggiornamento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Artista(){

    }

    public Artista(Integer artistaid,String nome,String nazione,Integer anno_inizio,LocalDateTime data_inserimento,LocalDateTime data_aggiornamento) {
        this.artistaid = artistaid;
        this.nome = nome;
        this.nazione = nazione;
        this.anno_inizio = anno_inizio;
        this.data_inserimento = data_inserimento;
        this.data_aggiornamento = data_aggiornamento;
    }


    public Integer getArtistaid() {
        return artistaid;
    }

    public void setArtistaid(Integer artistaid) {
        this.artistaid = artistaid;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public LocalDateTime getData_inserimento() {
        return data_inserimento;
    }

    public void setData_inserimento(LocalDateTime data_inserimento) {
        this.data_inserimento = data_inserimento;
    }

    public Integer getAnno_inizio() {
        return anno_inizio;
    }

    public void setAnno_inizio(Integer anno_inizio) {
        this.anno_inizio = anno_inizio;
    }

    public LocalDateTime getData_aggiornamento() {
        return data_aggiornamento;
    }

    public void setData_aggiornamento(LocalDateTime data_aggiornamento) {
        this.data_aggiornamento = data_aggiornamento;
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
        return super.toString();
    }


}
