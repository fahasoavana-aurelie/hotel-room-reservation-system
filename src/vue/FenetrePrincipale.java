/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import javax.swing.*;

public class FenetrePrincipale extends JFrame {

    public FenetrePrincipale() {
        setTitle("Gestion des Réservations d'Hôtel");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        JTabbedPane onglets = new JTabbedPane();
        onglets.addTab("Chambres", new PanelChambre());
        onglets.addTab("Réservations", new PanelReserver());
        onglets.addTab("Occupations", new PanelOccuper());
        onglets.addTab("Séjours", new PanelSejourner());
        onglets.addTab("Solde", new PanelSolde());
        onglets.addTab("Recherche dans libre", new PanelRechercheChambre());
        
        // Les autres onglets viendront ensuite : Réservations, Occupations, Séjours, Solde

        add(onglets);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FenetrePrincipale fenetre = new FenetrePrincipale();
            fenetre.setVisible(true);
        });
    }
}