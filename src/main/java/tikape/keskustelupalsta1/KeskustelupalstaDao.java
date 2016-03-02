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
public class KeskustelupalstaDao implements Dao<Keskustelupalsta, Integer> {

    private Database data;

    public KeskustelupalstaDao(Database data) {
        this.data = data;
    }

    @Override
    public Keskustelupalsta findOne(Integer key) throws SQLException {
        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelupalsta WHERE PalstaId = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("PalstaId");
        String nimi = rs.getString("Keskustelupalstan_Nimi");

        Keskustelupalsta k = new Keskustelupalsta(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelupalsta> findAll() throws SQLException {
        Connection connection = data.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelupalsta");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelupalsta> palstat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("PalstaId");
            String nimi = rs.getString("Keskustelupalstan_Nimi");

            palstat.add(new Keskustelupalsta(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return palstat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
