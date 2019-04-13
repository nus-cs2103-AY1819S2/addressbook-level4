package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;

import org.junit.Test;

import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.commands.AnswerCommand;
import seedu.knowitall.logic.commands.EndCommand;
import seedu.knowitall.logic.commands.NextCommand;
import seedu.knowitall.logic.commands.ReportCommand;
import seedu.knowitall.logic.commands.SortCommand;
import seedu.knowitall.logic.commands.TestCommand;
import seedu.knowitall.model.Model;
import seedu.knowitall.testutil.TypicalCards;

public class TestCommandSystemTest extends CardFolderSystemTest {

    @Test
    public void test() {
        Model expectedModel = getModel();

        /* ----------------- Performing test operations ---------------------- */

        /* Case: test command with leading spaces, trailing spaces
         * -> enter test
         */
        String command = " " + TestCommand.COMMAND_WORD + "  ";
        expectedModel.testCardFolder();
        assertCommandSuccess(command, expectedModel, TestCommand.MESSAGE_ENTER_TEST_FOLDER_SUCCESS);

        /* Case: Execute invalid commands in report
         * -> fails
         */
        command = TestCommand.COMMAND_WORD;
        assertCommandFailure(command, MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        command = ReportCommand.COMMAND_WORD;
        assertCommandFailure(command, MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        command = SortCommand.COMMAND_WORD;
        assertCommandFailure(command, MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);

        /* Case: Answer correctly
         * -> Answer command works
         */
        command = AnswerCommand.COMMAND_WORD + " " + TypicalCards.ALICE.getAnswer().toString();
        assertCommandSuccess(command, expectedModel, AnswerCommand.MESSAGE_ANSWER_SUCCESS);

        /* Case: Proceed to next screen
         * -> Next question
         */
        command = NextCommand.COMMAND_WORD;
        assertCommandSuccess(command, expectedModel, NextCommand.MESSAGE_NEXT_QUESTION_SUCCESS);

        /* Case: Answer wrongly
         * -> Answer command works
         */
        // Guarantees a wrong answer
        command = AnswerCommand.COMMAND_WORD + " " + TypicalCards.BENSON.getAnswer().toString() + "abc";
        assertCommandSuccess(command, expectedModel, AnswerCommand.MESSAGE_ANSWER_SUCCESS);

        /* Case: Execute end in test
         * -> exits report back to folder (do not use assertCommandSuccess here)
         */
        command = EndCommand.COMMAND_WORD;
        executeCommand(command);
        assertTrue(getModel().getState() == Model.State.IN_FOLDER);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see TestCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * 4. Asserts that the status bar still indicates the user is in folder correctly.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CardFolderSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarIsInTestSession();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarIsInTestSession();
    }
}
