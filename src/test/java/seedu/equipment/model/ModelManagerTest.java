package seedu.equipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.HWIYOHCC;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.NameContainsKeywordsPredicate;
import seedu.equipment.model.equipment.exceptions.EquipmentNotFoundException;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.EquipmentBuilder;
import seedu.equipment.testutil.EquipmentManagerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EquipmentManager(), new EquipmentManager(modelManager.getEquipmentManager()));
        assertEquals(null, modelManager.getSelectedEquipment());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEquipmentManagerFilePath(Paths.get("equipment/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setEquipmentManagerFilePath(Paths.get("new/equipment/book/file/path"));
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
        modelManager.setEquipmentManagerFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("equipment/book/file/path");
        modelManager.setEquipmentManagerFilePath(path);
        assertEquals(path, modelManager.getEquipmentManagerFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEquipment(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEquipment(ANCHORVALECC));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addEquipment(ANCHORVALECC);
        assertTrue(modelManager.hasEquipment(ANCHORVALECC));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addEquipment(ANCHORVALECC);
        modelManager.setSelectedEquipment(ANCHORVALECC);
        modelManager.deleteEquipment(ANCHORVALECC);
        assertEquals(null, modelManager.getSelectedEquipment());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addEquipment(ANCHORVALECC);
        modelManager.addEquipment(BOB);
        assertEquals(Arrays.asList(ANCHORVALECC, BOB), modelManager.getFilteredPersonList());
        modelManager.setSelectedEquipment(BOB);
        modelManager.deleteEquipment(BOB);
        assertEquals(ANCHORVALECC, modelManager.getSelectedEquipment());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addEquipment(ANCHORVALECC);
        modelManager.setSelectedEquipment(ANCHORVALECC);
        Equipment updatedAlice = new EquipmentBuilder(ANCHORVALECC).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setEquipment(ANCHORVALECC, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedEquipment());
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
        modelManager.setSelectedEquipment(ANCHORVALECC);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addEquipment(ANCHORVALECC);
        assertEquals(Collections.singletonList(ANCHORVALECC), modelManager.getFilteredPersonList());
        modelManager.setSelectedEquipment(ANCHORVALECC);
        assertEquals(ANCHORVALECC, modelManager.getSelectedEquipment());
    }

    @Test
    public void equals() {
        EquipmentManager equipmentManager = new EquipmentManagerBuilder().withPerson(ANCHORVALECC)
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
        String[] keywords = ANCHORVALECC.getName().name.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertTrue(modelManager.equals(new ModelManager(equipmentManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEquipmentManagerFilePath(Paths.get("differentFilePath"));
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
        expectedModelManager.updateEquipment(AMY, amyWithoutFriendTag);
        expectedModelManager.updateEquipment(BOB, bobWithoutFriendTag);

        assertEquals(expectedModelManager, modelManager);
    }
}
