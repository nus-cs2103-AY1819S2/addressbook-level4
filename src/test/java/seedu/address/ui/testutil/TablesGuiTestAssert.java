package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TableCardHandle;
import seedu.address.model.table.Table;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class TablesGuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TableCardHandle expectedCard, TableCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getTableNumber(), actualCard.getTableNumber());
        assertEquals(expectedCard.getTableStatus(), actualCard.getTableStatus());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTable}.
     */
    public static void assertCardDisplaysTable(Table expectedTable, TableCardHandle actualCard) {
        assertEquals(expectedTable.getTableNumber().toString(), actualCard.getTableNumber());
        assertEquals(expectedTable.getTableStatus().toString(), actualCard.getTableStatus());
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
