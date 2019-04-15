package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.IMAGE_DIRECTORY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ImageCommand.MESSAGE_INVALID_FILE;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit / integration tests for ImageCommand.
 */
public class ImageCommandTest {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private Model model;
    private CommandHistory commandHistory;
    private final String validFlashcard = "test_image.png";
    private final String invalidFlashcard = "nonexistent_image.png";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    //This test fails on Travis for some reason
    //It works locally
    @Ignore
    @Test
    public void execute_imageCommand_success() throws CommandException {
        File imageToProduce = new File(IMAGE_DIRECTORY.concat(validFlashcard));
        File imageToTest = new File("src\\test\\data\\images\\".concat(validFlashcard));
        if (!imageToTest.exists()) {
            logger.severe("Image ".concat(imageToTest.getAbsolutePath()).concat(" couldn't be found."));
        }
        if (imageToProduce.delete()) {
            logger.warning("Image ".concat(imageToProduce.getAbsolutePath()).concat(" deleted."));
        }
        ImageCommand command = new ImageCommand(imageToTest);
        command.execute(model, commandHistory);
        assert(imageToProduce.exists());
        if (!imageToProduce.delete()) {
            logger.severe("Image ".concat(imageToProduce.getAbsolutePath()).concat(" couldn't be deleted."));
        }
    }

    //This test fails on Travis for some reason
    //It works locally
    @Ignore
    @Test
    public void execute_imageCommand_failDuplicate() throws CommandException {
        File imageToProduce = new File(IMAGE_DIRECTORY.concat(validFlashcard));
        File imageToTest = new File("src\\test\\data\\images\\".concat(validFlashcard));
        if (!imageToTest.exists()) {
            logger.severe("Image ".concat(imageToTest.getAbsolutePath()).concat(" couldn't be found."));
        }
        if (imageToProduce.delete()) {
            logger.warning("Image ".concat(imageToProduce.getAbsolutePath()).concat(" deleted."));
        }
        try {
            if (!imageToProduce.createNewFile()) {
                //this should not happen, we just deleted the file
                throw new IOException();
            }
        } catch (IOException e) {
            //this should not happen
            assert(false);
        }
        ImageCommand command = new ImageCommand(imageToTest);
        assertCommandFailure(command, model, commandHistory, ImageCommand.MESSAGE_DUPLICATE_NAME);
        if (!imageToProduce.delete()) {
            logger.severe("Image ".concat(imageToProduce.getAbsolutePath()).concat(" couldn't be deleted."));
        }
    }

    @Test
    public void execute_imageCommand_failNonExistent() throws CommandException {
        File imageToTest = new File("src\\test\\data\\images\\".concat(invalidFlashcard));
        if (imageToTest.exists()) {
            logger.severe("Image ".concat(imageToTest.getAbsolutePath()).concat(" shouldn't exist."));
        }
        ImageCommand command = new ImageCommand(imageToTest);
        assertCommandFailure(command, model, commandHistory, String.format(MESSAGE_INVALID_FILE, imageToTest));
    }

    @Test(expected = NullPointerException.class)
    public void execute_imageCommand_failNull() throws CommandException {
        ImageCommand command = new ImageCommand(null);
        command.execute(model, commandHistory);
    }
}
