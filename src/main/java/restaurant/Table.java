package restaurant;

import lombok.*;

@Data @Builder @AllArgsConstructor
public class Table {

    @Getter @Setter
    private int tableNum;
    private int capacity;



}


