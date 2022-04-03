package restaurant;

import utils.ConnectDB;
import utils.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableCRUD implements Operation<Table> {

    static Table t;
    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    //Logger L = Logger.getInstance();
    Scanner in = new Scanner(System.in);

    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS `EXAMPLE`.`table` (\n" +
            "  `tableNum` INTEGER PRIMARY KEY ,\n" +
            "  `capacity` INTEGER NOT NULL);";
    public static final String TABLE_INSERT = "INSERT INTO `EXAMPLE`.`table` (`tableNum`, `capacity`) VALUES (?, ?)";
    public static final String TABLE_SELECT = "SELECT * FROM `EXAMPLE`.`table` WHERE `capacity` >= ?";
    public static final String TABLE_SELECT2 = "SELECT * FROM `EXAMPLE`.`table` WHERE = `tableNum` = ?";
    public static final String TABLE_UPDATE = "UPDATE `EXAMPLE`.`table`\n" +
            "SET `capacity` = ? WHERE `tableNum` = ?";

    private int nTavolo, capacità;


    public int disponibilitàTavolo(String data, int numeroPersone) {
     //   con il numero di persone, vado a prendere i numeri di tavoli con quella capienza
        t = new Table(0, numeroPersone);
        List<Table> tavoli = select(t);
        if(tavoli.isEmpty())
            return 0;

        // trovo i tavoli prenotati nella data passata come paramentro
        ReservedTables reservedTables = new ReservedTables();
        List<Integer> nTavoliPrenotati = reservedTables.select(data);
        if(nTavoliPrenotati.isEmpty())
            //non ci sono tavoli prenotati in quella data, prendo il primo tavolo della prima lista
            return tavoli.get(0).getTableNum();

        // confronto le due liste di tavoli, e trovo il tavolo diponibile
        for (int i = 0; i < tavoli.size(); i++) {
            for (int j = 0; j < nTavoliPrenotati.size(); j++ ) {
                // se il tavolo è già prenotato lo elimino dalla lista
                if(i == j)
                    tavoli.remove(i);
            }
        }
        // il/i tavolo/i rimasti sono quelli disponibili in quella data, prendo il primo
        if(tavoli.isEmpty())
            return 0;
        // inserisco il tavolo prenotato e restituisco il suo numero
        Table tavoloDisponibile = tavoli.get(0);
        ReservedTables rt = new ReservedTables(data, tavoloDisponibile.getTableNum());
        reservedTables.insert(rt);

        return tavoloDisponibile.getTableNum();

    }

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
    public boolean insert(Table table) {
        int result = 0;
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(TABLE_INSERT);

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            ps.setInt(1, table.getTableNum() );
            ps.setInt(2, table.getCapacity());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();

        return result != 0;


    }

    @Override
    public void update() {
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
    public boolean delete(Table object) {
        if (object == null) {
            throw new IllegalArgumentException(
                    "Il tavolo passato come parametro è null");

        }
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(TABLE_SELECT2);

            ps.setInt(1, object.getTableNum());
            rs = ps.executeQuery() ;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        try {
        if (!rs.next()) {
            close();
            return false;
        }
        rs.deleteRow();
        close();

    } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Table> select(Table t) {
        List<Table> tavoli = new ArrayList<>();
        int nTavolo;

        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(TABLE_SELECT);


        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            ps.setInt(1, t.getCapacity());

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
            if(rs != null)
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
