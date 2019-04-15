/* @@author itszp */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

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
import seedu.address.model.UserPrefs;

public class ListFilesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Album album = Album.getInstance();
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
    public void execute_listSampleFiles_success() {
        ListFilesCommand command = new ListFilesCommand();
        String expectedMessage = String.format(ListFilesCommand.MESSAGE_LIST_FILES_HEADER,
                Arrays.toString(album.getFileNames()) + "\n" + ListFilesCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
    }

    @Test
    public void execute_listEmpty_success() {
        album.clearAlbum();
        ListFilesCommand command = new ListFilesCommand();
        assertCommandSuccess(command, model, commandHistory, ListFilesCommand.MESSAGE_ASSETS_EMPTY, currentEdit);
    }

    @Test
    public void execute_listOneFile_success() {
        album.clearAlbum();
        try {
            String validPngTest = "src/main/resources/imageTest/valid/validPNGTest.png";
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse(validPngTest).execute(currentEdit, model, commandHistory);
            ListFilesCommand command = new ListFilesCommand();
            String expectedMessage = String.format(ListFilesCommand.MESSAGE_LIST_FILES_HEADER,
                    Arrays.toString(album.getFileNames()) + "\n" + ListFilesCommand.MESSAGE_SUCCESS);
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void clearAlbum() {
        album.clearAlbum();
        currentEdit.clearTemp();
    }
}
