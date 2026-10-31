package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        if(map.containsKey(key)){
            return true;
        }
        return false;
    }

    @Override
    public V lookup(K key) throws ADTException {
        if(!isDefined(key)){ throw new ADTException("Key not found"); }
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if(!isDefined(key)){ throw new ADTException("Key not found"); }
        map.put(key, value);
    }

    @Override
    public String toString(){
        String out = "";
        for( K key : map.keySet()){
            V value = map.get(key);
            if(value != null){
                out += key.toString() + "=" + value.toString() + "\n";
            }
        }
        return out;
    }

    @Override
    public void remove(K key) throws ADTException{
        if(!isDefined(key)){ throw new ADTException("Key not found"); }
        map.remove(key);
    }

    @Override
    public Map<K,V> toMap(){
        return Map.copyOf(map);
    }
}
