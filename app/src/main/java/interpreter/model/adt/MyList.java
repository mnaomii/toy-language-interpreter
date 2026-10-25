package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;


// used for the output of the program

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T get(int index) throws ADTException {
        if(index < 0 || index >=  list.size()) throw new ADTException("Index out of bounds");
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        String out = "";
        for (T elem : list) {
            out  += elem.toString() + " ";
        }
        return out;
    }
}
