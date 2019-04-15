package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.testutil.TypicalFlashcards.EAT;
import static seedu.address.testutil.TypicalFlashcards.GOOD;
import static seedu.address.testutil.TypicalFlashcards.HELLO;
import static seedu.address.testutil.TypicalFlashcards.KEYWORD_MATCHING_GOOD;
import static seedu.address.testutil.TypicalFlashcards.NEWTON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Statistics;

public class StatsCommandSystemTest extends CardCollectionSystemTest {

    @Test
    public void stats() {
        /* Case: find statistics of multiple flashcards in card collection, command with leading spaces and
         * trailing spaces
         */
        String command = "   " + StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + KEYWORD_MATCHING_GOOD + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, GOOD);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous stats command where flashcard list is displaying the flashcards we are finding */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + KEYWORD_MATCHING_GOOD;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of flashcard where flashcard list is not displaying the flashcard we are finding */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " Eat";
        ModelHelper.setFilteredList(expectedModel, EAT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of multiple flashcards in card collection, 2 keywords */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + "Eat Newton's";
        ModelHelper.setFilteredList(expectedModel, EAT, NEWTON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of multiple flashcards in card collection, 2 keywords in reversed order */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + "Newton's Eat";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of multiple flashcards in card collection, 2 keywords with 1 repeat */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + "Newton's Eat Newton's";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of multiple flashcards in card collection, 2 matching keywords and
         * 1 non-matching keyword
         */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + "Newton's Eat Cow";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous stats command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous stats command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find statistics of same flashcards in card collection after deleting 1 of them */
        executeCommand(StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " Newton's");
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getCardCollection().getFlashcardList().contains(NEWTON));
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + KEYWORD_MATCHING_GOOD;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, GOOD);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of flashcard in card collection, keyword is same as name but of different case */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " GoOd";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of flashcard in card collection, keyword is substring of name */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " Hell";
        ModelHelper.setFilteredList(expectedModel, HELLO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of flashcard in card collection, name is substring of keyword */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " Hellow";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of flashcard not in card collection */
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " LUL";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find statistics of while a flashcard is selected */
        showAllFlashcards();
        selectFlashcard(Index.fromOneBased(1));
        assertFalse(getFlashcardListPanel().getHandleToSelectedCard().getFrontFace().equals(GOOD.getFrontFace().text));
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + " Good";
        ModelHelper.setFilteredList(expectedModel, GOOD);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find statistics of flashcard in empty card collection */
        deleteAllFlashcards();
        command = StatsCommand.COMMAND_WORD + " " + PREFIX_FRONT_FACE + KEYWORD_MATCHING_GOOD;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, GOOD);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "StaTs Hello";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_FLASHCARDS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                StatsCommand.MESSAGE_STATISTICS_FORMAT,
                expectedModel.getFilteredFlashcardList()
                        .stream()
                        .map(Flashcard::getStatistics)
                        .reduce(Statistics::merge)
                        .map(s -> s.getSuccessRate() * 100)
                        .orElse(0.0)
        );

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
