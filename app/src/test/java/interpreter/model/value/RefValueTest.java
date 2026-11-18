package interpreter.model.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefValueTest {

    // (1, int) has type Ref(int)
    @Test
    void typeIsRefOfLocationType() {
        assertEquals(new RefType(new IntType()), new RefValue(1, new IntType()).getType());
    }

    // (2, Ref(int)) has type Ref(Ref(int))
    @Test
    void nestedTypeIsRefOfRef() {
        var value = new RefValue(2, new RefType(new IntType()));
        assertEquals(new RefType(new RefType(new IntType())), value.getType());
    }

    // same address and same location type means equal values
    @Test
    void sameAddressAndTypeIsEqual() {
        assertEquals(new RefValue(1, new IntType()), new RefValue(1, new IntType()));
    }

    // a different address means a different reference
    @Test
    void differentAddressIsNotEqual() {
        assertNotEquals(new RefValue(1, new IntType()), new RefValue(2, new IntType()));
    }

    // a reference prints as (address, locationType)
    @Test
    void printsAddressAndLocationType() {
        assertEquals("(2, Ref(int))", new RefValue(2, new RefType(new IntType())).toString());
    }
}
