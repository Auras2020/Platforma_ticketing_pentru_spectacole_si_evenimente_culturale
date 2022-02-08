package com.example.platformaticketing.model;

import javax.persistence.*;

@Entity
public class Angajat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nume;
    private String interval_orar;
    private int post_de_munca;

    public Angajat(String nume, String interval_orar, int post_de_munca) {
        this.nume = nume;
        this.interval_orar = interval_orar;
        this.post_de_munca = post_de_munca;
    }

    public Angajat() {

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

    public int getPost_de_munca() {
        return post_de_munca;
    }

    public void setPost_de_munca(int post_de_munca) {
        this.post_de_munca = post_de_munca;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", interval_orar='" + interval_orar + '\'' +
                ", post_de_munca=" + post_de_munca +
                '}';
    }
}
