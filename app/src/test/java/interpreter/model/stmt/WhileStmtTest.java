package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {
    private final IStmt body = new PrintStmt(new ValueExp(new IntValue(1)));

    // int v; v = start; (while (v > 0) (print(v); v = v - 1))
    private static IStmt countdown(int start) {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(start))),
                new WhileStmt(new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp(ArithOp.SUB,
                                        new VarExp("v"), new ValueExp(new IntValue(1))))))));
    }

    // executes statements until the stack is empty and returns what was printed
    private static List<IValue> runToEnd(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        while (!state.getExeStack().isEmpty())
            state.getExeStack().pop().execute(state);
        return state.getOut().toList();
    }

    // true: the body goes on top, the loop right under it
    @Test
    void trueConditionPushesTheBodyAboveTheLoop() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        IStmt loop = new WhileStmt(new ValueExp(new BoolValue(true)), body);
        loop.execute(state);
        assertEquals(body, state.getExeStack().pop());
        assertEquals(loop, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    // false: nothing is pushed, the loop ends
    @Test
    void falseConditionPushesNothing() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        new WhileStmt(new ValueExp(new BoolValue(false)), body).execute(state);
        assertTrue(state.getExeStack().isEmpty());
    }

    // while (1) ... gives a StatementException
    @Test
    void nonBooleanConditionThrows() {
        PrgState state = PrgState.of(new NopStmt());
        IStmt loop = new WhileStmt(new ValueExp(new IntValue(1)), body);
        assertThrows(StatementException.class, () -> loop.execute(state));
    }

    // starting at 0, the body never runs
    @Test
    void loopRunsZeroTimes() throws MyException {
        assertEquals(List.of(), runToEnd(countdown(0)));
    }

    // starting at 1, the body runs once
    @Test
    void loopRunsOnce() throws MyException {
        assertEquals(List.of(new IntValue(1)), runToEnd(countdown(1)));
    }

    // starting at 3, the body runs three times
    @Test
    void loopRunsSeveralTimes() throws MyException {
        assertEquals(List.of(new IntValue(3), new IntValue(2), new IntValue(1)), runToEnd(countdown(3)));
    }
}
