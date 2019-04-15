package seedu.equipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;

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
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
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
        assertEquals(Collections.emptyList(), equipmentManager.getEquipmentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEquipmentManager_replacesData() {
        EquipmentManager newData = getTypicalEquipmentManager();
        equipmentManager.resetData(newData);
        assertEquals(newData, equipmentManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicateEquipmentException() {
        // Two equipment with the same identity fields
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Equipment> newEquipments = Arrays.asList(ANCHORVALECC, editedAlice);
        EquipmentManagerStub newData = new EquipmentManagerStub(newEquipments);

        thrown.expect(DuplicateEquipmentException.class);
        equipmentManager.resetData(newData);
    }

    @Test
    public void hasEquipment_nullEquipment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.hasEquipment(null);
    }

    @Test
    public void hasWorkList_nullWorkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.hasWorkList(null);
    }

    @Test
    public void hasEquipmentWithSerialNumber_nullSerialNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.hasEquipmentWithSerialNumber(null);
    }

    @Test
    public void hasEquipmentWithSerialNumber_equipmentNotInEquipmentManager_returnsFalse() {
        assertFalse(equipmentManager.hasEquipmentWithSerialNumber(ANCHORVALECC.getSerialNumber()));
    }

    @Test
    public void hasEquipment_equipmentNotInEquipmentManager_returnsFalse() {
        assertFalse(equipmentManager.hasEquipment(ANCHORVALECC));
    }
    @Test
    public void hasWorkList_workListNotInEquipmentManager_returnsFalse() {
        assertFalse(equipmentManager.hasWorkList(LISTA));
    }

    @Test
    public void hasEquipment_equipmentInEquipmentManager_returnsTrue() {
        equipmentManager.addPerson(ANCHORVALECC);
        assertTrue(equipmentManager.hasEquipment(ANCHORVALECC));
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
    public void putEquipment_nullSerialNumberAndId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.putEquipment(null, null);
    }

    @Test
    public void removeEquipment_nullSerialNumberAndId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        equipmentManager.removeEquipment(null, null);
    }

    @Test
    public void hasEquipment_equipmentWithSameIdentityFieldsInEquipmentManager_returnsTrue() {
        equipmentManager.addPerson(ANCHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(equipmentManager.hasEquipment(editedAlice));
    }

    @Test
    public void getEquipmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        equipmentManager.getEquipmentList().remove(0);
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
        private final ObservableList<WorkList> workLists = FXCollections.observableArrayList();
        private final ObservableList<Name> name = FXCollections.observableArrayList();

        EquipmentManagerStub(Collection<Equipment> equipment) {
            this.equipment.setAll(equipment);
        }

        @Override
        public ObservableList<Equipment> getEquipmentList() {
            return equipment;
        }

        @Override
        public ObservableList<WorkList> getWorkListList() {
            return workLists;
        }

        @Override
        public ObservableList<Name> getClientList() {
            return name;
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
