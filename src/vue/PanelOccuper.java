/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import dao.OccuperDAO;
import dao.ReserverDAO;
import model.Occuper;
import model.Reserver;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PanelOccuper extends JPanel {

    private OccuperDAO dao = new OccuperDAO();
    private ReserverDAO reserverDao = new ReserverDAO();
    private JTable table;
    private DefaultTableModel modele;
    private JComboBox<Integer> comboIdReserv;
    private JTextField champMontant;

    public PanelOccuper() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulaire (en haut) ----
        JPanel panelFormulaire = new JPanel(new GridLayout(1, 4, 5, 5));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Occupation (arrivée du client)"));

        comboIdReserv = new JComboBox<>();
        champMontant = new JTextField();

        panelFormulaire.add(new JLabel("ID Réservation :"));
        panelFormulaire.add(comboIdReserv);
        panelFormulaire.add(new JLabel("Montant à ajouter au solde :"));
        panelFormulaire.add(champMontant);

        add(panelFormulaire, BorderLayout.NORTH);

        // ---- Table (au centre) ----
        modele = new DefaultTableModel(new String[]{"ID Occupation", "ID Réservation"}, 0);
        table = new JTable(modele);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Boutons (en bas) ----
        JPanel panelBoutons = new JPanel();
        JButton btnAjouter = new JButton("Ajouter (enregistrer arrivée)");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnRafraichir = new JButton("Rafraîchir");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRafraichir);

        add(panelBoutons, BorderLayout.SOUTH);

        // ---- Actions des boutons ----
        btnAjouter.addActionListener(this::ajouter);
        btnSupprimer.addActionListener(this::supprimer);
        btnRafraichir.addActionListener(e -> { chargerComboReservations(); chargerTable(); });

        chargerComboReservations();
        chargerTable();
    }

    // Remplir le JComboBox avec les id des réservations existantes
    private void chargerComboReservations() {
        comboIdReserv.removeAllItems();
        List<Reserver> reservations = reserverDao.listerTout();
        for (Reserver r : reservations) {
            comboIdReserv.addItem(r.getIdReserv());
        }
    }

    // Charger toutes les occupations dans la table
    private void chargerTable() {
        modele.setRowCount(0);
        List<Occuper> liste = dao.listerTout();
        for (Occuper o : liste) {
            modele.addRow(new Object[]{o.getIdOccup(), o.getIdReserv()});
        }
    }

    private void ajouter(ActionEvent e) {
        if (comboIdReserv.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Aucune réservation disponible. Créez d'abord une réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int idReserv = (int) comboIdReserv.getSelectedItem();
            int montant = Integer.parseInt(champMontant.getText());

            Occuper o = new Occuper(idReserv);
            dao.ajouter(o, montant);
            chargerTable();
            champMontant.setText("");
            JOptionPane.showMessageDialog(this, "Occupation enregistrée et solde mis à jour !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le montant doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimer(ActionEvent e) {
        int ligne = table.getSelectedRow();
        if (ligne < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une occupation à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idOccup = Integer.parseInt(modele.getValueAt(ligne, 0).toString());
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer cette occupation ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            dao.supprimer(idOccup);
            chargerTable();
        }
    }
}
