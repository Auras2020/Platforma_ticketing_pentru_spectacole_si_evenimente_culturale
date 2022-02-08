package com.example.platformaticketing.model;

import javax.persistence.*;

@Entity
public class EvenimentCultural {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nume;
    private String tip;
    private String data;
    private int capacitate;
    private int pret;

    public EvenimentCultural(String nume, String tip, String data, int capacitate, int pret) {
        this.nume = nume;
        this.tip = tip;
        this.data = data;
        this.capacitate = capacitate;
        this.pret = pret;
    }

    public EvenimentCultural() {

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

    @Override
    public String toString() {
        return "EvenimentCultural{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", tip='" + tip + '\'' +
                ", data='" + data + '\'' +
                ", capacitate=" + capacitate +
                ", pret=" + pret +
                '}';
    }
}
