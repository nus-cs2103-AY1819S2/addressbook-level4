package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;
import static seedu.address.logic.commands.management.AddLessonCommand.MESSAGE_DUPLICATE_NAME;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.model.lesson.LessonList.EXCEPTION_INVALID_INDEX;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalLessonList;

/**
 * Unit tests for the {@link AddLessonCommand}.
 */
public class AddLessonCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        // add null lesson -> NullPointerException thrown
        thrown.expect(NullPointerException.class);
        new AddLessonCommand(null);
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        MgtModelStubAcceptingAdd modelStub = new MgtModelStubAcceptingAdd();
        Lesson validLesson = new LessonBuilder().build();

        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(modelStub, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());
        // lesson added successfully -> lesson in lessons
        assertEquals(Collections.singletonList(validLesson), modelStub.lessons);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_validLessonButHaveOpenedLesson_addUnsuccessful() throws Exception {
        MgtModelStubAcceptingAdd modelStub = new MgtModelStubAcceptingAdd();
        Lesson validLesson = new LessonBuilder().build();

        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(modelStub, commandHistory);
        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // open added lesson
        modelStub.openLesson(0);

        // attempt to add valid lesson but there is an opened lesson ->
        // ask user to close opened lesson first
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_LESSON_VIEW_COMMAND);
        new AddLessonCommand(validLesson).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_duplicateLessonName_throwsCommandException() throws Exception {
        MgtModelStubAcceptingAdd modelStub = new MgtModelStubAcceptingAdd();
        Lesson validLesson = new LessonBuilder().build();

        // add valid lesson -> lesson added successfully
        CommandResult commandResult = new AddLessonCommand(validLesson).execute(modelStub, commandHistory);
        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // add valid lesson with the same name -> command exception thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_DUPLICATE_NAME, validLesson.getName()));
        new AddLessonCommand(validLesson).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Lesson validLesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(validLesson);

        // attempting to execute AddLessonCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        addLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Lesson lessonDefault = new LessonBuilder(TypicalLessonList.LESSON_DEFAULT).build();
        Lesson lessonTrueFalse = new LessonBuilder(TypicalLessonList.LESSON_TRUE_FALSE).build();
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
     * A ManagementModel stub which always accept the lesson being added.
     */
    private class MgtModelStubAcceptingAdd extends ManagementModelStub {
        private final ArrayList<Lesson> lessons = new ArrayList<>();
        /**
         * The {@link Lesson} object currently in focus. All lesson-editing-related commands will apply
         * to this lesson.
         */
        private Lesson openedLesson = null; // The lesson currently being edited

        @Override
        public boolean hasLessonWithName(String name) {
            for (Lesson lesson : lessons) {
                if (lesson.getName().equals(name)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public String openLesson(int index) {
            try {
                openedLesson = lessons.get(index);
                return openedLesson.getName();
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
            }
        }

        @Override
        public boolean isThereOpenedLesson() {
            return openedLesson != null;
        }

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessons.add(lesson);
        }
    }
}
