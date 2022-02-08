package com.example.platformaticketing.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Spectacol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nume;
    private String tip_muzica;
    private String data;
    private String locatie;
    private String artisti;
    private String interval_orar;

    public Spectacol(String nume, String tip_muzica, String data, String locatie, String artisti, String interval_orar) {
        this.nume = nume;
        this.tip_muzica = tip_muzica;
        this.data = data;
        this.locatie = locatie;
        this.artisti = artisti;
        this.interval_orar = interval_orar;
    }

    public Spectacol() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTip_muzica() {
        return tip_muzica;
    }

    public void setTip_muzica(String tip_muzica) {
        this.tip_muzica = tip_muzica;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getArtisti() {
        return artisti;
    }

    public void setArtisti(String artisti) {
        this.artisti = artisti;
    }

    public String getInterval_orar() {
        return interval_orar;
    }

    public void setInterval_orar(String interval_orar) {
        this.interval_orar = interval_orar;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", tip_muzica='" + tip_muzica + '\'' +
                ", data='" + data + '\'' +
                ", locatie='" + locatie + '\'' +
                ", artisti='" + artisti + '\'' +
                ", interval_orar='" + interval_orar + '\'' +
                '}';
    }
}
