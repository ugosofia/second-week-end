package restaurant;

public interface Operation<K> {

    public void createT();
    public void insert();
    public void update(K object);
    public void delete(K object);
    public void select();
    public void close();

}
