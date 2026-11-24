/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.SejournerDAO;
import model.Sejourner;
import java.util.List;

public class TestSejourner {
    public static void main(String[] args) {
        
        SejournerDAO dao = new SejournerDAO();
        
        // 1) Test AJOUTER
        // Fanamarihana: tsy misy dateEntreeSejour ato satria CURRENT_DATE no mametraka azy automatique
        Sejourner s1 = new Sejourner(
            "101",                    // numChambre (mila efa misy ao amin'ny tabilao chambre!)
            null,                      // dateEntreeSejour (tsy ampiasaina, CURRENT_DATE no mandray)
            2,                         // nbrJour
            "RAVAO Marie",             // nomClient
            "0341234567"               // telephone
        );
        dao.ajouter(s1, 60000); // 60000 = ohatra montant (prixNuite x nbrJour, azo ovaina)
        
        // 2) Test LISTER
        System.out.println("\n--- Liste des séjours ---");
        List<Sejourner> liste = dao.listerTout();
        for (Sejourner s : liste) {
            System.out.println(s);
        }
        
        // 3) Test MODIFIER (mila mahalala ny idsejour marina avy amin'ny liste ambony)
        // Sejourner s2 = new Sejourner(1, "102", null, 3, "RAVAO Marie", "0341234567");
        // dao.modifier(s2);
        
        // 4) Test SUPPRIMER (décommente si tu veux tester)
        // dao.supprimer(1);
    }
}
