/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HY
 */
public class Connexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/Gestion_reservation";
     private static final String USER = "postgres";
      private static final String PASSWORD = "Fiadanana2016";
      
      public static Connection getConnexion() {
         Connection conn = null;
          try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie !");
            } catch (ClassNotFoundException e) {
            System.out.println("Driver introuvable : "+ e.getMessage());
            } catch (SQLException e) {
            System.out.println("Erreur de connexion : "+ e.getMessage());
            }
          return conn;
      }
      public static void main (String[] args) {
      getConnexion();
      }
      
}
