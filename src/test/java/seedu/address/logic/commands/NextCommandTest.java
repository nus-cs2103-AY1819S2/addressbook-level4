package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NEXT_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.NextCommand.MESSAGE_NEXT_QUESTION_SUCCESS;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code NextCommand}.
 */
public class NextCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nextCommand_success() {
        model.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        expectedModel.setCardAsAnswered();
        model.setCardAsAnswered();
        expectedModel.testNextCard();

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_NEXT_QUESTION_SUCCESS,
                CommandResult.Type.SHOW_NEXT_CARD);
        assertCommandSuccess(new NextCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidNextCommandOutsideTestSession_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        assertCommandFailure(new NextCommand(), model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidNextCommandBeforeAnswerAttempt_fail() {
        model.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        String expectedMessage = String.format(MESSAGE_INVALID_NEXT_COMMAND);
        assertCommandFailure(new NextCommand(), model, commandHistory, expectedMessage);
    }
}
