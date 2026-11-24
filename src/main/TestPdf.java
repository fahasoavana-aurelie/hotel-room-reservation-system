/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import service.PdfService;

public class TestPdf {
    public static void main(String[] args) {
        // Générer le reçu exactement comme l'exemple du sujet
        PdfService.genererRecu(
            "011", 
            "RAKOTO Bernard", 
            "Chambre 102", 
            3, 
            "24/04/2023", 
            "27/04/2023", 
            "Recu_Sejour_011.pdf"
        );
    }
}
