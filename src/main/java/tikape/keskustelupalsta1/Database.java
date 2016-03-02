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

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.databaseAddress);
    }

    public void setDebugMode(boolean d) {
        debug = d;
    }    
    
    private void debug(ResultSet rs) throws SQLException {
        int columns = rs.getMetaData().getColumnCount();
        for (int i = 0; i < columns; i++) {
            System.out.print(
                    rs.getObject(i + 1) + ":"
                    + rs.getMetaData().getColumnName(i + 1) + "  ");
        }

        System.out.println();
    }

//    public List<T> queryAndCollect(String query, Collector<T> col) throws SQLException {
//        List<T> rows = new ArrayList<>();
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(query);
//
//        while (rs.next()) {
//            if (debug) {
//                System.out.println("---");
//                System.out.println(query);
//                debug(rs);
//                System.out.println("---");
//            }
//
//            rows.add(col.collect(rs));
//        }
//
//        rs.close();
//        stmt.close();
//        return rows;
//    }

}
