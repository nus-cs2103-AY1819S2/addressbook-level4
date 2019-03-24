package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.Command.MESSAGE_EXPECTED_MGT_MODEL;
import static seedu.address.model.Lessons.EXCEPTION_INVALID_INDEX;
import static seedu.address.testutil.TypicalLessons.LESSON_DEFAULT;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lessons;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.modelmanager.quiz.QuizModelStub;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

public class DeleteLessonCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Attempts to delete a non-existent lesson
     */
    @Test
    public void execute_lessonDeletedByModel_deleteUnsuccessful() throws Exception {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        Index toDeleteIndex = Index.fromZeroBased(0);
        thrown.expect(CommandException.class);
        new DeleteLessonCommand(toDeleteIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_lessonDeletedByModel_deleteSuccessful() throws Exception {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        modelStub.addLesson(LESSON_DEFAULT);

        Index toDeleteIndex = Index.fromZeroBased(0);
        CommandResult commandResult =
                new DeleteLessonCommand(toDeleteIndex).execute(modelStub, commandHistory);

        assertEquals(String.format(DeleteLessonCommand.MESSAGE_SUCCESS, LESSON_DEFAULT.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Index toDeleteIndex = Index.fromZeroBased(0);
        DeleteLessonCommand addLessonCommand = new DeleteLessonCommand(toDeleteIndex);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MGT_MODEL);
        addLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Index toDeleteIndex1 = Index.fromZeroBased(0);
        Index toDeleteIndex2 = Index.fromZeroBased(1);
        DeleteLessonCommand deleteLessonCommand1 = new DeleteLessonCommand(toDeleteIndex1);
        DeleteLessonCommand deleteLessonCommand2 = new DeleteLessonCommand(toDeleteIndex2);

        // same object -> returns true
        assertEquals(deleteLessonCommand1, deleteLessonCommand1);

        // same values -> returns true
        DeleteLessonCommand deleteLessonCommandCopy = new DeleteLessonCommand(toDeleteIndex1);
        assertEquals(deleteLessonCommand1, deleteLessonCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteLessonCommand1, 1);

        // null -> returns false
        assertNotEquals(deleteLessonCommand1, null);

        // different lesson -> returns false
        assertNotEquals(deleteLessonCommand1, deleteLessonCommand2);
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
    private class MgtModelStubAcceptingAddDelete extends ManagementModelStub {
        private final Lessons lessons = new Lessons();

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessons.addLesson(lesson);
        }

        /**
         * Gets the entire list of lessons.
         */
        public Lesson getLesson(int index) {
            try {
                return lessons.getLesson(index);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
            }
        }

        /**
         * Gets the entire list of lessons.
         */
        @Override
        public List<Lesson> getLessons() {
            return lessons.getLessons();
        }

        @Override
        public void deleteLesson(int index) {
            lessons.deleteLesson(index);
        }
    }
}
