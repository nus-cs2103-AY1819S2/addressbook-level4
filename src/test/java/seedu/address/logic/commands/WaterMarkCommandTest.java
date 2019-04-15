/* @@author kayheen */
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.OptionalDouble;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.logic.parser.OpenCommandParser;
import seedu.address.logic.parser.WaterMarkCommandParser;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;


public class WaterMarkCommandTest {
    private Album album = Album.getInstance();
    private Model model = new ModelManager();
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void createImagesInAlbum() {
        try {
            // when image is null, should throw an error
            WaterMarkCommandParser parserRotate = new WaterMarkCommandParser();
            WaterMarkCommand waterMark = parserRotate.parse(" FomoFoto");
            String expectedMessage = Messages.MESSAGE_DID_NOT_OPEN;
            assertCommandFailure(waterMark, model, commandHistory, expectedMessage, currentEdit);
            ImportCommandParser parser = new ImportCommandParser();
            parser.parse("sample").execute(currentEdit, model, commandHistory);
            OpenCommandParser parser2 = new OpenCommandParser();
            parser2.parse("iu.jpg").execute(currentEdit, model, commandHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This tests for when WaterMark Command is a new command and image has no watermark
    @Test
    public void execute_validWaterMark_success() {
        try {
            WaterMarkCommandParser parser = new WaterMarkCommandParser();
            WaterMarkCommand command = parser.parse(" FomoFoto");
            String expectedMessage = Messages.MESSAGE_WATERMARK_SUCCESS;
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This tests for when WaterMark Command is a new command and image has a watermark.
    @Test
    public void execute_addWaterMarkWhenAlreadyHave_failure() {
        try {
            // preparing a the initial image with watermark.
            WaterMarkCommandParser parser = new WaterMarkCommandParser();
            parser.parse(" FomoFoto").execute(currentEdit, model, commandHistory);
            // execute the test here.
            WaterMarkCommand command = parser.parse(" FomoFoto");
            String expectedMessage = Messages.MESSAGE_HAS_WATERMARK;
            assertCommandFailure(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This tests for when WaterMark Command is undone on an image.
    @Test
    public void execute_addWaterMarkCommandUndoRedo_success() {
        try {
            // add rotate, watermark, contrast commands
            new RotateCommand(90).execute(currentEdit, model, commandHistory);
            new WaterMarkCommand("FomoFoto", true).execute(currentEdit, model, commandHistory);
            new ContrastCommand(OptionalDouble.of(Double.parseDouble("0.5"))).execute(currentEdit, model,
                    commandHistory);
            // undo contrast, undo wm
            new UndoCommand().execute(currentEdit, model, commandHistory);
            new UndoCommand().execute(currentEdit, model, commandHistory);
            // image now should only have rotate applied to it.
            WaterMarkCommandParser parser = new WaterMarkCommandParser();
            WaterMarkCommand command = parser.parse(" FomoFoto");
            String expectedMessage = Messages.MESSAGE_WATERMARK_SUCCESS;
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This tests for when WaterMark Command is undone and then redone on an image.
    @Test
    public void execute_addWaterMarkCommandUndoRedo_failure() {
        try {
            // same as previous test case.
            new RotateCommand(90).execute(currentEdit, model, commandHistory);
            new WaterMarkCommand("FomoFoto", true).execute(currentEdit, model, commandHistory);
            new ContrastCommand(OptionalDouble.of(Double.parseDouble("0.5"))).execute(currentEdit, model,
                    commandHistory);
            new UndoCommand().execute(currentEdit, model, commandHistory);
            new UndoCommand().execute(currentEdit, model, commandHistory);
            // redo the WaterMark Command to test that redo also allows the detection that image has a watermark.
            new RedoCommand().execute(currentEdit, model, commandHistory);
            // should fail here.
            WaterMarkCommandParser parser = new WaterMarkCommandParser();
            WaterMarkCommand command = parser.parse(" FomoFoto");
            String expectedMessage = Messages.MESSAGE_HAS_WATERMARK;
            assertCommandFailure(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This tests for when a preset is set and applies on an image without a watermark (success)
    @Test
    public void execute_addWaterMarkCommandPreset_success() {
        try {
            // imitates the case when a preset is added and a watermark command within a preset is created.
            new WaterMarkCommand("FomoFoto", true).execute(currentEdit, model, commandHistory);
            WaterMarkCommand command = new WaterMarkCommand("FomoFoto", false);
            // opens a new image to edit on (new image opened does not have a watermark and hence should succeed)
            OpenCommandParser parser = new OpenCommandParser();
            parser.parse("validPNGTest.png").execute(currentEdit, model, commandHistory);
            String expectedMessage = Messages.MESSAGE_WATERMARK_SUCCESS;
            assertCommandSuccess(command, model, commandHistory, expectedMessage, currentEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This tests for when a preset is set and applies on an image with a watermark (failure)
    @Test
    public void execute_addWaterMarkCommandPreset_failure() {
        try {
            // imitates the case when a preset is added and a watermark command within a preset is created.
            new WaterMarkCommand("FomoFoto", true).execute(currentEdit, model, commandHistory);
            WaterMarkCommand command = new WaterMarkCommand("FomoFoto", false);
            // the test should fail because the current image already has a watermark before the preset is added.
            String expectedMessage = Messages.MESSAGE_HAS_WATERMARK;
            assertCommandFailure(command, model, commandHistory, expectedMessage, currentEdit);
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
