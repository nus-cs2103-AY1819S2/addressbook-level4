/* @@author Carrein */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_DIR_SUCCESS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ImportCommandTest {

    private Album album = Album.getInstance();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        album.clearAlbum();
    }

    @Test
    public void execute_successful_singleImport() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
        assertCommandSuccess(new ImportCommand(false), model, commandHistory, expectedCommandResult,
                expectedModel, currentEdit);
    }

    @Test
    public void execute_successful_directoryImport() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_DIR_SUCCESS, false, false);
        assertCommandSuccess(new ImportCommand(true), model, commandHistory, expectedCommandResult,
                expectedModel, currentEdit);
    }
}
