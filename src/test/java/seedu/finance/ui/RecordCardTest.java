package seedu.finance.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.ui.testutil.GuiTestAssert.assertCardDisplaysRecord;

import org.junit.Test;

import guitests.guihandles.RecordCardHandle;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;


public class RecordCardTest extends GuiUnitTest {


    @Test
    public void display() {
        // The record must all have a tag to it (category)
        // with tags
        Record recordWithTags = new RecordBuilder().build();
        RecordCard recordCard = new RecordCard(recordWithTags, 2);
        uiPartRule.setUiPart(recordCard);
        assertCardDisplay(recordCard, recordWithTags, 2);
    }

    @Test
    public void equals() {
        Record record = new RecordBuilder().build();
        RecordCard recordCard = new RecordCard(record, 0);

        // same record, same index -> returns true
        RecordCard copy = new RecordCard(record, 0);
        assertTrue(recordCard.equals(copy));

        // same object -> returns true
        assertTrue(recordCard.equals(recordCard));

        // null -> returns false
        assertFalse(recordCard.equals(null));

        // different types -> returns false
        assertFalse(recordCard.equals(0));

        // different record, same index -> returns false
        Record differentRecord = new RecordBuilder().withName("differentName").build();
        assertFalse(recordCard.equals(new RecordCard(differentRecord, 0)));

        // same record, different index -> returns false
        assertFalse(recordCard.equals(new RecordCard(record, 1)));
    }


    /**
     * Asserts that {@code recordCard} displays the details of {@code expectedRecord} correctly and matches
     * {@code expectedId}.
     */

    private void assertCardDisplay(RecordCard recordCard, Record expectedRecord, int expectedId) {
        guiRobot.pauseForHuman();

        RecordCardHandle recordCardHandle = new RecordCardHandle(recordCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", recordCardHandle.getId());

        // verify record details are displayed correctly
        assertCardDisplaysRecord(expectedRecord, recordCardHandle);
    }
}
