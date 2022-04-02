package ristorante;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reservation {

    private String lastname;
    private Date date;
    private int nPeople;
    private long tNumber;

    public void createT(Connection c) throws SQLException {
        String sql = "CREATE TABLE reservation (lastname VARCHAR(30) , date DATE, nPeople INTEGER NOT NULL, tNumber INTEGER,)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.executeUpdate();

        ps.close();


    }
}
