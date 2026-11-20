package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {
    private IDictionary<String, IValue> symTable;
    private IHeap heap;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
    }

    // an int constant evaluates to itself
    @Test
    void returnsItsIntValue() throws MyException {
        assertEquals(new IntValue(7), new ValueExp(new IntValue(7)).eval(symTable, heap));
    }

    // a bool constant evaluates to itself
    @Test
    void returnsItsBoolValue() throws MyException {
        assertEquals(new BoolValue(true), new ValueExp(new BoolValue(true)).eval(symTable, heap));
    }
}
