/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import java.util.Date;


/**
 *
 * @author veerakoskinen
 */
public class Keskustelu {
    private int id;
    private String otsikko;
    private Keskustelualue alue;
    private Date viimeisinViesti;
    private int viestienMaara;
    
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

    public void setViestienMaara(int viestienMaara) {
        this.viestienMaara = viestienMaara;
    }

    public void setViimeisinViesti(Date viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }

    public int getViestienMaara() {
        return viestienMaara;
    }

    public Date getViimeisinViesti() {
        return viimeisinViesti;
    }
    
    
    
    
    
}
