package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;
import static seedu.address.testutil.TypicalMedicines.ALICE;
import static seedu.address.testutil.TypicalMedicines.BENSON;
import static seedu.address.testutil.TypicalMedicines.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.medicine.NameContainsKeywordsPredicate;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.MedicineBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedMedicine());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasMedicine_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasMedicine(null);
    }

    @Test
    public void hasMedicine_medicineNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasMedicine(ALICE));
    }

    @Test
    public void hasMedicine_medicineInAddressBook_returnsTrue() {
        modelManager.addMedicine(ALICE);
        assertTrue(modelManager.hasMedicine(ALICE));
    }

    @Test
    public void deleteMedicine_medicineIsSelectedAndFirstMedicineInFilteredMedicineList_selectionCleared() {
        modelManager.addMedicine(ALICE);
        modelManager.setSelectedMedicine(ALICE);
        modelManager.deleteMedicine(ALICE);
        assertEquals(null, modelManager.getSelectedMedicine());
    }

    @Test
    public void deleteMedicine_medicineIsSelectedAndSecondMedicineInFilteredMedicineList_firstMedicineSelected() {
        modelManager.addMedicine(ALICE);
        modelManager.addMedicine(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredMedicineList());
        modelManager.setSelectedMedicine(BOB);
        modelManager.deleteMedicine(BOB);
        assertEquals(ALICE, modelManager.getSelectedMedicine());
    }

    @Test
    public void setMedicine_medicineIsSelected_selectedMedicineUpdated() {
        modelManager.addMedicine(ALICE);
        modelManager.setSelectedMedicine(ALICE);
        Medicine updatedAlice = new MedicineBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setMedicine(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedMedicine());
    }

    @Test
    public void getFilteredMedicineList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredMedicineList().remove(0);
    }

    @Test
    public void setSelectedMedicine_medicineNotInFilteredMedicineList_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        modelManager.setSelectedMedicine(ALICE);
    }

    @Test
    public void setSelectedMedicine_medicineInFilteredMedicineList_setsSelectedMedicine() {
        modelManager.addMedicine(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredMedicineList());
        modelManager.setSelectedMedicine(ALICE);
        assertEquals(ALICE, modelManager.getSelectedMedicine());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withMedicine(ALICE).withMedicine(BENSON).build();
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
        modelManager.updateFilteredMedicineList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
