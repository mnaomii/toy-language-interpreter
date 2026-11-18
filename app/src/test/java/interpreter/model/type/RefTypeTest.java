package interpreter.model.type;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    // the default of Ref int is the null reference (0, int)
    @Test
    void defaultValueIsNullReference() {
        assertEquals(new RefValue(0, new IntType()), new RefType(new IntType()).defaultValue());
    }

    // the default of Ref Ref int keeps the inner type: (0, Ref(int))
    @Test
    void defaultValueOfNestedRefKeepsInnerType() {
        var refRefInt = new RefType(new RefType(new IntType()));
        assertEquals(new RefValue(0, new RefType(new IntType())), refRefInt.defaultValue());
    }

    // Ref(Ref(int)) equals Ref(Ref(int)): records compare the inner types recursively
    @Test
    void nestedRefTypesAreEqual() {
        var a = new RefType(new RefType(new IntType()));
        var b = new RefType(new RefType(new IntType()));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // Ref(Ref(int)) is not Ref(int)
    @Test
    void differentDepthIsNotEqual() {
        assertNotEquals(new RefType(new RefType(new IntType())), new RefType(new IntType()));
    }

    // Ref(int) is not Ref(bool)
    @Test
    void differentInnerTypeIsNotEqual() {
        assertNotEquals(new RefType(new IntType()), new RefType(new BoolType()));
    }

    // a nested reference type prints as Ref(Ref(int))
    @Test
    void printsNested() {
        assertEquals("Ref(Ref(int))", new RefType(new RefType(new IntType())).toString());
    }
}
