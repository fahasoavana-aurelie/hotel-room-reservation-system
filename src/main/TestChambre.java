/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.ChambreDAO;
import model.Chambre;
import java.util.List;

public class TestChambre {
    public static void main(String[] args) {
        
        ChambreDAO dao = new ChambreDAO();
        
        // 1) Test AJOUTER
        Chambre c1 = new Chambre("101", "Chambre vue mer", "Simple", 30000);
        dao.ajouter(c1);
        
        // 2) Test LISTER
        System.out.println("\n--- Liste des chambres ---");
        List<Chambre> liste = dao.listerTout();
        for (Chambre c : liste) {
            System.out.println(c);
        }
        
        // 3) Test MODIFIER
        Chambre c2 = new Chambre("101", "Chambre vue mer rénovée", "Double", 45000);
        dao.modifier(c2);
        
        // 4) Test SUPPRIMER (décommente si tu veux tester)
        // dao.supprimer("101");
    }
}
