package restaurant;

import lombok.*;

import java.beans.ConstructorProperties;

@Data @Builder @AllArgsConstructor
public class Table {

    @Getter @Setter
    private int tableNum;
    private int capacity;



}


