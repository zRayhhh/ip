package ayre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ayre.enums.CommandType;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;

public class ParserTest {
    @Test
    public void parseInput_nonCommand_exceptionThrown() {
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("hello"));
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("bye123"));
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("by"));
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput(" "));
    }

    @Test
    public void tokenizeArgs_validCommandAndArguments_success() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        assertEquals(list, Parser.tokenizeArguments(CommandType.BYE, "bye"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.BYE, "b"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.LIST, "list"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.LIST, "l"));
        list.add("1");
        assertEquals(list, Parser.tokenizeArguments(CommandType.MARK, "mark 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.MARK, "m 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.UNMARK, "unmark 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.UNMARK, "u 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.DELETE, "delete 1"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.DELETE, "rm 1"));
        list.clear();
        list.add("name");
        assertEquals(list, Parser.tokenizeArguments(CommandType.TODO, "todo name"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.TODO, "t name"));
        list.add("2003-01-01");
        assertEquals(list, Parser.tokenizeArguments(CommandType.DEADLINE, "deadline name /by 2003-01-01"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.DEADLINE, "d name /by 2003-01-01"));
        list.add("2003-01-01");
        assertEquals(list, Parser.tokenizeArguments(CommandType.EVENT,
                "event name /from 2003-01-01 /to 2003-01-01"));
        assertEquals(list, Parser.tokenizeArguments(CommandType.EVENT,
                "e name /from 2003-01-01 /to 2003-01-01"));
    }

    @Test
    public void tokenizeArgs_extraArguments_exceptionThrown() {
        assertThrows(WrongNumberOfArgumentsException.class,
                () -> Parser.tokenizeArguments(CommandType.BYE, "bye bye"));
        assertThrows(WrongNumberOfArgumentsException.class,
                () -> Parser.tokenizeArguments(CommandType.BYE, "b 1"));
        assertThrows(WrongNumberOfArgumentsException.class,
                () -> Parser.tokenizeArguments(CommandType.LIST, "list 3"));
        assertThrows(WrongNumberOfArgumentsException.class,
                () -> Parser.tokenizeArguments(CommandType.LIST, "l l"));
    }

    @Test
    public void tokenizeArgs_invalidUseOfTaskTimeFlags_exceptionThrown() {
        assertThrows(WrongNumberOfArgumentsException.class, () -> Parser.tokenizeArguments(CommandType.DEADLINE,
                "deadline name /by 2003-01-01 /by 2003-01-01"));
        assertThrows(WrongNumberOfArgumentsException.class, () -> Parser.tokenizeArguments(CommandType.EVENT,
                "event name /from 2003-01-01 /from 2003-01-01"));
        assertThrows(WrongNumberOfArgumentsException.class, () -> Parser.tokenizeArguments(CommandType.EVENT,
                "event name /to 2003-01-01 /to 2003-01-01"));
    }
}
