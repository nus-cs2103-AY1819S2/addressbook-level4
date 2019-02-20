package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

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
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.testutil.MedicineBuilder;

public class InventoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Inventory Inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), Inventory.getMedicineList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Inventory.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = getTypicalInventory();
        Inventory.resetData(newData);
        assertEquals(newData, Inventory);
    }

    @Test
    public void resetData_withDuplicateMedicines_throwsDuplicateMedicineException() {
        // Two medicines with the same identity fields
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, editedParacetamol);
        InventoryStub newData = new InventoryStub(newMedicines);

        thrown.expect(DuplicateMedicineException.class);
        Inventory.resetData(newData);
    }

    @Test
    public void hasMedicine_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Inventory.hasMedicine(null);
    }

    @Test
    public void hasMedicine_medicineNotInInventory_returnsFalse() {
        assertFalse(Inventory.hasMedicine(PARACETAMOL));
    }

    @Test
    public void hasMedicine_medicineInInventory_returnsTrue() {
        Inventory.addMedicine(PARACETAMOL);
        assertTrue(Inventory.hasMedicine(PARACETAMOL));
    }

    @Test
    public void hasMedicine_medicineWithSameIdentityFieldsInInventory_returnsTrue() {
        Inventory.addMedicine(PARACETAMOL);
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(Inventory.hasMedicine(editedParacetamol));
    }

    @Test
    public void getMedicineList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        Inventory.getMedicineList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        Inventory.addListener(listener);
        Inventory.addMedicine(PARACETAMOL);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        Inventory.addListener(listener);
        Inventory.removeListener(listener);
        Inventory.addMedicine(PARACETAMOL);
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
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
