package com.example.bankapp;

public class Compte {
    private String numero;
    private String libelle;

    private double solde;
    public Compte(String numero, String libelle, double solde)
    {   this.numero = numero;
        this.libelle = libelle;
        this.solde = solde;
    }
    public String getNumero() { return numero; }
    public String getLibelle() { return libelle; }
    public double getSolde() { return solde; }
}
