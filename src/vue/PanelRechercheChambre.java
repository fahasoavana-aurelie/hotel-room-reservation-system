/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import dao.ChambreDAO;
import model.Chambre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

public class PanelRechercheChambre extends JPanel {

    private ChambreDAO dao = new ChambreDAO();
    private JTable table;
    private DefaultTableModel modele;
    private JTextField champDate;

    public PanelRechercheChambre() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulaire de recherche ----
        JPanel panelFormulaire = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Rechercher une chambre libre"));

        champDate = new JTextField(12);
        JButton btnRechercher = new JButton("Rechercher");

        panelFormulaire.add(new JLabel("Date (AAAA-MM-JJ) :"));
        panelFormulaire.add(champDate);
        panelFormulaire.add(btnRechercher);

        add(panelFormulaire, BorderLayout.NORTH);

        // ---- Table des résultats ----
        modele = new DefaultTableModel(new String[]{"N° Chambre", "Design", "Type", "Prix/nuit"}, 0);
        table = new JTable(modele);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnRechercher.addActionListener(this::rechercher);
    }

    private void rechercher(ActionEvent e) {
        try {
            Date date = Date.valueOf(champDate.getText());
            modele.setRowCount(0);
            List<Chambre> liste = dao.rechercherChambresLibres(date);

            if (liste.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune chambre libre à cette date.");
            } else {
                for (Chambre c : liste) {
                    modele.addRow(new Object[]{c.getNumChambre(), c.getDesign(), c.getType(), c.getPrixNuite()});
                }
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide ! Utilisez AAAA-MM-JJ", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
