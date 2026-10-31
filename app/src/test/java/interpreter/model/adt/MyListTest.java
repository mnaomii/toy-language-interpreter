package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    // a new list is empty
    @Test
    void newListHasSizeZero() {
        assertEquals(0, list.size());
    }

    // each add makes the list one longer
    @Test
    void addIncreasesTheSize() {
        list.add(10);
        list.add(20);
        assertEquals(2, list.size());
    }

    // get returns the elements in the order they were added
    @Test
    void getReturnsElementsInTheOrderTheyWereAdded() throws ADTException {
        list.add(10);
        list.add(20);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    // reading past the end gives an ADTException
    @Test
    void getOutOfBoundsThrows() {
        list.add(10);
        assertThrows(ADTException.class, () -> list.get(1));
    }

    // toList keeps the order the elements were added in
    @Test
    void toListKeepsInsertionOrder() {
        list.add(10);
        list.add(20);
        list.add(30);
        assertEquals(List.of(10, 20, 30), list.toList());
    }
}
