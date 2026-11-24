package vue;

import dao.ChambreDAO;
import model.Chambre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PanelChambre extends JPanel {

    private ChambreDAO dao = new ChambreDAO();
    private JTable table;
    private DefaultTableModel modele;
    private JTextField champNumChambre, champDesign, champType, champPrix;

    public PanelChambre() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulaire (en haut) ----
        JPanel panelFormulaire = new JPanel(new GridLayout(2, 4, 5, 5));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Chambre"));

        champNumChambre = new JTextField();
        champDesign = new JTextField();
        champType = new JTextField();
        champPrix = new JTextField();

        panelFormulaire.add(new JLabel("N° Chambre :"));
        panelFormulaire.add(champNumChambre);
        panelFormulaire.add(new JLabel("Design :"));
        panelFormulaire.add(champDesign);
        panelFormulaire.add(new JLabel("Type :"));
        panelFormulaire.add(champType);
        panelFormulaire.add(new JLabel("Prix/nuit :"));
        panelFormulaire.add(champPrix);

        add(panelFormulaire, BorderLayout.NORTH);

        // ---- Table (au centre) ----
        modele = new DefaultTableModel(new String[]{"N° Chambre", "Design", "Type", "Prix/nuit"}, 0);
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
        btnRafraichir.addActionListener(e -> chargerTable());
        btnEffacer.addActionListener(e -> viderFormulaire());

        chargerTable(); // Charger les données au démarrage
    }

    // Charger toutes les chambres dans la table
    private void chargerTable() {
        modele.setRowCount(0); // Vider la table
        List<Chambre> liste = dao.listerTout();
        for (Chambre c : liste) {
            modele.addRow(new Object[]{c.getNumChambre(), c.getDesign(), c.getType(), c.getPrixNuite()});
        }
    }

    // Remplir le formulaire quand on clique sur une ligne de la table
    private void remplirFormulaireDepuisTable() {
        int ligne = table.getSelectedRow();
        if (ligne >= 0) {
            champNumChambre.setText(modele.getValueAt(ligne, 0).toString());
            champDesign.setText(modele.getValueAt(ligne, 1).toString());
            champType.setText(modele.getValueAt(ligne, 2).toString());
            champPrix.setText(modele.getValueAt(ligne, 3).toString());
        }
    }

    private void viderFormulaire() {
        champNumChambre.setText("");
        champDesign.setText("");
        champType.setText("");
        champPrix.setText("");
        table.clearSelection();
    }

    private void ajouter(ActionEvent e) {
        try {
            Chambre c = new Chambre(
                champNumChambre.getText(),
                champDesign.getText(),
                champType.getText(),
                Integer.parseInt(champPrix.getText())
            );
            dao.ajouter(c);
            chargerTable();
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Chambre ajoutée avec succès !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le prix doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifier(ActionEvent e) {
        if (champNumChambre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une chambre à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Chambre c = new Chambre(
                champNumChambre.getText(),
                champDesign.getText(),
                champType.getText(),
                Integer.parseInt(champPrix.getText())
            );
            dao.modifier(c);
            chargerTable();
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Chambre modifiée avec succès !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le prix doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimer(ActionEvent e) {
        if (champNumChambre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une chambre à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer cette chambre ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            dao.supprimer(champNumChambre.getText());
            chargerTable();
            viderFormulaire();
        }
    }
}