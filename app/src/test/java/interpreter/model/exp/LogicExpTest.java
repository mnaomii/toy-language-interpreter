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

class LogicExpTest {
    private IDictionary<String, IValue> symTable;
    private IExp trueExp;
    private IExp falseExp;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        trueExp = new ValueExp(new BoolValue(true));
        falseExp = new ValueExp(new BoolValue(false));
    }

    // true and true = true
    @Test
    void andOfTrueAndTrueIsTrue() throws MyException {
        IExp e = new LogicExp(LogicOp.AND, trueExp, trueExp);
        assertEquals(new BoolValue(true), e.eval(symTable));
    }

    // true and false = false
    @Test
    void andOfTrueAndFalseIsFalse() throws MyException {
        IExp e = new LogicExp(LogicOp.AND, trueExp, falseExp);
        assertEquals(new BoolValue(false), e.eval(symTable));
    }

    // true or false = true
    @Test
    void orOfTrueAndFalseIsTrue() throws MyException {
        IExp e = new LogicExp(LogicOp.OR, trueExp, falseExp);
        assertEquals(new BoolValue(true), e.eval(symTable));
    }

    // false or false = false
    @Test
    void orOfFalseAndFalseIsFalse() throws MyException {
        IExp e = new LogicExp(LogicOp.OR, falseExp, falseExp);
        assertEquals(new BoolValue(false), e.eval(symTable));
    }

    // 1 and true gives an ExpressionException
    @Test
    void nonBooleanFirstOperandThrows() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), trueExp);
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    // true or 1 gives an ExpressionException
    @Test
    void nonBooleanSecondOperandThrows() {
        IExp e = new LogicExp(LogicOp.OR, trueExp, new ValueExp(new IntValue(1)));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}
