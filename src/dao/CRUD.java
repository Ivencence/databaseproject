package dao;

import java.util.List;

public interface CRUD<T> {
    void insert(T obj);
    void update(T obj);
    void delete(int id);
    List<T> getAll();
}