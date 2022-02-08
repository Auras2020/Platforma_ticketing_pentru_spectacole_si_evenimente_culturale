package com.example.platformaticketing.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Vanzator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nume;
    private String interval_orar;
    private int numar_casa;

    public Vanzator(String nume, String interval_orar, int numar_casa) {
        this.nume = nume;
        this.interval_orar = interval_orar;
        this.numar_casa = numar_casa;
    }

    public Vanzator() {

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

    public String getInterval_orar() {
        return interval_orar;
    }

    public void setInterval_orar(String interval_orar) {
        this.interval_orar = interval_orar;
    }

    public int getNumar_casa() {
        return numar_casa;
    }

    public void setNumar_casa(int numar_casa) {
        this.numar_casa = numar_casa;
    }

    @Override
    public String toString() {
        return "Vanzator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", interval_orar='" + interval_orar + '\'' +
                ", numar_casa=" + numar_casa +
                '}';
    }
}
