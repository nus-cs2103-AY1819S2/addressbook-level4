package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOTHING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.FashionMatch;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditApparelDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_A = "Armani";
    public static final String VALID_NAME_B = "Bobohoo";
    public static final String VALID_COLOR_GREEN = "Green";
    public static final String VALID_COLOR_BLUE = "Blue";
    public static final String VALID_TYPE_TOP = "Top";
    public static final String VALID_TYPE_BOTTOM = "Bottom";
    public static final String VALID_TYPE_BELT = "BELT";
    public static final String VALID_TYPE_SHOES = "SHOES";

    public static final String INPUT_COLOR_BLUE = " " + PREFIX_COLOR + VALID_COLOR_BLUE;
    public static final String INPUT_COLOR_GREEN = " " + PREFIX_COLOR + VALID_COLOR_GREEN;
    public static final String INPUT_NAME_A = " " + PREFIX_NAME + VALID_NAME_A;
    public static final String INPUT_NAME_B = " " + PREFIX_NAME + VALID_NAME_B;
    public static final String INPUT_TYPE_TOP = " " + PREFIX_CLOTHING_TYPE + VALID_TYPE_TOP;
    public static final String INPUT_TYPE_BOTTOM = " " + PREFIX_CLOTHING_TYPE + VALID_TYPE_BOTTOM;

    public static final String INVALID_INPUT_NAME = " " + PREFIX_NAME + "Amames&"; // '&' not allowed in names
    public static final String INVALID_INPUT_COLOR = " " + PREFIX_COLOR + "Blue1"; // '1', digits not allowed in colors
    public static final String INVALID_INPUT_TYPE = " " + PREFIX_CLOTHING_TYPE + "Necklace"; // not supported yet

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_ARMANI;
    public static final EditCommand.EditPersonDescriptor DESC_BOBOHOO;

    static {
        DESC_ARMANI = new EditApparelDescriptorBuilder().withName(VALID_NAME_A)
                .withColor(VALID_COLOR_GREEN).withClothingType(VALID_TYPE_TOP).build();
        DESC_BOBOHOO = new EditApparelDescriptorBuilder().withName(VALID_NAME_B)
                .withColor(VALID_COLOR_BLUE).withClothingType(VALID_TYPE_BOTTOM).build();
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
            System.out.println(result.getFeedbackToUser());
            System.out.println(expectedCommandResult.getFeedbackToUser());
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches any of the {@code randomCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult[] randomCommandResults, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            System.out.println("result:");
            System.out.println(result.getFeedbackToUser());

            boolean match = false;
            for (CommandResult anyRandomCommandResult : randomCommandResults) {
                System.out.println(anyRandomCommandResult.getFeedbackToUser());
                if (result.equals(anyRandomCommandResult)) {
                    match = true;
                }
            }

            if (!match) {
                Assert.fail("Result does not match with any of the random Messages from wash command.");
            }

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
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult[], Model)}
     * that takes a string array {@code randomMessagesMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String[] randomMessages, Model expectedModel) {
        CommandResult[] randomCommandResults = new CommandResult[randomMessages.length];
        int i = 0;
        for (String randomMessage : randomMessages) {
            randomCommandResults[i] = new CommandResult(randomMessage);
            i = i + 1;
        }
        assertCommandSuccess(command, actualModel, actualCommandHistory, randomCommandResults, expectedModel);
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

    /**
     * Updates {@code model}'s filtered list to show only the apparel at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showApparelAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredApparelList().size());

        Apparel apparel = model.getFilteredApparelList().get(targetIndex.getZeroBased());
        System.out.println("apparel = " + apparel.toString());

        final String[] splitName = apparel.getName().fullName.split("\\s+");
        for (String s : splitName) {
            System.out.println("s = " + s);
        }
        model.updateFilteredApparelList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        for (Apparel a : model.getFilteredApparelList()) {
            System.out.println(a.toString());
        }

        assertEquals(1, model.getFilteredApparelList().size());
    }

    /**
     * Deletes the first apparel in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Apparel firstApparel = model.getFilteredApparelList().get(0);
        model.deleteApparel(firstApparel);
        model.commitAddressBook();
    }

    /**
     * that takes two strings that are {@code expectedMessage}.
     * if one is true assert true otherwise false
     */
    public static void assertCommandSuccessTwoSoln(Command command, Model actualModel,
            CommandHistory actualCommandHistory, String expectedMessage1,
            String expectedMessage2, Model expectedModel) {
        CommandResult expectedCommandResult1 = new CommandResult(expectedMessage1);
        CommandResult expectedCommandResult2 = new CommandResult(expectedMessage2);

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            if (expectedCommandResult1.equals(result) || expectedCommandResult2.equals(result)) {
                assert true;
            } else {
                assert false;
            }
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
