/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

/**
 *
 * @author veerakoskinen
 */
public class Keskustelualue {
    private int id;
    private String otsikko;
    private Keskustelupalsta palsta;
            
    public Keskustelualue() {
        
    } 
    
    public Keskustelualue(int id, String otsikko, Keskustelupalsta palsta) {
        this.id = id;
        this.otsikko = otsikko;
        this.palsta = palsta;
    } 
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public String getOtsikko() {
        return otsikko;
    }

    public Keskustelupalsta getPalsta() {
        return palsta;
    }

    public void setPalsta(Keskustelupalsta palsta) {
        this.palsta = palsta;
    }
    
    
}
