package ayre;

import ayre.enums.Command;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    @Test
    public void validate_nonIntegerString_exceptionThrown() {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertTrue(Command.MARK.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Expected integer value", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertTrue(Command.UNMARK.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Expected integer value", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertTrue(Command.DELETE.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Expected integer value", e.getMessage());
        }
    }

    @Test
    public void validate_nonIsoLocalDateString_exceptionThrown() {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-13-13");
            assertTrue(Command.DEADLINE.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-13-13");
            list.add("2002-12-12");
            assertTrue(Command.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-12-12");
            list.add("2002-13-13");
            assertTrue(Command.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("hi");
            assertTrue(Command.DEADLINE.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("hi");
            list.add("2002-12-12");
            assertTrue(Command.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-12-12");
            list.add("hi");
            assertTrue(Command.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
    }
}
