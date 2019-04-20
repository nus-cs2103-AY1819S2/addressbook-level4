package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.management.EditLessonCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.management.EditLessonCommand;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

public class EditLessonCommandSystemTest extends BrainTrainSystemTest {
    @Test
    public void edit() {
        /* Case: invalid command
         * -> fails, invalid command
         */
        String command = "someinvalidcommand";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: edits lesson at index 1
         * -> edits lesson
         */
        Index index = Index.fromZeroBased(0);
        command = EditLessonCommand.COMMAND_WORD + " " + index.getOneBased();
        ManagementModel expectedModel = getManagementModel();
        String expectedLesson = expectedModel.getLesson(index.getZeroBased()).getName();
        assertCommandSuccess(command, expectedModel, expectedLesson);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see BrainTrainSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, ManagementModel expectedModel, String expectedLesson) {
        String expectedResultMessage = String.format(MESSAGE_SUCCESS, expectedLesson, true);
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code BrainTrainSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see BrainTrainSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        ManagementModel expectedModel = getManagementModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
