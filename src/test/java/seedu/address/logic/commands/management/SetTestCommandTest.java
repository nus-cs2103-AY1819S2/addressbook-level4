package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OPENED_LESSON;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.model.lesson.Lesson.EXCEPTION_INVALID_INDEX;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;
import seedu.address.testutil.TypicalIndices;

/**
 * Unit tests for the {@link SetTestCommand}.
 */
public class SetTestCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Index indexOne = TypicalIndices.INDEX_FIRST;
    private Index indexTwo = TypicalIndices.INDEX_SECOND;
    private Index indexThree = Index.fromZeroBased(2);

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_openedLessonAndValidIndices_setSuccessful() throws Exception {
        MgtModelWithThreeCores modelStub = new MgtModelWithThreeCores();
        List<String> cores = modelStub.getOpenedLessonCoreHeaders();
        String coreOne = cores.get(0);
        String coreTwo = cores.get(1);
        String coreThree = cores.get(2);

        // set to core one and core two -> success since 3 cores exist
        CommandResult commandResult =
                new SetTestCommand(indexOne, indexTwo).execute(modelStub, commandHistory);

        // set test values successfully -> success feedback
        assertEquals(String.format(SetTestCommand.MESSAGE_SUCCESS, coreOne, coreTwo),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // set to core three and core two -> success since 3 cores exist
        commandResult =
                new SetTestCommand(indexThree, indexTwo).execute(modelStub, commandHistory);

        // set test values successfully -> success feedback
        assertEquals(String.format(SetTestCommand.MESSAGE_SUCCESS, coreThree, coreTwo),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_openedLessonAndInvalidIndices_setUnsuccessful() throws Exception {
        MgtModelWithThreeCores modelStub = new MgtModelWithThreeCores();
        int index = 5;
        Index invalidIndex = Index.fromZeroBased(index);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_INDEX, index));
        new SetTestCommand(invalidIndex, indexThree).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_missingOpenedLesson_throwsCommandException() throws Exception {
        MgtModelStubNoOpenLesson modelStub = new MgtModelStubNoOpenLesson();

        // add valid card but there is no open lesson -> command exception thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NO_OPENED_LESSON);
        new SetTestCommand(indexOne, indexTwo).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        SetTestCommand setTestCommand = new SetTestCommand(indexOne, indexTwo);

        // attempting to execute SetTestCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        setTestCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        SetTestCommand setTestCommand = new SetTestCommand(indexOne, indexThree);

        // same object -> returns true
        assertEquals(setTestCommand, setTestCommand);

        // same values -> returns true
        SetTestCommand setTestCommandCopy = new SetTestCommand(indexOne, indexThree);
        assertEquals(setTestCommand, setTestCommandCopy);

        // same values different order -> returns true
        setTestCommandCopy = new SetTestCommand(indexThree, indexOne);
        assertEquals(setTestCommand, setTestCommandCopy);

        // different types -> returns false
        assertNotEquals(setTestCommand, 1);

        // null -> returns false
        assertNotEquals(setTestCommand, null);

        // different card -> returns false
        SetTestCommand setTestDiffCommand = new SetTestCommand(indexTwo, indexThree);
        assertNotEquals(setTestCommand, setTestDiffCommand);
    }

    /**
     * A ManagementModel stub which always fail when attempting to set test
     * given there is no open lesson.
     */
    private class MgtModelStubNoOpenLesson extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return false;
        }
    }

    /**
     * A ManagementModel stub which always fail when attempting to add
     * card given there is no open lesson. It has an opened lesson with 3 cores.
     */
    private class MgtModelWithThreeCores extends ManagementModelStub {
        private int openedLessonQuestionIndex = 0;
        private int openedLessonAnswerIndex = 1;
        private List<String> openedLessonCoreHeaders;

        public MgtModelWithThreeCores() {
            openedLessonCoreHeaders =
                    new ArrayList<>(List.of("Core 1", "Core 2", "Core 3"));
        }

        @Override
        public List<String> getOpenedLessonCoreHeaders() {
            return openedLessonCoreHeaders;
        }

        @Override
        public boolean isThereOpenedLesson() {
            return true;
        }

        @Override
        public void setOpenedLessonTestValues(int questionIndex, int answerIndex) {
            if (questionIndex < 0 || questionIndex > openedLessonCoreHeaders.size()) {
                throw new IllegalArgumentException(String.format(EXCEPTION_INVALID_INDEX, questionIndex));
            } else if (answerIndex < 0 || answerIndex > openedLessonCoreHeaders.size()) {
                throw new IllegalArgumentException(String.format(EXCEPTION_INVALID_INDEX, answerIndex));
            }

            openedLessonQuestionIndex = questionIndex;
            openedLessonAnswerIndex = answerIndex;
        }
    }
}
