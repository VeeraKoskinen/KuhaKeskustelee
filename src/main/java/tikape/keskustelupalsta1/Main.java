/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import static spark.Spark.*;
import java.util.*;
import java.sql.*;
import spark.ModelAndView;
import spark.Request;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.keskustelupalsta1.*;

public class Main {

    public static void main(String[] args) throws Exception {
        
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
         // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:keskustelupalsta.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 

        

        Database database = new Database(jdbcOsoite);
        database.setDebugMode(true);

        KeskustelupalstaDao palstadao = new KeskustelupalstaDao(database);
        KeskustelualueDao aluedao = new KeskustelualueDao(database, palstadao);
        KeskusteluDao keskusteludao = new KeskusteluDao(database, aluedao);
        ViestiDao viestidao = new ViestiDao(database, keskusteludao);

        //aluelistaus
        get("/kuha", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("alueet", aluedao.findAll(getSivu(req)));
            return new ModelAndView(map, "aluelistaus");
        }, new ThymeleafTemplateEngine());
        post("/kuha", (req, res) -> {
            palstadao.lisaaAlue(req.queryParams("Otsikko"));
            res.redirect("/kuha");
            return "Lisäys onnistui!";
        });

        //keskustelulistaus
        get("/alue/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            HashMap map = new HashMap<>();
            map.put("alue", aluedao.findOne(id));
            map.put("keskustelut", keskusteludao.jarjestetytKeskustelut(id,getSivu(req)));
            return new ModelAndView(map, "keskustelulistaus");
        }, new ThymeleafTemplateEngine());
        post("/alue/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            aluedao.lisaaKeskustelu(id, req.queryParams("Otsikko"));
            res.redirect("/alue/" + id);
            return "Lisäys onnistui!";
        });

        //viestilistaus
        get("/alue/:alueid/keskustelu/:id", (req, res) -> {
            int alueid = Integer.parseInt(req.params(":alueid"));
            int id = Integer.parseInt(req.params(":id"));
            HashMap map = new HashMap<>();
            map.put("alue", aluedao.findOne(alueid));
            map.put("keskustelu", keskusteludao.findOne(id));
            map.put("viestit", viestidao.findAll(id));
            return new ModelAndView(map, "viestilistaus");
        }, new ThymeleafTemplateEngine());
        post("/alue/:alueid/keskustelu/:id", (req, res) -> {
            int alueId = Integer.parseInt(req.params(":alueid"));
            int id = Integer.parseInt(req.params(":id"));
            String nimi =req.queryParams("nimimerkki");
            if (nimi == null || nimi.isEmpty()) {
                res.redirect("/alue/" + alueId + "/keskustelu/" + id);
                return "Ei lisätty";
            }
            keskusteludao.lisaaViesti(id, req.queryParams("nimimerkki"), req.queryParams("viestisisalto"));
            res.redirect("/alue/" + alueId + "/keskustelu/" + id);
            return "Lisäys onnistui!";
        });

    }

    private static int getSivu(Request req) {
        try {
            return Integer.parseInt(req.queryParams("sivu"));
        } catch (NumberFormatException e) {
        }
        return 0;
    }

}
