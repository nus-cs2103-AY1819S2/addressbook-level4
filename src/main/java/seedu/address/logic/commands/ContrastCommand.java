/* @@author thamsimun */
package seedu.address.logic.commands;

import java.io.File;
import java.util.OptionalDouble;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.ContrastFilter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * This command allows users to adjust the contrast of images.
 */
public class ContrastCommand extends Command {

    public static final String COMMAND_WORD = "contrast";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adjust the contrast of the image according to ratio value given.\n"
            + "If ratio is not given, default contrast ratio will be 1.1\n"
            + "Parameters: [CONTRAST_VALUE] \n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example2: " + COMMAND_WORD + "1.3";

    private OptionalDouble contrastValue;
    private boolean isNewCommand;

    /**
     * Creates a ContrastCommand object.
     *
     * @param contrastValue contrast value to put on image
     */
    public ContrastCommand(OptionalDouble contrastValue) {
        setCommandName(COMMAND_WORD);
        if (contrastValue.isPresent()) {
            setArguments(String.valueOf(contrastValue.getAsDouble()));
        }
        this.contrastValue = contrastValue;
        this.isNewCommand = true;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
            throws CommandException {
        if (currentEdit.tempImageDoNotExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        seedu.address.model.image.Image initialImage = currentEdit.getTempImage();

        if (this.contrastValue.isPresent()) {
            BufferedOpFilter contrastFilter =
                    new ContrastFilter(this.contrastValue.getAsDouble());
            Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(contrastFilter);
            currentEdit.updateTempImage(outputImage);
        } else {
            BufferedOpFilter contrastFilter =
                    new ContrastFilter(1.1);
            Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(contrastFilter);
            currentEdit.updateTempImage(outputImage);
        }
        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }
        return new CommandResult(Messages.MESSAGE_CONTRAST_SUCCESS);
    }

    @Override
    public String toString() {
        if (contrastValue.isPresent()) {
            return "contrast " + contrastValue.getAsDouble();
        }
        return "contrast";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ContrastCommand) // instanceof handles nulls
            && isValuesEqual(other);
    }

    /**
     * Checks if the value in the contrast command is equal to the value in the other command.
     *
     * @param other command object to be compared
     * */
    private boolean isValuesEqual(Object other) {
        ContrastCommand otherCommand = (ContrastCommand) other;
        if (this.contrastValue.isPresent() && otherCommand.contrastValue.isPresent()) {
            return this.contrastValue.getAsDouble() == otherCommand.contrastValue.getAsDouble();
        } else {
            return (!this.contrastValue.isPresent() && !otherCommand.contrastValue.isPresent());
        }
    }
}
/* @@author */
