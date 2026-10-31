package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    // shortcut: num(2) is the constant expression 2
    private IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    // shortcut: compare(LT, 1, 2) evaluates (1 < 2)
    private IValue compare(RelOp op, int a, int b) throws MyException {
        return new RelationalExp(op, num(a), num(b)).eval(symTable);
    }

    // 1 < 2 is true, 2 < 2 is false
    @Test
    void lessThan() throws MyException {
        assertEquals(new BoolValue(true), compare(RelOp.LT, 1, 2));
        assertEquals(new BoolValue(false), compare(RelOp.LT, 2, 2));
    }

    // 2 <= 2 is true, 3 <= 2 is false
    @Test
    void lessOrEqual() throws MyException {
        assertEquals(new BoolValue(true), compare(RelOp.LE, 2, 2));
        assertEquals(new BoolValue(false), compare(RelOp.LE, 3, 2));
    }

    // 2 == 2 is true, 2 == 3 is false
    @Test
    void equal() throws MyException {
        assertEquals(new BoolValue(true), compare(RelOp.EQ, 2, 2));
        assertEquals(new BoolValue(false), compare(RelOp.EQ, 2, 3));
    }

    // 2 != 3 is true, 2 != 2 is false
    @Test
    void notEqual() throws MyException {
        assertEquals(new BoolValue(true), compare(RelOp.NE, 2, 3));
        assertEquals(new BoolValue(false), compare(RelOp.NE, 2, 2));
    }

    // 3 > 2 is true, 2 > 2 is false
    @Test
    void greaterThan() throws MyException {
        assertEquals(new BoolValue(true), compare(RelOp.GT, 3, 2));
        assertEquals(new BoolValue(false), compare(RelOp.GT, 2, 2));
    }

    // 2 >= 2 is true, 1 >= 2 is false
    @Test
    void greaterOrEqual() throws MyException {
        assertEquals(new BoolValue(true), compare(RelOp.GE, 2, 2));
        assertEquals(new BoolValue(false), compare(RelOp.GE, 1, 2));
    }

    // true < 1 gives an ExpressionException
    @Test
    void nonIntegerFirstOperandThrows() {
        IExp e = new RelationalExp(RelOp.LT, new ValueExp(new BoolValue(true)), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    // 1 < true gives an ExpressionException
    @Test
    void nonIntegerSecondOperandThrows() {
        IExp e = new RelationalExp(RelOp.LT, num(1), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    // printed in infix form, as in the log and the menu
    @Test
    void printsInInfixForm() {
        assertEquals("(1 < 2)", new RelationalExp(RelOp.LT, num(1), num(2)).toString());
    }
}
