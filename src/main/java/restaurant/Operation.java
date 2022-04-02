package restaurant;

import java.util.List;

public interface Operation<K> {

    public void createT();
    public void insert();
    public void update(K object);
    public void delete(K object);
    public List<K> select();
    public void close();

}
