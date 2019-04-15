/* @@author Carrein */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Imports a image to FomoFoto.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the image specified to the assets directory. "
            + "Parameters: <absolute path of file>\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Fomo/Pictures/sample.jpg";


    public static final String MESSAGE_SUCCESS = "Image successfully imported.";
    public static final String MESSAGE_DIR_SUCCESS =
            "Directory successfully imported (Note: Invalid file types, hidden files and duplicates are skipped).";

    private final Album album = Album.getInstance();
    private boolean isDirectory;

    /**
     * Creates an ImportCommand to add the specified {@code Image}
     */
    public ImportCommand(boolean isDirectory) {
        requireNonNull(isDirectory);
        this.isDirectory = isDirectory;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) {
        requireNonNull(model);
        album.refreshAlbum();
        String returnString = isDirectory ? MESSAGE_DIR_SUCCESS : MESSAGE_SUCCESS;
        return new CommandResult(returnString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && isDirectory == (((ImportCommand) other).isDirectory)); // state check
    }
}
