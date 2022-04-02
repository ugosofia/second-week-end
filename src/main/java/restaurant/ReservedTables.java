package restaurant;

import lombok.*;
import utils.ConnectDB;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ReservedTables {

    @Getter
    @Setter
    Date data;
    int tableNum;

    public static final String CREATE = "CREATE TABLE `EXAMPLE`.`reserved` (\n" +
            "  `date` DATE ,\n" +
            "  `tableNum` INTEGER L,\n" +
            "  PRIMARY KEY (`date`,`tableNum`));";
    public static final String SELECT = "SELECT tableNum FROM reserved WHERE data <> ?";
    public static final String INSERT = "INSERT INTO `EXAMPLE`.`reserved` (`date`, `tableNum`) VALUES (?, ?)";

    static Connection c;
    static PreparedStatement ps;
    static ResultSet rs;

    public void createT() {
        try {
            Connection c = ConnectDB.connect();
            PreparedStatement ps = c.prepareStatement(CREATE);
            ps.executeUpdate();
            c.close();
            ps.close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> select(Date d) {
        List<Integer> tavoli = new ArrayList<>();

        try {
            Connection c = ConnectDB.connect();
            PreparedStatement ps = c.prepareStatement(SELECT);

            ps.setDate(1, d);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tavoli.add(rs.getInt(1));
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tavoli;
    }


    public void insert(ReservedTables reservedTables) {
        try {
            c = ConnectDB.connect();
            ps = c.prepareStatement(INSERT);

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ps.setDate(1, reservedTables.getData());
            ps.setInt(2, reservedTables.getTableNum() );

            ps.executeUpdate();

            c.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}