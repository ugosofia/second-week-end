package restaurant;

import lombok.*;
import java.sql.Date;

@Data @Builder @AllArgsConstructor
public class Reservation {

    @Getter @Setter
     private String lastname;
     private Date date;
     private int nPeople;
     private String tNumber;


}
