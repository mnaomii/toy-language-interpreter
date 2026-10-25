package interpreter.model.adt;

import interpreter.exceptions.ADTException;

// Out: values kept in the order they were added
public interface IList<T> {
    void add(T v);
    T get(int index) throws ADTException;   // index out of bounds -> ADTException
    int size();
}
