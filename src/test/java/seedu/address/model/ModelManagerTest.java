package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new AddressBook(), new AddressBook(modelManager.getArchiveBook()));
        assertEquals(new AddressBook(), new AddressBook(modelManager.getPinBook()));
        assertEquals(null, modelManager.getSelectedPerson());
        assertEquals(null, modelManager.getSelectedArchivedPerson());
        assertEquals(null, modelManager.getSelectedPinPerson());
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
    public void setArchiveBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setArchiveBookFilePath(null);
    }

    @Test
    public void setArchiveBookFilePath_validPath_setsArchiveBookFilePath() {
        Path path = Paths.get("archive/book/file/path");
        modelManager.setArchiveBookFilePath(path);
        assertEquals(path, modelManager.getArchiveBookFilePath());
    }

    @Test
    public void setPinBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setPinBookFilePath(null);
    }

    @Test
    public void setPinBookFilePath_validPath_setsPinBookFilePath() {
        Path path = Paths.get("pin/book/file/path");
        modelManager.setPinBookFilePath(path);
        assertEquals(path, modelManager.getPinBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPersonArchive_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPersonArchive(null);
    }

    @Test
    public void hasPersonArchive_personNotInArchiveBook_returnsFalse() {
        assertFalse(modelManager.hasPersonArchive(ALICE));
    }

    @Test
    public void hasPersonArchive_personInArchiveBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.archivePerson(ALICE);
        assertTrue(modelManager.hasPersonArchive(ALICE));
    }

    @Test
    public void hasPersonPin_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPersonPin(null);
    }

    @Test
    public void hasPersonPin_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPersonPin(ALICE));
    }

    @Test
    public void hasPersonPin_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.pinPerson(ALICE);
        assertTrue(modelManager.hasPersonPin(ALICE));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addPerson(ALICE);
        modelManager.setSelectedPerson(ALICE);
        modelManager.deletePerson(ALICE);
        assertEquals(null, modelManager.getSelectedPerson());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredPersonList());
        modelManager.setSelectedPerson(BOB);
        modelManager.deletePerson(BOB);
        assertEquals(ALICE, modelManager.getSelectedPerson());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addPerson(ALICE);
        modelManager.setSelectedPerson(ALICE);
        Person updatedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setPerson(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedPerson());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void setSelectedPerson_personNotInFilteredPersonList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        modelManager.setSelectedPerson(ALICE);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addPerson(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredPersonList());
        modelManager.setSelectedPerson(ALICE);
        assertEquals(ALICE, modelManager.getSelectedPerson());
    }

    @Test
    public void setSelectedArchivedPerson_personNotInFilteredArchivedPersonList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        modelManager.setSelectedArchivedPerson(ALICE);
    }

    @Test
    public void setSelectedArchivedPerson_personInFilteredArchivedPersonList_setsSelectedArchivedPerson() {
        modelManager.addPerson(ALICE);
        modelManager.archivePerson(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredArchivedPersonList());
        modelManager.setSelectedArchivedPerson(ALICE);
        assertEquals(ALICE, modelManager.getSelectedArchivedPerson());
    }

    @Test
    public void setSelectedPinPerson_personNotInFilteredPinPersonList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        modelManager.setSelectedPinPerson(ALICE);
    }

    @Test
    public void setSelectedPinPerson_personInFilteredPinPersonList_setsSelectedPinPerson() {
        modelManager.addPerson(ALICE);
        modelManager.pinPerson(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredPinnedPersonList());
        modelManager.setSelectedPinPerson(ALICE);
        assertEquals(ALICE, modelManager.getSelectedPinPerson());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook archiveBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook pinBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        AddressBook differentArchiveBook = new AddressBook();
        AddressBook differentPinBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, archiveBook, pinBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, archiveBook, pinBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, archiveBook, pinBook, userPrefs)));

        // different archiveBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentArchiveBook, pinBook, userPrefs)));

        // different pinBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, archiveBook, differentPinBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, archiveBook, pinBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, archiveBook, pinBook, differentUserPrefs)));
    }
}
