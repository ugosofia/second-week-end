package restaurant;

import java.util.List;

public interface Operation<K> {

    public void createT();
    public boolean insert(K object);
    public void update();
    public boolean delete(K object);
    public List<K> select(K object);
    public void close();

}
