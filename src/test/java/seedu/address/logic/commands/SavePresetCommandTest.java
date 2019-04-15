/* @@author thamsimun */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertPresetCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.BrightnessCommandParser;
import seedu.address.logic.parser.ContrastCommandParser;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.logic.parser.RotateCommandParser;
import seedu.address.logic.parser.SavePresetCommandParser;
import seedu.address.logic.parser.WaterMarkCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TransformationSet;
import seedu.address.model.UserPrefs;

public class SavePresetCommandTest {
    private Album album = Album.getInstance();
    private TransformationSet transformationSet = TransformationSet.getInstance();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void createImagesInAlbum() {
        album.clearAlbum();
        transformationSet.clear();
        try {
            SavePresetCommandParser parserSavePreset = new SavePresetCommandParser();
            SavePresetCommand savePreset = parserSavePreset.parse(" 1.3");
            String expectedMessage = Messages.MESSAGE_DID_NOT_OPEN;
            assertCommandFailure(savePreset, model, commandHistory, expectedMessage, currentEdit);
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse("sample").execute(currentEdit, model, commandHistory);
            OpenCommandParser parser2 = new OpenCommandParser();
            parser2.parse("iu.jpg").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void execute_savePreset_success() {
        //Execute some editing commands to save in preset
        try {
            ContrastCommandParser contrastParser = new ContrastCommandParser();
            ContrastCommand contrastCommand = contrastParser.parse(" 1.3");
            contrastCommand.execute(currentEdit, model, commandHistory);
            WaterMarkCommandParser waterMarkCommandParser = new WaterMarkCommandParser();
            WaterMarkCommand waterMarkCommand = waterMarkCommandParser.parse(" hi");
            waterMarkCommand.execute(currentEdit, model, commandHistory);
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute(currentEdit, model, commandHistory);
            WaterMarkCommand waterMarkCommand1 = waterMarkCommandParser.parse(" test");
            waterMarkCommand1.execute(currentEdit, model, commandHistory);
            SavePresetCommandParser parser = new SavePresetCommandParser();
            SavePresetCommand command = parser.parse(" preset1");
            List<Command> presetList = currentEdit.getTempImage().getSubHistory();
            StringBuilder toPrint = new StringBuilder();
            for (Command editcommand: presetList) {
                toPrint.append("[" + editcommand.toString() + "]");
            }
            String expectedMessage = Messages.MESSAGE_SAVEPRESET_SUCCESS + " " + toPrint.toString();
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void execute_savePresetWhenEmpty_fail() {
        //Save the no commands into the preset
        try {
            SavePresetCommandParser parser = new SavePresetCommandParser();
            SavePresetCommand command = parser.parse(" empty");
            String expectedMessage = Messages.MESSAGE_SAVEPRESET_FAIL_EMPTY;
            assertCommandFailure(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void execute_savePresetWhenDuplicate_fail() {
        //Save a preset with name "hi"
        try {
            BrightnessCommandParser brightnessCommandParser = new BrightnessCommandParser();
            BrightnessCommand brightnessCommand = brightnessCommandParser.parse("1.3");
            brightnessCommand.execute(currentEdit, model, commandHistory);
            RotateCommandParser rotateCommandParser = new RotateCommandParser();
            RotateCommand rotateCommand = rotateCommandParser.parse("90");
            rotateCommand.execute(currentEdit, model, commandHistory);
            SavePresetCommandParser savePresetCommandParser = new SavePresetCommandParser();
            SavePresetCommand savePresetCommand = savePresetCommandParser.parse("hi");
            savePresetCommand.execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Save a preset with the same name "hi"
        try {
            ContrastCommandParser contrastCommandParser = new ContrastCommandParser();
            ContrastCommand contrastCommand = contrastCommandParser.parse("1.3");
            contrastCommand.execute(currentEdit, model, commandHistory);
            SavePresetCommandParser savePresetCommandParser = new SavePresetCommandParser();
            SavePresetCommand savePresetCommand = savePresetCommandParser.parse("hi");
            String expectedMessage = Messages.MESSAGE_SAVEPRESET_FAIL_DUPLICATE;
            assertPresetCommandFailure(savePresetCommand, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void clearTransformationAndAlbum() {
        transformationSet.clear();
        album.clearAlbum();
    }
}
/* @@author*/
