/* @@author itszp */
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FILE;
import static seedu.address.commons.core.Messages.MESSAGE_UNABLE_TO_SAVE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.logic.parser.SaveCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SaveCommandTest {

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
    public void execute_saveImageWithoutOpen_failure() {
        try {
            SaveCommandParser saveParser = new SaveCommandParser();
            SaveCommand command = saveParser.parse("newImage.png");
            assertCommandFailure(command, model, commandHistory, MESSAGE_UNABLE_TO_SAVE, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void execute_saveImageDuplicate_failure() {
        try {
            OpenCommandParser openParser = new OpenCommandParser();
            openParser.parse("validPNGTest.png").execute(currentEdit, model, commandHistory);
            SaveCommandParser saveParser = new SaveCommandParser();
            SaveCommand command = saveParser.parse("validPNGTest.png");
            assertCommandFailure(command, model, commandHistory, MESSAGE_DUPLICATE_FILE, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_saveImageNewName_success() {
        try {
            OpenCommandParser openParser = new OpenCommandParser();
            openParser.parse("validPNGTest.png").execute(currentEdit, model, commandHistory);
            SaveCommandParser saveParser = new SaveCommandParser();
            SaveCommand command = saveParser.parse("newImage.png");
            String expectedMessage = String.format(SaveCommand.MESSAGE_SUCCESS, "newImage.png");
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_saveImageOverwrite_success() {
        try {
            OpenCommandParser openParser = new OpenCommandParser();
            openParser.parse("validPNGTest.png").execute(currentEdit, model, commandHistory);
            SaveCommandParser saveParser = new SaveCommandParser();
            SaveCommand command = saveParser.parse("");
            String expectedMessage = String.format(SaveCommand.MESSAGE_SUCCESS, "validPNGTest.png");
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals_test_success() {
        assertEquals(new SaveCommand("newImage.png"), new SaveCommand("newImage.png"));
    }

    @Test
    public void equals_test_failure() {
        assertNotEquals(new SaveCommand("newImage.png"), new OpenCommand("oldImage.jpg"));
    }

    @After
    public void clearAlbum() {
        album.clearAlbum();
        currentEdit.clearTemp();
    }
}
