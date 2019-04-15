package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;
import static seedu.address.ui.testutil.TablesGuiTestAssert.assertCardDisplaysTable;

import org.junit.Test;

import guitests.guihandles.TableCardHandle;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;

public class TableCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Table table = new TableBuilder(TABLE1).build();
        TableCard tableCard = new TableCard(TABLE1);
        uiPartRule.setUiPart(tableCard);
        assertCardDisplay(tableCard, table, 1);
    }

    @Test
    public void equals() {
        Table table = new TableBuilder().build();
        TableCard tableCard = new TableCard(table);

        // same table -> returns true
        TableCard copy = new TableCard(table);
        assertTrue(tableCard.equals(copy));

        // same object -> returns true
        assertTrue(tableCard.equals(tableCard));

        // null -> returns false
        assertFalse(tableCard.equals(null));

        // different types -> returns false
        assertFalse(tableCard.equals(0));

        // different table -> returns false
        Table differentTable = new TableBuilder(TABLE2).build();
        assertFalse(tableCard.equals(new TableCard(differentTable)));
    }

    /**
     * Asserts that {@code tableCard} displays the details of {@code expectedTable} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TableCard tableCard, Table expectedTable, int expectedId) {
        guiRobot.pauseForHuman();

        TableCardHandle tableCardHandle = new TableCardHandle(tableCard.getRoot());

        // verify table details are displayed correctly
        assertCardDisplaysTable(expectedTable, tableCardHandle);
    }
}
