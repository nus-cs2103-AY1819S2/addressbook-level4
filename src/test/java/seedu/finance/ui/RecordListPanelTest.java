package seedu.finance.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.finance.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.finance.testutil.TypicalRecords.getTypicalRecords;
import static seedu.finance.ui.testutil.GuiTestAssert.assertCardDisplaysRecord;
import static seedu.finance.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.RecordCardHandle;
import guitests.guihandles.RecordListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;

public class RecordListPanelTest extends GuiUnitTest {
    private static final ObservableList<Record>TYPICAL_RECORDS =
            FXCollections.observableList(getTypicalRecords());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();
    private RecordListPanelHandle recordListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RECORDS);

        for (int i = 0; i < TYPICAL_RECORDS.size(); i++) {
            recordListPanelHandle.navigateToCard(TYPICAL_RECORDS.get(i));
            Record expectedRecord = TYPICAL_RECORDS.get(i);
            RecordCardHandle actualCard = recordListPanelHandle.getRecordCardHandle(i);

            assertCardDisplaysRecord(expectedRecord, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedRecordChanged_selectionChanges() {
        initUi(TYPICAL_RECORDS);
        Record secondRecord = TYPICAL_RECORDS.get(INDEX_SECOND_RECORD.getZeroBased());
        guiRobot.interact(() -> selectedRecord.set(secondRecord));
        guiRobot.pauseForHuman();

        RecordCardHandle expectedRecord = recordListPanelHandle.getRecordCardHandle(INDEX_SECOND_RECORD.getZeroBased());
        RecordCardHandle selectedRecord = recordListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRecord, selectedRecord);
    }

    /**
     * Verifies that creating and deleting large number of records in {@code RecordListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Record> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of record cards exceeded time limit");
    }

    /**
     * Returns a list of records containing {@code recordCount} records that is used to populate the
     * {@code RecordListPanel}.
     */
    private ObservableList<Record> createBackingList(int recordCount) {
        ObservableList<Record> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < recordCount; i++) {
            Name name = new Name(i + "a");
            Amount amount = new Amount("123");
            Date date = new Date("12/12/2018");
            Description description = new Description ("");
            Record record = new Record(name, amount, date, description, Collections.emptySet());
            backingList.add(record);
        }
        return backingList;
    }

    /**
     * Initializes {@code recordListPanelHandle} with a {@code RecordListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RecordListPanel}.
     */
    private void initUi(ObservableList<Record> backingList) {
        RecordListPanel recordListPanel =
                new RecordListPanel(backingList, selectedRecord, selectedRecord::set);
        uiPartRule.setUiPart(recordListPanel);

        recordListPanelHandle = new RecordListPanelHandle(getChildNode(recordListPanel.getRoot(),
                RecordListPanelHandle.RECORD_LIST_VIEW_ID));
    }
}
