/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class Sejourner {
    private int idSejour;
    private String numChambre;
    private Date dateEntreeSejour;
    private int nbrJour;
    private String nomClient;
    private String telephone;

    public Sejourner() {
    }

    public Sejourner(int idSejour, String numChambre, Date dateEntreeSejour, 
                      int nbrJour, String nomClient, String telephone) {
        this.idSejour = idSejour;
        this.numChambre = numChambre;
        this.dateEntreeSejour = dateEntreeSejour;
        this.nbrJour = nbrJour;
        this.nomClient = nomClient;
        this.telephone = telephone;
    }

    // Constructeur sans idSejour (pour l'ajout, id auto-généré)
    public Sejourner(String numChambre, Date dateEntreeSejour, 
                      int nbrJour, String nomClient, String telephone) {
        this.numChambre = numChambre;
        this.dateEntreeSejour = dateEntreeSejour;
        this.nbrJour = nbrJour;
        this.nomClient = nomClient;
        this.telephone = telephone;
    }

    public int getIdSejour() {
        return idSejour;
    }

    public void setIdSejour(int idSejour) {
        this.idSejour = idSejour;
    }

    public String getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(String numChambre) {
        this.numChambre = numChambre;
    }

    public Date getDateEntreeSejour() {
        return dateEntreeSejour;
    }

    public void setDateEntreeSejour(Date dateEntreeSejour) {
        this.dateEntreeSejour = dateEntreeSejour;
    }

    public int getNbrJour() {
        return nbrJour;
    }

    public void setNbrJour(int nbrJour) {
        this.nbrJour = nbrJour;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Sejourner{" + "idSejour=" + idSejour + ", numChambre=" + numChambre 
                + ", dateEntreeSejour=" + dateEntreeSejour + ", nbrJour=" + nbrJour 
                + ", nomClient=" + nomClient + ", telephone=" + telephone + '}';
    }
}
