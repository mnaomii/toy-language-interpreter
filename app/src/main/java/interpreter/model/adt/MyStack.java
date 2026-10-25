package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T v) {
        stack.addFirst(v);
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty()) {throw new  ADTException("Stack is empty");}
        return stack.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return  stack.isEmpty();
    }

    @Override
    public String toString() {
        String out = "";
        for (T elem : stack) {
            out += elem.toString() + "; ";
        }
        return out;
    }
}
