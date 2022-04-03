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

    public static final String RESERVATION_CREATE = "CREATE TABLE `EXAMPLE`.`reservation` (\n" +
            "  `lastname` VARCHAR(30) NOT NULL,\n" +
            "  `date` DATE NOT NULL,\n" +
            "  `nPeople` INT NOT NULL,\n" +
            "  `tNumber` VARCHAR(10) NOT NULL,\n" +
            "  PRIMARY KEY (`lastname`, `date`));";

    public static final String RESERVATION_INSERT = "INSERT INTO `EXAMPLE`.`reservation` (`lastname`, `date` , `nPeople`, `tNumber`) VALUES (?, ?, ?, ?)";

    public static final String RESERVATION_SELECT = "SELECT * FROM `EXAMPLE`.`reservation` WHERE `lastname` = ? AND `date` = ?";
    public static final String RESERVATION_UPDATE = "UPDATE `EXAMPLE`.`reservation` SET `nPeople` = ? , `tNumber` = ? WHERE `lastname` = ? AND `date` = ? ;";
    public static final String RESERVATION_DELETE = "DELETE FROM `EXAMPLE`.`reservation` WHERE (`lastname` = ?) and (`date` = ?);";
    private String cognome, data;
    private int numeroPersone, tNum;

    public boolean richiestaPrenotazione(String cognome, Date data, int
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

        if(!(richiestaPrenotazione(reservation.getLastname(),
                reservation.getDate(),reservation.getNPeople(),
                reservation.getTNumber()))) {
            L.err("ERRORE, non è possibile eseguire l'inserimento");
            return false;
        }

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
            ps.setDate(2, reservation.getDate());
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
           numeroPersone = in.nextInt();
           System.out.print("\nInserisci cellulare: ");
           tNum = in.nextInt();

           ps.setInt(1, numeroPersone);
           ps.setInt(2, tNum);
           ps.setString(3, cognome);
           ps.setDate(4, Date.valueOf(data));

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
            ps.setDate(2, object.getDate());
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

            ps.setString(1, r.getLastname() );
            ps.setDate(2, r.getDate() );

            rs = ps.executeQuery();
            prenotazioni = List.of(
                    Reservation.builder()
                            .lastname(rs.getString("lastname"))
                            .date(rs.getDate("date"))
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
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
