package dao; 
import java.util.List; 
public interface CRUD<T> { 
  void insert(T obj) throws Exception; 
  void update(T obj) throws Exception; 
  void delete(int id) throws Exception; 
  List<T> getAll() throws Exception; 
}
