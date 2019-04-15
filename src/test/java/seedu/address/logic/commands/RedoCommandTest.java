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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.BrightnessCommandParser;
import seedu.address.logic.parser.ContrastCommandParser;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        //Image not opened yet
        try {
            new RedoCommand().execute(currentEdit, model, commandHistory);
            assertCommandFailure(new RedoCommand(), model, commandHistory, Messages.MESSAGE_DID_NOT_OPEN,
                currentEdit);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        //set up
        try {
            ImportCommandParser importParser = new ImportCommandParser();
            importParser.parse("sample").execute(currentEdit, model, commandHistory);
            OpenCommandParser openParser = new OpenCommandParser();
            openParser.parse("iu.jpg").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute() {
        //No Undoable states
        try {
            new RedoCommand().execute(currentEdit, model, commandHistory);
            assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE,
                currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ContrastCommandParser contrastParser = new ContrastCommandParser();
            ContrastCommand command1 = contrastParser.parse(" 2.0");
            BrightnessCommandParser brightnessParser = new BrightnessCommandParser();
            BrightnessCommand command2 = brightnessParser.parse(" 2.0");
            assertCommandSuccess(command1, model, commandHistory, Messages.MESSAGE_CONTRAST_SUCCESS, currentEdit);
            assertCommandSuccess(command2, model, commandHistory, Messages.MESSAGE_BRIGHTNESS_SUCCESS, currentEdit);
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, currentEdit);
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, currentEdit);
            assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
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
