/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connexion.Connexion;
import model.Sejourner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SejournerDAO {

    // 1) CREATE - Ajouter un séjour (dateEntreeSejour = date courante automatique) + additionner solde
    public void ajouter(Sejourner s, int montant) {
        String sqlSejourner = "INSERT INTO sejourner (numchambr, dateentreesejour, nbrjour, nomclient, telephone) "
                             + "VALUES (?, CURRENT_DATE, ?, ?, ?)";
        String sqlSolde = "UPDATE solde SET soldeactuel = soldeactuel + ? WHERE id = 1";

        Connection conn = null;
        try {
            conn = Connexion.getConnexion();
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlSejourner)) {
                ps1.setString(1, s.getNumChambre());
                ps1.setInt(2, s.getNbrJour());
                ps1.setString(3, s.getNomClient());
                ps1.setString(4, s.getTelephone());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlSolde)) {
                ps2.setInt(1, montant);
                ps2.executeUpdate();
            }

            conn.commit();
            System.out.println("Séjour ajouté et solde mis à jour !");

        } catch (SQLException e) {
            System.out.println("Erreur ajout séjour : " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erreur rollback : " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Erreur fermeture connexion : " + e.getMessage());
                }
            }
        }
    }

    // 2) READ - Lister tous les séjours
    public List<Sejourner> listerTout() {
        List<Sejourner> liste = new ArrayList<>();
        String sql = "SELECT * FROM sejourner";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Sejourner s = new Sejourner(
                    rs.getInt("idsejour"),
                    rs.getString("numchambr"),
                    rs.getDate("dateentreesejour"),
                    rs.getInt("nbrjour"),
                    rs.getString("nomclient"),
                    rs.getString("telephone")
                );
                liste.add(s);
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur listage : " + e.getMessage());
        }
        return liste;
    }

    // 3) UPDATE - Modifier un séjour
    public void modifier(Sejourner s) {
        String sql = "UPDATE sejourner SET numchambr = ?, nbrjour = ?, nomclient = ?, telephone = ? "
                    + "WHERE idsejour = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, s.getNumChambre());
            ps.setInt(2, s.getNbrJour());
            ps.setString(3, s.getNomClient());
            ps.setString(4, s.getTelephone());
            ps.setInt(5, s.getIdSejour());
            
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("Séjour modifié avec succès !");
            } else {
                System.out.println("Aucun séjour trouvé avec cet id.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur modification : " + e.getMessage());
        }
    }

    // 4) DELETE - Supprimer un séjour
    public void supprimer(int idSejour) {
        String sql = "DELETE FROM sejourner WHERE idsejour = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idSejour);
            int lignes = ps.executeUpdate();
            
            if (lignes > 0) {
                System.out.println("Séjour supprimé avec succès !");
            } else {
                System.out.println("Aucun séjour trouvé avec cet id.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}
