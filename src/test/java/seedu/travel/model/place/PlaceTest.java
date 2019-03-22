package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_COUNTRY_CODE_AMERICA;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BEDOK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.testutil.PlaceBuilder;

public class PlaceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Place place = new PlaceBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        place.getTags().remove(0);
    }

    @Test
    public void isSamePlace() {
        // same object -> returns true
        assertTrue(ALICE.isSamePlace(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePlace(null));

        // different name -> returns false
        Place editedAlice = new PlaceBuilder(ALICE).withName(VALID_NAME_BEDOK).build();
        assertFalse(ALICE.isSamePlace(editedAlice));

        // same name, different rating -> returns true
        editedAlice = new PlaceBuilder(ALICE).withRating(VALID_RATING_BEDOK).build();
        assertTrue(ALICE.isSamePlace(editedAlice));

        // same name, different description -> returns true
        editedAlice = new PlaceBuilder(ALICE).withDescription(VALID_DESCRIPTION_BEDOK).build();
        assertTrue(ALICE.isSamePlace(editedAlice));

        // same name, different travel -> returns true
        editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).build();
        assertTrue(ALICE.isSamePlace(editedAlice));

        // same name, different tags -> returns true
        editedAlice = new PlaceBuilder(ALICE).withTags(VALID_TAG_EWL).build();
        assertTrue(ALICE.isSamePlace(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Place aliceCopy = new PlaceBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different place -> returns false
        assertFalse(ALICE.equals(BEDOK));

        // different name -> returns false
        Place editedAlice = new PlaceBuilder(ALICE).withName(VALID_NAME_BEDOK).build();
        assertFalse(ALICE.equals(editedAlice));

        // different country code -> returns false
        editedAlice = new PlaceBuilder(ALICE).withCountryCode(VALID_COUNTRY_CODE_AMERICA).build();
        assertFalse(ALICE.equals(editedAlice));

        // different rating -> returns false
        editedAlice = new PlaceBuilder(ALICE).withRating(VALID_RATING_BEDOK).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new PlaceBuilder(ALICE).withDescription(VALID_DESCRIPTION_BEDOK).build();
        assertFalse(ALICE.equals(editedAlice));

        // different travel -> returns false
        editedAlice = new PlaceBuilder(ALICE).withAddress(VALID_ADDRESS_BEDOK).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PlaceBuilder(ALICE).withTags(VALID_TAG_EWL).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
