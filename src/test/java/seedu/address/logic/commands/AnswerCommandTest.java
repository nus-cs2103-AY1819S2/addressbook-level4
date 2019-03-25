package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ANSWER_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_TEST_SESSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.address.logic.AnswerCommandResultType;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Score;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code AnswerCommand}.
 */
public class AnswerCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Answer firstAttemptedAnswer = new Answer(VALID_ANSWER_1);
        Answer secondAttemptedAnswer = new Answer(VALID_ANSWER_2);

        AnswerCommand answerFirstCommand = new AnswerCommand(firstAttemptedAnswer);
        AnswerCommand answerSecondCommand = new AnswerCommand(secondAttemptedAnswer);

        // same object -> returns true
        assertTrue(answerFirstCommand.equals(answerFirstCommand));

        // same values -> returns true
        AnswerCommand answerFirstCommandCopy = new AnswerCommand(firstAttemptedAnswer);
        assertTrue(answerFirstCommand.equals(answerFirstCommandCopy));

        // different types -> returns false
        assertFalse(answerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(answerFirstCommand.equals(null));

        // different answer -> returns false
        assertFalse(answerFirstCommand.equals(answerSecondCommand));
    }

    @Test
    public void execute_correctAnswerAttempt_markCorrect() {
        CommandResult expectedCommandResult = new CommandResult(AnswerCommand.MESSAGE_ANSWER_SUCCESS,
                CommandResult.TYPE.ANSWER_CORRECT);

        model.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(testedCard.getAnswer());

        expectedModel.setCardAsAnswered();
        Score newScore = new Score(testedCard.getScore().correctAttempts + 1,
                testedCard.getScore().totalAttempts + 1);
        expectedModel.setCard(testedCard, new Card(testedCard.getQuestion(), testedCard.getAnswer(), newScore,
                testedCard.getHints()));
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(answerCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_wrongAnswerAttempt_markWrong() {
        CommandResult expectedCommandResult = new CommandResult(AnswerCommand.MESSAGE_ANSWER_SUCCESS,
                CommandResult.TYPE.ANSWER_WRONG);

        model.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(new Answer(VALID_ANSWER_2));

        expectedModel.setCardAsAnswered();
        Score newScore = new Score(testedCard.getScore().correctAttempts,
                testedCard.getScore().totalAttempts + 1);
        expectedModel.setCard(testedCard, new Card(testedCard.getQuestion(), testedCard.getAnswer(), newScore,
                testedCard.getHints()));
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(answerCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidAnswerOutsideTestSession_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_TEST_SESSION);
        AnswerCommand answerCommand = new AnswerCommand(new Answer(VALID_ANSWER_2));
        assertCommandFailure(answerCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidAnswerAfterAnsweredAttempt_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_ANSWER_COMMAND);
        model.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(testedCard.getAnswer());
        model.setCardAsAnswered();

        assertCommandFailure(answerCommand, model, commandHistory, expectedMessage);
    }
}
