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

        KeskustelupalstaDao palstaDao = new KeskustelupalstaDao(database);
        KeskustelualueDao aluedao = new KeskustelualueDao(database, palstaDao);
        KeskusteluDao k = new KeskusteluDao(database, aluedao);
        ViestiDao v = new ViestiDao(database, k);

        get("/kuha", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", aluedao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        

    }

}
