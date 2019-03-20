package seedu.equipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;

import static seedu.equipment.testutil.TypicalWorkLists.LISTA;
import static seedu.equipment.testutil.TypicalWorkLists.LISTB;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.equipment.testutil.EquipmentBuilder;
import seedu.equipment.testutil.EquipmentManagerBuilder;

public class EquipmentManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EquipmentManager equipmentManager = new EquipmentManager();
    private final EquipmentManager equipmentManagerWithBobAndAmy = new EquipmentManagerBuilder().withPerson(BOB)
            .withPerson(AMY).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), equipmentManager.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EquipmentManager newData = getTypicalAddressBook();
        equipmentManager.resetData(newData);
        assertEquals(newData, equipmentManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two equipment with the same identity fields
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Equipment> newEquipments = Arrays.asList(ANCHORVALECC, editedAlice);
        EquipmentManagerStub newData = new EquipmentManagerStub(newEquipments);

        thrown.expect(DuplicateEquipmentException.class);
        equipmentManager.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.hasPerson(null);
    }

    @Test
    public void hasWorkList_nullWorkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.hasWorkList(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(equipmentManager.hasPerson(ANCHORVALECC));
    }

    @Test
    public void hasWorkList_workListNotInEquipmentManager_returnsFalse() {
        assertFalse(equipmentManager.hasWorkList(LISTA));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        equipmentManager.addPerson(ANCHORVALECC);
        assertTrue(equipmentManager.hasPerson(ANCHORVALECC));
    }

    @Test
    public void deleteWorkList_workListDoesNotExist() {
        equipmentManager.addWorkList(LISTB);
        equipmentManager.removeWorkList(LISTB);
    }

    @Test
    public void hasWorkList_workListInEquipmentManager_returnsTrue() {
        equipmentManager.addWorkList(LISTA);
        assertTrue(equipmentManager.hasWorkList(LISTA));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        equipmentManager.addPerson(ANCHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(equipmentManager.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        equipmentManager.getPersonList().remove(0);
    }

    @Test
    public void getWorkListList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        equipmentManager.getWorkListList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        equipmentManager.addListener(listener);
        equipmentManager.addPerson(ANCHORVALECC);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        equipmentManager.addListener(listener);
        equipmentManager.removeListener(listener);
        equipmentManager.addPerson(ANCHORVALECC);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyEquipmentManager whose equipment list can violate interface constraints.
     */
    private static class EquipmentManagerStub implements ReadOnlyEquipmentManager {
        private final ObservableList<Equipment> equipment = FXCollections.observableArrayList();

        EquipmentManagerStub(Collection<Equipment> equipment) {
            this.equipment.setAll(equipment);
        }

        @Override
        public ObservableList<Equipment> getPersonList() {
            return equipment;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
