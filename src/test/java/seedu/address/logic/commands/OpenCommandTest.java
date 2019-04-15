/* @@author itszp */
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class OpenCommandTest {

    private Album album = Album.getInstance();
    private Model model = new ModelManager();
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void importSampleToAssets() {
        album.clearAlbum();
        try {
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse("sample").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_openImage_success() {
        OpenCommand command = new OpenCommand("validPNGTest.png");
        assertCommandSuccess(command, model, commandHistory, OpenCommand.MESSAGE_SUCCESS, currentEdit);
    }

    @Test
    public void execute_openImage_failure() {
        OpenCommand command = new OpenCommand("notfound.png");
        assertCommandFailure(command, model, commandHistory, MESSAGE_FILE_NOT_FOUND, currentEdit);
    }

    @Test
    public void execute_openOnEmptyAssets_failure() {
        album.clearAlbum();
        OpenCommand command = new OpenCommand("validPNGTest.png");
        assertCommandFailure(command, model, commandHistory, MESSAGE_FILE_NOT_FOUND, currentEdit);
    }

    @Test
    public void equals_test_success() {
        assertEquals(new OpenCommand("validPNGTest.png"), new OpenCommand("validPNGTest.png"));
    }

    @Test
    public void equals_test_failure() {
        assertNotEquals(new OpenCommand("validPNGTest.png"), new OpenCommand("ui.jpg"));
    }

    @After
    public void clearAlbum() {
        album.clearAlbum();
        currentEdit.clearTemp();
    }
}
