/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;

public class PdfService {

    public static boolean genererRecu(String numSejour, String nomClient, String numChambre, int nbrJour, String dateEntree, String dateSortie, String cheminFichier) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(cheminFichier));
            document.open();

            // Titre du Reçu
            Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titre = new Paragraph("Exemple d'un Reçu\n\n", fontTitre);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);

            // Contenu
            Font fontText = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            
            document.add(new Paragraph("Sejour N° : " + numSejour, fontText));
            document.add(new Paragraph("Nom du Client : " + nomClient, fontText));
            document.add(new Paragraph("Désignation chambre : " + numChambre, fontText));
            document.add(new Paragraph("Nombre de jour : " + nbrJour + " jours", fontText));
            document.add(new Paragraph("Date d'entrée : " + dateEntree, fontText));
            document.add(new Paragraph("Date de sortie : " + dateSortie, fontText));

            document.close();
            System.out.println("Reçu PDF généré avec succès !");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
