package restaurant;

import java.util.List;

public interface Operation<K> {

    public void createT();
    public boolean insert(K object);
    public void update(K object);
    public void delete(K object);
    public List<K> select(int object);
    public void close();

}
