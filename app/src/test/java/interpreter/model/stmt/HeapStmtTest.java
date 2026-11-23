package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
    }

    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new NewStmt("v", exp).execute(state));
    }

    @Test
    void newWithAnUndeclaredVariableThrows() {
        var exp = new ValueExp(new IntValue(20));
        assertThrows(StatementException.class, () -> new NewStmt("x", exp).execute(state));
    }

    @Test
    void newWithANonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("n", new IntType()).execute(state);
        var exp = new ValueExp(new IntValue(20));
        assertThrows(StatementException.class, () -> new NewStmt("n", exp).execute(state));
    }

    @Test
    void writeHeapOverwritesTheValueAndKeepsTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    void writeHeapWithAnUndeclaredVariableThrows() {
        var exp = new ValueExp(new IntValue(30));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("x", exp).execute(state));
    }

    @Test
    void writeHeapWithANonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("n", new IntType()).execute(state);
        var exp = new ValueExp(new IntValue(30));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("n", exp).execute(state));
    }

    @Test
    void writeHeapToAnAddressNotInTheHeapThrows() {
        var exp = new ValueExp(new IntValue(30));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("v", exp).execute(state));
    }

    @Test
    void writeHeapWithAValueOfTheWrongTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("v", exp).execute(state));
    }
}
