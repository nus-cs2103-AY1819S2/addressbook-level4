package seedu.travel.model.place;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BEDOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.model.place.exceptions.DuplicatePlaceException;
import seedu.travel.model.place.exceptions.PlaceNotFoundException;
import seedu.travel.testutil.PlaceBuilder;

public class UniquePlaceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniquePlaceList uniquePlaceList = new UniquePlaceList();

    @Test
    public void contains_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.contains(null);
    }

    @Test
    public void contains_placeNotInList_returnsFalse() {
        assertFalse(uniquePlaceList.contains(ALICE));
    }

    @Test
    public void contains_placeInList_returnsTrue() {
        uniquePlaceList.add(ALICE);
        assertTrue(uniquePlaceList.contains(ALICE));
    }

    @Test
    public void contains_placeWithSameIdentityFieldsInList_returnsTrue() {
        uniquePlaceList.add(ALICE);
        Place editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_EWL)
                .build();
        assertTrue(uniquePlaceList.contains(editedAlice));
    }

    @Test
    public void add_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.add(null);
    }

    @Test
    public void add_duplicatePlace_throwsDuplicatePlaceException() {
        uniquePlaceList.add(ALICE);
        thrown.expect(DuplicatePlaceException.class);
        uniquePlaceList.add(ALICE);
    }

    @Test
    public void setPlace_nullTargetPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.setPlace(null, ALICE);
    }

    @Test
    public void setPlace_nullEditedPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.setPlace(ALICE, null);
    }

    @Test
    public void setPlace_targetPlaceNotInList_throwsPlaceNotFoundException() {
        thrown.expect(PlaceNotFoundException.class);
        uniquePlaceList.setPlace(ALICE, ALICE);
    }

    @Test
    public void setPlace_editedPlaceIsSamePlace_success() {
        uniquePlaceList.add(ALICE);
        uniquePlaceList.setPlace(ALICE, ALICE);
        UniquePlaceList expectedUniquePlaceList = new UniquePlaceList();
        expectedUniquePlaceList.add(ALICE);
        assertEquals(expectedUniquePlaceList, uniquePlaceList);
    }

    @Test
    public void setPlace_editedPlaceHasSameIdentity_success() {
        uniquePlaceList.add(ALICE);
        Place editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_EWL)
                .build();
        uniquePlaceList.setPlace(ALICE, editedAlice);
        UniquePlaceList expectedUniquePlaceList = new UniquePlaceList();
        expectedUniquePlaceList.add(editedAlice);
        assertEquals(expectedUniquePlaceList, uniquePlaceList);
    }

    @Test
    public void setPlace_editedPlaceHasDifferentIdentity_success() {
        uniquePlaceList.add(ALICE);
        uniquePlaceList.setPlace(ALICE, BEDOK);
        UniquePlaceList expectedUniquePlaceList = new UniquePlaceList();
        expectedUniquePlaceList.add(BEDOK);
        assertEquals(expectedUniquePlaceList, uniquePlaceList);
    }

    @Test
    public void setPlace_editedPlaceHasNonUniqueIdentity_throwsDuplicatePlaceException() {
        uniquePlaceList.add(ALICE);
        uniquePlaceList.add(BEDOK);
        thrown.expect(DuplicatePlaceException.class);
        uniquePlaceList.setPlace(ALICE, BEDOK);
    }

    @Test
    public void remove_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.remove(null);
    }

    @Test
    public void remove_placeDoesNotExist_throwsPlaceNotFoundException() {
        thrown.expect(PlaceNotFoundException.class);
        uniquePlaceList.remove(ALICE);
    }

    @Test
    public void remove_existingPlace_removesPlace() {
        uniquePlaceList.add(ALICE);
        uniquePlaceList.remove(ALICE);
        UniquePlaceList expectedUniquePlaceList = new UniquePlaceList();
        assertEquals(expectedUniquePlaceList, uniquePlaceList);
    }

    @Test
    public void setPlaces_nullUniquePlaceList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.setPlaces((UniquePlaceList) null);
    }

    @Test
    public void setPlaces_uniquePlaceList_replacesOwnListWithProvidedUniquePlaceList() {
        uniquePlaceList.add(ALICE);
        UniquePlaceList expectedUniquePlaceList = new UniquePlaceList();
        expectedUniquePlaceList.add(BEDOK);
        uniquePlaceList.setPlaces(expectedUniquePlaceList);
        assertEquals(expectedUniquePlaceList, uniquePlaceList);
    }

    @Test
    public void setPlaces_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePlaceList.setPlaces((List<Place>) null);
    }

    @Test
    public void setPlaces_list_replacesOwnListWithProvidedList() {
        uniquePlaceList.add(ALICE);
        List<Place> placeList = Collections.singletonList(BEDOK);
        uniquePlaceList.setPlaces(placeList);
        UniquePlaceList expectedUniquePlaceList = new UniquePlaceList();
        expectedUniquePlaceList.add(BEDOK);
        assertEquals(expectedUniquePlaceList, uniquePlaceList);
    }

    @Test
    public void setPlaces_listWithDuplicatePlaces_throwsDuplicatePlaceException() {
        List<Place> listWithDuplicatePlaces = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePlaceException.class);
        uniquePlaceList.setPlaces(listWithDuplicatePlaces);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePlaceList.asUnmodifiableObservableList().remove(0);
    }
}
