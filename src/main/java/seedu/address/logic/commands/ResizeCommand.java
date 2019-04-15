/* @@author kayheen */
package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.MAX_FILE_SIZE;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import org.imgscalr.Scalr;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * This command allows users to resize images to desired size.
 */
public class ResizeCommand extends Command {

    public static final String COMMAND_WORD = "resize";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resize the image according to the width and height specified.\n"
            + "Parameters: WIDTH HEIGHT (only positive integers are allowed)\n"
            + "Example: " + COMMAND_WORD + " 20 40 ";

    private int width;
    private int height;
    private boolean isNewCommand;

    /**
     * Creates a Resize Command object.
     * @param w is the width of the new image.
     * @param h is the height of the new image.
     */
    public ResizeCommand(int w, int h) {
        setCommandName(COMMAND_WORD);
        String argument = w + " : " + h;
        setArguments(argument);
        width = w;
        height = h;
        this.isNewCommand = true;
    }
    @Override
    public CommandResult execute(CurrentEdit currentEdit,
                                 Model model, CommandHistory history) throws CommandException {
        if (currentEdit.tempImageDoNotExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        Image initialImage = currentEdit.getTempImage();
        try {
            BufferedImage bufferedImage = initialImage.getBufferedImage();
            BufferedImage editedBuffer = Scalr.resize(bufferedImage, Scalr.Method.QUALITY,
                    Scalr.Mode.FIT_EXACT, width, height);
            if (size(editedBuffer) > MAX_FILE_SIZE) {
                throw new CommandException(Messages.MESSAGE_RESIZE_VALUES_TOO_LARGE);
            }
            currentEdit.updateTempImage(editedBuffer);
        } catch (IllegalArgumentException x) {
            throw new CommandException(MESSAGE_USAGE);
        } catch (OutOfMemoryError | NegativeArraySizeException e) {
            throw new CommandException(Messages.MESSAGE_RESIZE_VALUES_TOO_LARGE);
        }
        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }

        return new CommandResult(Messages.MESSAGE_RESIZE_SUCCESS);
    }

    /**
     * This method calculates the size of the final image.
     * @param buffer the BufferedImage object to calculate size.
     * @return the size of the object in bytes.
     */
    private long size(BufferedImage buffer) {
        DataBuffer dataBuffer = buffer.getData().getDataBuffer();

        // Each bank element in the data buffer is a 32-bit integer
        return ((long) dataBuffer.getSize()) * 4L;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResizeCommand // instanceof handles nulls
                && isValuesEqual(other));
    }
    private boolean isValuesEqual(Object other) {
        ResizeCommand temp = (ResizeCommand) other;
        return this.width == temp.width && this.height == temp.height;
    }

    @Override
    public String toString() {
        return "resize " + width + " " + height;
    }
}
/* @@author*/
