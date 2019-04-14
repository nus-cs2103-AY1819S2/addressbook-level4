package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardCollection;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_FRONTFACE_GOOD = "Good";
    public static final String VALID_BACKFACE_GOOD = "Anjir";
    public static final String VALID_FRONTFACE_DUCK = "Duck";
    public static final String VALID_FRONTFACE_HITBAG = "Hit Bag";
    public static final String VALID_BACKFACE_HITBAG = "打包";
    public static final String VALID_TAG_INDONESIAN = "indonesian";
    public static final String VALID_TAG_CHINESE = "chinese";

    public static final Optional<String> VALID_IMAGE_NONE = Optional.empty();

    public static final String FRONTFACE_DESC_GOOD = " " + PREFIX_FRONT_FACE + VALID_FRONTFACE_GOOD;
    public static final String BACKFACE_DESC_GOOD = " " + PREFIX_BACK_FACE + VALID_BACKFACE_GOOD;
    public static final String FRONTFACE_DESC_HITBAG = " " + PREFIX_FRONT_FACE + VALID_FRONTFACE_HITBAG;
    public static final String BACKFACE_DESC_HITBAG = " " + PREFIX_BACK_FACE + VALID_BACKFACE_HITBAG;
    public static final String FRONTFACE_DESC_DUCK = " " + PREFIX_FRONT_FACE + VALID_FRONTFACE_DUCK;

    public static final String TAG_DESC_INDONESIAN = " " + PREFIX_TAG + VALID_TAG_INDONESIAN;
    public static final String TAG_DESC_CHINESE = " " + PREFIX_TAG + VALID_TAG_CHINESE;


    public static final String INVALID_FRONTFACE_DESC = " "
        + PREFIX_FRONT_FACE
        + " "; // empty string not allowed for face
    public static final String INVALID_BACKFACE_DESC = " "
        + PREFIX_BACK_FACE
        + " "; // empty string not allowed for face
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "physics*"; // '*' not allowed in tags
    public static final String INVALID_PREFIX_UNDEFINED = "/a";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_GOOD;
    public static final EditCommand.EditFlashcardDescriptor DESC_HITBAG;

    static {
        DESC_GOOD = new EditFlashcardDescriptorBuilder().withFrontFace(VALID_FRONTFACE_GOOD)
            .withBackFace(VALID_BACKFACE_GOOD).withTags(VALID_TAG_INDONESIAN).build();
        DESC_HITBAG = new EditFlashcardDescriptorBuilder().withFrontFace(VALID_FRONTFACE_HITBAG)
            .withBackFace(VALID_BACKFACE_HITBAG).withTags(VALID_TAG_CHINESE).build();
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
     * - the card collection, filtered flashcard list and selected flashcard in {@code actualModel} remain unchanged
     * <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CardCollection expectedCardCollection = new CardCollection(actualModel.getCardCollection());
        List<Flashcard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashcardList());
        Flashcard expectedSelectedFlashcard = actualModel.getSelectedFlashcard();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCardCollection, actualModel.getCardCollection());
            assertEquals(expectedFilteredList, actualModel.getFilteredFlashcardList());
            assertEquals(expectedSelectedFlashcard, actualModel.getSelectedFlashcard());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the flashcard at the given {@code targetIndex} in the
     * {@code model}'s card collection.
     */
    public static void showFlashcardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Flashcard flashcard = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitFront = flashcard.getFrontFace().text.split("\\s+");
        final String[] splitBack = flashcard.getBackFace().text.split("\\s+");
        final Set<Tag> tagSet = flashcard.getTags();
        ArrayList<String> splitTag = new ArrayList<>();
        for (Tag tag : tagSet) {
            splitTag.add(tag.tagName);
        }

        model.updateFilteredFlashcardList(
            new FlashcardContainsKeywordsPredicate(Arrays.asList(splitFront), Arrays.asList(splitBack), splitTag));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }

    /**
     * Deletes the first flashcard in {@code model}'s filtered list from {@code model}'s card collection.
     */
    public static void deleteFirstFlashcard(Model model) {
        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(0);
        model.deleteFlashcard(firstFlashcard);
        model.commitCardCollection("delete 1");
    }

}
