package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.testutil.MedicineBuilder;

public class InventoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getMedicineList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetData_withDuplicateMedicines_throwsDuplicateMedicineException() {
        // Two medicines with the same identity fields
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withTags(VALID_TAG_PAINKILLER).build();
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, editedParacetamol);
        InventoryStub newData = new InventoryStub(newMedicines);

        thrown.expect(DuplicateMedicineException.class);
        inventory.resetData(newData);
    }

    @Test
    public void hasMedicine_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.hasMedicine(null);
    }

    @Test
    public void hasMedicine_medicineNotInInventory_returnsFalse() {
        assertFalse(inventory.hasMedicine(PARACETAMOL));
    }

    @Test
    public void hasMedicine_medicineInInventory_returnsTrue() {
        inventory.addMedicine(PARACETAMOL);
        assertTrue(inventory.hasMedicine(PARACETAMOL));
    }

    @Test
    public void hasMedicine_medicineWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addMedicine(PARACETAMOL);
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withExpiry(VALID_EXPIRY_AMOXICILLIN)
                .withTags(VALID_TAG_PAINKILLER).build();
        assertTrue(inventory.hasMedicine(editedParacetamol));
    }

    @Test
    public void getMedicineList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventory.getMedicineList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        inventory.addListener(listener);
        inventory.addMedicine(PARACETAMOL);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        inventory.addListener(listener);
        inventory.removeListener(listener);
        inventory.addMedicine(PARACETAMOL);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyInventory whose medicines list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Medicine> medicines = FXCollections.observableArrayList();

        InventoryStub(Collection<Medicine> medicines) {
            this.medicines.setAll(medicines);
        }

        @Override
        public ObservableList<Medicine> getMedicineList() {
            return medicines;
        }

        @Override
        public ObservableList<Medicine> getSortedMedicineList(Comparator<Medicine> comparator) {
            return new SortedList<>(medicines, comparator);
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
