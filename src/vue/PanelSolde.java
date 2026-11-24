/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue;

import dao.SoldeDAO;
import model.Solde;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelSolde extends JPanel {

    private SoldeDAO dao = new SoldeDAO();
    private JLabel labelSolde;

    public PanelSolde() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        labelSolde = new JLabel("Solde actuel : ...", SwingConstants.CENTER);
        labelSolde.setFont(new Font("Arial", Font.BOLD, 28));
        add(labelSolde, BorderLayout.CENTER);

        JButton btnRafraichir = new JButton("Rafraîchir");
        btnRafraichir.addActionListener(this::rafraichir);

        JPanel panelBouton = new JPanel();
        panelBouton.add(btnRafraichir);
        add(panelBouton, BorderLayout.SOUTH);

        rafraichir(null);
    }

    private void rafraichir(ActionEvent e) {
        Solde s = dao.getSolde();
        if (s != null) {
            labelSolde.setText("Solde actuel : " + s.getSoldeActuel() + " Ar");
        } else {
            labelSolde.setText("Aucun solde trouvé.");
        }
    }
}
