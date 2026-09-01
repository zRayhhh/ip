package ayre;

import ayre.enums.CommandType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTypeTest {
    @Test
    public void validate_nonIntegerString_exceptionThrown() {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertTrue(CommandType.MARK.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Expected integer value", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertTrue(CommandType.UNMARK.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Expected integer value", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertTrue(CommandType.DELETE.validate(list));
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
            assertTrue(CommandType.DEADLINE.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-13-13");
            list.add("2002-12-12");
            assertTrue(CommandType.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-12-12");
            list.add("2002-13-13");
            assertTrue(CommandType.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("hi");
            assertTrue(CommandType.DEADLINE.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("hi");
            list.add("2002-12-12");
            assertTrue(CommandType.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("name");
            list.add("2002-12-12");
            list.add("hi");
            assertTrue(CommandType.EVENT.validate(list));
            fail();
        } catch (Exception e) {
            assertEquals("Date does not adhere to ISO_LOCAL_DATE format", e.getMessage());
        }
    }
}
