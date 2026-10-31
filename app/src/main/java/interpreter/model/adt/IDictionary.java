package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;

// SymTable: maps a key (variable name) to a value
public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;                // missing key -> ADTException
    void put(K key, V value);
    void update(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;
    Map<K,V> toMap();
}
