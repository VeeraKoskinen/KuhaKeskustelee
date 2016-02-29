/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import static spark.Spark.*;
import java.sql.*;

public class Main {
    
     public static void main(String[] args) throws Exception {
          get("/rapupiirakka", (req, res) -> {
            return "HipHEi!";
        });
         
//        Connection connection = DriverManager.getConnection("jdbc:sqlite:keskustelupalsta.db");
//
//        Statement s = connection.createStatement();
//
//        ResultSet rs = s.executeQuery("SELECT * FROM Keskustelualue;");
//        
//        while (rs.next()) {
//            int keskusteluAlueId = rs.getInt("KeskustelualueId");
//            String otsikko = rs.getString("Otsikko");
//            int palstaId = rs.getInt("Palsta");
//            
//            System.out.println(keskusteluAlueId + " " + otsikko + " " + palstaId);
//            //KeskustelualueId|Otsikko|Palsta
//        }
//        
//        s.close();
//        rs.close();
//        
//        connection.close();

     }
    
}
