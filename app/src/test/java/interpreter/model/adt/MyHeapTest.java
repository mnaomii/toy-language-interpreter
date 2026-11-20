package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private IHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    // addresses are handed out 1, 2, 3 ... (0 is the null address)
    @Test
    void allocateReturnsConsecutiveAddressesFromOne() {
        assertEquals(1, heap.allocate(new IntValue(20)));
        assertEquals(2, heap.allocate(new IntValue(30)));
        assertEquals(3, heap.allocate(new IntValue(40)));
    }

    // the value stored by allocate is the one get gives back
    @Test
    void getReturnsTheAllocatedValue() throws ADTException {
        int address = heap.allocate(new IntValue(20));
        assertEquals(new IntValue(20), heap.get(address));
    }

    // after allocate, the address counts as defined
    @Test
    void allocateMakesTheAddressDefined() {
        int address = heap.allocate(new IntValue(20));
        assertTrue(heap.isDefined(address));
    }

    // the null address is never part of the heap
    @Test
    void addressZeroIsNeverDefined() {
        heap.allocate(new IntValue(20));
        assertFalse(heap.isDefined(0));
    }

    // get of an address that was never allocated gives an ADTException
    @Test
    void getOfMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    // update overwrites the value at an existing address
    @Test
    void updateChangesTheValue() throws ADTException {
        int address = heap.allocate(new IntValue(20));
        heap.update(address, new IntValue(30));
        assertEquals(new IntValue(30), heap.get(address));
    }

    // update of an address that was never allocated gives an ADTException
    @Test
    void updateOfMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(30)));
    }

    // getContent holds every location, references included
    @Test
    void getContentContainsTheLocations() {
        heap.allocate(new IntValue(20));
        heap.allocate(new RefValue(1, new IntType()));
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())), heap.getContent());
    }

    // getContent is a read-only copy: changing it is not allowed
    @Test
    void getContentIsReadOnly() {
        heap.allocate(new IntValue(20));
        assertThrows(UnsupportedOperationException.class, () -> heap.getContent().put(2, new IntValue(30)));
    }

    // setContent replaces the old locations with the new ones
    @Test
    void setContentReplacesTheContent() throws ADTException {
        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));
        heap.setContent(Map.of(2, new IntValue(30)));
        assertFalse(heap.isDefined(1));
        assertEquals(new IntValue(30), heap.get(2));
    }

    // after a collection the next address is still a fresh one, never a reused one
    @Test
    void setContentKeepsTheNextFreeAddress() {
        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));
        heap.setContent(Map.of(2, new IntValue(30)));
        assertEquals(3, heap.allocate(new IntValue(40)));
    }

    // one "address --> value" line per location, sorted by address
    @Test
    void printsOneLocationPerLineInAddressOrder() {
        heap.setContent(Map.of(3, new IntValue(40), 1, new IntValue(20), 2, new IntValue(30)));
        assertEquals("1 --> 20\n2 --> 30\n3 --> 40\n", heap.toString());
    }
}
