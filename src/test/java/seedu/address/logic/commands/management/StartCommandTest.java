package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;
import static seedu.address.logic.commands.management.StartCommand.MESSAGE_LESSON;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.SessionBuilder;
import seedu.address.testutil.SrsCardBuilder;


public class StartCommandTest {
    private static final CommandHistory commandHistory = new CommandHistory();
    private static final String MESSAGE_COUNT = "Not enough cards in current lesson.\nSet the count to the maximum"
            + " number for you by default.";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_throwsNullPointerException () {
        Assert.assertThrows(NullPointerException.class, () ->
                new StartCommand(null));
    }
    /**
     * A ManagementModel stub which always rejects reload lessons.
     */
    private class MgtModelStubWithOpenedLesson extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return true;
        }
    }
    @Test
    public void execute_modelWithOpenedLesson_closeUnsuccessful() throws CommandException {
        MgtModelStubWithOpenedLesson modelStub = new MgtModelStubWithOpenedLesson();
        // attempt to reload lessons but there is an opened lesson ->
        // ask user to close opened lesson first
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_LESSON_VIEW_COMMAND);
        new StartCommand(new SessionBuilder().build()).execute(modelStub, null);
    }
    @Test
    public void execute_wrongModel() throws CommandException {
        QuizModel quizModel = new QuizModelManager();
        Session session = new SessionBuilder().build();
        thrown.expectMessage("Expected ManagementModel but received QuizModel instead.");
        CommandResult commandResult = new StartCommand(session).execute(quizModel, commandHistory);
        assertNull(commandResult);
    }
    @Test
    public void execute_lessonExist() throws Exception {
        ManagementModel managementModel = new ManagementModelManager();
        final Session session = new SessionBuilder().build();
        thrown.expectMessage("Lesson index is out of range. Please try a smaller one.");
        CommandResult commandResult = new StartCommand(session).execute(managementModel, commandHistory);
        assertNull(commandResult);
    }
    @Test
    public void execute_lessonExistAnother() throws Exception {
        ManagementModel managementModel = new ManagementModelManager();
        final Session session = new SessionBuilder(new Session(0, 1, QuizMode.LEARN)).build();
        thrown.expectMessage("Lesson index is out of range. Please try a larger one.");
        CommandResult commandResult = new StartCommand(session).execute(managementModel, commandHistory);
        assertNull(commandResult);
    }
    @Test
    public void execute_difficultMode() throws CommandException {
        ManagementModel failManagementModel = new ManagementModelManager();
        ManagementModel managementModel = new ManagementModelManager();
        failManagementModel.addLesson(new LessonBuilder().build());
        managementModel.addLesson(new LessonBuilder().build());
        managementModel.addCardSrsData(new CardSrsData(CARD_BELGIUM.hashCode(), 1, 1,
                Instant.ofEpochMilli(123), true));
        SrsCard srsCard = new SrsCardBuilder().build();
        Session session = new SessionBuilder(new Session(1, 1, QuizMode.DIFFICULT,
                List.of(srsCard))).build();
        Session sessionMoreCount = new SessionBuilder(new Session(1, 3, QuizMode.DIFFICULT,
                List.of(srsCard))).build();
        CommandResult commandResult = new StartCommand(session).execute(managementModel, commandHistory);
        assertEquals("Starting new quiz", commandResult.getFeedbackToUser());
        assertEquals(session.getSrsCards().get(0), srsCard);
        CommandResult commandResultMoreCount = new StartCommand(sessionMoreCount).execute(managementModel,
                commandHistory);
        assertEquals("Starting new quiz", commandResultMoreCount.getFeedbackToUser());
        thrown.expectMessage("There is no difficult card in this lesson.");
        CommandResult wrongCommandResult = new StartCommand(session).execute(failManagementModel, commandHistory);
        assertNull(wrongCommandResult);
    }
    @Test
    public void execute_learnMode() throws CommandException {
        ManagementModel managementModel = new ManagementModelManager();
        ManagementModel failManagementModel = new ManagementModelManager();
        managementModel.addLesson(new LessonBuilder().build());
        failManagementModel.addLesson(new LessonBuilder().build());
        failManagementModel.addCardSrsData(new CardSrsData(CARD_BELGIUM.hashCode(), 1, 1,
                Instant.ofEpochMilli(123), false));
        failManagementModel.addCardSrsData(new CardSrsData(CARD_JAPAN.hashCode(), 1, 1,
                Instant.ofEpochMilli(123), false));
        SrsCard srsCard = new SrsCardBuilder().build();
        Session session = new SessionBuilder(new Session(1, 1, QuizMode.LEARN,
                List.of(srsCard))).build();
        CommandResult commandResult = new StartCommand(session).execute(managementModel, commandHistory);
        assertEquals("Starting new quiz", commandResult.getFeedbackToUser());
        assertEquals(session.getSrsCards().get(0), srsCard);
        thrown.expectMessage("There is no more new card to learn in this lesson.");
        CommandResult wrongCommandResult = new StartCommand(session).execute(failManagementModel, commandHistory);
        assertNull(wrongCommandResult);
    }
    @Test
    public void execute_reviewMode() throws CommandException {
        ManagementModel managementModel = new ManagementModelManager();
        ManagementModel failManagementModel = new ManagementModelManager();
        Lesson lesson = new LessonBuilder().withCards(CARD_BELGIUM).build();
        failManagementModel.addLesson(lesson);
        managementModel.addLesson(lesson);
        managementModel.addCardSrsData(new CardSrsData(CARD_BELGIUM.hashCode(), 1, 1,
                Instant.ofEpochMilli(123), false));
        failManagementModel.addCardSrsData(new CardSrsData(CARD_BELGIUM.hashCode(), 1, 1,
                Instant.now().plus(Duration.ofHours(1234)), false));
        SrsCard srsCard = new SrsCardBuilder().build();
        Session session = new SessionBuilder(new Session(1, 1, QuizMode.REVIEW,
                List.of(srsCard))).build();
        CommandResult commandResult = new StartCommand(session).execute(managementModel, commandHistory);
        assertEquals("Starting new quiz", commandResult.getFeedbackToUser());
        assertEquals(session.getSrsCards().get(0), srsCard);
        thrown.expectMessage("There is no card for review since all cards in current lesson"
                + " do not reach the due date.");
        CommandResult wrongCommandResult = new StartCommand(session).execute(failManagementModel, commandHistory);
        assertNull(wrongCommandResult);
    }
    @Test
    public void executeActual_learn_success() throws CommandException {
        Lesson lesson = new LessonBuilder().build();
        final Session session = new SessionBuilder(new Session("Capitals", 2,
                QuizMode.LEARN, List.of(new SrsCardBuilder().build(),
                new SrsCardBuilder(new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1,
                        1, Instant.now().plus(Duration.ofHours(2)), false), lesson)).build()))).build();
        final Quiz quiz = new Quiz(session.generateSession(), QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz, session);
        expectedModel.getNextCard();
        CommandResult expectedCommandResult = new StartCommand(session).executeQuiz(expectedModel,
            commandHistory);

        QuizModel actualModel = new QuizModelManager();
        StartCommand startCommand = new StartCommand(session);
        assertEquals(startCommand.getSession(), session);

        CommandHistory expectedCommandHistory = new CommandHistory(commandHistory);
        CommandResult result = startCommand.executeQuiz(actualModel, commandHistory);
        assertEquals(expectedCommandResult, result);
        assertEquals(expectedCommandHistory, commandHistory);
        session.setCount(10);
        CommandResult largeCount = new StartCommand(session).executeQuiz(expectedModel, commandHistory);
        assertEquals(MESSAGE_COUNT + MESSAGE_LESSON + "default", largeCount.getFeedbackToUser());
    }

    @Test
    public void executeActual_review_success() throws CommandException {
        Lesson lesson = new LessonBuilder().build();
        final Session session = new SessionBuilder(new Session("Capitals", 2,
            QuizMode.REVIEW, List.of(new SrsCardBuilder().build(),
            new SrsCardBuilder(new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1,
                1, Instant.now().plus(Duration.ofHours(2)), false), lesson)).build()))).build();
        final Quiz quiz = new Quiz(session.generateSession(), QuizMode.REVIEW);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz, session);
        expectedModel.getNextCard();
        CommandResult expectedCommandResult = new StartCommand(session).executeQuiz(expectedModel,
            commandHistory);

        QuizModel actualModel = new QuizModelManager();
        StartCommand startCommand = new StartCommand(session);
        assertEquals(startCommand.getSession(), session);

        CommandHistory expectedCommandHistory = new CommandHistory(commandHistory);
        CommandResult result = startCommand.executeQuiz(actualModel, commandHistory);
        assertEquals(expectedCommandResult, result);
        assertEquals(expectedCommandHistory, commandHistory);
    }
}
