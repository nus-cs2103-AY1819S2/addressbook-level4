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
            + "Parameters: [BRIGHTNESSVALUE] "
            + "Example: " + COMMAND_WORD + "\n"
            + "Example2: " + COMMAND_WORD + "0.9";
    private OptionalDouble brightnessValue;
    private boolean isNewCommand;

    /**
     * Creates a ContrastCommand object.
     *
     * @param brightnessValue brightness value to add on image
     */
    public BrightnessCommand(OptionalDouble brightnessValue) {
        setCommandName(COMMAND_WORD);
        if (brightnessValue.isPresent()) {
            setArguments(String.valueOf(brightnessValue.getAsDouble()));
        }
        this.brightnessValue = brightnessValue;
        this.isNewCommand = true;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
            throws CommandException {
        if (currentEdit.tempImageDoNotExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        seedu.address.model.image.Image initialImage = currentEdit.getTempImage();
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

    @Override
    public String toString() {
        if (brightnessValue.isPresent()) {
            return "brightness " + brightnessValue.getAsDouble();
        }
        return "brightness";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BrightnessCommand) // instanceof handles nulls
            && isValuesEqual(other);
    }

    /**
     * Checks if the value in the contrast command is equal to the value in the other command.
     *
     * @param other command object to be compared
     * */
    private boolean isValuesEqual(Object other) {
        BrightnessCommand otherCommand = (BrightnessCommand) other;
        if (this.brightnessValue.isPresent() && otherCommand.brightnessValue.isPresent()) {
            return this.brightnessValue.getAsDouble() == otherCommand.brightnessValue.getAsDouble();
        } else {
            return (!this.brightnessValue.isPresent() && !otherCommand.brightnessValue.isPresent());
        }
    }
}
/* @@author */
