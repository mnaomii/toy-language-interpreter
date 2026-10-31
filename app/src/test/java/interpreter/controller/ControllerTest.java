package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    // runs a whole program (display off) and returns the final state
    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(state), false);
        controller.allStep();
        return state;
    }

    // Ex1 runs to the end and prints 2
    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    // Ex2 runs to the end and prints 18
    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    // Ex3 runs to the end and prints 2
    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    // one more step after the program ended gives a StatementException
    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = run(new NopStmt());   // after this the stack is empty
        Controller controller = new Controller(new Repository(state), false);
        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }
}
