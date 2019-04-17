package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPARELS;
import static seedu.address.testutil.TypicalApparels.BOBYIN;
import static seedu.address.testutil.TypicalApparels.PANTS1;
import static seedu.address.testutil.TypicalApparels.SHIRT1;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.NameContainsKeywordsPredicate;
import seedu.address.model.apparel.exceptions.ApparelNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ApparelBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FashionMatch(), new FashionMatch(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedApparel());
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
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasApparel(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasApparel(SHIRT1));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addApparel(SHIRT1);
        assertTrue(modelManager.hasApparel(SHIRT1));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addApparel(SHIRT1);
        modelManager.setSelectedPerson(SHIRT1);
        modelManager.deleteApparel(SHIRT1);
        assertEquals(null, modelManager.getSelectedApparel());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addApparel(SHIRT1);
        modelManager.addApparel(BOBYIN);
        assertEquals(Arrays.asList(SHIRT1, BOBYIN), modelManager.getFilteredApparelList());
        modelManager.setSelectedPerson(BOBYIN);
        modelManager.deleteApparel(BOBYIN);
        assertEquals(SHIRT1, modelManager.getSelectedApparel());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addApparel(SHIRT1);
        modelManager.setSelectedPerson(SHIRT1);
        Apparel updatedAlice = new ApparelBuilder(SHIRT1).withClothingType(VALID_TYPE_BOTTOM).build();
        modelManager.setPerson(SHIRT1, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedApparel());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredApparelList().remove(0);
    }

    @Test
    public void setSelectedPerson_personNotInFilteredPersonList_throwsPersonNotFoundException() {
        thrown.expect(ApparelNotFoundException.class);
        modelManager.setSelectedPerson(SHIRT1);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addApparel(SHIRT1);
        assertEquals(Collections.singletonList(SHIRT1), modelManager.getFilteredApparelList());
        modelManager.setSelectedPerson(SHIRT1);
        assertEquals(SHIRT1, modelManager.getSelectedApparel());
    }

    @Test
    public void equals() {
        FashionMatch addressBook = new AddressBookBuilder().withPerson(SHIRT1).withPerson(PANTS1).build();
        FashionMatch differentAddressBook = new FashionMatch();
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
        String[] keywords = BOBYIN.getName().fullName.split("\\s+");
        modelManager.updateFilteredApparelList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
