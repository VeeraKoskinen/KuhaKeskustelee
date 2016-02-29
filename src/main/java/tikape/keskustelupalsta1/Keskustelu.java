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
public class Keskustelu {
    private int id;
    private String otsikko;
    private Keskustelualue alue;
    
    public Keskustelu() {
        
    }
    
    public Keskustelu(int id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public Keskustelualue getAlue() {
        return alue;
    }

    public void setAlue(Keskustelualue alue) {
        this.alue = alue;
    }
    
}
