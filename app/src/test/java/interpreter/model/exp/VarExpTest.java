package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    // a variable evaluates to its value in the SymTable
    @Test
    void definedVariableReturnsItsValue() throws MyException {
        symTable.put("a", new IntValue(2));
        IExp e = new VarExp("a");
        assertEquals(new IntValue(2), e.eval(symTable));
    }

    // a variable that is not in the SymTable gives an ExpressionException
    @Test
    void undefinedVariableThrows() {
        IExp e = new VarExp("a");
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}
