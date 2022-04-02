package restaurant;

import utils.ConnectDB;
import utils.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableCRUD implements Operation<Table> {

    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    Logger L = Logger.getInstance();

    public static final String TABLE_CREATE = "CREATE TABLE table (tableNum INTEGER PRIMARY KEY , capacity INTEGER NOT NULL )";
    public static final String TABLE_INSERT = "INSERT INTO table (tableNum, capacity) VALUES (?, ?)";
    public static final String TABLE_SELECT = "SELECT * FROM table WHERE ? = ?";
    public static final String TABLE_UPDATE = "";
    public static final String TABLE_DELETE = "";


    @Override
    public void createT() {
        try{
        c = ConnectDB.connect();
        ps = c.prepareStatement(TABLE_CREATE);
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
            ps = c.prepareStatement(TABLE_INSERT);

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ps.setInt(1,1 );
            ps.setInt(2,5 );

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

       close();
    }

    @Override
    public void update(Table object) {

    }

    @Override
    public void delete(Table object) {

    }

    @Override
    public void select() {

    }

    @Override
    public void close() {
        try{
            c.close();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
