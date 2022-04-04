package restaurant;

import utils.ConnectDB;
import utils.ReadProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Tester {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
/**
        Table t1 = new Table(1,3);
        Table t2 = new Table(2,2);
        Table t3 = new Table(3,5);
        Table t4 = new Table(4,4);
        TableCRUD tableCRUD = new TableCRUD();
        tableCRUD.createT();
        tableCRUD.insert(t1);
        tableCRUD.insert(t2);
        tableCRUD.insert(t3);
        tableCRUD.insert(t4);

        Reservation r1 = new Reservation("rossi", "2022-01-22", 5, "3294356675");
        Reservation r2 = new Reservation("bianchi", "2022-03-23", 2, "3913431105");
        Reservation r3 = new Reservation("neri", "2022-01-22", 3, "3325489906");

        ReservationCRUD reservationCRUD = new ReservationCRUD();
        reservationCRUD.createT();
        reservationCRUD.insert(r1);
        reservationCRUD.insert(r2);
        reservationCRUD.insert(r3);
*/
        printFile();
    }

    public static void printFile() {
        String query = "SELECT * FROM `EXAMPLE`.`reservation`";
        Connection conn = null;
        Statement s;
        ResultSet rs;
        PrintWriter outputStream;

        String lastname, date, peopleNum, phone;
        int tableNum;

        try {
            outputStream = new PrintWriter("prenotazioni.txt");
            outputStream.println(String.format("LASTNAME", "DATE", "PEOPLE NUMBER", "PHONE NUMBER"));

            conn = ConnectDB.connect();
            s = conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                lastname = rs.getString(1);
                date = rs.getString(2);
                peopleNum = rs.getString(3);
                phone = rs.getString(4);

                outputStream.println(String.format( lastname, date, peopleNum, phone));
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
