package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BETTY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_HEALTHWORKERS;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
import static seedu.address.testutil.TypicalHealthWorkers.PANIEL;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBookBuilder;
import seedu.address.testutil.RequestBookBuilder;
import seedu.address.testutil.RequestBuilder;


public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
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

    // Added tests for added supporting operations on UniqueHealthWorkerList
    // @author: Lookaz

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
        HealthWorkerBook healthWorkerBook = new HealthWorkerBookBuilder().withHealthWorker(ANDY)
                .withHealthWorker(BETTY).build();
        RequestBook requestBook = new RequestBookBuilder().withRequest(ALICE_REQUEST).build();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(healthWorkerBook, requestBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(healthWorkerBook, requestBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(new HealthWorkerBook(), requestBook, userPrefs)));

        // different filteredList -> returns false
        // modelManager.updateFilteredHealthWorkerList(x -> x.getName().contains("Andy"));
        // assertFalse(modelManager.equals(new ModelManager(healthWorkerBook, requestBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredHealthWorkerList(PREDICATE_SHOW_ALL_HEALTHWORKERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(healthWorkerBook, requestBook, differentUserPrefs)));
    }

    @Test
    public void isAssigned() {
        modelManager = new ModelManager(getTypicalHealthWorkerBook(), getTypicalRequestBook(), new UserPrefs());

        // unassigned request
        assertFalse(modelManager.isAssigned(PANIEL.getName().toString()));

        // assigned request
        modelManager.updateRequest(ALICE_REQUEST, new RequestBuilder(ALICE_REQUEST).withHealthWorker(VALID_NAME_ANDY)
                .withStatus("ONGOING").build());
        assertTrue(modelManager.isAssigned(VALID_NAME_ANDY));

        // completed request
        modelManager.updateRequest(BENSON_REQUEST, new RequestBuilder(BENSON_REQUEST).withHealthWorker(VALID_NAME_BETTY)
                .withStatus("COMPLETED").build());
        assertFalse(modelManager.isAssigned(VALID_NAME_BETTY));
    }
}
