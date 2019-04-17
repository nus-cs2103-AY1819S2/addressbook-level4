package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FashionMatch;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;
import seedu.address.testutil.AvailableApparelDescriptorBuilder;
import seedu.address.testutil.UnavailableApparelDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtilExtra {

    public static final String VALID_NAME_A = "Armani";
    public static final String VALID_NAME_B = "Bobohoo";
    public static final String VALID_COLOR_GREEN = "Green";
    public static final String VALID_COLOR_BLUE = "Blue";
    public static final String VALID_COLOR_RED = "Red";
    public static final String VALID_COLOR_BLACK = "Black";
    public static final String VALID_TYPE_TOP = "Top";
    public static final String VALID_TYPE_BOTTOM = "Bottom";

    public static final WashCommand.AvailablePersonDescriptor DESC_ANDY;
    public static final WashCommand.AvailablePersonDescriptor DESC_BOBBY;
    public static final WearCommand.UnavailablePersonDescriptor DESC_CONNY;
    public static final WearCommand.UnavailablePersonDescriptor DESC_DENDI;

    static {
        DESC_ANDY = new AvailableApparelDescriptorBuilder().withName(VALID_NAME_A)
                .withColor(VALID_COLOR_GREEN).withClothingType(VALID_TYPE_TOP).build();
        DESC_BOBBY = new AvailableApparelDescriptorBuilder().withName(VALID_NAME_B)
                .withColor(VALID_COLOR_BLUE).withClothingType(VALID_TYPE_BOTTOM).build();
        DESC_CONNY = new UnavailableApparelDescriptorBuilder().withName(VALID_NAME_A)
                .withColor(VALID_COLOR_RED).withClothingType(VALID_TYPE_TOP).build();
        DESC_DENDI = new UnavailableApparelDescriptorBuilder().withName(VALID_NAME_B)
                .withColor(VALID_COLOR_BLACK).withClothingType(VALID_TYPE_BOTTOM).build();
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
     * - the address book, filtered apparel list and selected apparel in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FashionMatch expectedAddressBook = new FashionMatch(actualModel.getAddressBook());
        List<Apparel> expectedFilteredList = new ArrayList<>(actualModel.getFilteredApparelList());
        Apparel expectedSelectedApparel = actualModel.getSelectedApparel();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredApparelList());
            assertEquals(expectedSelectedApparel, actualModel.getSelectedApparel());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }
}
