/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connexion.Connexion;
import model.Reserver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReserverDAO {

    // 1) CREATE - Ajouter une réservation (dateReserv = date courante automatique)
    public void ajouter(Reserver r) {
        String sql = "INSERT INTO reserver (numchambre, datereserv, dateentree, nbrjour, nomclient, mail) "
                    + "VALUES (?, CURRENT_DATE, ?, ?, ?, ?)";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, r.getNumChambre());
            ps.setDate(2, r.getDateEntree());
            ps.setInt(3, r.getNbrJour());
            ps.setString(4, r.getNomClient());
            ps.setString(5, r.getMail());
            
            ps.executeUpdate();
            System.out.println("Réservation ajoutée avec succès !");
            
        } catch (SQLException e) {
            System.out.println("Erreur ajout : " + e.getMessage());
        }
    }

    // 2) READ - Lister toutes les réservations
    public List<Reserver> listerTout() {
        List<Reserver> liste = new ArrayList<>();
        String sql = "SELECT * FROM reserver";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Reserver r = new Reserver(
                    rs.getInt("idreserv"),
                    rs.getString("numchambre"),
                    rs.getDate("datereserv"),
                    rs.getDate("dateentree"),
                    rs.getInt("nbrjour"),
                    rs.getString("nomclient"),
                    rs.getString("mail")
                );
                liste.add(r);
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur listage : " + e.getMessage());
        }
        return liste;
    }

    // 3) UPDATE - Modifier une réservation
    public void modifier(Reserver r) {
        String sql = "UPDATE reserver SET numchambre = ?, dateentree = ?, nbrjour = ?, "
                    + "nomclient = ?, mail = ? WHERE idreserv = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, r.getNumChambre());
            ps.setDate(2, r.getDateEntree());
            ps.setInt(3, r.getNbrJour());
            ps.setString(4, r.getNomClient());
            ps.setString(5, r.getMail());
            ps.setInt(6, r.getIdReserv());
            
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("Réservation modifiée avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec cet id.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur modification : " + e.getMessage());
        }
    }

    // 4) DELETE - Supprimer une réservation (= annulation, donc la chambre redevient libre)
    public void supprimer(int idReserv) {
        String sql = "DELETE FROM reserver WHERE idreserv = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idReserv);
            int lignes = ps.executeUpdate();
            
            if (lignes > 0) {
                System.out.println("Réservation supprimée avec succès ! (chambre libérée)");
            } else {
                System.out.println("Aucune réservation trouvée avec cet id.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}
