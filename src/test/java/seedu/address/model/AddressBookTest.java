package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalMedicines.ALICE;
import static seedu.address.testutil.TypicalMedicines.getTypicalAddressBook;

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

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getMedicineList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateMedicines_throwsDuplicateMedicineException() {
        // Two medicines with the same identity fields
        Medicine editedAlice = new MedicineBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Medicine> newMedicines = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newMedicines);

        thrown.expect(DuplicateMedicineException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasMedicine_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasMedicine(null);
    }

    @Test
    public void hasMedicine_medicineNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasMedicine(ALICE));
    }

    @Test
    public void hasMedicine_medicineInAddressBook_returnsTrue() {
        addressBook.addMedicine(ALICE);
        assertTrue(addressBook.hasMedicine(ALICE));
    }

    @Test
    public void hasMedicine_medicineWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addMedicine(ALICE);
        Medicine editedAlice = new MedicineBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasMedicine(editedAlice));
    }

    @Test
    public void getMedicineList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getMedicineList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addMedicine(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addMedicine(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyAddressBook whose medicines list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Medicine> medicines = FXCollections.observableArrayList();

        AddressBookStub(Collection<Medicine> medicines) {
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
