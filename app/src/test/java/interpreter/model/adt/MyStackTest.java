package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    // a new stack has nothing in it
    @Test
    void newStackIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    // last pushed comes out first
    @Test
    void popReturnsElementsInLifoOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    // pop with nothing left gives an ADTException
    @Test
    void popOnEmptyStackThrows() {
        assertThrows(ADTException.class, () -> stack.pop());
    }

    // toList gives the top of the stack first
    @Test
    void toListGivesTopFirst() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(List.of(3, 2, 1), stack.toList());
    }
}
