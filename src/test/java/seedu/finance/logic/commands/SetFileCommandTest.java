package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SetFileCommandTest {

    private static final String DEFAULT_FILE_PATH = "data\\SetFileCommandTest.json";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetFileCommand(null);
    }

    @Test
    public void execute_incorrectDataFormat_setFileSuccessful() {

    }

    @Test
    public void execute_newFile_setFileSuccessful() {

    }


    @Test
    public void execute_existingFormattedFile_setFileSuccessful() {

    }

    @Test
    public void equals() {
        final Path path = Paths.get(DEFAULT_FILE_PATH);

        final SetFileCommand standardCommand = new SetFileCommand(path);

        // same path -> returns true
        SetFileCommand commandWithSamePath = new SetFileCommand(Paths.get(DEFAULT_FILE_PATH));
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
