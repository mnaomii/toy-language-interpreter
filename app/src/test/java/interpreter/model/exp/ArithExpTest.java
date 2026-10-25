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

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    // shortcut: num(2) is the constant expression 2
    private IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    // 2 + 3 = 5
    @Test
    void additionReturnsTheSum() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, num(2), num(3));
        assertEquals(new IntValue(5), e.eval(symTable));
    }

    // 5 - 3 = 2
    @Test
    void subtractionReturnsTheDifference() throws MyException {
        IExp e = new ArithExp(ArithOp.SUB, num(5), num(3));
        assertEquals(new IntValue(2), e.eval(symTable));
    }

    // 2 * 3 = 6
    @Test
    void multiplicationReturnsTheProduct() throws MyException {
        IExp e = new ArithExp(ArithOp.MUL, num(2), num(3));
        assertEquals(new IntValue(6), e.eval(symTable));
    }

    // 8 / 2 = 4
    @Test
    void divisionReturnsTheQuotient() throws MyException {
        IExp e = new ArithExp(ArithOp.DIV, num(8), num(2));
        assertEquals(new IntValue(4), e.eval(symTable));
    }

    // 2 + 3 * 5 = 17, the shape of the tree decides what runs first
    @Test
    void respectsOperatorPrecedenceEncodedInTheTree() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, num(2), new ArithExp(ArithOp.MUL, num(3), num(5)));
        assertEquals(new IntValue(17), e.eval(symTable));
    }

    // 1 / 0 gives an ExpressionException
    @Test
    void divisionByZeroThrows() {
        IExp e = new ArithExp(ArithOp.DIV, num(1), num(0));
        ExpressionException ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }

    // true + 1 gives an ExpressionException
    @Test
    void nonIntegerFirstOperandThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    // 1 + true gives an ExpressionException
    @Test
    void nonIntegerSecondOperandThrows() {
        IExp e = new ArithExp(ArithOp.ADD, num(1), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}
