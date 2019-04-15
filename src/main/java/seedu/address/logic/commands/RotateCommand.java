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
 * This command allows users to rotate images.
 */
public class RotateCommand extends Command {

    public static final String COMMAND_WORD = "rotate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rotate the image according to the degree of input.\n"
            + "Parameters: DEGREE (only 90, 180 and 270 are accepted.) \n"
            + "Example: " + COMMAND_WORD + " 90";

    private int degree;
    private Scalr.Rotation rotate;
    private boolean isNewCommand;

    /**
     * Creates a RotateCommand object.
     * @param value is the degree of rotation.
     */
    public RotateCommand(int value) {
        setCommandName(COMMAND_WORD);
        setArguments(String.valueOf(value));
        this.isNewCommand = true;
        this.degree = value;
    }
    @Override
    public CommandResult execute(CurrentEdit currentEdit,
                                 Model model, CommandHistory history) throws CommandException {
        if (currentEdit.tempImageDoNotExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        Image initialImage = currentEdit.getTempImage();
        if (degree == 90) {
            rotate = Scalr.Rotation.CW_90;
        } else if (degree == 180) {
            rotate = Scalr.Rotation.CW_180;
        } else if (degree == 270) {
            rotate = Scalr.Rotation.CW_270;
        } else {
            throw new CommandException(String.format(Messages.MESSAGE_ROTATE_DEGREE_ERROR,
                    RotateCommand.MESSAGE_USAGE));
        }

        BufferedImage bufferedImage = initialImage.getBufferedImage();
        BufferedImage editedBuffer = Scalr.rotate(bufferedImage, rotate);
        currentEdit.updateTempImage(editedBuffer);

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }

        return new CommandResult(Messages.MESSAGE_ROTATE_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RotateCommand // instanceof handles nulls
                && (this.degree == ((RotateCommand) other).degree));
    }
    public String toString() {
        return "rotate " + degree;
    }
}
