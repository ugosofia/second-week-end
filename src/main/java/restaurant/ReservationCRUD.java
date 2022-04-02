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

    public static final String RESERVATION_INSERT = "INSERT INTO reservation (lastname, date , nPeople, tNumber) VALUES (?, ?, ?, ?)";

    public static final String RESERVATION_SELECT = "SELECT * FROM reservation WHERE lastname = ?";
    public static final String RESERVATION_UPDATE = "";
    public static final String RESERVATION_DELETE = "";
    private String cognome;


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
            ps.setDate(2, reservation.getDate());
            ps.setInt(3,reservation.getNPeople());
            ps.setString(4, reservation.getTNumber() );

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();

        if(result != 0)
            return true;
        return false;
    }

    @Override
    public void update(Reservation object) {

    }

    @Override
    public void delete(Reservation object) {

    }

    @Override
    public List<Reservation> select() {
        List<Reservation> prenotazioni = new ArrayList<>();
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(RESERVATION_SELECT);


        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Cognome prenotazione: ");
            cognome = in.next();
            ps.setString(1, cognome );

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
