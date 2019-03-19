package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.AMY;
import static seedu.address.testutil.TypicalEquipments.BOB;
import static seedu.address.testutil.TypicalEquipments.HWIYOHCC;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.NameContainsKeywordsPredicate;
import seedu.address.model.equipment.exceptions.EquipmentNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EquipmentBuilder;
import seedu.address.testutil.EquipmentManagerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EquipmentManager(), new EquipmentManager(modelManager.getAddressBook()));
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
        assertFalse(modelManager.hasPerson(ACHORVALECC));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ACHORVALECC);
        assertTrue(modelManager.hasPerson(ACHORVALECC));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addPerson(ACHORVALECC);
        modelManager.setSelectedPerson(ACHORVALECC);
        modelManager.deletePerson(ACHORVALECC);
        assertEquals(null, modelManager.getSelectedPerson());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addPerson(ACHORVALECC);
        modelManager.addPerson(BOB);
        assertEquals(Arrays.asList(ACHORVALECC, BOB), modelManager.getFilteredPersonList());
        modelManager.setSelectedPerson(BOB);
        modelManager.deletePerson(BOB);
        assertEquals(ACHORVALECC, modelManager.getSelectedPerson());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addPerson(ACHORVALECC);
        modelManager.setSelectedPerson(ACHORVALECC);
        Equipment updatedAlice = new EquipmentBuilder(ACHORVALECC).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setPerson(ACHORVALECC, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedPerson());
    }

    @Test
    public void getFilteredEquipmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredWorkListList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredWorkListList().remove(0);
    }

    @Test
    public void setSelectedPerson_personNotInFilteredPersonList_throwsPersonNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        modelManager.setSelectedPerson(ACHORVALECC);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addPerson(ACHORVALECC);
        assertEquals(Collections.singletonList(ACHORVALECC), modelManager.getFilteredPersonList());
        modelManager.setSelectedPerson(ACHORVALECC);
        assertEquals(ACHORVALECC, modelManager.getSelectedPerson());
    }

    @Test
    public void equals() {
        EquipmentManager equipmentManager = new EquipmentManagerBuilder().withPerson(ACHORVALECC)
                .withPerson(HWIYOHCC).build();
        EquipmentManager differentEquipmentManager = new EquipmentManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(equipmentManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(equipmentManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different equipmentManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEquipmentManager, userPrefs)));

        // different filteredList -> returns True
        String[] keywords = ACHORVALECC.getName().name.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertTrue(modelManager.equals(new ModelManager(equipmentManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(equipmentManager, differentUserPrefs)));
    }

    @Test
    public void deleteTag_nonExistentTag_modelUnchanged() {
        EquipmentManager equipmentManager = new EquipmentManagerBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(equipmentManager, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(new ModelManager(equipmentManager, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagUsedByMultiplePersons_tagRemoved() {
        EquipmentManager equipmentManager = new EquipmentManagerBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(equipmentManager, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        ModelManager expectedModelManager = new ModelManager(equipmentManager, userPrefs);
        Equipment amyWithoutFriendTag = new EquipmentBuilder(AMY).withTags().build();
        Equipment bobWithoutFriendTag = new EquipmentBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        expectedModelManager.updatePerson(AMY, amyWithoutFriendTag);
        expectedModelManager.updatePerson(BOB, bobWithoutFriendTag);

        assertEquals(expectedModelManager, modelManager);
    }
}
