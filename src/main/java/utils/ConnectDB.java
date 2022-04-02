package utils;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    public static Connection connect() throws SQLException, ClassNotFoundException, IOException {
        ReadProperties rp = new ReadProperties();
        rp.read("application.properties");

        Class.forName(rp.getProperties().getProperty("db_mysqlurl"));
        return DriverManager.getConnection(
                rp.getProperties().getProperty("db_url"),
                rp.getProperties().getProperty("db_username"),
                rp.getProperties().getProperty("db_password"));
    }
}

