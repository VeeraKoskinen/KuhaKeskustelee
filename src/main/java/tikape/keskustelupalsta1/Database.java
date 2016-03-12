/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.keskustelupalsta1;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author veerakoskinen
 */
public class Database<T> {

    private String databaseAddress;
    private boolean debug;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        
        init();

    }

//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(this.databaseAddress);
//    }

     public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }
     
    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä    
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Keskustelupalsta (PalstaId serial PRIMARY KEY, Keskustelupalstan_Nimi varchar(100) NOT NULL);");
        lista.add("INSERT INTO Keskustelupalsta (Keskustelupalstan_Nimi) VALUES ('KuhaKeskustelee');");
        lista.add("CREATE TABLE Keskustelualue (KeskustelualueId serial PRIMARY KEY, Otsikko varchar(100) NOT NULL, Palsta integer NOT NULL, FOREIGN KEY (Palsta) REFERENCES Keskustelupalsta(PalstaId));");
        lista.add("CREATE TABLE Keskustelu (KeskusteluId serial PRIMARY KEY, Otsikko varchar(1000) NOT NULL, Keskustelualue integer NOT NULL, FOREIGN KEY (Keskustelualue) REFERENCES Keskustelualue(KeskustelualueId));");
        lista.add("CREATE TABLE Viesti (Id serial PRIMARY KEY, Keskustelu integer NOT NULL, Nimimerkki varchar(20) NOT NULL, Saapumishetki DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')), Viestisisältö varchar(100000) NOT NULL,FOREIGN KEY (Keskustelu) REFERENCES Keskustelu(KeskusteluId));");
 
        return lista;
    }
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Keskustelupalsta (PalstaId integer PRIMARY KEY, Keskustelupalstan_Nimi varchar(100) NOT NULL);");
        lista.add("INSERT INTO Keskustelupalsta (Keskustelupalsta_Nimi) VALUES('KuhaKeskustelee')");
        lista.add("CREATE TABLE Keskustelualue (KeskustelualueId integer PRIMARY KEY, Otsikko varchar(100) NOT NULL, Palsta integer NOT NULL, FOREIGN KEY (Palsta) REFERENCES Keskustelupalsta(PalstaId));");
        lista.add("CREATE TABLE Keskustelu (KeskusteluId integer PRIMARY KEY, Otsikko varchar(1000) NOT NULL, Keskustelualue integer NOT NULL, FOREIGN KEY (Keskustelualue) REFERENCES Keskustelualue(KeskustelualueId));");
        lista.add("CREATE TABLE Viesti (Id integer PRIMARY KEY, Keskustelu integer NOT NULL, Nimimerkki varchar(20) NOT NULL, Saapumishetki DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')), Viestisisältö varchar(100000) NOT NULL, FOREIGN KEY (Keskustelu) REFERENCES Keskustelu(KeskusteluId));");
        
        return lista;
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
    
    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
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
