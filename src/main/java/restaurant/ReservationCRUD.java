package restaurant;

import utils.ConnectDB;
import utils.Logger;

import java.io.IOException;
import java.sql.*;

public class ReservationCRUD implements Operation<Reservation>{

    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    Logger L = Logger.getInstance();

    public static final String RESERVATION_CREATE = "CREATE TABLE table (tableNum INTEGER PRIMARY KEY , capacity INTEGER NOT NULL )";
    public static final String RESERVATION_INSERT = "INSERT INTO table (tableNum, capacity) VALUES (?, ?)";
    public static final String RESERVATION_SELECT = "SELECT * FROM table WHERE ? = ?";
    public static final String RESERVATION_UPDATE = "";
    public static final String RESERVATION_DELETE = "";


    @Override
    public void createT() {
        try{
            c = ConnectDB.connect();
            ps = c.prepareStatement(RESERVATION_CREATE);
            ps.executeUpdate();
            close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert() {
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(RESERVATION_INSERT);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ps.setString(1,"Sofia" );
            ps.setDate(2, Date.valueOf("15/04/2022"));
            ps.setInt(3,5 );
            ps.setString(4, "3205554199" );

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    @Override
    public void update(Reservation object) {

    }

    @Override
    public void delete(Reservation object) {

    }

    @Override
    public void select() {

    }

    @Override
    public void close() {

    }
}
