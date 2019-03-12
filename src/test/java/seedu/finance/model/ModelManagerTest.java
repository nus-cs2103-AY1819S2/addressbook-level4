package seedu.finance.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;
import static seedu.finance.testutil.TypicalRecords.ALICE;
import static seedu.finance.testutil.TypicalRecords.BENSON;
import static seedu.finance.testutil.TypicalRecords.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.commons.core.GuiSettings;
import seedu.finance.model.record.NameContainsKeywordsPredicate;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.exceptions.RecordNotFoundException;
import seedu.finance.testutil.AddressBookBuilder;
import seedu.finance.testutil.RecordBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedRecord());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("finance/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/finance/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("finance/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasRecordn_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasRecord(ALICE));
    }

    @Test
    public void hasRecord_recordInAddressBook_returnsTrue() {
        modelManager.addRecord(ALICE);
        assertTrue(modelManager.hasRecord(ALICE));
    }

    @Test
    public void deleteRecord_recordIsSelectedAndFirstRecordInFilteredRecordList_selectionCleared() {
        modelManager.addRecord(ALICE);
        modelManager.setSelectedRecord(ALICE);
        modelManager.deleteRecord(ALICE);
        assertEquals(null, modelManager.getSelectedRecord());
    }

    @Test
    public void deleteRecord_recordIsSelectedAndSecondRecordInFilteredRecordList_firstRecordSelected() {
        modelManager.addRecord(ALICE);
        modelManager.addRecord(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredRecordList());
        modelManager.setSelectedRecord(BOB);
        modelManager.deleteRecord(BOB);
        assertEquals(ALICE, modelManager.getSelectedRecord());
    }

    @Test
    public void setRecord_recordIsSelected_selectedRecordUpdated() {
        modelManager.addRecord(ALICE);
        modelManager.setSelectedRecord(ALICE);
        Record updatedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB).build();
        modelManager.setRecord(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedRecord());
    }

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecordList().remove(0);
    }

    @Test
    public void setSelectedRecord_recordNotInFilteredRecordList_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        modelManager.setSelectedRecord(ALICE);
    }

    @Test
    public void setSelectedRecord_recordInFilteredRecordList_setsSelectedRecord() {
        modelManager.addRecord(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredRecordList());
        modelManager.setSelectedRecord(ALICE);
        assertEquals(ALICE, modelManager.getSelectedRecord());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withRecord(ALICE).withRecord(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecordList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
