package com.example.platformaticketing.model;

import javax.persistence.*;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nume;
    private String gen;
    private int varsta;
    private String tip;
    private String data;
    private int capacitate;
    private int pret;
    private String regizor;
    private String actori;

    public Film(String nume, String gen, int varsta, String tip, String data, int capacitate, int pret,
                String regizor, String actori) {
        this.nume = nume;
        this.gen = gen;
        this.varsta = varsta;
        this.tip = tip;
        this.data = data;
        this.capacitate = capacitate;
        this.pret = pret;
        this.regizor = regizor;
        this.actori = actori;
    }

    public Film() {

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

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getRegizor() {
        return regizor;
    }

    public void setRegizor(String regizor) {
        this.regizor = regizor;
    }

    public String getActori() {
        return actori;
    }

    public void setActori(String actori) {
        this.actori = actori;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", gen='" + gen + '\'' +
                ", varsta=" + varsta +
                ", tip='" + tip + '\'' +
                ", data='" + data + '\'' +
                ", capacitate=" + capacitate +
                ", pret=" + pret +
                ", regizor='" + regizor + '\'' +
                ", actori='" + actori + '\'' +
                '}';
    }
}
