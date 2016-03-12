/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 *
 * @author veerakoskinen
 */
public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database data;
    private KeskustelualueDao alueDao;

    public KeskusteluDao(Database data, KeskustelualueDao alueDao) {
        this.alueDao = alueDao;
        this.data = data;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        try (Connection connection = data.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE KeskusteluId = ?");
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            Integer id = rs.getInt("KeskusteluId");
            String otsikko = rs.getString("Otsikko");

            Keskustelu k = new Keskustelu(id, otsikko);

            Integer alue = rs.getInt("Keskustelualue");

            rs.close();
            stmt.close();

            k.setAlue(this.alueDao.findOne(alue));
            return k;
        }
    }

    public List<Keskustelu> findAll() throws SQLException {
        try (Connection connection = data.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");
            ResultSet rs = stmt.executeQuery();
//
            Map<Integer, List<Keskustelu>> alueenKeskustelut = new HashMap<>();
//
            List<Keskustelu> keskustelut = new ArrayList<>();
//
            while (rs.next()) {

                Integer id = rs.getInt("KeskusteluId");
                String otsikko = rs.getString("Otsikko");
//
                Keskustelu k = new Keskustelu(id, otsikko);
                keskustelut.add(k);

                Integer alue = rs.getInt("Keskustelualue");

                if (!alueenKeskustelut.containsKey(alue)) {
                    alueenKeskustelut.put(alue, new ArrayList<>());
                }
                alueenKeskustelut.get(alue).add(k);
            }

            rs.close();
            stmt.close();

            for (Keskustelualue ka : this.alueDao.findAll()) {
                if (!alueenKeskustelut.containsKey(ka.getId())) {
                    continue;
                }

                for (Keskustelu keskustelu : alueenKeskustelut.get(ka.getId())) {
                    keskustelu.setAlue(ka);
                }
            }

            return keskustelut;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Keskustelu> jarjestetytKeskustelut(int alue) throws SQLException {
        return jarjestetytKeskustelut(alue, 0);
    }

    public List<Keskustelu> jarjestetytKeskustelut(int alue, int offset) throws SQLException {
        try (Connection connection = data.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT k.KeskusteluId AS ID, k.otsikko AS Otsikko, COUNT(v.Id) AS Maara, MAX(v.saapumishetki) AS Päivä FROM Keskustelu k LEFT JOIN Viesti v ON k.KeskusteluId=v.keskustelu WHERE k.Keskustelualue = ? GROUP BY k.KeskusteluId ORDER BY DATE(MAX(v.saapumishetki)) DESC LIMIT 10 OFFSET ?;");
            stmt.setInt(1, alue);
            stmt.setInt(2, offset * 10);
            ResultSet rs = stmt.executeQuery();

//
            Map<Integer, List<Keskustelu>> alueenKeskustelut = new HashMap<>();
//
            List<Keskustelu> keskustelut = new ArrayList<>();
//
            while (rs.next()) {

                Integer id = rs.getInt("ID");
                String otsikko = rs.getString("Otsikko");
                Integer maara = rs.getInt("Maara");
                Date paiva = rs.getDate("Päivä");
//
                Keskustelu k = new Keskustelu(id, otsikko);
                keskustelut.add(k);
                k.setViestienMaara(maara);
                k.setViimeisinViesti(paiva);

                if (!alueenKeskustelut.containsKey(id)) {
                    alueenKeskustelut.put(id, new ArrayList<>());
                }
                alueenKeskustelut.get(id).add(k);
            }

            rs.close();
            stmt.close();

            for (Keskustelualue ka : this.alueDao.findAll()) {
                if (!alueenKeskustelut.containsKey(ka.getId())) {
                    continue;
                }

                for (Keskustelu keskustelu : alueenKeskustelut.get(ka.getId())) {
                    keskustelu.setAlue(ka);
                }
            }

            return keskustelut;
        }
    }

    public void lisaaViesti(int keskustelu, String nimimerkki, String viesti) throws SQLException {
        try (Connection connection = data.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (Keskustelu, Nimimerkki, Viestisisältö) VALUES(?,?,?)");
            stmt.setInt(1, keskustelu);
            stmt.setString(2, nimimerkki);
            stmt.setString(3, viesti);
            stmt.executeUpdate();
        }
    }

}
