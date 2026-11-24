/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connexion.Connexion;
import model.Chambre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreDAO {

    // 1) CREATE - Ajouter une chambre
    public void ajouter(Chambre c) {
        String sql = "INSERT INTO chambre (numchambre, design, type, prixnuite) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, c.getNumChambre());
            ps.setString(2, c.getDesign());
            ps.setString(3, c.getType());
            ps.setInt(4, c.getPrixNuite());
            
            ps.executeUpdate();
            System.out.println("Chambre ajoutée avec succès !");
            
        } catch (SQLException e) {
            System.out.println("Erreur ajout : " + e.getMessage());
        }
    }

    // 2) READ - Lister toutes les chambres
    public List<Chambre> listerTout() {
        List<Chambre> liste = new ArrayList<>();
        String sql = "SELECT * FROM chambre";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Chambre c = new Chambre(
                    rs.getString("numchambre"),
                    rs.getString("design"),
                    rs.getString("type"),
                    rs.getInt("prixnuite")
                );
                liste.add(c);
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur listage : " + e.getMessage());
        }
        return liste;
    }

    // 3) UPDATE - Modifier une chambre
    public void modifier(Chambre c) {
        String sql = "UPDATE chambre SET design = ?, type = ?, prixnuite = ? WHERE numchambre = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, c.getDesign());
            ps.setString(2, c.getType());
            ps.setInt(3, c.getPrixNuite());
            ps.setString(4, c.getNumChambre());
            
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("Chambre modifiée avec succès !");
            } else {
                System.out.println("Aucune chambre trouvée avec ce numéro.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur modification : " + e.getMessage());
        }
    }

    // 4) DELETE - Supprimer une chambre
    public void supprimer(String numChambre) {
        String sql = "DELETE FROM chambre WHERE numchambre = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, numChambre);
            int lignes = ps.executeUpdate();
            
            if (lignes > 0) {
                System.out.println("Chambre supprimée avec succès !");
            } else {
                System.out.println("Aucune chambre trouvée avec ce numéro.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
    // Rechercher les chambres libres à une date donnée
public List<Chambre> rechercherChambresLibres(java.sql.Date date) {
    List<Chambre> liste = new ArrayList<>();
    String sql = "SELECT * FROM chambre WHERE numchambre NOT IN ("
               + "  SELECT numchambre FROM reserver WHERE ? BETWEEN datereserv AND (dateentree + nbrjour - 1)"
               + "  UNION "
               + "  SELECT numchambr FROM sejourner WHERE ? BETWEEN dateentreesejour AND (dateentreesejour + nbrjour - 1)"
               + ")";

    try (Connection conn = Connexion.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDate(1, date);
        ps.setDate(2, date);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Chambre c = new Chambre(
                    rs.getString("numchambre"),
                    rs.getString("design"),
                    rs.getString("type"),
                    rs.getInt("prixnuite")
                );
                liste.add(c);
            }
        }

    } catch (SQLException e) {
        System.out.println("Erreur recherche chambres libres : " + e.getMessage());
    }
    return liste;
}
// Vérifier si une chambre est libre à une date donnée
public boolean estChambreLibre(String numChambre, java.sql.Date date) {
    String sql = "SELECT COUNT(*) FROM ("
               + "  SELECT numchambre FROM reserver WHERE numchambre = ? AND ? BETWEEN dateentree AND (dateentree + nbrjour - 1)"
               + "  UNION "
               + "  SELECT numchambr FROM sejourner WHERE numchambr = ? AND ? BETWEEN dateentreesejour AND (dateentreesejour + nbrjour - 1)"
               + ") AS occupations";

    try (Connection conn = Connexion.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, numChambre);
        ps.setDate(2, date);
        ps.setString(3, numChambre);
        ps.setDate(4, date);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) == 0; // 0 = aucune occupation trouvée = chambre libre
            }
        }

    } catch (SQLException e) {
        System.out.println("Erreur vérification disponibilité : " + e.getMessage());
    }
    return false; // par sécurité, si erreur on considère non disponible
}
}
