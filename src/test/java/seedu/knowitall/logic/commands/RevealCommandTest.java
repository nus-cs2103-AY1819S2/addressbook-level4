package seedu.knowitall.logic.commands;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_REVEAL_COMMAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneAsList;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code RevealCommand}.
 */
public class RevealCommandTest {
    private Model model = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validRevealCommand_success() {
        CommandResult expectedCommandResult = new CommandResult(RevealCommand.MESSAGE_REVEAL_SUCCESS,
                CommandResult.Type.ANSWER_REVEAL);
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        model.startTestSession();
        expectedModel.startTestSession();

        Card testedCard = model.getCurrentTestedCard();
        RevealCommand revealCommand = new RevealCommand();

        expectedModel.setCardAsAnswered();
        Card scoredCard = expectedModel.createScoredCard(testedCard, false);
        expectedModel.setCard(testedCard, scoredCard);
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(revealCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidRevealOutsideTestSession_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        RevealCommand revealCommand = new RevealCommand();
        assertCommandFailure(revealCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidRevealAfterAnsweredAttempt_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_REVEAL_COMMAND);
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        model.startTestSession();
        model.setCardAsAnswered();
        RevealCommand revealCommand = new RevealCommand();
        assertCommandFailure(revealCommand, model, commandHistory, expectedMessage);
    }
}
