/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;
import java.sql.*;
import java.util.*;
/**
 *
 * @author veerakoskinen
 */
public class Keskustelupalsta {
    private int id;
    private String nimi;       
    
    public Keskustelupalsta() {
        
    }
    
    public Keskustelupalsta(int id, String keskustelupalsta) {
        this.id = id;
        this.nimi = keskustelupalsta;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public void setNimi(String keskustelupalsta) {
        this.nimi = keskustelupalsta;
    }
}
