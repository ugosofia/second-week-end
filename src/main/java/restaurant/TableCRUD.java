package restaurant;

import utils.ConnectDB;
import utils.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableCRUD implements Operation<Table> {

    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    Logger L = Logger.getInstance();
    Scanner in = new Scanner(System.in);

    public static final String TABLE_CREATE = "CREATE TABLE `EXAMPLE`.`table` (\n" +
            "  `tableNum` INTEGER NOT NULL,\n" +
            "  `capacity` INTEGER NOT NULL,\n" +
            "  PRIMARY KEY `tableNum`);";
    public static final String TABLE_INSERT = "INSERT INTO `EXAMPLE`.`table` (`tableNum`, `capacity`) VALUES (?, ?)";
    public static final String TABLE_SELECT = "SELECT * FROM `EXAMPLE`.`table` WHERE `tableNum` = ?";
    public static final String TABLE_UPDATE = "UPDATE `EXAMPLE`.`table`\n" +
            "SET `capacity` = ? WHERE `tableNum` = ?";
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
        int nTavolo, capacità;
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(TABLE_INSERT);

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Inserici numero tavolo: ");
            nTavolo = in.nextInt();
            System.out.println("Inserici numero posti tavolo: ");
            capacità = in.nextInt();

            ps.setInt(1, nTavolo );
            ps.setInt(2, capacità );

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

       close();
    }

    @Override
    public void update(Table object) {
        int nTavolo, capacità;
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(TABLE_UPDATE);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        try {
        System.out.println("Inserici numero tavolo da modificare: ");
        nTavolo = in.nextInt();
        System.out.println("Inserici nuovo numero posti del tavolo: ");
        capacità = in.nextInt();

        ps.setInt(1, capacità );
        ps.setInt(2, nTavolo );
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
        close();
    }

    @Override
    public void delete(Table object) {

    }

    @Override
    public List<Table> select() {
        List<Table> tavoli = new ArrayList<>();
        int nTavolo;

        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(TABLE_SELECT);


        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Inserici numero tavolo: ");
            nTavolo = in.nextInt();
            ps.setInt(1, nTavolo );

            rs = ps.executeQuery();
            tavoli = List.of(
                    Table.builder()
                            .tableNum(rs.getInt("tableNum"))
                            .capacity(rs.getInt("capacity"))
                            .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return tavoli;

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
