package systemtests;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_COUNTRY_CODE_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.NAME_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.NAME_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.TAG_DESC_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.TAG_DESC_MRT;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.AMK;
import static seedu.travel.testutil.TypicalPlaces.BEDOK;
import static seedu.travel.testutil.TypicalPlaces.CARL;
import static seedu.travel.testutil.TypicalPlaces.HOON;
import static seedu.travel.testutil.TypicalPlaces.IDA;
import static seedu.travel.testutil.TypicalPlaces.KEYWORD_MATCHING_SINGAPORE;

import org.junit.Test;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.commands.AddCommand;
import seedu.travel.logic.commands.RedoCommand;
import seedu.travel.logic.commands.UndoCommand;
import seedu.travel.model.Model;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;
import seedu.travel.testutil.PlaceBuilder;
import seedu.travel.testutil.PlaceUtil;

public class AddCommandSystemTest extends TravelBuddySystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a place without tags to a non-empty travel book, command with leading spaces and trailing spaces
         * -> added
         */
        Place toAdd = AMK;
        String command =
            "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMK + "  " + COUNTRY_CODE_DESC_AMK + "  "
                + RATING_DESC_AMK + "  "
                + DESCRIPTION_AMK + "   " + ADDRESS_DESC_AMK + "   " + TAG_DESC_MRT + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPlace(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a place with all fields same as another place in the travel book except name -> added */
        toAdd = new PlaceBuilder(AMK).withName(VALID_NAME_BEDOK).build();
        command =
            AddCommand.COMMAND_WORD + NAME_DESC_BEDOK + COUNTRY_CODE_DESC_AMK + RATING_DESC_AMK
                + DESCRIPTION_AMK + ADDRESS_DESC_AMK + TAG_DESC_MRT;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty travel book -> added */
        deleteAllPlaces();
        assertCommandSuccess(ALICE);

        /* Case: add a place with tags, command with parameters in random order -> added */
        toAdd = BEDOK;
        command =
            AddCommand.COMMAND_WORD + TAG_DESC_MRT + RATING_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK
                + ADDRESS_DESC_BEDOK + NAME_DESC_BEDOK + TAG_DESC_EWL + DESCRIPTION_BEDOK;
        assertCommandSuccess(command, toAdd);

        /* Case: add a place, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the place list before adding -> added */
        showPlacesWithName(KEYWORD_MATCHING_SINGAPORE);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a place card is selected --------------------------- */

        /* Case: selects first card in the place list, add a place -> added, card selection remains unchanged */
        selectPlace(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate place -> rejected */
        command = PlaceUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PLACE);

        /* Case: add a duplicate place except with different rating -> rejected */
        toAdd = new PlaceBuilder(HOON).withRating(VALID_RATING_BEDOK).build();
        command = PlaceUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PLACE);

        /* Case: add a duplicate place except with different description -> rejected */
        toAdd = new PlaceBuilder(HOON).withDescription(VALID_DESCRIPTION_BEDOK).build();
        command = PlaceUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PLACE);

        /* Case: add a duplicate place except with different travel -> rejected */
        toAdd = new PlaceBuilder(HOON).withAddress(VALID_ADDRESS_BEDOK).build();
        command = PlaceUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PLACE);

        /* Case: add a duplicate place except with different tags -> rejected */
        command = PlaceUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PLACE);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + RATING_DESC_AMK + DESCRIPTION_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing country code  -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + RATING_DESC_AMK + DESCRIPTION_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing rating -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + DESCRIPTION_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + RATING_DESC_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing travel -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + RATING_DESC_AMK + DESCRIPTION_AMK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PlaceUtil.getPlaceDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + COUNTRY_CODE_DESC_AMK + RATING_DESC_AMK
                + DESCRIPTION_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid country code -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + INVALID_COUNTRY_CODE_DESC + RATING_DESC_AMK
                + DESCRIPTION_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, CountryCode.MESSAGE_CONSTRAINTS);

        /* Case: invalid rating -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + COUNTRY_CODE_DESC_AMK
                + INVALID_RATING_DESC + DESCRIPTION_AMK + ADDRESS_DESC_AMK;
        assertCommandFailure(command, Rating.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + COUNTRY_CODE_DESC_AMK
                + RATING_DESC_AMK + INVALID_DESCRIPTION + ADDRESS_DESC_AMK;
        assertCommandFailure(command, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid travel -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + COUNTRY_CODE_DESC_AMK
                + RATING_DESC_AMK + DESCRIPTION_AMK + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMK + COUNTRY_CODE_DESC_AMK
                + RATING_DESC_AMK + DESCRIPTION_AMK + ADDRESS_DESC_AMK + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PlaceListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Place toAdd) {
        assertCommandSuccess(PlaceUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Place)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Place)
     */
    private void assertCommandSuccess(String command, Place toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPlace(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Place)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PlaceListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Place)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PlaceListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
