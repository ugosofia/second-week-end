package restaurant;

import lombok.*;

@Data @Builder
public class Table {

    @Getter @Setter
    private int tableNum;
    private int capacity;



}


