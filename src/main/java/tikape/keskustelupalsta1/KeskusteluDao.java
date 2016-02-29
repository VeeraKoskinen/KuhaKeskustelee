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
public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database data;
    private Dao<Keskustelualue, Integer> alueDao;

    public KeskusteluDao(Database data, Dao<Keskustelualue, Integer> alueDao) {
        this.alueDao = alueDao;
        this.data = data;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = data.getConnection();
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
        connection.close();

        k.setAlue(this.alueDao.findOne(alue));
        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = data.getConnection();
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
        connection.close();

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

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
