/* @@author thamsimun */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.BlackWhiteCommandParser;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class BlackWhiteCommandTest {
    private Album album = Album.getInstance();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();
    @Before
    public void createImagesInAlbum() {
        album.clearAlbum();
        try {
            BlackWhiteCommandParser parserBlackWhite = new BlackWhiteCommandParser();
            BlackWhiteCommand blackwhite = parserBlackWhite.parse(" 123");
            String expectedMessage = Messages.MESSAGE_DID_NOT_OPEN;
            assertCommandFailure(blackwhite, model, commandHistory, expectedMessage, currentEdit);
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse("sample").execute(currentEdit, model, commandHistory);
            OpenCommandParser parser2 = new OpenCommandParser();
            parser2.parse("iu.jpg").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_validBlackWhiteCheckIsNewCommand_success() {
        try {
            BlackWhiteCommandParser parser = new BlackWhiteCommandParser();
            BlackWhiteCommand command = parser.parse(" 123");
            String expectedMessage = Messages.MESSAGE_BLACKWHITE_SUCCESS;
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
/* @@author*/
