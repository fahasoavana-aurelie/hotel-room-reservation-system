/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.OccuperDAO;
import model.Occuper;
import java.util.List;

public class TestOccuper {
    public static void main(String[] args) {
        
        OccuperDAO dao = new OccuperDAO();
        
        // 1) Test AJOUTER (idReserv mila mitovy amin'ny idreserv efa misy ao amin'ny tabilao reserver!)
        Occuper o1 = new Occuper(1); // 1 = idReserv, jereo tsara ny idreserv marina avy amin'ny liste RESERVER teo aloha
        dao.ajouter(o1, 90000); // 90000 = ohatra montant (prixNuite x nbrJour, azo ovaina)
        
        // 2) Test LISTER
        System.out.println("\n--- Liste des occupations ---");
        List<Occuper> liste = dao.listerTout();
        for (Occuper o : liste) {
            System.out.println(o);
        }
    }
}
