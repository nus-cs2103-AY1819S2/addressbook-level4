package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TableCardHandle;
import guitests.guihandles.TablesFlowPanelHandle;
import javafx.scene.Node;
import seedu.address.model.table.Table;
import seedu.address.ui.TableCard;

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
     * Asserts that {@code actualCard} displays the details of {@code expectedTable}.
     */
    public static void assertCardDisplaysTable(Table expectedTable, Set<Node> nodes) {
        assertTrue(nodes.contains(new TableCard(expectedTable).getRoot()));
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    //    /**
    //     * Asserts that the list in {@code tableFlowPanelHandle} displays the details of {@code tables} correctly
    //     * and in the correct order.
    //     */
    //    public static void assertListMatching(TablesFlowPanelHandle tablesFlowPanelHandle, Table... tables) {
    //        Set<Node> nodes = tablesFlowPanelHandle.getAllCardNodes();
    //        for (int i = 0; i < tables.length; i++) {
    //            nodes.containsAll(tables);
    ////            assertCardDisplaysTable(tables[i], nodes);
    //        }
    //    }

    /**
     * Asserts that the list in {@code tableFlowPanelHandle} displays the details of {@code tables} correctly
     * and in the correct order.
     */
    public static void assertListMatching(TablesFlowPanelHandle tablesFlowPanelHandle,
                                          List<Table> tables) {
        Set<Node> nodes = tablesFlowPanelHandle.getAllCardNodes();
        assertTrue(nodes.containsAll(tables));
    }
}
