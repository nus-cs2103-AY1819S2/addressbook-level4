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
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.logic.parser.RotateCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RotateCommandTest {
    private Album album = Album.getInstance();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();
    @Before
    public void createImagesInAlbum() {
        album.clearAlbum();
        try {
            // when image is null, should throw an error
            RotateCommandParser parserRotate = new RotateCommandParser();
            RotateCommand rotate = parserRotate.parse(" 90");
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
    public void execute_validRotateCheckIsNewCommand_success() {
        try {
            RotateCommandParser parser = new RotateCommandParser();
            RotateCommand command = parser.parse(" 90");
            String expectedMessage = Messages.MESSAGE_ROTATE_SUCCESS;
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_invalidDegreeInRotate_failure() {
        try {
            RotateCommandParser parser = new RotateCommandParser();
            RotateCommand command = parser.parse(" 45");
            RotateCommand command1 = parser.parse(" 170");
            RotateCommand command2 = parser.parse(" 200");
            RotateCommand command3 = parser.parse(" 390");
            String expectedMessage = String.format(Messages.MESSAGE_ROTATE_DEGREE_ERROR,
                    RotateCommand.MESSAGE_USAGE);
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
