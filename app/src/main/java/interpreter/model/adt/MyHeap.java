package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class MyHeap implements IHeap {
    private Map<Integer, IValue> heap = new TreeMap<>();
    private int nextFree = 1;

    @Override
    public int allocate(IValue value) {
        heap.put(nextFree++,value);
        return nextFree-1;
    }

    @Override
    public IValue get(int address) throws ADTException {
        if(heap.containsKey(address)){
            return heap.get(address);
        } else throw new ADTException(String.format("Address %d is out of heap", address));
    }

    @Override
    public void update(int address, IValue value) throws ADTException {
        if(isDefined(address)){
            heap.put(address,value);
        } else throw new ADTException(String.format("Address %d is not defined",address));
    }

    @Override
    public boolean isDefined(int address) {
        if(heap.containsKey(address)) {
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return Collections.unmodifiableMap( new TreeMap<>(heap));
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        heap = new TreeMap<>(content);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        heap.forEach((key,value) -> s.append(key + " --> " + value + "\n"));
        return s.toString();
    }
}
