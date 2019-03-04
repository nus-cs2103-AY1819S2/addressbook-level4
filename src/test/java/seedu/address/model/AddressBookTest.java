package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.address.testutil.TypicalPlaces.ALICE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

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
import seedu.address.model.place.Place;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.testutil.PlaceBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPlaceList());
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
    public void resetData_withDuplicatePlaces_throwsDuplicatePlaceException() {
        // Two places with the same identity fields
        Place editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_EWL)
                .build();
        List<Place> newPlaces = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPlaces);

        thrown.expect(DuplicatePlaceException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPlace_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPlace(null);
    }

    @Test
    public void hasPlace_placeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPlace(ALICE));
    }

    @Test
    public void hasPlace_placeInAddressBook_returnsTrue() {
        addressBook.addPlace(ALICE);
        assertTrue(addressBook.hasPlace(ALICE));
    }

    @Test
    public void hasPlace_placeWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPlace(ALICE);
        Place editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_EWL)
                .build();
        assertTrue(addressBook.hasPlace(editedAlice));
    }

    @Test
    public void getPlaceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPlaceList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addPlace(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addPlace(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyAddressBook whose places list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Place> places = FXCollections.observableArrayList();

        AddressBookStub(Collection<Place> places) {
            this.places.setAll(places);
        }

        @Override
        public ObservableList<Place> getPlaceList() {
            return places;
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
