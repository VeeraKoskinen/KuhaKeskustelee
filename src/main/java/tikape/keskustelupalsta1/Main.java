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
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.keskustelupalsta1.*;

public class Main {

    public static void main(String[] args) throws Exception {
        

        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.setDebugMode(true);

        KeskustelupalstaDao palstadao = new KeskustelupalstaDao(database);
        KeskustelualueDao aluedao = new KeskustelualueDao(database, palstadao);
        KeskusteluDao keskusteludao = new KeskusteluDao(database, aluedao);
        ViestiDao viestidao = new ViestiDao(database, keskusteludao);

        //index
        get("/kuha", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", aluedao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        post("/kuha", (req, res) -> {
            palstadao.lisaaAlue(req.queryParams("Otsikko"));
            res.redirect("/kuha");
            return "Lisäys onnistui!";
        });
        
        
        //index2
        get("/alue/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            HashMap map = new HashMap<>();
            map.put("alue", aluedao.findOne(id));
            map.put("keskustelut", keskusteludao.jarjestetytKeskustelut(id));
            return new ModelAndView(map, "index2");
        }, new ThymeleafTemplateEngine());
        post("/alue/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            aluedao.lisaaKeskustelu(id, req.queryParams("Keskustelu"));
            res.redirect("/alue/" + id);
            return "Lisäys onnistui!";
        });
         
        
        //index3
        get("/alue/:alueid/keskustelu/:id", (req, res) -> {
            int alueid = Integer.parseInt(req.params(":alueid"));
            int id = Integer.parseInt(req.params(":id"));
            HashMap map = new HashMap<>();
            map.put("alue", aluedao.findOne(alueid));
            map.put("keskustelu", keskusteludao.findOne(id));
            map.put("viestit", viestidao.findAll(id));
            return new ModelAndView(map, "index3");
        }, new ThymeleafTemplateEngine());
        post("/alue/:alueid/keskustelu/:id", (req, res) -> {
            int alueId = Integer.parseInt(req.params(":alueid"));
            int id = Integer.parseInt(req.params(":id"));
            keskusteludao.lisaaViesti(id, req.queryParams("nimimerkki"), req.queryParams("viestisisalto"));
            res.redirect("/alue/" + alueId + "/keskustelu/" + id);
            return "Lisäys onnistui!";
        });
         
        
        

    }

}
