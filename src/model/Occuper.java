/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Occuper {
    private int idOccup;
    private int idReserv;

    public Occuper() {
    }

    public Occuper(int idOccup, int idReserv) {
        this.idOccup = idOccup;
        this.idReserv = idReserv;
    }

    // Constructeur sans idOccup (pour l'ajout, id auto-généré)
    public Occuper(int idReserv) {
        this.idReserv = idReserv;
    }

    public int getIdOccup() {
        return idOccup;
    }

    public void setIdOccup(int idOccup) {
        this.idOccup = idOccup;
    }

    public int getIdReserv() {
        return idReserv;
    }

    public void setIdReserv(int idReserv) {
        this.idReserv = idReserv;
    }

    @Override
    public String toString() {
        return "Occuper{" + "idOccup=" + idOccup + ", idReserv=" + idReserv + '}';
    }
}
