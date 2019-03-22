package seedu.travel.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

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
import seedu.travel.model.place.Place;
import seedu.travel.model.place.exceptions.DuplicatePlaceException;
import seedu.travel.testutil.PlaceBuilder;

public class TravelBuddyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TravelBuddy travelBuddy = new TravelBuddy();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), travelBuddy.getPlaceList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        travelBuddy.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTravelBuddy_replacesData() {
        TravelBuddy newData = getTypicalTravelBuddy();
        travelBuddy.resetData(newData);
        assertEquals(newData, travelBuddy);
    }

    @Test
    public void resetData_withDuplicatePlaces_throwsDuplicatePlaceException() {
        // Two places with the same identity fields
        Place editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_EWL)
                .build();
        List<Place> newPlaces = Arrays.asList(ALICE, editedAlice);
        TravelBuddyStub newData = new TravelBuddyStub(newPlaces);

        thrown.expect(DuplicatePlaceException.class);
        travelBuddy.resetData(newData);
    }

    @Test
    public void hasPlace_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        travelBuddy.hasPlace(null);
    }

    @Test
    public void hasPlace_placeNotInTravelBuddy_returnsFalse() {
        assertFalse(travelBuddy.hasPlace(ALICE));
    }

    @Test
    public void hasPlace_placeInTravelBuddy_returnsTrue() {
        travelBuddy.addPlace(ALICE);
        assertTrue(travelBuddy.hasPlace(ALICE));
    }

    @Test
    public void hasPlace_placeWithSameIdentityFieldsInTravelBuddy_returnsTrue() {
        travelBuddy.addPlace(ALICE);
        Place editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_EWL)
                .build();
        assertTrue(travelBuddy.hasPlace(editedAlice));
    }

    @Test
    public void getPlaceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        travelBuddy.getPlaceList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        travelBuddy.addListener(listener);
        travelBuddy.addPlace(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        travelBuddy.addListener(listener);
        travelBuddy.removeListener(listener);
        travelBuddy.addPlace(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyTravelBuddy whose places list can violate interface constraints.
     */
    private static class TravelBuddyStub implements ReadOnlyTravelBuddy {
        private final ObservableList<Place> places = FXCollections.observableArrayList();

        TravelBuddyStub(Collection<Place> places) {
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
