package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private  List<PrgState> states = new ArrayList<>();

    // stores the given program state in the list
    public Repository(PrgState state) {
        states.add(state);
    }

    @Override
    public PrgState getCrtPrg() {
        return states.getLast();
    }
}
