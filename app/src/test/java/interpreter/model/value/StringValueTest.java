package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    // two string values with the same text are equal
    @Test
    void sameTextIsEqual() {
        assertEquals(new StringValue("test.in"), new StringValue("test.in"));
    }

    // equal string values have the same hashCode (needed for the FileTable keys)
    @Test
    void sameTextHasSameHashCode() {
        assertEquals(new StringValue("test.in").hashCode(), new StringValue("test.in").hashCode());
    }

    // different text means different values
    @Test
    void differentTextIsNotEqual() {
        assertNotEquals(new StringValue("a.in"), new StringValue("b.in"));
    }

    // the type of a string value is string
    @Test
    void typeIsString() {
        assertEquals(new StringType(), new StringValue("abc").getType());
    }

    // a string value is printed between quotes
    @Test
    void printsWithQuotes() {
        assertEquals("\"test.in\"", new StringValue("test.in").toString());
    }
}
