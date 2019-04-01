package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TableCardHandle;
import guitests.guihandles.TablesFlowPanelHandle;
import seedu.address.model.table.Table;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class TablesGuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TableCardHandle expectedCard, TableCardHandle actualCard) {
        assertEquals(expectedCard.getTableNumber(), actualCard.getTableNumber());
        assertEquals(expectedCard.getTableStatus(), actualCard.getTableStatus());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTable}.
     */
    public static void assertCardDisplaysTable(Table expectedTable, TableCardHandle actualCard) {
        assertEquals("Table " + expectedTable.getTableNumber().toString(), actualCard.getTableNumber());
        assertEquals("Status: " + expectedTable.getTableStatus().toString(), actualCard.getTableStatus());
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    /**
     * Asserts that the list in {@code tableFlowPanelHandle} displays the details of {@code tables} correctly
     * and in the correct order.
     */
    public static void assertListMatching(TablesFlowPanelHandle tablesFlowPanelHandle, Table... tables) {
        for (int i = 0; i < tables.length; i++) {
            assertCardDisplaysTable(tables[i], tablesFlowPanelHandle.getTableCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code tableFlowPanelHandle} displays the details of {@code tables} correctly
     * and in the correct order.
     */
    public static void assertListMatching(TablesFlowPanelHandle tablesFlowPanelHandle,
                                          List<Table> tables) {
        assertListMatching(tablesFlowPanelHandle, tables.toArray(new Table[0]));
    }
}
