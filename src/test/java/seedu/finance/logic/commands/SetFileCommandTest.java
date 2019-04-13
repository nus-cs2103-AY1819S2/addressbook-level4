package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.logic.commands.SetFileCommand.MESSAGE_SUCCESS;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTrackerWithoutSetBudget;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;

public class SetFileCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "SetFileTest");
    private static final Path VALID_DATA_FILE = TEST_DATA_FOLDER.resolve("validDataFile.json");
    private static final Path BLANK_DATA_FILE = TEST_DATA_FOLDER.resolve("blankDataFile.json");
    private static final Path INVALID_DATA_FILE = TEST_DATA_FOLDER.resolve("invalidDataFile.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalFinanceTrackerWithoutSetBudget(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetFileCommand(null);
    }

    @Test
    public void execute_incorrectDataFormat_setFileSuccessful() {
        SetFileCommand setFileCommand = new SetFileCommand(INVALID_DATA_FILE);
        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, INVALID_DATA_FILE), true, false, false);
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithoutSetBudget(), new UserPrefs());
        expectedModel.addPreviousDataFile(model.getFinanceTrackerFilePath());
        expectedModel.setFinanceTrackerFilePath(INVALID_DATA_FILE);
        expectedModel.setFinanceTracker(new FinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(setFileCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_newFile_setFileSuccessful() {
        SetFileCommand setFileCommand = new SetFileCommand(BLANK_DATA_FILE);
        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, BLANK_DATA_FILE), true, false, false);
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithoutSetBudget(), new UserPrefs());
        expectedModel.addPreviousDataFile(model.getFinanceTrackerFilePath());
        expectedModel.setFinanceTrackerFilePath(BLANK_DATA_FILE);
        expectedModel.setFinanceTracker(new FinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(setFileCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }


    @Test
    public void execute_existingFormattedFile_setFileSuccessful() {
        SetFileCommand setFileCommand = new SetFileCommand(VALID_DATA_FILE);
        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, VALID_DATA_FILE), true, false, false);
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithoutSetBudget(), new UserPrefs());
        expectedModel.setFinanceTrackerFilePath(VALID_DATA_FILE);
        expectedModel.setFinanceTracker(getTypicalFinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(setFileCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        final SetFileCommand standardCommand = new SetFileCommand(VALID_DATA_FILE);

        // same path -> returns true
        SetFileCommand commandWithSamePath = new SetFileCommand(VALID_DATA_FILE);
        assertTrue(standardCommand.equals(commandWithSamePath));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different path -> returns false
        SetFileCommand commandWithDifferentPath =
                new SetFileCommand(Paths.get("data\\SetFileCommandDiffPath.json"));
        assertFalse(standardCommand.equals(commandWithDifferentPath));

        // different types -> returns false
        assertFalse(standardCommand.equals(new SetCommand("100")));
    }
}
