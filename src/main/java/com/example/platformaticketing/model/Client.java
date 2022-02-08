package com.example.platformaticketing.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nume;
    private int varsta;
    private String certificatVerde;

    public Client(String nume, int varsta, String certificatVerde) {
        this.nume = nume;
        this.varsta = varsta;
        this.certificatVerde = certificatVerde;
    }

    public Client() {

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

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getCertificatVerde() {
        return certificatVerde;
    }

    public void setCertificatVerde(String certificatVerde) {
        this.certificatVerde = certificatVerde;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", certificatVerde='" + certificatVerde + '\'' +
                '}';
    }
}
