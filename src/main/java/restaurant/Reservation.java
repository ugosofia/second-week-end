package restaurant;

import lombok.*;
import java.sql.Date;

@Data @Builder @AllArgsConstructor
public class Reservation {

    @Getter @Setter
     private String lastname;
     private String date;
     private int nPeople;
     private String tNumber;


}
