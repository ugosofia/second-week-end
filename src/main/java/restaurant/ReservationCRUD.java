package restaurant;

import utils.ConnectDB;
import utils.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationCRUD implements Operation<Reservation>{

    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    Logger L = Logger.getInstance();
    Scanner in = new Scanner(System.in);

    public static final String RESERVATION_CREATE = "CREATE TABLE IF NOT EXISTS `EXAMPLE`.`reservation` (\n" +
            "  `lastname` VARCHAR(30) NOT NULL,\n" +
            "  `date` VARCHAR(30) NOT NULL,\n" +
            "  `nPeople` INT NOT NULL,\n" +
            "  `tNumber` VARCHAR(10) NOT NULL,\n" +
            "  PRIMARY KEY (`lastname`, `date`));";

    public static final String RESERVATION_INSERT = "INSERT INTO `EXAMPLE`.`reservation` (`lastname`, `date` , `nPeople`, `tNumber`) VALUES (?, ?, ?, ?)";

    public static final String RESERVATION_SELECT = "SELECT * FROM `EXAMPLE`.`reservation` WHERE `lastname` = ? AND `date` = ?";
    public static final String RESERVATION_UPDATE = "UPDATE `EXAMPLE`.`reservation` SET `nPeople` = ? , `tNumber` = ? WHERE `lastname` = ? AND `date` = ? ;";
    public static final String RESERVATION_DELETE = "DELETE FROM `EXAMPLE`.`reservation` WHERE (`lastname` = ?) and (`date` = ?);";
    private String cognome, data;

    public boolean richiestaPrenotazione(String cognome, String data, int
            numeroPersone, String cellulare) {

        TableCRUD t = new TableCRUD();

        int numeroTavoloDisponibile = t.disponibilitàTavolo(data, numeroPersone);
        if((numeroTavoloDisponibile == 0))
            return false;

        Reservation reservation = new Reservation(cognome,data,numeroPersone,cellulare);
        ReservedTables reservedTables = new ReservedTables(data, numeroTavoloDisponibile);

        ReservationCRUD r = new ReservationCRUD();
        r.insert(reservation);

        reservedTables.insert(reservedTables);
        return true;
    }

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
    public boolean insert(Reservation reservation) {

        String nTel, data;
        int persone, result = 0;

        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(RESERVATION_INSERT);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            ps.setString(1, reservation.getLastname());
            ps.setString(2, reservation.getDate());
            ps.setInt(3,reservation.getNPeople());
            ps.setString(4, reservation.getTNumber() );

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();

        if(result != 0) {
            L.info("Inserimento riuscito!");
            return true;
        }
        return false;
    }

    @Override
    public void update() {
       try {
           c = ConnectDB.connect();
           ps = c.prepareStatement(RESERVATION_UPDATE);
       } catch (SQLException | IOException | ClassNotFoundException e) {
           e.printStackTrace();
       }
        try {
           System.out.print("\nInserisci cognome: ");
           cognome = in.next();
           System.out.print("\nInserisci data: ");
           data = in.next();
           System.out.print("\nInserisci numero persone: ");
            int numeroPersone = in.nextInt();
           System.out.print("\nInserisci cellulare: ");
            int tNum = in.nextInt();

           ps.setInt(1, numeroPersone);
           ps.setInt(2, tNum);
           ps.setString(3, cognome);
           ps.setString(4, data);

           ps.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }
        close();
    }

    @Override
    public boolean delete(Reservation object) {
        if (object == null) {
            throw new IllegalArgumentException(
                    "La prenotazione passata come parametro è null");
        }
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(RESERVATION_DELETE);

            ps.setString(1, object.getLastname());
            ps.setString(2, object.getDate());
            rs = ps.executeQuery() ;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        try {
            if (!rs.next()) {
                close();
                return false;
            }
            close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Reservation> select(Reservation r) {
        List<Reservation> prenotazioni = new ArrayList<>();
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(RESERVATION_SELECT);


        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Inserisci il cognome: ");
            cognome = in.next();
            System.out.println("Inserisci la data: (yyyy-mm-dd) ");
            data = in.next();
            ps.setString(1, cognome);
            ps.setString(2, data );

            rs = ps.executeQuery();
            prenotazioni = List.of(
                    Reservation.builder()
                            .lastname(rs.getString("lastname"))
                            .date(rs.getString("date"))
                            .nPeople(rs.getInt("personID"))
                            .tNumber(rs.getString("tNumber"))
                            .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return prenotazioni;

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
