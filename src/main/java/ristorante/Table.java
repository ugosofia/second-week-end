package ristorante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Table {

    private int tableNum;
    private int capacity;


    public void createT(Connection c) throws SQLException {
        String sql = "CREATE TABLE table (tableNum INTEGER PRIMARY KEY , capacity INTEGER NOT NULL )";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.executeUpdate();

        ps.close();


    }
}


