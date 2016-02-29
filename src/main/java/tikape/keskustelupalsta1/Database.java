/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import java.sql.*;

/**
 *
 * @author veerakoskinen
 */
public class Database<T> {
    private String databaseAddress;
    
    private boolean debug;
    private Connection connection;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        this.connection = DriverManager.getConnection(address);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
}
