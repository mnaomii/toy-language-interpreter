package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.List;

// ExeStack: last in, first out
public interface IStack<T> {
    void push(T v);
    T pop() throws ADTException;   // empty stack -> ADTException
    boolean isEmpty();
    List<T> toList();
}
