/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;


import java.sql.Date;
import java.sql.Timestamp;


/**
 *
 * @author veerakoskinen
 */
public class Viesti {
    private Keskustelu keskustelu;
    private String nimimerkki;
    private Timestamp saapumishetki;
    private int id;
    private String viestisisalto;

    public Viesti(String nimimerkki, Timestamp saapumishetki, int id, String viestisisalto) {
        this.keskustelu = null;
        this.nimimerkki = nimimerkki;
        this.saapumishetki = saapumishetki;
        this.id = id;
        this.viestisisalto = viestisisalto;
    }

    public Keskustelu getKeskustelu() {
        return keskustelu;
    }

    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public Timestamp getSaapumishetki() {
        return saapumishetki;
    }

    public void setSaapumishetki(Timestamp saapumishetki) {
        this.saapumishetki = saapumishetki;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setViestisisalto(String viestisisalto) {
        this.viestisisalto = viestisisalto;
    }

    public int getId() {
        return id;
    }

    public String getViestisisalto() {
        return viestisisalto;
    }
    
    
    
    
}
