/* @@author thamsimun */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertPresetCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ContrastCommandParser;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.logic.parser.SavePresetCommandParser;
import seedu.address.logic.parser.SetPresetCommandParser;
import seedu.address.logic.parser.WaterMarkCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TransformationSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.image.Image;

public class SetPresetCommandTest {
    private Album album = Album.getInstance();
    private TransformationSet transformationSet = TransformationSet.getInstance();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        album.clearAlbum();
        transformationSet.clear();
        try {
            SetPresetCommandParser parserSetPreset = new SetPresetCommandParser();
            SetPresetCommand setPreset = parserSetPreset.parse(" 1.3");
            String expectedMessage = Messages.MESSAGE_DID_NOT_OPEN;
            assertCommandFailure(setPreset, model, commandHistory, expectedMessage, currentEdit);
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse("sample").execute(currentEdit, model, commandHistory);
            OpenCommandParser parser2 = new OpenCommandParser();
            parser2.parse("iu.jpg").execute(currentEdit, model, commandHistory);
            ContrastCommandParser contrastCommandParser = new ContrastCommandParser();
            contrastCommandParser.parse(" 1.3").execute(currentEdit, model, commandHistory);
            WaterMarkCommandParser waterMarkCommandParser = new WaterMarkCommandParser();
            waterMarkCommandParser.parse("cute").execute(currentEdit, model, commandHistory);
            SavePresetCommandParser savePresetCommandParser = new SavePresetCommandParser();
            savePresetCommandParser.parse("success").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void execute_setPresetOnDifferentImage_success() {

        try {
            OpenCommandParser openCommandParser = new OpenCommandParser();
            openCommandParser.parse("validJPGTest.jpg").execute(currentEdit, model, commandHistory);
            SetPresetCommandParser setPresetCommandParser = new SetPresetCommandParser();
            SetPresetCommand setPresetCommand = setPresetCommandParser.parse("success");
            List<Command> presetList = transformationSet.findTransformation("success");
            StringBuilder toPrint = new StringBuilder();
            for (Command command: presetList) {
                toPrint.append("[" + command.toString() + "]");
            }
            String expectedMessage = Messages.MESSAGE_SETPRESET_SUCCESS + " " + toPrint.toString();
            assertCommandSuccess(setPresetCommand, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_setPresetNotFound_fail() {
        try {
            SetPresetCommandParser setPresetCommandParser = new SetPresetCommandParser();
            SetPresetCommand setPresetCommand = setPresetCommandParser.parse("fail");
            String expectedMessage = Messages.MESSAGE_SETPRESET_FAIL_NOTFOUND;
            assertCommandFailure(setPresetCommand, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void execute_setPresetOnSameImagewithWatermarkAlready_fail() {
        String expectedMessage = "";
        //
        try {
            SetPresetCommandParser setPresetCommandParser = new SetPresetCommandParser();
            SetPresetCommand setPresetCommand = setPresetCommandParser.parse("success");
            List<Command> presetList = transformationSet.findTransformation("success");
            for (Command editCommand: presetList) {
                try {
                    editCommand.execute(currentEdit, model, commandHistory);
                } catch (CommandException exception) {
                    Image initialImage = currentEdit.getTempImage();
                    List<Command> listBefore = initialImage.getSubHistory();
                    currentEdit.replaceTempWithOriginal();
                    currentEdit.getTempImage().setWaterMark(false);
                    for (Command pastCommand: listBefore) {
                        pastCommand.execute(currentEdit, model, commandHistory);
                    }
                    expectedMessage = "Error in [" + editCommand.toString() + "]:\n"
                        + exception.toString().substring(58);
                }
            }
            assertPresetCommandFailure(setPresetCommand, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/* @@author*/
