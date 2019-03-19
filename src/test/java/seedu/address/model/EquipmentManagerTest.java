package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.AMY;
import static seedu.address.testutil.TypicalEquipments.BOB;
import static seedu.address.testutil.TypicalEquipments.getTypicalAddressBook;

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
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.address.testutil.EquipmentBuilder;
import seedu.address.testutil.EquipmentManagerBuilder;

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
        Equipment editedAlice = new EquipmentBuilder(ACHORVALECC).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Equipment> newEquipments = Arrays.asList(ACHORVALECC, editedAlice);
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
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(equipmentManager.hasPerson(ACHORVALECC));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        equipmentManager.addPerson(ACHORVALECC);
        assertTrue(equipmentManager.hasPerson(ACHORVALECC));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        equipmentManager.addPerson(ACHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ACHORVALECC).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(equipmentManager.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        equipmentManager.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        equipmentManager.addListener(listener);
        equipmentManager.addPerson(ACHORVALECC);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        equipmentManager.addListener(listener);
        equipmentManager.removeListener(listener);
        equipmentManager.addPerson(ACHORVALECC);
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
