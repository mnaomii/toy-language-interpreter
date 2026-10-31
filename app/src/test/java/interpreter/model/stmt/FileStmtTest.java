package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {
    @TempDir
    Path dir;               // a new empty folder for every test, deleted afterwards

    private PrgState state;
    private StringValue fileName;   // "<dir>/numbers.in"
    private ValueExp file;          // the same name as an expression

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "15\n50\n");
        fileName = new StringValue(f.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    // close what a test left open, otherwise Windows cannot delete the temp folder
    @AfterEach
    void closeOpenFiles() throws Exception {
        for (BufferedReader reader : state.getFileTable().toMap().values())
            reader.close();
    }

    // ---- openRFile (S8) ----

    // openRFile puts the file in the FileTable
    @Test
    void openAddsTheFileToTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined(fileName));
    }

    // openRFile(1) gives a StatementException
    @Test
    void openNonStringThrows() {
        IStmt stmt = new OpenRFileStmt(new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    // opening a file that is already open gives a StatementException
    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
    }

    // opening a file that does not exist gives a StatementException
    @Test
    void openMissingFileThrows() {
        ValueExp missing = new ValueExp(new StringValue(dir.resolve("missing.in").toString()));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(missing).execute(state));
    }

    // ---- readFile (S9) ----

    // readFile gives the numbers in order, then 0 at the end of the file
    @Test
    void readGivesTheNumbersInOrderThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    // reading into an undeclared variable gives a StatementException
    @Test
    void readIntoUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "y").execute(state));
    }

    // reading into a string variable gives a StatementException
    @Test
    void readIntoNonIntVariableThrows() throws MyException {
        new VarDeclStmt("s", new StringType()).execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "s").execute(state));
    }

    // reading from a file that was not opened gives a StatementException
    @Test
    void readFromFileNotOpenThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

    // a line that is not a number gives a StatementException
    @Test
    void readNonIntegerLineThrows() throws Exception {
        Path bad = dir.resolve("bad.in");
        Files.writeString(bad, "abc\n");
        ValueExp badFile = new ValueExp(new StringValue(bad.toString()));
        new OpenRFileStmt(badFile).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(badFile, "x").execute(state));
    }

    // readFile(1, x) gives a StatementException
    @Test
    void readNonStringThrows() {
        IStmt stmt = new ReadFileStmt(new ValueExp(new IntValue(1)), "x");
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    // a Java I/O error while reading becomes a StatementException
    @Test
    void readIoErrorThrows() throws Exception {
        new OpenRFileStmt(file).execute(state);
        state.getFileTable().lookup(fileName).close();   // closed behind the interpreter's back
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

    // ---- closeRFile (S10) ----

    // closeRFile takes the file out of the FileTable
    @Test
    void closeRemovesTheFileFromTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    // closing a file that is not open gives a StatementException
    @Test
    void closingAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
    }

    // closeRFile(1) gives a StatementException
    @Test
    void closeNonStringThrows() {
        IStmt stmt = new CloseRFileStmt(new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    // ---- toString ----

    // the three statements print as in the program text
    @Test
    void printInProgramForm() {
        VarExp varf = new VarExp("varf");
        assertEquals("openRFile(varf)", new OpenRFileStmt(varf).toString());
        assertEquals("readFile(varf, varc)", new ReadFileStmt(varf, "varc").toString());
        assertEquals("closeRFile(varf)", new CloseRFileStmt(varf).toString());
    }
}
