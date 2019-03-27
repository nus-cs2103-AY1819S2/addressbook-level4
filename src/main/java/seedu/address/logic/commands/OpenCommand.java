package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * Opens a previously imported image for editing.
 */

public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens previously imported image. "
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " paris.jpg";

    public static final String MESSAGE_SUCCESS = "Image successfully opened.";

    private final Image toOpen;

    /**
     * Creates an OpenCommand to add the specified {@code Image}
     */
    public OpenCommand(Image image) {
        requireNonNull(image);
        toOpen = image;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album, Model model, CommandHistory history) {
        requireNonNull(currentEdit);
        currentEdit.setOriginalImage(toOpen);
        currentEdit.saveTemp();
        currentEdit.displayTempImage();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toOpen));
    }
}
