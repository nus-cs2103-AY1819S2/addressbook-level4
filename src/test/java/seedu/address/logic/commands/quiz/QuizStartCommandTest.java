package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.SessionBuilder;
import seedu.address.testutil.SrsCardBuilder;


public class QuizStartCommandTest {
    private static final CommandHistory commandHistory = new CommandHistory();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_throwsNullPointerException () {
        Assert.assertThrows(NullPointerException.class, () ->
                new QuizStartCommand(null));
    }
    @Test
    public void execute_correctModel() throws CommandException {
        QuizModel quizModel = new QuizModelManager();
        Session session = new SessionBuilder().build();
        thrown.expectMessage("Expected ManagementModel but received QuizModel instead.");
        CommandResult commandResult = new QuizStartCommand(session).execute(quizModel, commandHistory);
        assertNull(commandResult);
    }
    @Test
    public void execute_lessonExist() throws Exception {
        ManagementModel managementModel = new ManagementModelManager();
        final Session session = new SessionBuilder().build();
        thrown.expectMessage("Lesson is not found. Please try another one.");
        CommandResult commandResult = new QuizStartCommand(session).execute(managementModel, commandHistory);
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
        Session session = new SessionBuilder(new Session("Capitals", 1, QuizMode.DIFFICULT,
                List.of(srsCard))).build();
        Session session_moreCount = new SessionBuilder(new Session("Capitals", 3, QuizMode.DIFFICULT,
                List.of(srsCard))).build();
        CommandResult commandResult = new QuizStartCommand(session).execute(managementModel, commandHistory);
        assertEquals("Starting new quiz", commandResult.getFeedbackToUser());
        assertEquals(session.getSrsCards().get(0), srsCard);
        CommandResult commandResult_moreCount = new QuizStartCommand(session_moreCount).execute(managementModel,
                commandHistory);
        assertEquals("Not enough cards in current lesson. Set the count to the maximum"
                + " number for you by default.", commandResult_moreCount.getFeedbackToUser());
        thrown.expectMessage("There is no difficult card in this lesson.");
        CommandResult wrongCommandResult = new QuizStartCommand(session).execute(failManagementModel, commandHistory);
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
        Session session = new SessionBuilder(new Session("Capitals", 1, QuizMode.LEARN,
                List.of(srsCard))).build();
        CommandResult commandResult = new QuizStartCommand(session).execute(managementModel, commandHistory);
        assertEquals("Starting new quiz", commandResult.getFeedbackToUser());
        assertEquals(session.getSrsCards().get(0), srsCard);
        thrown.expectMessage("There is no more new card to learn in this lesson.");
        CommandResult wrongCommandResult = new QuizStartCommand(session).execute(failManagementModel, commandHistory);
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
        Session session = new SessionBuilder(new Session("Capitals", 1, QuizMode.REVIEW,
                List.of(srsCard))).build();
        CommandResult commandResult = new QuizStartCommand(session).execute(managementModel, commandHistory);
        assertEquals("Starting new quiz", commandResult.getFeedbackToUser());
        assertEquals(session.getSrsCards().get(0), srsCard);
        thrown.expectMessage("There is no card for review since all cards in current lesson"
                + " do not reach the due date.");
        CommandResult wrongCommandResult = new QuizStartCommand(session).execute(failManagementModel, commandHistory);
        assertNull(wrongCommandResult);
    }
    @Test
    public void executeActual_learn_success() {
        Lesson lesson = new LessonBuilder().build();
        final Session session = new SessionBuilder(new Session("Capitals", 2,
                QuizMode.LEARN, List.of(new SrsCardBuilder().build(),
                new SrsCardBuilder(new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1,
                        1, Instant.now().plus(Duration.ofHours(2)), false), lesson)).build()))).build();
        final QuizCard card1 = new QuizCard("Belgium", "Brussels");
        final QuizCard card2 = new QuizCard("Japan", "Tokyo");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz, session);
        expectedModel.getNextCard();
        CommandResult expectedCommandResult = new QuizStartCommand(session).executeActual(expectedModel,
            commandHistory);

        QuizModel actualModel = new QuizModelManager();
        QuizStartCommand quizStartCommand = new QuizStartCommand(session);
        assertEquals(quizStartCommand.getSession(), session);

        CommandHistory expectedCommandHistory = new CommandHistory(commandHistory);
        CommandResult result = quizStartCommand.executeActual(actualModel, commandHistory);
        assertEquals(expectedCommandResult, result);
        assertEquals(expectedCommandHistory, commandHistory);

    }

    @Test
    public void executeActual_review_success() {
        Lesson lesson = new LessonBuilder().build();
        final Session session = new SessionBuilder(new Session("Capitals", 2,
            QuizMode.REVIEW, List.of(new SrsCardBuilder().build(),
            new SrsCardBuilder(new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1,
                1, Instant.now().plus(Duration.ofHours(2)), false), lesson)).build()))).build();
        final QuizCard card1 = new QuizCard("Belgium", "Brussels");
        final QuizCard card2 = new QuizCard("Japan", "Tokyo");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, QuizMode.REVIEW);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz, session);
        expectedModel.getNextCard();
        CommandResult expectedCommandResult = new QuizStartCommand(session).executeActual(expectedModel,
            commandHistory);

        QuizModel actualModel = new QuizModelManager();
        QuizStartCommand quizStartCommand = new QuizStartCommand(session);
        assertEquals(quizStartCommand.getSession(), session);

        CommandHistory expectedCommandHistory = new CommandHistory(commandHistory);
        CommandResult result = quizStartCommand.executeActual(actualModel, commandHistory);
        assertEquals(expectedCommandResult, result);
        assertEquals(expectedCommandHistory, commandHistory);
    }
}
