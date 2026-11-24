/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Solde {
    private int id;
    private int soldeActuel;

    public Solde() {
    }

    public Solde(int id, int soldeActuel) {
        this.id = id;
        this.soldeActuel = soldeActuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoldeActuel() {
        return soldeActuel;
    }

    public void setSoldeActuel(int soldeActuel) {
        this.soldeActuel = soldeActuel;
    }

    @Override
    public String toString() {
        return "Solde{" + "id=" + id + ", soldeActuel=" + soldeActuel + '}';
    }
}
