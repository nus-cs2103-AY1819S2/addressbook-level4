package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizCard;
import seedu.address.model.modelmanager.quiz.QuizModel;
import seedu.address.model.modelmanager.quiz.QuizUiDisplayFormatter;
import seedu.address.model.session.Session;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalLessons;

public class AddLessonCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLessonCommand(null);
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ManagementModelStubAcceptingLesson modelStub = new ManagementModelStubAcceptingLesson();
        Lesson validLesson = new LessonBuilder().build();

        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLesson), modelStub.lessonsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Lesson validLesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(validLesson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MGT_MODEL);
        addLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Lesson lessonDefault = new LessonBuilder(TypicalLessons.LESSON_DEFAULT).build();
        Lesson lessonTrueFalse = new LessonBuilder(TypicalLessons.LESSON_TRUE_FALSE).build();
        AddLessonCommand addLessonDefCommand = new AddLessonCommand(lessonDefault);
        AddLessonCommand addLessonPropCommand = new AddLessonCommand(lessonTrueFalse);

        // same object -> returns true
        assertEquals(addLessonDefCommand, addLessonDefCommand);

        // same values -> returns true
        AddLessonCommand addLessonDefCommandCopy = new AddLessonCommand(lessonDefault);
        assertEquals(addLessonDefCommand, addLessonDefCommandCopy);

        // different types -> returns false
        assertNotEquals(addLessonDefCommand, 1);

        // null -> returns false
        assertNotEquals(addLessonDefCommand, null);

        // different lesson -> returns false
        assertNotEquals(addLessonDefCommand, addLessonPropCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ManagementModelStub implements ManagementModel {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Gets the lesson by index.
         */
        @Override
        public Lesson getLesson(int index) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Gets the entire list of lessons.
         */
        @Override
        public List<Lesson> getLessons() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Adds the lesson.
         */
        @Override
        public void addLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Updates the lesson at the given index.
         */
        @Override
        public void setLesson(int index, Lesson updatedLesson) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Removes the lesson at the given index from memory, and deletes its corresponding file.
         * @param index w
         */
        @Override
        public void deleteLesson(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CardSrsData getCardSrsData(int hashCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCardSrsData(CardSrsData card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCardSrsData(CardSrsData card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCardSrsData(CardSrsData card) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the lesson being added.
     */
    private class ManagementModelStubAcceptingLesson extends ManagementModelStub {
        public final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessonsAdded.add(lesson);
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class QuizModelStub implements QuizModel {
        /**
         * This method should not be called.
         */
        public List<QuizCard> generateSession() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * This method should not be called.
         */
        public Quiz.Mode getMode() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * This method should not be called.
         */
        public int getCount() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * This method should not be called.
         */
        public String getName() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Sets the {@code Quiz} information.
         */
        public void init(Quiz quiz) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Sets the {@code Quiz} and {@code Session} information.
         */
        public void initWithSession(Quiz quiz, Session session) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns if there is still card left in {@code Quiz}.
         */
        public boolean hasCardLeft() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the next card in line for {@code Quiz}.
         */
        public QuizCard getNextCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getCurrentProgress() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the current QuizCard in {@code Quiz}.
         */
        public QuizCard getCurrentQuizCard() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Update the totalAttempts and streak of a specified card in the current session.
         * @param index of the current {@code QuizCard}
         * @param answer user input
         */
        public boolean updateTotalAttemptsAndStreak(int index, String answer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getQuizTotalAttempts() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getQuizTotalCorrectQuestions() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean toggleIsCardDifficult(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isQuizDone() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns data needed by {@code Session} when {@code Quiz} end.
         */
        public List<List<Integer>> end() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDisplayFormatter(QuizUiDisplayFormatter formatter) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public QuizUiDisplayFormatter getDisplayFormatter() {
            throw new AssertionError("This method should not be called.");
        }
    }
}
