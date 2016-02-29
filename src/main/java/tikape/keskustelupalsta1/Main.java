/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import static spark.Spark.*;
import java.util.*;
import java.sql.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.keskustelupalsta1.*;

public class Main {
    
     public static void main(String[] args) throws Exception {
        get("/rapupiirakka", (req, res) -> {
            return "HipHEi!";
        });
         
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.setDebugMode(true);

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());


     }
    
}
