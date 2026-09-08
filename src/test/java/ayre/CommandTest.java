package ayre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ayre.commands.ByeCommand;
import ayre.commands.Command;
import ayre.commands.DeadlineCommand;
import ayre.commands.DeleteCommand;
import ayre.commands.EventCommand;
import ayre.commands.FindCommand;
import ayre.commands.ListCommand;
import ayre.commands.MarkCommand;
import ayre.commands.TodoCommand;
import ayre.commands.UnmarkCommand;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;

public class CommandTest {
    @TempDir
    Path tmpDir;

    @Test
    public void validate_validArguments_noExceptionThrown() {
        Command bye = new ByeCommand(null);
        assertDoesNotThrow(() -> bye.validate(new ArrayList<>()));
        Command list = new ListCommand(null);
        assertDoesNotThrow(() -> list.validate(new ArrayList<>()));

        ArrayList<String> args = new ArrayList<>();
        args.add("-0000099996969667");
        Command mark = new MarkCommand(null);
        assertDoesNotThrow(() -> mark.validate(args));
        Command unmark = new UnmarkCommand(null);
        assertDoesNotThrow(() -> unmark.validate(args));
        Command delete = new DeleteCommand(null);
        assertDoesNotThrow(() -> delete.validate(args));
        Command find = new FindCommand(null);
        assertDoesNotThrow(() -> find.validate(args));
        Command todo = new TodoCommand(null);
        assertDoesNotThrow(() -> todo.validate(args));

        args.add("2008-08-08");
        Command deadline = new DeadlineCommand(null);
        assertDoesNotThrow(() -> deadline.validate(args));

        args.add("2009-09-09");
        Command event = new EventCommand(null);
        assertDoesNotThrow(() -> event.validate(args));
    }

    @Test
    public void validate_invalidArguments_exceptionThrown() {
        // CommandTypes with nothing to validate: BYE, LIST, FIND, TODO
        ArrayList<String> args = new ArrayList<>();
        args.add("hi");
        Command mark = new MarkCommand(null);
        assertThrows(InvalidCommandArgumentsException.class, () -> mark.validate(args));
        Command unmark = new UnmarkCommand(null);
        assertThrows(InvalidCommandArgumentsException.class, () -> unmark.validate(args));
        Command delete = new DeleteCommand(null);
        assertThrows(InvalidCommandArgumentsException.class, () -> delete.validate(args));

        args.add("not-a-date");
        Command deadline = new DeadlineCommand(null);
        assertThrows(InvalidCommandArgumentsException.class, () -> deadline.validate(args));
        args.remove(1);
        args.add("2009-13-13");
        assertThrows(InvalidCommandArgumentsException.class, () -> deadline.validate(args));
        args.remove(1);

        args.add("not-a-date");
        args.add("2003-03-03");
        Command event = new EventCommand(null);
        assertThrows(InvalidCommandArgumentsException.class, () -> event.validate(args));
        args.remove(1);
        args.add("2026-26-26");
        assertThrows(InvalidCommandArgumentsException.class, () -> event.validate(args));
    }

    @Test
    public void doCommand_validArguments_success() throws InvalidCommandArgumentsException {
        Path testFile = tmpDir.resolve("test.txt");
        LiveTaskList tasks = new LiveTaskList(new TaskList(), new Storage(testFile.toString()));
        ArrayList<String> args = new ArrayList<>();
        args.add("hi");
        Command todo = new TodoCommand(tasks);
        CommandResult todoRes = todo.doCommand(args);
        assertEquals("~ New mission added:\n[T][ ] hi", todoRes.message());
        assertEquals(AyreStatus.CONTINUE, todoRes.status());

        args.add("2005-05-05");
        Command deadline = new DeadlineCommand(tasks);
        CommandResult deadlineRes = deadline.doCommand(args);
        assertEquals("~ New mission added:\n[D][ ] hi <by: May 05, 2005>", deadlineRes.message());
        assertEquals(AyreStatus.CONTINUE, deadlineRes.status());

        args.add("2006-06-06");
        Command event = new EventCommand(tasks);
        CommandResult eventRes = event.doCommand(args);
        assertEquals("~ New mission added:\n[E][ ] hi <from: May 05, 2005 to: Jun 06, 2006>",
                eventRes.message());
        assertEquals(AyreStatus.CONTINUE, eventRes.status());

        args.clear();
        Command bye = new ByeCommand(tasks);
        CommandResult byeRes = bye.doCommand(args);
        assertEquals(AyreStatus.TERMINATE, byeRes.status());

        Command list = new ListCommand(tasks);
        CommandResult listRes = list.doCommand(args);
        assertEquals("""
                ~ Current missions:
                1. [T][ ] hi
                2. [D][ ] hi <by: May 05, 2005>
                3. [E][ ] hi <from: May 05, 2005 to: Jun 06, 2006>
                ~ You have 3 pending missions. Let's do this.""", listRes.message());
        assertEquals(AyreStatus.CONTINUE, listRes.status());

        args.add("2");
        Command mark = new MarkCommand(tasks);
        CommandResult markRes = mark.doCommand(args);
        assertEquals("""
                ~ Mission complete. Good work, Raven.
                [D][+] hi <by: May 05, 2005>""", markRes.message());
        assertEquals(AyreStatus.CONTINUE, markRes.status());

        Command unmark = new UnmarkCommand(tasks);
        CommandResult unmarkRes = unmark.doCommand(args);
        assertEquals("""
                ~ The mission is still pending, Raven. Let's get to it.
                [D][ ] hi <by: May 05, 2005>""", unmarkRes.message());
        assertEquals(AyreStatus.CONTINUE, unmarkRes.status());

        Command delete = new DeleteCommand(tasks);
        CommandResult deleteRes = delete.doCommand(args);
        assertEquals("""
                ~ The mission has been dropped.
                Deleted: [D][ ] hi <by: May 05, 2005>""", deleteRes.message());
        assertEquals(AyreStatus.CONTINUE, deleteRes.status());

        args.clear();
        args.add("H");
        Command find = new FindCommand(tasks);
        CommandResult findRes = find.doCommand(args);
        assertEquals("""
                ~ These are the matching missions:
                1. [T][ ] hi
                2. [E][ ] hi <from: May 05, 2005 to: Jun 06, 2006>
                ~ Did you find what you were looking for, Raven?""", findRes.message());
        assertEquals(AyreStatus.CONTINUE, findRes.status());
    }

    @Test
    public void doCommand_invalidArguments_exceptionThrown() {
        Path testFile = tmpDir.resolve("test.txt");
        LiveTaskList tasks = new LiveTaskList(new TaskList(), new Storage(testFile.toString()));
        Command mark = new MarkCommand(tasks);
        Command unmark = new UnmarkCommand(tasks);
        Command delete = new DeleteCommand(tasks);

        ArrayList<String> args = new ArrayList<>();
        args.add("-1");

        assertThrows(InvalidCommandArgumentsException.class, () -> mark.doCommand(args));
        assertThrows(InvalidCommandArgumentsException.class, () -> unmark.doCommand(args));
        assertThrows(InvalidCommandArgumentsException.class, () -> delete.doCommand(args));

        args.clear();
        args.add("1");

        assertThrows(InvalidCommandArgumentsException.class, () -> mark.doCommand(args));
        assertThrows(InvalidCommandArgumentsException.class, () -> unmark.doCommand(args));
        assertThrows(InvalidCommandArgumentsException.class, () -> delete.doCommand(args));
    }
}
