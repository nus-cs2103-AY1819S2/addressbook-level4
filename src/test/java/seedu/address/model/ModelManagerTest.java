package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;

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
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBookBuilder;
import seedu.address.testutil.PatientBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RequestBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedPerson());
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
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addPerson(ALICE);
        modelManager.setSelectedPerson(ALICE);
        modelManager.deletePerson(ALICE);
        assertEquals(null, modelManager.getSelectedPerson());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BETTY);
        assertEquals(Arrays.asList(ALICE, BETTY), modelManager.getFilteredPersonList());
        modelManager.setSelectedPerson(BETTY);
        modelManager.deletePerson(BETTY);
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

    // Added tests for added supporting operations on UniqueHealthWorkerList
    // @author: Lookaz
    // TODO: Update tests accordingly after adding further operations supporting HealthWorker

    @Test
    public void addHealthWorker() {
        // add null health worker
        Assert.assertThrows(NullPointerException.class, () -> modelManager
                .addHealthWorker(null));

        // health worker already in addressbook
        modelManager.addHealthWorker(ANDY);
        Assert.assertThrows(DuplicatePersonException.class, () ->
                modelManager.addHealthWorker(ANDY));
    }

    @Test
    public void hasHealthWorker() {
        // null health worker
        Assert.assertThrows(NullPointerException.class, () -> modelManager
                .hasHealthWorker(null));

        // health worker does not exist -> return false
        assertFalse(modelManager.hasHealthWorker(ANDY));

        // health worker exists -> return true
        modelManager.addHealthWorker(ANDY);
        assertTrue(modelManager.hasHealthWorker(ANDY));
    }

    @Test
    public void deleteHealthWorker() {
        // null health worker
        Assert.assertThrows(NullPointerException.class, () -> modelManager
                .deleteHealthWorker(null));

        // delete non existent person
        Assert.assertThrows(PersonNotFoundException.class, () -> modelManager
                .deleteHealthWorker(ANDY));
    }

    @Test
    public void setHealthWorker() {
        // setting null health worker
        modelManager.addHealthWorker(ANDY);
        Assert.assertThrows(NullPointerException.class, () -> modelManager
                .setHealthWorker(ANDY, null));
        Assert.assertThrows(NullPointerException.class, () -> modelManager
                .setHealthWorker(null, ANDY));

        // setting non existent health worker
        Assert.assertThrows(PersonNotFoundException.class, () -> modelManager
                .setHealthWorker(BETTY, ANDY));

        // setting to duplicate health worker
        modelManager.addHealthWorker(BETTY);
        Assert.assertThrows(DuplicatePersonException.class, () ->
                modelManager.setHealthWorker(BETTY, ANDY));
    }

    // ======================================================================

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        HealthWorkerBook healthWorkerBook = new HealthWorkerBookBuilder().withHealthWorker(ANDY)
                .withHealthWorker(BETTY).build();
        PatientBook patientBook = new PatientBookBuilder().withPatient(ALICE)
            .withPatient(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        RequestBook requestBook = new RequestBookBuilder().withRequest(ALICE_REQUEST).build();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, healthWorkerBook, patientBook, requestBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, healthWorkerBook, patientBook,
            requestBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, healthWorkerBook, patientBook,
            requestBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, healthWorkerBook, patientBook,
            requestBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, healthWorkerBook, patientBook,
            requestBook, differentUserPrefs)));
    }
}
