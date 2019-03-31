/* @@author itszp */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNABLE_TO_SAVE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * Saves edited image into assets folder
 */

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS = "Image saved as: %1$s";

    private String toName;

    /**
     * Creates an SaveCommand to add the specified {@code name}
     */
    public SaveCommand(String name) {
        this.toName = name;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) throws CommandException {
        requireNonNull(currentEdit);

        Album album = Album.getInstance();

        if (currentEdit.getTempImage() == null) {
            throw new CommandException(MESSAGE_UNABLE_TO_SAVE);
        }

        Image image = currentEdit.getTempImage();

        if (toName.isEmpty()) {
            this.toName = currentEdit.getOriginalImageName();
        }
        album.saveToAssets(image, toName);
        currentEdit.overwriteOriginal(toName);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toName));
    }
}
