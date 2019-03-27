/* @@author thamsimun */
package seedu.address.logic.commands;

import java.io.File;
import java.util.OptionalDouble;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.BrightnessFilter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * This command allows users to adjust the brightness of images.
 */
public class BrightnessCommand extends Command {

    public static final String COMMAND_WORD = "brightness";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adjust the brightness of the image according to ratio value given.\n"
        + "If ratio is not given, default brightness ratio will be 1.1\n"
        + "Parameters: [BRIGHTNESS RATIO (double)] "
        + "Example: " + COMMAND_WORD
        + "Example2: " + COMMAND_WORD;
    private OptionalDouble brightnessValue;
    private boolean isNewCommand;

    /**
     * Creates a ContrastCommand object.
     * @param brightnessValue brightness value to add on image
     */
    public BrightnessCommand(OptionalDouble brightnessValue) {
        this.brightnessValue = brightnessValue;
        this.isNewCommand = true;
    }
    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album, Model model, CommandHistory history)
        throws CommandException {
        seedu.address.model.image.Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        if (this.brightnessValue.isPresent()) {
            BufferedOpFilter brightnessFilter =
                new BrightnessFilter(this.brightnessValue.getAsDouble());
            Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(brightnessFilter);
            currentEdit.updateTempImage(outputImage);
        } else {
            BufferedOpFilter brightnessFilter =
                new BrightnessFilter(1.1);
            Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(brightnessFilter);
            currentEdit.updateTempImage(outputImage);
        }

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }
        return new CommandResult(Messages.MESSAGE_BRIGHTNESS_SUCCESS);
    }

}
