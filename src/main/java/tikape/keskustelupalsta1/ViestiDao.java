/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author veerakoskinen
 */
public class ViestiDao implements Dao<Viesti, Integer> {
    
    private Database data;
    private Dao <Keskustelu, Integer> keskusteluDao;
    
    public ViestiDao (Database data, Dao<Keskustelu, Integer> keskusteluDao) {
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
        
        Integer id = rs.getInt("id"); // vielä luotava
        Integer keskustelu = rs.getInt("keskustelu");
        String nimimerkki = rs.getString("nimimerkki");
        Timestamp saapumishetki = rs.getTimestamp("saapumishetki");
        String viestisisalto = rs.getString("viestisisalto");
        
        Viesti v = new Viesti(nimimerkki, saapumishetki, id, viestisisalto);
        
        
        rs.close();
        stmt.close();
        connection.close();
        
        v.setKeskustelu(this.keskusteluDao.findOne(keskustelu));
        
        return v;
               
    }
        
        

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
