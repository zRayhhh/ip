package ayre;

import ayre.enums.CommandType;
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
        assertEquals(list, Parser.tokenizeArguments(CommandType.BYE, "bye"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.LIST, "bye"));
        list.add("1");
        assertEquals(list, Parser.tokenizeArguments(CommandType.MARK, "mark 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.UNMARK, "unmark 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.DELETE, "delete 1"));
        list.clear();
        list.add("name");
        assertEquals(list, Parser.tokenizeArguments(CommandType.TODO, "todo name"));
        list.add("2003-01-01");
        assertEquals(list, Parser.tokenizeArguments(CommandType.DEADLINE, "deadline name /by 2003-01-01"));
        list.add("2003-01-01");
        assertEquals(list, Parser.tokenizeArguments(CommandType.EVENT, "event name /from 2003-01-01 /to 2003-01-01"));
    }

    @Test
    public void tokenizeArgs_extraArguments_exceptionThrown() {
        ArrayList<String> list = new ArrayList<>();
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.BYE, "bye bye"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.LIST, "list list"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.MARK, "mark 1 2"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.UNMARK, "unmark 1 2"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.DELETE, "delete 1 2"));
            fail();
        } catch (Exception e) {
            assertEquals("Arguments provided where not expected", e.getMessage());
        }
    }

    @Test
    public void tokenizeArgs_invalidUseOfTaskTimeFlags_exceptionThrown() {
        ArrayList<String> list = new ArrayList<>();
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.DEADLINE,
                    "deadline name /by 2003-01-01 /by 2003-01-01"));
            fail();
        } catch (Exception e) {
            assertEquals("Placeholder", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.EVENT,
                    "event name /from 2003-01-01 /from 2003-01-01"));
            fail();
        } catch (Exception e) {
            assertEquals("Placeholder", e.getMessage());
        }
        try {
            assertEquals(list, Parser.tokenizeArguments(CommandType.EVENT,
                    "event name /to 2003-01-01 /to 2003-01-01"));
            fail();
        } catch (Exception e) {
            assertEquals("Placeholder", e.getMessage());
        }
    }
}
