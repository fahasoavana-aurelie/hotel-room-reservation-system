/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import dao.SejournerDAO;
import dao.ChambreDAO;
import model.Sejourner;
import model.Chambre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JFileChooser;
import java.time.LocalDate;

public class PanelSejourner extends JPanel {

    private SejournerDAO dao = new SejournerDAO();
    private ChambreDAO chambreDao = new ChambreDAO();
    private JTable table;
    private DefaultTableModel modele;
    private JComboBox<String> comboNumChambre;
    private JTextField champNbrJour, champNomClient, champTelephone, champMontant;
    private int idSejourSelectionne = -1;

    public PanelSejourner() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulaire (en haut) ----
        JPanel panelFormulaire = new JPanel(new GridLayout(3, 4, 5, 5));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Séjour (arrivée directe sans réservation)"));

        comboNumChambre = new JComboBox<>();
        champNbrJour = new JTextField();
        champNomClient = new JTextField();
        champTelephone = new JTextField();
        champMontant = new JTextField();

        panelFormulaire.add(new JLabel("N° Chambre :"));
        panelFormulaire.add(comboNumChambre);
        panelFormulaire.add(new JLabel("Nombre de jours :"));
        panelFormulaire.add(champNbrJour);
        panelFormulaire.add(new JLabel("Nom client :"));
        panelFormulaire.add(champNomClient);
        panelFormulaire.add(new JLabel("Téléphone :"));
        panelFormulaire.add(champTelephone);
        panelFormulaire.add(new JLabel("Montant à ajouter au solde :"));
        panelFormulaire.add(champMontant);

        add(panelFormulaire, BorderLayout.NORTH);

        // ---- Table (au centre) ----
        modele = new DefaultTableModel(new String[]{"ID", "N° Chambre", "Date entrée", "Jours", "Client", "Téléphone"}, 0);
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
        JButton btnGenererPdf = new JButton("Générer reçu PDF");
            panelBoutons.add(btnGenererPdf);
            btnGenererPdf.addActionListener(this::genererRecu);

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

        chargerComboChambres();
        chargerTable();
    }

    private void chargerComboChambres() {
        comboNumChambre.removeAllItems();
        List<Chambre> chambres = chambreDao.listerTout();
        for (Chambre c : chambres) {
            comboNumChambre.addItem(c.getNumChambre());
        }
    }

    private void chargerTable() {
        modele.setRowCount(0);
        List<Sejourner> liste = dao.listerTout();
        for (Sejourner s : liste) {
            modele.addRow(new Object[]{
                s.getIdSejour(), s.getNumChambre(), s.getDateEntreeSejour(), 
                s.getNbrJour(), s.getNomClient(), s.getTelephone()
            });
        }
    }

    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne >= 0) {
            idSejourSelectionne = Integer.parseInt(modele.getValueAt(ligne, 0).toString());
            comboNumChambre.setSelectedItem(modele.getValueAt(ligne, 1).toString());
            champNbrJour.setText(modele.getValueAt(ligne, 3).toString());
            champNomClient.setText(modele.getValueAt(ligne, 4).toString());
            champTelephone.setText(modele.getValueAt(ligne, 5).toString());
        }
    }

    private void viderFormulaire() {
        champNbrJour.setText("");
        champNomClient.setText("");
        champTelephone.setText("");
        champMontant.setText("");
        idSejourSelectionne = -1;
        table.clearSelection();
    }

    private void ajouter(ActionEvent e) {
        try {
            Sejourner s = new Sejourner(
                (String) comboNumChambre.getSelectedItem(),
                null, // dateEntreeSejour : CURRENT_DATE gère automatiquement
                Integer.parseInt(champNbrJour.getText()),
                champNomClient.getText(),
                champTelephone.getText()
            );
            int montant = Integer.parseInt(champMontant.getText());
            dao.ajouter(s, montant);
            chargerTable();
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Séjour ajouté et solde mis à jour !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le nombre de jours et le montant doivent être des nombres !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifier(ActionEvent e) {
        if (idSejourSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un séjour à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Sejourner s = new Sejourner(
                idSejourSelectionne,
                (String) comboNumChambre.getSelectedItem(),
                null,
                Integer.parseInt(champNbrJour.getText()),
                champNomClient.getText(),
                champTelephone.getText()
            );
            dao.modifier(s);
            chargerTable();
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Séjour modifié avec succès !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le nombre de jours doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimer(ActionEvent e) {
        if (idSejourSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un séjour à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer ce séjour ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            dao.supprimer(idSejourSelectionne);
            chargerTable();
            viderFormulaire();
        }
    }
    
    private void genererRecu(ActionEvent e) {
    int ligne = table.getSelectedRow();
    if (ligne < 0) {
        JOptionPane.showMessageDialog(this, "Sélectionnez un séjour pour générer le reçu.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String idSejour = modele.getValueAt(ligne, 0).toString();
    String numChambre = modele.getValueAt(ligne, 1).toString();
    String dateEntree = modele.getValueAt(ligne, 2).toString();
    int nbrJour = Integer.parseInt(modele.getValueAt(ligne, 3).toString());
    String nomClient = modele.getValueAt(ligne, 4).toString();

    // Calcul de la date de sortie = dateEntree + nbrJour
    LocalDate dEntree = LocalDate.parse(dateEntree);
    LocalDate dSortie = dEntree.plusDays(nbrJour);

    // Choisir où enregistrer le fichier
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(new java.io.File("Recu_Sejour_" + idSejour + ".pdf"));
    int resultat = fileChooser.showSaveDialog(this);

    if (resultat == JFileChooser.APPROVE_OPTION) {
        String chemin = fileChooser.getSelectedFile().getAbsolutePath();
        if (!chemin.toLowerCase().endsWith(".pdf")) {
            chemin += ".pdf";
        }

        boolean succes = service.PdfService.genererRecu(
            idSejour, nomClient, numChambre, nbrJour, 
            dEntree.toString(), dSortie.toString(), chemin
        );

        if (succes) {
            JOptionPane.showMessageDialog(this, "Reçu PDF généré avec succès !\n" + chemin);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la génération du PDF.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}
