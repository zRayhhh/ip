package ayre;

import ayre.enums.Command;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void parseInput_nonCommand_exceptionThrown() {
        try {
            assertEquals(new ParsedInput(null, null), Parser.parseInput("hello"));
            fail();
        } catch (Exception e) {
            assertEquals("Input not recognized as a valid command", e.getMessage());
        }
        try {
            assertEquals(new ParsedInput(null, null), Parser.parseInput("bye123"));
            fail();
        } catch (Exception e) {
            assertEquals("Input not recognized as a valid command", e.getMessage());
        }
        try {
            assertEquals(new ParsedInput(null, null), Parser.parseInput("listy"));
            fail();
        } catch (Exception e) {
            assertEquals("Input not recognized as a valid command", e.getMessage());
        }
    }

    @Test
    public void tokenizeArgs_validCommandAndArguments_success() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        assertEquals(list, Parser.tokenizeArgs(Command.BYE, "bye"));
        assertEquals(list, Parser.tokenizeArgs(Command.LIST, "bye"));
        list.add("1");
        assertEquals(list, Parser.tokenizeArgs(Command.MARK, "mark 1"));
        assertEquals(list, Parser.tokenizeArgs(Command.UNMARK, "unmark 1"));
        assertEquals(list, Parser.tokenizeArgs(Command.DELETE, "delete 1"));
        list.clear();
        list.add("name");
        assertEquals(list, Parser.tokenizeArgs(Command.TODO, "todo name"));
        list.add("2003-01-01");
        assertEquals(list, Parser.tokenizeArgs(Command.DEADLINE, "deadline name /by 2003-01-01"));
        list.add("2003-01-01");
        assertEquals(list, Parser.tokenizeArgs(Command.EVENT, "event name /from 2003-01-01 /to 2003-01-01"));
    }

    @Test
    public void tokenizeArgs_extraArguments_exceptionThrown() {
        ArrayList<String> list = new ArrayList<>();
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.BYE, "bye bye"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.LIST, "list list"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.MARK, "mark 1 2"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.UNMARK, "unmark 1 2"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.DELETE, "delete 1 2"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
    }

    @Test
    public void tokenizeArgs_invalidUseOfTaskTimeFlags_exceptionThrown() {
        ArrayList<String> list = new ArrayList<>();
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.DEADLINE,
                    "deadline name /by 2003-01-01 /by 2003-01-01"));
            fail();
        } catch (Exception e) {
            assertEquals("Placeholder", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.EVENT,
                    "event name /from 2003-01-01 /from 2003-01-01"));
            fail();
        } catch (Exception e) {
            assertEquals("Placeholder", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArgs(Command.EVENT,
                    "event name /to 2003-01-01 /to 2003-01-01"));
            fail();
        } catch (Exception e) {
            assertEquals("Placeholder", e.getMessage());
        }
    }
}
