package seedu.address.logic.commands;

import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Album;
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
        width = w;
        height = h;
        this.isNewCommand = true;
    }
    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album,
                                 Model model, CommandHistory history) throws CommandException {
        Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        try {
            BufferedImage bufferedImage = initialImage.getBufferedImage();
            BufferedImage editedBuffer = Scalr.resize(bufferedImage, Scalr.Method.QUALITY,
                    Scalr.Mode.FIT_EXACT, width, height, null);
            // need to give a sneak peak before you actually write into the file.
            currentEdit.updateTempImage(editedBuffer);
        } catch (IllegalArgumentException x) {
            throw new CommandException(MESSAGE_USAGE);
        }
        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }

        return new CommandResult(Messages.MESSAGE_RESIZE_SUCCESS);
    }
}

