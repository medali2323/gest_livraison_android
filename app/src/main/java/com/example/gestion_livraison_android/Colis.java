package com.example.gestion_livraison_android;

public class Colis {
    private int codeColis;
    private String nomClt;
    private String gouvernement;
    private String ville;
    private String adresseClt;
    private String telClt;
    private String telClt2;
    private String des;
    private double prix;
    private int nbArticle;
    private String date;
    private String commentaire;
    private String modePaiement;

    public int getCodeColis() {
        return codeColis;
    }

    public void setCodeColis(int codeColis) {
        this.codeColis = codeColis;
    }

    public String getNomClt() {
        return nomClt;
    }

    public void setNomClt(String nomClt) {
        this.nomClt = nomClt;
    }

    public String getGouvernement() {
        return gouvernement;
    }

    public void setGouvernement(String gouvernement) {
        this.gouvernement = gouvernement;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresseClt() {
        return adresseClt;
    }

    public void setAdresseClt(String adresseClt) {
        this.adresseClt = adresseClt;
    }

    public String getTelClt() {
        return telClt;
    }

    public void setTelClt(String telClt) {
        this.telClt = telClt;
    }

    public String getTelClt2() {
        return telClt2;
    }

    public void setTelClt2(String telClt2) {
        this.telClt2 = telClt2;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getNbArticle() {
        return nbArticle;
    }

    public void setNbArticle(int nbArticle) {
        this.nbArticle = nbArticle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public int getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(int expediteur) {
        this.expediteur = expediteur;
    }

    private int expediteur;


}
