/* @@author Carrein */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
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
            "Directory successfully imported (Note: Invalid file types and duplicates are skipped).";

    private final boolean isDirectory;

    /**
     * Creates an ImportCommand to add the specified {@code Image}
     */
    public ImportCommand(boolean isDirectory) {
        requireNonNull(isDirectory);
        this.isDirectory = isDirectory;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.refreshAlbum();
        String returnString = isDirectory ? MESSAGE_DIR_SUCCESS : MESSAGE_SUCCESS;
        return new CommandResult(String.format(returnString));
    }
}
