/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.ReserverDAO;
import model.Reserver;
import java.util.List;

public class TestReserver {
    public static void main(String[] args) {
        
        ReserverDAO dao = new ReserverDAO();
        
        // 1) Test AJOUTER
        // Fanamarihana: tsy misy dateReserv ato satria CURRENT_DATE no mametraka azy automatique
        Reserver r1 = new Reserver(
            "101",                              // numChambre (mila efa misy ao amin'ny tabilao chambre!)
            null,                                // dateReserv (tsy ampiasaina, CURRENT_DATE no mandray)
            java.sql.Date.valueOf("2026-09-01"), // dateEntree
            3,                                   // nbrJour
            "RAKOTO Bernard",                    // nomClient
            "rakoto.bernard@email.com"           // mail
        );
        dao.ajouter(r1);
        
        // 2) Test LISTER
        System.out.println("\n--- Liste des réservations ---");
        List<Reserver> liste = dao.listerTout();
        for (Reserver r : liste) {
            System.out.println(r);
        }
        
        // 3) Test MODIFIER (mila mahalala ny idreserv marina avy amin'ny liste ambony)
        // Reserver r2 = new Reserver(1, "101", null, java.sql.Date.valueOf("2026-09-05"), 5, "RAKOTO Bernard", "rakoto.bernard@email.com");
        // dao.modifier(r2);
        
        // 4) Test SUPPRIMER (décommente si tu veux tester)
        // dao.supprimer(1);
    }
}