/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.SoldeDAO;
import model.Solde;

public class TestSolde {
    public static void main(String[] args) {
        
        SoldeDAO dao = new SoldeDAO();
        
        Solde s = dao.getSolde();
        if (s != null) {
            System.out.println("Solde actuel : " + s.getSoldeActuel() + " Ar");
        } else {
            System.out.println("Aucun solde trouvé.");
        }
    }
}
