package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_ANSWER_COMMAND;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN;
import static seedu.knowitall.logic.commands.AnswerCommand.MESSAGE_OPTION_NUMBER_EXPECTED;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneAsList;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code AnswerCommand}.
 */
public class AnswerCommandTest {
    private Model model = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
    }

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
                CommandResult.Type.ANSWER_CORRECT);
        model.startTestSession();
        expectedModel.startTestSession();

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(testedCard.getAnswer());

        expectedModel.setCardAsAnswered();
        Card scoredCard = expectedModel.createScoredCard(testedCard, true);
        expectedModel.setCard(testedCard, scoredCard);

        assertCommandSuccess(answerCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_correctMcqAnswerAttempt_markCorrect() {
        CommandResult expectedCommandResult = new CommandResult(AnswerCommand.MESSAGE_ANSWER_SUCCESS,
                CommandResult.Type.ANSWER_CORRECT);

        model.startTestSession();
        expectedModel.startTestSession();

        while (model.getCurrentTestedCard().getCardType() != Card.CardType.MCQ) {
            model.testNextCard();
        }
        while (expectedModel.getCurrentTestedCard().getCardType() != Card.CardType.MCQ) {
            expectedModel.testNextCard();
        }

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(new Answer(String.valueOf(testedCard.getAnswerIndex())));

        expectedModel.setCardAsAnswered();
        Card scoredCard = expectedModel.createScoredCard(testedCard, true);
        expectedModel.setCard(testedCard, scoredCard);

        assertCommandSuccess(answerCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_wrongAnswerAttempt_markWrong() {
        CommandResult expectedCommandResult = new CommandResult(AnswerCommand.MESSAGE_ANSWER_SUCCESS,
                CommandResult.Type.ANSWER_WRONG);

        model.startTestSession();
        expectedModel.startTestSession();

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(new Answer(VALID_ANSWER_2));
        expectedModel.setCardAsAnswered();
        Card scoredCard = expectedModel.createScoredCard(testedCard, false);
        expectedModel.setCard(testedCard, scoredCard);

        assertCommandSuccess(answerCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_testMcqCardGiveStringNotNumber_fail() {
        model.startTestSession();
        expectedModel.startTestSession();

        while (model.getCurrentTestedCard().getCardType() != Card.CardType.MCQ) {
            model.testNextCard();
        }
        while (expectedModel.getCurrentTestedCard().getCardType() != Card.CardType.MCQ) {
            expectedModel.testNextCard();
        }

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(testedCard.getAnswer());
        assertCommandFailure(answerCommand, model, commandHistory, MESSAGE_OPTION_NUMBER_EXPECTED);
    }

    @Test
    public void execute_invalidAnswerOutsideTestSession_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        AnswerCommand answerCommand = new AnswerCommand(new Answer(VALID_ANSWER_2));
        assertCommandFailure(answerCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidAnswerAfterAnsweredAttempt_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_ANSWER_COMMAND);

        model.startTestSession();

        Card testedCard = model.getCurrentTestedCard();
        AnswerCommand answerCommand = new AnswerCommand(testedCard.getAnswer());
        model.setCardAsAnswered();

        assertCommandFailure(answerCommand, model, commandHistory, expectedMessage);
    }
}
