/* @@author randytqw */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
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

public class UndoCommandTest {
    private final Album album = Album.getInstance();
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private final CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute() {
        album.clearAlbum();
        //Image not opened yet
        try {
            new UndoCommand().execute(currentEdit, model, commandHistory);
            assertCommandFailure(new UndoCommand(), model, commandHistory, Messages.MESSAGE_DID_NOT_OPEN,
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

        //No Undoable states
        try {
            new UndoCommand().execute(currentEdit, model, commandHistory);
            assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE,
                currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ContrastCommandParser contrastParser = new ContrastCommandParser();
            ContrastCommand command1 = contrastParser.parse(" 2.0");
            command1.execute(currentEdit, model, commandHistory);
            BrightnessCommandParser brightnessParser = new BrightnessCommandParser();
            BrightnessCommand command2 = brightnessParser.parse(" 2.0");
            command2.execute(currentEdit, model, commandHistory);
            //assertCommandSuccess(command1, model, commandHistory, Messages.MESSAGE_CONTRAST_SUCCESS, currentEdit);
            // assertCommandSuccess(command2, model, commandHistory, Messages.MESSAGE_BRIGHTNESS_SUCCESS, currentEdit);
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, currentEdit);
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, currentEdit);
            assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        // multiple undoable states in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
            currentEdit);

        // single undoable state in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
            currentEdit);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE, currentEdit); */
    }
    @After
    public void clearAlbum() {
        Album album = Album.getInstance();
        album.clearAlbum();
        currentEdit.clearTemp();
    }
}
