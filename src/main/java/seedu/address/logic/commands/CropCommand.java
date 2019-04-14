/* @@author kayheen */
package seedu.address.logic.commands;

import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * This command allows the user to crop an image.
 */
public class CropCommand extends Command {

    public static final String COMMAND_WORD = "crop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Crop the image according to the coordinates given. All values to be integer/whole numbers only.\n"
            + "Parameters: X-COORDINATE Y-COORDINATE WIDTH HEIGHT \n"
            + "Example: " + COMMAND_WORD + " 0 0 200 200";
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;
    private boolean isNewCommand;

    public CropCommand(int x, int y, int width, int height) {
        setCommandName(COMMAND_WORD);
        String argument = x + " : " + y + " : " + width + " : " + height;
        setArguments(argument);
        xCoord = x;
        yCoord = y;
        this.width = width;
        this.height = height;
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
            BufferedImage initImage = initialImage.getBufferedImage();
            BufferedImage finalImage = Scalr.crop(initImage, xCoord, yCoord, width, height);
            currentEdit.updateTempImage(finalImage);
        } catch (IllegalArgumentException e) {
            throw new CommandException("Invalid crop bounds, bounds for the inputs as follows:\n"
                    + "0 <= x-coordinate <= " + initialImage.getWidth()
                    + ", 0 <= y-coordinate <= " + initialImage.getHeight() + ",\n"
                    + "0 <= x + width <= " + initialImage.getWidth()
                    + ", 0 <= y + height <= " + initialImage.getHeight());
        }

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }
        return new CommandResult(Messages.MESSAGE_CROP_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CropCommand // instanceof handles nulls
                && isValuesEqual(other));
    }
    private boolean isValuesEqual(Object other) {
        CropCommand temp = (CropCommand) other;
        return this.xCoord == temp.xCoord && this.yCoord == temp.yCoord && this.width == temp.width
                && this.height == temp.height;
    }
    public String toString() {
        return "crop " + xCoord + " " + yCoord + " " + width + " " + height;
    }
}
