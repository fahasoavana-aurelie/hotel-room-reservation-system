/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import service.EnvoiMail;

public class TestMail {
   public static void main(String[] args) {
     EnvoiMail.envoyer(
             "fahasoavana31@gmail.com",
             "Test - Gestion Hotel",
             "ceci est message de texte"
     );
   } 
}
