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
public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {

    private Database data;
    private KeskustelupalstaDao palstaDao;

    public KeskustelualueDao(Database data, KeskustelupalstaDao palstaDao) {
        this.data = data;
        this.palstaDao = palstaDao;
    }

    @Override
    public Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE KeskustelualueId = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("KeskustelualueId");
        String otsikko = rs.getString("Otsikko");

        Keskustelualue ka = new Keskustelualue(id, otsikko);

        Integer palsta = rs.getInt("Palsta");

        rs.close();
        stmt.close();
        connection.close();

        ka.setPalsta(this.palstaDao.findOne(palsta));

        return ka;
    }

    
    public List<Keskustelualue> findAll() throws SQLException {

        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT a.Palsta AS Palsta, a.KeskustelualueId AS ID, a.otsikko AS Otsikko, COUNT(v.Id) AS Maara, MAX(v.saapumishetki) AS Päivä FROM Keskustelualue a LEFT JOIN Keskustelu k  ON a.KeskustelualueId = k.Keskustelualue LEFT JOIN Viesti v ON k.KeskusteluId=v.keskustelu GROUP BY a.KeskustelualueId ORDER BY Päivä DESC;");
        ResultSet rs = stmt.executeQuery();
//
        Map<Integer, List<Keskustelualue>> palstanAlueet = new HashMap<>();
//
        List<Keskustelualue> alueet = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("ID");
            String otsikko = rs.getString("Otsikko");

            Keskustelualue ka = new Keskustelualue(id, otsikko);
            
            alueet.add(ka);
            Integer maara = rs.getInt("Maara");
            java.sql.Date paiva = rs.getDate("Päivä");

            Integer palsta = rs.getInt("Palsta");
            
            
            ka.setViestienMaara(maara);
            ka.setViimeinenViesti(paiva);

            if (!palstanAlueet.containsKey(palsta)) {
                palstanAlueet.put(palsta, new ArrayList<>());
            }
            palstanAlueet.get(palsta).add(ka);
        }

        rs.close();
        stmt.close();
        connection.close();
//
        for (Keskustelupalsta kp : this.palstaDao.findAll()) {
            if (!palstanAlueet.containsKey(kp.getId())) {
                continue;
            }

            for (Keskustelualue ka : palstanAlueet.get(kp.getId())) {
                ka.setPalsta(kp);
            }
        }

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void lisaaKeskustelu(int alue, String otsikko) throws SQLException {
        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelu (Otsikko, Keskustelualue) VALUES(?,?);");
        stmt.setInt(2, alue);
        stmt.setString(1, otsikko);
        stmt.executeUpdate();
    }
    
   

}
