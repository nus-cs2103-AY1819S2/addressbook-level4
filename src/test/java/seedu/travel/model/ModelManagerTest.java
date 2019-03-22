package seedu.travel.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.model.Model.PREDICATE_SHOW_ALL_PLACES;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BEDOK;
import static seedu.travel.testutil.TypicalPlaces.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.commons.core.GuiSettings;
import seedu.travel.model.place.NameContainsKeywordsPredicate;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.exceptions.PlaceNotFoundException;
import seedu.travel.testutil.PlaceBuilder;
import seedu.travel.testutil.TravelBuddyBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TravelBuddy(), new TravelBuddy(modelManager.getTravelBuddy()));
        assertEquals(null, modelManager.getSelectedPlace());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTravelBuddyFilePath(Paths.get("travel/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTravelBuddyFilePath(Paths.get("new/travel/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTravelBuddyFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setTravelBuddyFilePath(null);
    }

    @Test
    public void setTravelBuddyFilePath_validPath_setsTravelBuddyFilePath() {
        Path path = Paths.get("travel/book/file/path");
        modelManager.setTravelBuddyFilePath(path);
        assertEquals(path, modelManager.getTravelBuddyFilePath());
    }

    @Test
    public void hasPlace_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPlace(null);
    }

    @Test
    public void hasPlace_placeNotInTravelBuddy_returnsFalse() {
        assertFalse(modelManager.hasPlace(ALICE));
    }

    @Test
    public void hasPlace_placeInTravelBuddy_returnsTrue() {
        modelManager.addPlace(ALICE);
        assertTrue(modelManager.hasPlace(ALICE));
    }

    @Test
    public void deletePlace_placeIsSelectedAndFirstPlaceInFilteredPlaceList_selectionCleared() {
        modelManager.addPlace(ALICE);
        modelManager.setSelectedPlace(ALICE);
        modelManager.deletePlace(ALICE);
        assertEquals(null, modelManager.getSelectedPlace());
    }

    @Test
    public void deletePlace_placeIsSelectedAndSecondPlaceInFilteredPlaceList_firstPlaceSelected() {
        modelManager.addPlace(ALICE);
        modelManager.addPlace(BEDOK);
        assertEquals(Arrays.asList(ALICE, BEDOK), modelManager.getFilteredPlaceList());
        modelManager.setSelectedPlace(BEDOK);
        modelManager.deletePlace(BEDOK);
        assertEquals(ALICE, modelManager.getSelectedPlace());
    }

    @Test
    public void setPlace_placeIsSelected_selectedPlaceUpdated() {
        modelManager.addPlace(ALICE);
        modelManager.setSelectedPlace(ALICE);
        Place updatedAlice = new PlaceBuilder(ALICE).withDescription(VALID_DESCRIPTION_BEDOK).build();
        modelManager.setPlace(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedPlace());
    }

    @Test
    public void getFilteredPlaceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPlaceList().remove(0);
    }

    @Test
    public void setSelectedPlace_placeNotInFilteredPlaceList_throwsPlaceNotFoundException() {
        thrown.expect(PlaceNotFoundException.class);
        modelManager.setSelectedPlace(ALICE);
    }

    @Test
    public void setSelectedPlace_placeInFilteredPlaceList_setsSelectedPlace() {
        modelManager.addPlace(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredPlaceList());
        modelManager.setSelectedPlace(ALICE);
        assertEquals(ALICE, modelManager.getSelectedPlace());
    }

    @Test
    public void equals() {
        TravelBuddy travelBuddy = new TravelBuddyBuilder().withPlace(ALICE).withPlace(BENSON).build();
        TravelBuddy differentTravelBuddy = new TravelBuddy();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(travelBuddy, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(travelBuddy, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different travelBuddy -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTravelBuddy, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPlaceList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(travelBuddy, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTravelBuddyFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(travelBuddy, differentUserPrefs)));
    }
}
