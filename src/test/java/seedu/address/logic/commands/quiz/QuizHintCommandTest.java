package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.quiz.QuizCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.quiz.QuizHintCommand.MESSAGE_EMPTY;
import static seedu.address.logic.commands.quiz.QuizHintCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.quiz.QuizHintCommand.MESSAGE_SUCCESS_ONE;
import static seedu.address.testutil.CardBuilder.DEFAULT_OPTIONAL;
import static seedu.address.testutil.TypicalLessonList.LESSON_DEFAULT;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;
import seedu.address.testutil.Assert;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.SessionBuilder;

/**
 * Contains integration tests with the QuizModel for QuizHintCommand.
 */
public class QuizHintCommandTest {
    private QuizModel actualModel = new QuizModelManager(new ManagementModelManager());
    private QuizModel expectedModel = new QuizModelManager(new ManagementModelManager());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizHintCommand().execute(model, commandHistory));
    }

    @Test
    public void execute_withMoreThanOneOpt_success() {
        Card oneOptCard = new CardBuilder().withOptionals("opt", "opt2").build();
        CardSrsData cardSrsData = new CardSrsData(oneOptCard.hashCode(), 1, 1,
            Instant.ofEpochMilli(123), false);
        SrsCard srsCard = new SrsCard(oneOptCard, cardSrsData, LESSON_DEFAULT);
        Session session = new SessionBuilder()
            .withSrsCards(List.of(srsCard))
            .build();

        Session actualSession = new SessionBuilder()
            .withSrsCards(List.of(srsCard))
            .build();

        expectedModel.init(new Quiz(session.generateSession(), session.getMode()), session);
        expectedModel.getNextCard();

        actualModel.init(new Quiz(actualSession.generateSession(), actualSession.getMode()), actualSession);
        actualModel.getNextCard();

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS,
            Arrays.asList("opt", "opt2")));
        assertCommandSuccess(new QuizHintCommand(), actualModel, commandHistory, expectedCommandResult, expectedModel);

    }

    @Test
    public void execute_withOnlyOneOpt_success() {
        Quiz quizExpected = new Quiz(SESSION_DEFAULT_2.generateSession(), SESSION_DEFAULT_2.getMode());
        Quiz quizActual = new Quiz(SESSION_DEFAULT_2_ACTUAL.generateSession(), SESSION_DEFAULT_2_ACTUAL.getMode());

        expectedModel.init(quizExpected, SESSION_DEFAULT_2);
        actualModel.init(quizActual, SESSION_DEFAULT_2_ACTUAL);

        expectedModel.getNextCard();
        actualModel.getNextCard();

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS_ONE,
            DEFAULT_OPTIONAL));
        assertCommandSuccess(new QuizHintCommand(), actualModel, commandHistory, expectedCommandResult, expectedModel);

    }

    @Test
    public void execute_emptyOpt_throwsCommandException() {
        Card withoutOptCard = new CardBuilder().withNoOptionals().build();
        CardSrsData cardSrsData = new CardSrsData(withoutOptCard.hashCode(), 1, 1,
            Instant.ofEpochMilli(123), false);
        SrsCard srsCard = new SrsCard(withoutOptCard, cardSrsData, LESSON_DEFAULT);
        Session session = new SessionBuilder()
            .withSrsCards(List.of(srsCard))
            .build();

        expectedModel.init(new Quiz(session.generateSession(), session.getMode()), session);
        expectedModel.getNextCard();

        Assert.assertThrows(CommandException.class, MESSAGE_EMPTY, () ->
            new QuizHintCommand().execute(expectedModel, commandHistory));

    }
}
