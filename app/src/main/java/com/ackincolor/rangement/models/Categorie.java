package com.ackincolor.rangement.models;

import java.util.UUID;

public class Categorie {
    private String nom;
    private int numberObjet;
    private UUID id;

    public Categorie(String nom) {
        this.nom = nom;
        this.id = UUID.randomUUID();

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumberObjet() {
        return numberObjet;
    }

    public void setNumberObjet(int numberObjet) {
        this.numberObjet = numberObjet;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
