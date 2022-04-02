package restaurant;

import utils.ConnectDB;
import utils.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            ps.setInt(1,1 );
            ps.setInt(1,5 );

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
