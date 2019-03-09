package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMK;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DG;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_AMK;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMK;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CLEMENTI;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DG;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_AMK;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_DG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EWL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MRT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CLEMENTI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPlaces.BEDOK;
import static seedu.address.testutil.TypicalPlaces.DG;
import static seedu.address.testutil.TypicalPlaces.KEYWORD_MATCHING_SINGAPORE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.place.Address;
import seedu.address.model.place.Description;
import seedu.address.model.place.Name;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PlaceBuilder;
import seedu.address.testutil.PlaceUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BEDOK + "  "
                + RATING_DESC_BEDOK + " " + DESCRIPTION_BEDOK + "  " + ADDRESS_DESC_BEDOK + " " + TAG_DESC_EWL + " ";
        Place editedPlace = new PlaceBuilder(BEDOK).withTags(VALID_TAG_EWL).build();
        assertCommandSuccess(command, index, editedPlace);

        /* Case: undo editing the last place in the list -> last place restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last place in the list -> last place edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setPlace(getModel().getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPlace);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a place with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_BEDOK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT + TAG_DESC_EWL;
        assertCommandSuccess(command, index, BEDOK);

        /* Case: edit a place with new values same as another place's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getPlaceList().contains(BEDOK));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredPlaceList().get(index.getZeroBased()), BEDOK);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMK + RATING_DESC_BEDOK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT + TAG_DESC_EWL;
        editedPlace = new PlaceBuilder(BEDOK).withName(VALID_NAME_AMK).build();
        assertCommandSuccess(command, index, editedPlace);

        /* Case: edit a place with new values same as another place's values but with different rating and email
         * -> rejected
         */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_AMK
                + DESCRIPTION_AMK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT + TAG_DESC_EWL;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Place placeToEdit = getModel().getFilteredPlaceList().get(index.getZeroBased());
        editedPlace = new PlaceBuilder(placeToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPlace);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered place list, edit index within bounds of address book and place list -> edited */
        showPlacesWithName(KEYWORD_MATCHING_SINGAPORE);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPlaceList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_CLEMENTI;
        placeToEdit = getModel().getFilteredPlaceList().get(index.getZeroBased());
        editedPlace = new PlaceBuilder(placeToEdit).withName(VALID_NAME_CLEMENTI).build();
        assertCommandSuccess(command, index, editedPlace);

        /* Case: filtered place list, edit index within bounds of address book but out of bounds of place list
         * -> rejected
         */
        showPlacesWithName(KEYWORD_MATCHING_SINGAPORE);
        int invalidIndex = getModel().getAddressBook().getPlaceList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BEDOK,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a place card is selected -------------------------- */

        /* Case: selects first card in the place list, edit a place -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPlaces();
        index = INDEX_FIRST_PERSON;
        selectPlace(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_DG + RATING_DESC_DG
                + DESCRIPTION_DG + ADDRESS_DESC_DG;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new place's name
        assertCommandSuccess(command, index, DG, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BEDOK,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BEDOK,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPlaceList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BEDOK,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BEDOK,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid rating -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a place with new values same as another place's values -> rejected */
        executeCommand(PlaceUtil.getAddCommand(BEDOK));
        assertTrue(getModel().getAddressBook().getPlaceList().contains(BEDOK));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPlaceList().get(index.getZeroBased()).equals(BEDOK));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_BEDOK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT + TAG_DESC_EWL;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a place with new values same as another place's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_BEDOK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_EWL;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a place with new values same as another place's values but with different address -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_BEDOK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_AMK + TAG_DESC_MRT + TAG_DESC_EWL;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a place with new values same as another place's values but with different rating -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_AMK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT + TAG_DESC_EWL;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a place with new values same as another place's values but with different description ->
        rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BEDOK + RATING_DESC_BEDOK
                + DESCRIPTION_AMK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT + TAG_DESC_EWL;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Place, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Place, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Place editedPlace) {
        assertCommandSuccess(command, toEdit, editedPlace, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the place at index {@code toEdit} being
     * updated to values specified {@code editedPlace}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Place editedPlace,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setPlace(expectedModel.getFilteredPlaceList().get(toEdit.getZeroBased()), editedPlace);
        expectedModel.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPlace), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
