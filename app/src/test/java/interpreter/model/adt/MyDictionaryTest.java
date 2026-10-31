package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    // what you put is what you get back
    @Test
    void putThenLookupReturnsTheValue() throws ADTException {
        dict.put("a", 1);
        assertEquals(1, dict.lookup("a"));
    }

    // after put, the key counts as defined
    @Test
    void putMakesTheKeyDefined() {
        dict.put("a", 1);
        assertTrue(dict.isDefined("a"));
    }

    // update overwrites the old value
    @Test
    void updateChangesTheValue() throws ADTException {
        dict.put("a", 1);
        dict.update("a", 2);
        assertEquals(2, dict.lookup("a"));
    }

    // a key that was never put is not defined
    @Test
    void missingKeyIsNotDefined() {
        assertFalse(dict.isDefined("a"));
    }

    // lookup of an unknown key gives an ADTException
    @Test
    void lookupOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("a"));
    }

    // after remove, the key is no longer defined
    @Test
    void removeDeletesTheKey() throws ADTException {
        dict.put("a", 1);
        dict.remove("a");
        assertFalse(dict.isDefined("a"));
    }

    // remove of an unknown key gives an ADTException
    @Test
    void removeOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.remove("a"));
    }

    // toMap holds every pair that was put
    @Test
    void toMapContainsThePairs() {
        dict.put("a", 1);
        dict.put("b", 2);
        assertEquals(Map.of("a", 1, "b", 2), dict.toMap());
    }

    // toMap is a read-only copy: changing it is not allowed
    @Test
    void toMapIsReadOnly() {
        dict.put("a", 1);
        assertThrows(UnsupportedOperationException.class, () -> dict.toMap().put("b", 2));
    }
}
