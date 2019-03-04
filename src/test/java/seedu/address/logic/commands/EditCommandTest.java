package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPlaceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.Place;
import seedu.address.testutil.EditPlaceDescriptorBuilder;
import seedu.address.testutil.PlaceBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Place editedPlace = new PlaceBuilder().build();
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(editedPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPlace(model.getFilteredPlaceList().get(0), editedPlace);
        expectedModel.commitAddressBook();

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

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPlace(lastPlace, editedPlace);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPlaceDescriptor());
        Place editedPlace = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPlaceAtIndex(model, INDEX_FIRST_PERSON);

        Place placeInFilteredList = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        Place editedPlace = new PlaceBuilder(placeInFilteredList).withName(VALID_NAME_BEDOK).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPlace(model.getFilteredPlaceList().get(0), editedPlace);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePlaceUnfilteredList_failure() {
        Place firstPlace = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(firstPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePlaceFilteredList_failure() {
        showPlaceAtIndex(model, INDEX_FIRST_PERSON);

        // edit place in filtered list into a duplicate in address book
        Place placeInList = model.getAddressBook().getPlaceList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPlaceDescriptorBuilder(placeInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPlaceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPlaceIndexFilteredList_failure() {
        showPlaceAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Place editedPlace = new PlaceBuilder().build();
        Place placeToEdit = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(editedPlace).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPlace(placeToEdit, editedPlace);
        expectedModel.commitAddressBook();

        // edit -> first place edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered place list to show all places
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first place edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
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
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPlaceAtIndex(model, INDEX_SECOND_PERSON);
        Place placeToEdit = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPlace(placeToEdit, editedPlace);
        expectedModel.commitAddressBook();

        // edit -> edits second place in unfiltered place list / first place in filtered place list
        editCommand.execute(model, commandHistory);

        // undo -> reverts TravelBuddy back to previous state and filtered place list to show all places
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased()), placeToEdit);
        // redo -> edits same second place in unfiltered place list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMK);

        // same values -> returns true
        EditPlaceDescriptor copyDescriptor = new EditPlaceDescriptor(DESC_AMK);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BEDOK)));
    }

}
