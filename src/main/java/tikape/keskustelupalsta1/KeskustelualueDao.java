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
    private Dao<Keskustelupalsta, Integer> palstaDao;

    public KeskustelualueDao(Database data, Dao<Keskustelupalsta, Integer> palstaDao) {
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

        Integer palsta = rs.getInt("Keskustelupalsta");

        rs.close();
        stmt.close();
        connection.close();

        ka.setPalsta(this.palstaDao.findOne(palsta));

        return ka;
    }

    @Override
    public List<Keskustelualue> findAll() throws SQLException {

        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");
        ResultSet rs = stmt.executeQuery();
//
        Map<Integer, List<Keskustelualue>> palstanAlueet = new HashMap<>();
//
        List<Keskustelualue> alueet = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("KeskustelualueId");
            String otsikko = rs.getString("Otsikko");

            Keskustelualue ka = new Keskustelualue(id, otsikko);
            
            alueet.add(ka);

            Integer palsta = rs.getInt("Keskustelupalsta");

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

}
