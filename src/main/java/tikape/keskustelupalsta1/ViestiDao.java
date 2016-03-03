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
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database data;
    private Dao<Keskustelu, Integer> keskusteluDao;

    public ViestiDao(Database data, Dao<Keskustelu, Integer> keskusteluDao) {
        this.data = data;
        this.keskusteluDao = keskusteluDao;
    }
    
    

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("Id"); // vielä luotava
        Integer keskustelu = rs.getInt("Keskustelu");
        String nimimerkki = rs.getString("Nimimerkki");
        Timestamp saapumishetki = rs.getTimestamp("Saapumishetki");
        String viestisisalto = rs.getString("Viestisisältö");

        Viesti v = new Viesti(nimimerkki, saapumishetki, id, viestisisalto);

        rs.close();
        stmt.close();
        connection.close();

        v.setKeskustelu(this.keskusteluDao.findOne(keskustelu));

        return v;

    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");
        ResultSet rs = stmt.executeQuery();

        Map<Integer, List<Viesti>> keskustelunViestit = new HashMap<>();

        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {

            Integer id = rs.getInt("Id");
            
            String nimimerkki = rs.getString("Nimimerkki");
            Timestamp saapumishetki = rs.getTimestamp("Saapumishetki");
            String viestisisalto = rs.getString("Viestisisältö");

            Viesti v = new Viesti(nimimerkki, saapumishetki, id, viestisisalto);
            viestit.add(v);

            Integer keskustelu = rs.getInt("Keskustelu");
            if (!keskustelunViestit.containsKey(keskustelu)) {
                keskustelunViestit.put(keskustelu, new ArrayList<>());
            }
            keskustelunViestit.get(keskustelu).add(v);
        }

        rs.close();
        stmt.close();
        connection.close();

        for (Keskustelu k : this.keskusteluDao.findAll()) {
            if (!keskustelunViestit.containsKey(k.getId())) {
                continue;
            }

            for (Viesti viesti : keskustelunViestit.get(k.getId())) {
                viesti.setKeskustelu(k);
            }
        }

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
