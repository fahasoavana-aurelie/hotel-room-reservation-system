package dao;

import connexion.Connexion;
import model.Occuper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OccuperDAO {

    // 1) CREATE - Enregistrer l'arrivée du client (occupation) + additionner le solde
    public void ajouter(Occuper o, int montant) {
        String sqlOccuper = "INSERT INTO occuper (idreserv) VALUES (?)";
        String sqlSolde = "UPDATE solde SET soldeactuel = soldeactuel + ? WHERE id = 1";

        Connection conn = null;
        try {
            conn = Connexion.getConnexion();
            conn.setAutoCommit(false); // Démarrer une transaction

            // Insertion dans OCCUPER
            try (PreparedStatement ps1 = conn.prepareStatement(sqlOccuper)) {
                ps1.setInt(1, o.getIdReserv());
                ps1.executeUpdate();
            }

            // Mise à jour du solde
            try (PreparedStatement ps2 = conn.prepareStatement(sqlSolde)) {
                ps2.setInt(1, montant);
                ps2.executeUpdate();
            }

            conn.commit(); // Valider les deux opérations ensemble
            System.out.println("Occupation enregistrée et solde mis à jour !");

        } catch (SQLException e) {
            System.out.println("Erreur ajout occupation : " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Annuler tout si erreur
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

    // 2) READ - Lister toutes les occupations
    public List<Occuper> listerTout() {
        List<Occuper> liste = new ArrayList<>();
        String sql = "SELECT * FROM occuper";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Occuper o = new Occuper(
                    rs.getInt("idoccup"),
                    rs.getInt("idreserv")
                );
                liste.add(o);
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur listage : " + e.getMessage());
        }
        return liste;
    }

    // 3) DELETE - Supprimer une occupation
    public void supprimer(int idOccup) {
        String sql = "DELETE FROM occuper WHERE idoccup = ?";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idOccup);
            int lignes = ps.executeUpdate();
            
            if (lignes > 0) {
                System.out.println("Occupation supprimée avec succès !");
            } else {
                System.out.println("Aucune occupation trouvée avec cet id.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}