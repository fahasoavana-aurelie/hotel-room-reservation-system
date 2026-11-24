/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import dao.ReserverDAO;
import dao.ChambreDAO;
import model.Reserver;
import model.Chambre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

public class PanelReserver extends JPanel {

    private ReserverDAO dao = new ReserverDAO();
    private ChambreDAO chambreDao = new ChambreDAO();
    private JTable table;
    private DefaultTableModel modele;
    private JComboBox<String> comboNumChambre;
    private JTextField champDateEntree, champNbrJour, champNomClient, champMail;
    private int idReservSelectionne = -1; // -1 = aucune sélection

    public PanelReserver() {
      
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulaire (en haut) ----
        JPanel panelFormulaire = new JPanel(new GridLayout(3, 4, 5, 5));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Réservation"));

        comboNumChambre = new JComboBox<>();
        champDateEntree = new JTextField();
        champNbrJour = new JTextField();
        champNomClient = new JTextField();
        champMail = new JTextField();
        
        

        panelFormulaire.add(new JLabel("N° Chambre :"));
        panelFormulaire.add(comboNumChambre);
        panelFormulaire.add(new JLabel("Date entrée (AAAA-MM-JJ) :"));
        panelFormulaire.add(champDateEntree);
        panelFormulaire.add(new JLabel("Nombre de jours :"));
        panelFormulaire.add(champNbrJour);
        panelFormulaire.add(new JLabel("Nom client :"));
        panelFormulaire.add(champNomClient);
        panelFormulaire.add(new JLabel("Email :"));
        panelFormulaire.add(champMail);

        add(panelFormulaire, BorderLayout.NORTH);

        // ---- Table (au centre) ----
        modele = new DefaultTableModel(new String[]{"ID", "N° Chambre", "Date résa", "Date entrée", "Jours", "Client", "Email"}, 0);
        table = new JTable(modele);
        table.getSelectionModel().addListSelectionListener(e -> remplirFormulaireDepuisTable());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Boutons (en bas) ----
        JPanel panelBoutons = new JPanel();
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnRafraichir = new JButton("Rafraîchir");
        JButton btnEffacer = new JButton("Effacer les champs");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRafraichir);
        panelBoutons.add(btnEffacer);

        add(panelBoutons, BorderLayout.SOUTH);

        // ---- Actions des boutons ----
        btnAjouter.addActionListener(this::ajouter);
        btnModifier.addActionListener(this::modifier);
        btnSupprimer.addActionListener(this::supprimer);
        btnRafraichir.addActionListener(e -> { chargerComboChambres(); chargerTable(); });
        btnEffacer.addActionListener(e -> viderFormulaire());

        chargerComboChambres(); // Remplir la liste déroulante des chambres
        chargerTable();         // Charger les réservations existantes
    }

    // Remplir le JComboBox avec les numéros de chambre existants
    private void chargerComboChambres() {
        comboNumChambre.removeAllItems();
        List<Chambre> chambres = chambreDao.listerTout();
        for (Chambre c : chambres) {
            comboNumChambre.addItem(c.getNumChambre());
        }
    }

    // Charger toutes les réservations dans la table
    private void chargerTable() {
        modele.setRowCount(0);
        List<Reserver> liste = dao.listerTout();
        for (Reserver r : liste) {
            modele.addRow(new Object[]{
                r.getIdReserv(), r.getNumChambre(), r.getDateReserv(), 
                r.getDateEntree(), r.getNbrJour(), r.getNomClient(), r.getMail()
            });
        }
    }

    // Remplir le formulaire quand on clique sur une ligne de la table
    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne >= 0) {
            idReservSelectionne = Integer.parseInt(modele.getValueAt(ligne, 0).toString());
            comboNumChambre.setSelectedItem(modele.getValueAt(ligne, 1).toString());
            champDateEntree.setText(modele.getValueAt(ligne, 3).toString());
            champNbrJour.setText(modele.getValueAt(ligne, 4).toString());
            champNomClient.setText(modele.getValueAt(ligne, 5).toString());
            champMail.setText(modele.getValueAt(ligne, 6).toString());
        }
    }

    private void viderFormulaire() {
        champDateEntree.setText("");
        champNbrJour.setText("");
        champNomClient.setText("");
        champMail.setText("");
        idReservSelectionne = -1;
        table.clearSelection();
    }

    private void ajouter(ActionEvent e) {
    try {
        String numChambre = (String) comboNumChambre.getSelectedItem();
        Date dateEntree = Date.valueOf(champDateEntree.getText());

        if (!chambreDao.estChambreLibre(numChambre, dateEntree)) {
            JOptionPane.showMessageDialog(this, 
                "Cette chambre est déjà réservée ou occupée à cette date !", 
                "Chambre indisponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nbrJour = Integer.parseInt(champNbrJour.getText());
        String nomClient = champNomClient.getText();
        String mail = champMail.getText();

        Reserver r = new Reserver(numChambre, null, dateEntree, nbrJour, nomClient, mail);
        dao.ajouter(r);
        chargerTable();
        viderFormulaire();

        // Envoi automatique du mail au client
        String sujet = "Confirmation de votre réservation";
        String contenu = "Bonjour " + nomClient + ",\n\n"
                + "Votre réservation a bien été enregistrée.\n\n"
                + "Chambre : " + numChambre + "\n"
                + "Date d'entrée : " + dateEntree + "\n"
                + "Nombre de jours : " + nbrJour + "\n\n"
                + "Merci de votre confiance.";
        service.EnvoiMail.envoyer(mail, sujet, contenu);

        JOptionPane.showMessageDialog(this, "Réservation ajoutée avec succès ! Un mail de confirmation a été envoyé.");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Le nombre de jours doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, "Format de date invalide ! Utilisez AAAA-MM-JJ", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}

    private void modifier(ActionEvent e) {
        if (idReservSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une réservation à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Reserver r = new Reserver(
                idReservSelectionne,
                (String) comboNumChambre.getSelectedItem(),
                null,
                Date.valueOf(champDateEntree.getText()),
                Integer.parseInt(champNbrJour.getText()),
                champNomClient.getText(),
                champMail.getText()
            );
            dao.modifier(r);
            chargerTable();
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Réservation modifiée avec succès !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le nombre de jours doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide ! Utilisez AAAA-MM-JJ", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimer(ActionEvent e) {
        if (idReservSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une réservation à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer cette réservation ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            dao.supprimer(idReservSelectionne);
            chargerTable();
            viderFormulaire();
        }
    }
   
}
}
