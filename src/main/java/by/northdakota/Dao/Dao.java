package by.northdakota.Dao;

import by.northdakota.Entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface Dao<K,V> {
    Optional<V> findById(K id);
    List<V> findAll();
    V save(V ticket);
    boolean update(V ticket);
    boolean delete(K id);

}
