package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;   // a new empty folder for every test, deleted afterwards

    // runs a whole program (display off, log in the temp folder) and returns the final state
    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(state, dir.resolve("log.txt").toString()), false);
        controller.allStep();
        return state;
    }

    // Ex1 runs to the end and prints 2
    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    // Ex2 runs to the end and prints 18
    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    // Ex3 runs to the end and prints 2
    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    // Ex4 on a file with 15 and 50 prints both numbers and closes the file at the end
    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState state = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(50), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    // Ex4 on an empty file prints 0 twice, because both reads hit the end of the file
    @Test
    void fileExampleOnEmptyFilePrintsZeroTwice() throws Exception {
        Path in = dir.resolve("empty.in");
        Files.writeString(in, "");
        PrgState state = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    // Ex5: 5 < 7, so only the then branch runs and prints 5
    @Test
    void example5PrintsFive() throws MyException {
        PrgState state = run(Examples.EX5);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    // one more step after the program ended gives a StatementException
    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = run(new NopStmt());   // after this the stack is empty
        Controller controller = new Controller(new Repository(state, dir.resolve("log.txt").toString()), false);
        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }

    // after allStep the log has the four sections in this order, even with display off
    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);

        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
            c = log.indexOf("Out:"), d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }
}
