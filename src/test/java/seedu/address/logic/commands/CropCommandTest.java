/* @@author kayheen */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CropCommandParser;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CropCommandTest {
    private Album album = Album.getInstance();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void createImagesInAlbum() {
        album.clearAlbum();
        try {
            // when image is null, should throw an error
            CropCommandParser parserCrop = new CropCommandParser();
            CropCommand rotate = parserCrop.parse(" 0 0 200 200");
            String expectedMessage = Messages.MESSAGE_DID_NOT_OPEN;
            assertCommandFailure(rotate, model, commandHistory, expectedMessage, currentEdit);
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse("sample").execute(currentEdit, model, commandHistory);
            OpenCommandParser parser2 = new OpenCommandParser();
            parser2.parse("iu.jpg").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void execute_validCrop_success() {
        try {
            CropCommandParser parser = new CropCommandParser();
            CropCommand command = parser.parse(" 0 0 200 200");
            String expectedMessage = Messages.MESSAGE_CROP_SUCCESS;
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void execute_invalidXOrYOrWidthOrHeightInCrop_failure() {
        try {
            CropCommandParser parser = new CropCommandParser();
            int currWidth = Integer.parseInt(currentEdit.getTempImage().getWidth().value);
            int currHeight = Integer.parseInt(currentEdit.getTempImage().getHeight().value);
            // test if width exceed.
            CropCommand command = parser.parse(" 50 0 " + currWidth + " " + currHeight);
            // test if height exceed.
            CropCommand command1 = parser.parse(" 0 50 " + currWidth + " " + currHeight);
            int wrongX = currWidth + 5;
            int wrongY = currHeight + 10;
            // test if x-coordinate wrong.
            CropCommand command2 = parser.parse(" " + wrongX + " 0 200 100");
            // test if y-coordinate wrong.
            CropCommand command3 = parser.parse(" 0 " + wrongY + " 200 200");
            String expectedMessage = "Invalid crop bounds, bounds for the inputs as follows:\n"
                    + "0 <= x-coordinate <= " + currWidth
                    + ", 0 <= y-coordinate <= " + currHeight + ",\n"
                    + "0 <= x + width <= " + currWidth
                    + ", 0 <= y + height <= " + currHeight;
            assertCommandFailure(command, model, commandHistory, expectedMessage, currentEdit);
            assertCommandFailure(command1, model, commandHistory, expectedMessage, currentEdit);
            assertCommandFailure(command2, model, commandHistory, expectedMessage, currentEdit);
            assertCommandFailure(command3, model, commandHistory, expectedMessage, currentEdit);
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
