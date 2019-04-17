package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalApparels.SHIRT1;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;

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
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.exceptions.DuplicateApparelException;
import seedu.address.testutil.ApparelBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FashionMatch addressBook = new FashionMatch();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getApparelList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        FashionMatch newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two apparels with the same identity fields
        Apparel editedAlice = new ApparelBuilder(SHIRT1).build();
        List<Apparel> newApparels = Arrays.asList(SHIRT1, editedAlice);
        FashionMatchStub newData = new FashionMatchStub(newApparels);

        thrown.expect(DuplicateApparelException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasApparel(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasApparel(SHIRT1));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addApparel(SHIRT1);
        assertTrue(addressBook.hasApparel(SHIRT1));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addApparel(SHIRT1);
        Apparel editedAlice = new ApparelBuilder(SHIRT1).build();
        assertTrue(addressBook.hasApparel(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getApparelList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addApparel(SHIRT1);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addApparel(SHIRT1);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFashionMatch whose apparels list can violate interface constraints.
     */
    private static class FashionMatchStub implements ReadOnlyFashionMatch {
        private final ObservableList<Apparel> apparels = FXCollections.observableArrayList();

        FashionMatchStub(Collection<Apparel> apparels) {
            this.apparels.setAll(apparels);
        }

        @Override
        public ObservableList<Apparel> getApparelList() {
            return apparels;
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
