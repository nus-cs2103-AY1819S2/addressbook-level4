/* @@author randytqw */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ExportCommandParser;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        //Empty Album
        try {
            ExportCommandParser exportParser = new ExportCommandParser();
            ExportCommand command1 = exportParser.parse(" f/iu.jpg d/" + System.getProperty("java.io.tmpdir"));
            assertCommandFailure(command1, model, commandHistory, Messages.MESSAGE_FILE_NOT_FOUND,
                currentEdit);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //set up
        try {
            ImportCommandParser importParser = new ImportCommandParser();
            importParser.parse("sample").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void execute() {
        //Invalid Directory
        try {
            ExportCommandParser exportParser = new ExportCommandParser();
            ExportCommand command1 = exportParser.parse(" f/iu.jpg d/lalala");
            assertCommandFailure(command1, model, commandHistory, Messages.MESSAGE_INVALID_PATH,
                currentEdit);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //File not in album
        try {
            ExportCommandParser exportParser = new ExportCommandParser();
            ExportCommand command2 = exportParser.parse(" f/hello.jpg d/" + System.getProperty("java.io.tmpdir"));
            assertCommandFailure(command2, model, commandHistory, Messages.MESSAGE_FILE_NOT_FOUND, currentEdit);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //Successful Export
        try {
            ExportCommandParser exportParser = new ExportCommandParser();
            ExportCommand command3 = exportParser.parse(" f/iu.jpg d/" + System.getProperty("java.io.tmpdir"));
            assertCommandSuccess(command3, model, commandHistory, Messages.MESSAGE_EXPORT_SUCCESS, currentEdit);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        /*
        // multiple redoable states in model
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
            currentEdit);

        // single redoable state in model
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
            currentEdit);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE, currentEdit);
    }*/
    }
    @After
    public void clearAlbum() {
        Album album = Album.getInstance();
        album.clearAlbum();
        currentEdit.clearTemp();
    }
}
