/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class Reserver {
    private int idReserv;
    private String numChambre;
    private Date dateReserv;
    private Date dateEntree;
    private int nbrJour;
    private String nomClient;
    private String mail;

    public Reserver() {
    }

    public Reserver(int idReserv, String numChambre, Date dateReserv, Date dateEntree, 
                     int nbrJour, String nomClient, String mail) {
        this.idReserv = idReserv;
        this.numChambre = numChambre;
        this.dateReserv = dateReserv;
        this.dateEntree = dateEntree;
        this.nbrJour = nbrJour;
        this.nomClient = nomClient;
        this.mail = mail;
    }

    // Constructeur sans idReserv (pour l'ajout, car id auto-généré)
    public Reserver(String numChambre, Date dateReserv, Date dateEntree, 
                     int nbrJour, String nomClient, String mail) {
        this.numChambre = numChambre;
        this.dateReserv = dateReserv;
        this.dateEntree = dateEntree;
        this.nbrJour = nbrJour;
        this.nomClient = nomClient;
        this.mail = mail;
    }

    // Getters et Setters
    public int getIdReserv() {
        return idReserv;
    }

    public void setIdReserv(int idReserv) {
        this.idReserv = idReserv;
    }

    public String getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(String numChambre) {
        this.numChambre = numChambre;
    }

    public Date getDateReserv() {
        return dateReserv;
    }

    public void setDateReserv(Date dateReserv) {
        this.dateReserv = dateReserv;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Reserver{" + "idReserv=" + idReserv + ", numChambre=" + numChambre 
                + ", dateReserv=" + dateReserv + ", dateEntree=" + dateEntree 
                + ", nbrJour=" + nbrJour + ", nomClient=" + nomClient + ", mail=" + mail + '}';
    }
}
