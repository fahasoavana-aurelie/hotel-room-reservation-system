/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connexion.Connexion;
import model.Solde;
import java.sql.*;

public class SoldeDAO {

    // READ - Récupérer le solde actuel (id = 1, unique ligne)
    public Solde getSolde() {
        Solde solde = null;
        String sql = "SELECT * FROM solde WHERE id = 1";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                solde = new Solde(
                    rs.getInt("id"),
                    rs.getInt("soldeactuel")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur lecture solde : " + e.getMessage());
        }
        return solde;
    }
}
