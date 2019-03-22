package seedu.travel.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_COUNTRY_CODE;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.NameContainsKeywordsPredicate;
import seedu.travel.model.place.Place;
import seedu.travel.testutil.EditPlaceDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMK = "Ang Mo Kio MRT";
    public static final String VALID_NAME_BEDOK = "Bedok MRT";
    public static final String VALID_NAME_CLEMENTI = "Clementi MRT";
    public static final String VALID_NAME_DG = "Dhoby Ghaut MRT";
    public static final String VALID_COUNTRY_CODE_AMK = "SGP";
    public static final String VALID_COUNTRY_CODE_BEDOK = "SGP";
    public static final String VALID_COUNTRY_CODE_CLEMENTI = "SGP";
    public static final String VALID_COUNTRY_CODE_DG = "SGP";
    public static final String VALID_COUNTRY_CODE_AMERICA = "USA";
    public static final String VALID_RATING_AMK = "5";
    public static final String VALID_RATING_BEDOK = "3";
    public static final String VALID_RATING_CLEMENTI = "4";
    public static final String VALID_RATING_DG = "2";
    public static final String VALID_DESCRIPTION_AMK = "An MRT Station in Ang Mo Kio";
    public static final String VALID_DESCRIPTION_BEDOK = "An MRT Station in Bedok";
    public static final String VALID_DESCRIPTION_CLEMENTI = "An MRT Station in Clementi";
    public static final String VALID_DESCRIPTION_DG = "An MRT Station in Dhoby Ghaut";
    public static final String VALID_ADDRESS_AMK = "2450 Ang Mo Kio Ave 8, Singapore 569811";
    public static final String VALID_ADDRESS_BEDOK = "315 New Upper Changi Road, Singapore 467347";
    public static final String VALID_ADDRESS_CLEMENTI = "3150 Commonwealth Avenue West, Singapore 129580";
    public static final String VALID_ADDRESS_DG = "11 Orchard Road, Singapore 238826";
    public static final String VALID_TAG_MRT = "mrt";
    public static final String VALID_TAG_EWL = "eastWestLine";

    public static final String NAME_DESC_AMK = " " + PREFIX_NAME + VALID_NAME_AMK;
    public static final String NAME_DESC_BEDOK = " " + PREFIX_NAME + VALID_NAME_BEDOK;
    public static final String NAME_DESC_CLEMENTI = " " + PREFIX_NAME + VALID_NAME_CLEMENTI;
    public static final String NAME_DESC_DG = " " + PREFIX_NAME + VALID_NAME_DG;
    public static final String COUNTRY_CODE_DESC_AMK = " " + PREFIX_COUNTRY_CODE + VALID_COUNTRY_CODE_AMK;
    public static final String COUNTRY_CODE_DESC_BEDOK = " " + PREFIX_COUNTRY_CODE + VALID_COUNTRY_CODE_BEDOK;
    public static final String COUNTRY_CODE_DESC_CLEMENTI = " " + PREFIX_COUNTRY_CODE + VALID_COUNTRY_CODE_CLEMENTI;
    public static final String COUNTRY_CODE_DESC_DG = " " + PREFIX_COUNTRY_CODE + VALID_COUNTRY_CODE_DG;
    public static final String RATING_DESC_AMK = " " + PREFIX_RATING + VALID_RATING_AMK;
    public static final String RATING_DESC_BEDOK = " " + PREFIX_RATING + VALID_RATING_BEDOK;
    public static final String RATING_DESC_CLEMENTI = " " + PREFIX_RATING + VALID_RATING_CLEMENTI;
    public static final String RATING_DESC_DG = " " + PREFIX_RATING + VALID_RATING_DG;
    public static final String DESCRIPTION_AMK = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMK;
    public static final String DESCRIPTION_BEDOK = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BEDOK;
    public static final String DESCRIPTION_CLEMENTI = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CLEMENTI;
    public static final String DESCRIPTION_DG = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DG;
    public static final String ADDRESS_DESC_AMK = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMK;
    public static final String ADDRESS_DESC_BEDOK = " " + PREFIX_ADDRESS + VALID_ADDRESS_BEDOK;
    public static final String ADDRESS_DESC_CLEMENTI = " " + PREFIX_ADDRESS + VALID_ADDRESS_CLEMENTI;
    public static final String ADDRESS_DESC_DG = " " + PREFIX_ADDRESS + VALID_ADDRESS_DG;
    public static final String TAG_DESC_MRT = " " + PREFIX_TAG + VALID_TAG_MRT;
    public static final String TAG_DESC_EWL = " " + PREFIX_TAG + VALID_TAG_EWL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_COUNTRY_CODE_DESC = " " + PREFIX_COUNTRY_CODE + "S1P"; // '1' not allowed in
    // names
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING + "4a"; // 'a' not allowed in ratings
    public static final String INVALID_DESCRIPTION = " " + PREFIX_DESCRIPTION + ".I love this place";
    // must begin with alphabet
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPlaceDescriptor DESC_AMK;
    public static final EditCommand.EditPlaceDescriptor DESC_BEDOK;

    static {
        DESC_AMK = new EditPlaceDescriptorBuilder().withName(VALID_NAME_AMK)
                .withCountryCode(VALID_COUNTRY_CODE_AMK)
                .withRating(VALID_RATING_AMK).withDescription(VALID_DESCRIPTION_AMK).withAddress(VALID_ADDRESS_AMK)
                .withTags(VALID_TAG_MRT).build();
        DESC_BEDOK = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BEDOK)
                .withCountryCode(VALID_COUNTRY_CODE_BEDOK)
                .withRating(VALID_RATING_BEDOK).withDescription(VALID_DESCRIPTION_BEDOK)
                .withAddress(VALID_ADDRESS_BEDOK).withTags(VALID_TAG_MRT, VALID_TAG_EWL).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the travel book, filtered place list and selected place in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TravelBuddy expectedTravelBuddy = new TravelBuddy(actualModel.getTravelBuddy());
        List<Place> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPlaceList());
        Place expectedSelectedPlace = actualModel.getSelectedPlace();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTravelBuddy, actualModel.getTravelBuddy());
            assertEquals(expectedFilteredList, actualModel.getFilteredPlaceList());
            assertEquals(expectedSelectedPlace, actualModel.getSelectedPlace());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the place at the given {@code targetIndex} in the
     * {@code model}'s travel book.
     */
    public static void showPlaceAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPlaceList().size());

        Place place = model.getFilteredPlaceList().get(targetIndex.getZeroBased());
        final String[] splitName = place.getName().fullName.split("\\s+");
        model.updateFilteredPlaceList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPlaceList().size());
    }

    /**
     * Deletes the first place in {@code model}'s filtered list from {@code model}'s travel book.
     */
    public static void deleteFirstPlace(Model model) {
        Place firstPlace = model.getFilteredPlaceList().get(0);
        model.deletePlace(firstPlace);
        model.commitTravelBuddy();
    }

}
