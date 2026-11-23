package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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

    // Ex6 prints the two references; both locations stay in the heap
    @Test
    void example6PrintsTheReferences() throws MyException {
        PrgState state = run(Examples.EX6);
        var v = new RefValue(1, new IntType());
        var a = new RefValue(2, new RefType(new IntType()));
        assertEquals(List.of(v, a), state.getOut().toList());
        assertEquals(Map.of("v", v, "a", a), state.getSymTable().toMap());
        assertEquals(Map.of(1, new IntValue(20), 2, v), state.getHeap().getContent());
    }

    // Ex7 reads through one and two references: prints 20 and 25
    @Test
    void example7PrintsTwentyAndTwentyFive() throws MyException {
        PrgState state = run(Examples.EX7);
        assertEquals(List.of(new IntValue(20), new IntValue(25)), state.getOut().toList());
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())),
                state.getHeap().getContent());
    }

    // Ex8 overwrites location 1 with 30: prints 20 and 35
    @Test
    void example8PrintsTwentyAndThirtyFive() throws MyException {
        PrgState state = run(Examples.EX8);
        assertEquals(List.of(new IntValue(20), new IntValue(35)), state.getOut().toList());
        assertEquals(Map.of("v", new RefValue(1, new IntType())), state.getSymTable().toMap());
        assertEquals(Map.of(1, new IntValue(30)), state.getHeap().getContent());
    }

    // Ex9 prints 20, because the safe collector keeps location 1 (reachable through a)
    @Test
    void example9PrintsTwenty() throws MyException {
        PrgState state = run(Examples.EX9);
        assertEquals(List.of(new IntValue(20)), state.getOut().toList());
    }

    // Ex10 counts down from 4, then prints the final 0
    @Test
    void example10CountsDownToZero() throws MyException {
        PrgState state = run(Examples.EX10);
        assertEquals(List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1), new IntValue(0)),
                state.getOut().toList());
    }

    // one more step after the program ended gives a StatementException
    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = run(new NopStmt());   // after this the stack is empty
        Controller controller = new Controller(new Repository(state, dir.resolve("log.txt").toString()), false);
        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }

    // Ref int v; new(v, 20); new(v, 30): after the second new, location 1 is garbage and gets removed
    @Test
    void garbageCollectorRunsAfterEveryStep() throws MyException {
        PrgState state = run(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new NewStmt("v", new ValueExp(new IntValue(30))))));
        assertEquals(Map.of(2, new IntValue(30)), state.getHeap().getContent());
    }

    // after allStep the log has the five sections in this order, even with display off
    @Test
    void logFileContainsTheFiveSectionsInOrder() throws Exception {
        run(Examples.EX1);

        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
            c = log.indexOf("Out:"), d = log.indexOf("FileTable:"), e = log.indexOf("Heap:");
        assertTrue(0 <= a && a < b && b < c && c < d && d < e);
    }
}
