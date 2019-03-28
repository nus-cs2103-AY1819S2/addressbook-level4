package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.travel.commons.core.GuiSettings;
import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.ReadOnlyUserPrefs;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.testutil.PlaceBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_placeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPlaceAdded modelStub = new ModelStubAcceptingPlaceAdded();
        Place validPlace = new PlaceBuilder().build();

        CommandResult commandResult = new AddCommand(validPlace).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPlace), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPlace), modelStub.placesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePlace_throwsCommandException() throws Exception {
        Place validPlace = new PlaceBuilder().build();
        AddCommand addCommand = new AddCommand(validPlace);
        ModelStub modelStub = new ModelStubWithPlace(validPlace);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PLACE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Place alice = new PlaceBuilder().withName("Alice").build();
        Place bob = new PlaceBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different place -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public Map<CountryCode, Integer> generateChartCountry() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<Rating, Integer> generateChartRating() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<String, Integer> generateChartYear() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTravelBuddyFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTravelBuddyFilePath(Path travelBuddyFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPlace(Place place) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTravelBuddy(ReadOnlyTravelBuddy newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTravelBuddy getTravelBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPlace(Place place) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePlace(Place target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPlace(Place target, Place editedPlace) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Place> getFilteredPlaceList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPlaceList(Predicate<Place> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTravelBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTravelBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTravelBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTravelBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTravelBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Place> selectedPlaceProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Place getSelectedPlace() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPlace(Place place) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single place.
     */
    private class ModelStubWithPlace extends ModelStub {
        private final Place place;

        ModelStubWithPlace(Place place) {
            requireNonNull(place);
            this.place = place;
        }

        @Override
        public boolean hasPlace(Place place) {
            requireNonNull(place);
            return this.place.isSamePlace(place);
        }
    }

    /**
     * A Model stub that always accept the place being added.
     */
    private class ModelStubAcceptingPlaceAdded extends ModelStub {
        final ArrayList<Place> placesAdded = new ArrayList<>();

        @Override
        public boolean hasPlace(Place place) {
            requireNonNull(place);
            return placesAdded.stream().anyMatch(place::isSamePlace);
        }

        @Override
        public void addPlace(Place place) {
            requireNonNull(place);
            placesAdded.add(place);
        }

        @Override
        public void commitTravelBuddy() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyTravelBuddy getTravelBuddy() {
            return new TravelBuddy();
        }
    }

}
