package com.example.gestion_livraison_android;

public class Etat {
    private int code_etat;
    private String libelle;

    // Constructor
    public Etat() {
        // Default constructor with no parameters
    }
    public Etat(int code_etat, String libelle) {
        this.code_etat = code_etat;
        this.libelle = libelle;
    }

    // Getter for code_etat
    public int getCode_etat() {
        return code_etat;
    }

    // Setter for code_etat
    public void setCode_etat(int code_etat) {
        this.code_etat = code_etat;
    }

    // Getter for libelle
    public String getLibelle() {
        return libelle;
    }

    // Setter for libelle
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    @Override
    public String toString() {
        return libelle; // Ou toute autre représentation souhaitée
    }
}
