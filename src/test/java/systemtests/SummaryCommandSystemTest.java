package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SummaryCommand;
import seedu.address.model.Model;

public class SummaryCommandSystemTest extends BookShelfSystemTest {
    private static final String command = SummaryCommand.COMMAND_WORD;

    @Test
    public void summary() {
        Model expectedModel = getModel();
        String expectedResultMessage = "You've read 8 books.\n"
            + "These book receive a rating of 10 from you: To Kill a Mocking Bird\n"
            + "You preferred books that you labeled as fantasy(including The Hunger Games, Life of Pi)\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays with a summary of the book in the book shelf
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class.
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedBookCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
