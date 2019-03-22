package seedu.travel.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.logic.commands.CommandTestUtil.showPlaceAtIndex;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Test;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.UserPrefs;
import seedu.travel.model.place.Place;
import seedu.travel.testutil.EditPlaceDescriptorBuilder;
import seedu.travel.testutil.PlaceBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Place editedPlace = new PlaceBuilder().build();
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(editedPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLACE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new TravelBuddy(model.getTravelBuddy()), new UserPrefs());
        expectedModel.setPlace(model.getFilteredPlaceList().get(0), editedPlace);
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPlace = Index.fromOneBased(model.getFilteredPlaceList().size());
        Place lastPlace = model.getFilteredPlaceList().get(indexLastPlace.getZeroBased());

        PlaceBuilder placeInList = new PlaceBuilder(lastPlace);
        Place editedPlace = placeInList.withName(VALID_NAME_BEDOK).withRating(VALID_RATING_BEDOK)
                .withTags(VALID_TAG_EWL).build();

        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK)
                .withRating(VALID_RATING_BEDOK).withTags(VALID_TAG_EWL).build();
        EditCommand editCommand = new EditCommand(indexLastPlace, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new TravelBuddy(model.getTravelBuddy()), new UserPrefs());
        expectedModel.setPlace(lastPlace, editedPlace);
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLACE, new EditPlaceDescriptor());
        Place editedPlace = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new TravelBuddy(model.getTravelBuddy()), new UserPrefs());
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);

        Place placeInFilteredList = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        Place editedPlace = new PlaceBuilder(placeInFilteredList).withName(VALID_NAME_BEDOK).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLACE,
                new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new TravelBuddy(model.getTravelBuddy()), new UserPrefs());
        expectedModel.setPlace(model.getFilteredPlaceList().get(0), editedPlace);
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePlaceUnfilteredList_failure() {
        Place firstPlace = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(firstPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PLACE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PLACE);
    }

    @Test
    public void execute_duplicatePlaceFilteredList_failure() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);

        // edit place in filtered list into a duplicate in travel book
        Place placeInList = model.getTravelBuddy().getPlaceList().get(INDEX_SECOND_PLACE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLACE,
                new EditPlaceDescriptorBuilder(placeInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PLACE);
    }

    @Test
    public void execute_invalidPlaceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of travel book
     */
    @Test
    public void execute_invalidPlaceIndexFilteredList_failure() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);
        Index outOfBoundIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of travel book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTravelBuddy().getPlaceList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Place editedPlace = new PlaceBuilder().build();
        Place placeToEdit = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(editedPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLACE, descriptor);
        Model expectedModel = new ModelManager(new TravelBuddy(model.getTravelBuddy()), new UserPrefs());
        expectedModel.setPlace(placeToEdit, editedPlace);
        expectedModel.commitTravelBuddy();

        // edit -> first place edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts travelBuddy back to previous state and filtered place list to show all places
        expectedModel.undoTravelBuddy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first place edited again
        expectedModel.redoTravelBuddy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> travel book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);

        // single travel book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Place} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited place in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the place object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePlaceEdited() throws Exception {
        Place editedPlace = new PlaceBuilder().build();
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(editedPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLACE, descriptor);
        Model expectedModel = new ModelManager(new TravelBuddy(model.getTravelBuddy()), new UserPrefs());

        showPlaceAtIndex(model, INDEX_SECOND_PLACE);
        Place placeToEdit = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        expectedModel.setPlace(placeToEdit, editedPlace);
        expectedModel.commitTravelBuddy();

        // edit -> edits second place in unfiltered place list / first place in filtered place list
        editCommand.execute(model, commandHistory);

        // undo -> reverts TravelBuddy back to previous state and filtered place list to show all places
        expectedModel.undoTravelBuddy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased()), placeToEdit);
        // redo -> edits same second place in unfiltered place list
        expectedModel.redoTravelBuddy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PLACE, DESC_AMK);

        // same values -> returns true
        EditPlaceDescriptor copyDescriptor = new EditPlaceDescriptor(DESC_AMK);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PLACE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PLACE, DESC_AMK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PLACE, DESC_BEDOK)));
    }

}
