/* @@author thamsimun */
package seedu.address.logic.commands;

import java.io.File;
import java.util.OptionalInt;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.ThresholdFilter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * This command allows users to apply the black/white filter on the image.
 */
public class BlackWhiteCommand extends Command {

    public static final String COMMAND_WORD = "bw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Apply the black and white filter on the image with threshold given.\n"
        + "If threshold is not given, default threshold value is 127.\n"
        + "Parameters: [THRESHOLDVALUE] "
        + "Example: " + COMMAND_WORD + "\n"
        + "Example2: " + COMMAND_WORD + "130";
    private OptionalInt threshold;
    private boolean isNewCommand;

    /**
     * Creates a ContrastCommand object.
     * @param threshold pixels lighter than this threshold becomes white and pixels darker than it becomes black.
     */
    public BlackWhiteCommand(OptionalInt threshold) {
        setCommandName(COMMAND_WORD);
        if (threshold.isPresent()) {
            setArguments(String.valueOf(threshold.getAsInt()));
        }
        this.threshold = threshold;
        this.isNewCommand = true;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
        throws CommandException {

        if (currentEdit.tempImageDoNotExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        seedu.address.model.image.Image initialImage = currentEdit.getTempImage();

        if (threshold.isPresent()) {
            BufferedOpFilter bwFilter =
                new ThresholdFilter(threshold.getAsInt(), 0xffffff, 0x000000);
            Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(bwFilter);
            currentEdit.updateTempImage(outputImage);
        } else {
            BufferedOpFilter bwFilter =
                new ThresholdFilter(127, 0xffffff, 0x000000);
            Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(bwFilter);
            currentEdit.updateTempImage(outputImage);
        }

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }
        return new CommandResult(Messages.MESSAGE_BLACKWHITE_SUCCESS);
    }

    @Override
    public String toString() {
        if (threshold.isPresent()) {
            return "bw " + threshold.getAsInt();
        }
        return "bw";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BlackWhiteCommand) // instanceof handles nulls
            && isValuesEqual(other);
    }

    /**
     * Checks if the value in the contrast command is equal to the value in the other command.
     *
     * @param other command object to be compared
     * */
    private boolean isValuesEqual(Object other) {
        BlackWhiteCommand otherCommand = (BlackWhiteCommand) other;
        if (this.threshold.isPresent() && otherCommand.threshold.isPresent()) {
            return this.threshold.getAsInt() == otherCommand.threshold.getAsInt();
        } else {
            return (!this.threshold.isPresent() && !otherCommand.threshold.isPresent());
        }
    }
}
/* @@author*/
