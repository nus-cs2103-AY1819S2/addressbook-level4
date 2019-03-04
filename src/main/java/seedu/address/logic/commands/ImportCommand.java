package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * Imports a image to FomoFoto.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the image specified to the assets directory.\n"
            + ": Specified path must be an absolute path.\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " paris.jpg";

    public static final String MESSAGE_SUCCESS = "Image successfully imported.";

    private final Image toImport;

    /**
     * Creates an ImportCommand to add the specified {@code Image}
     */
    public ImportCommand(Image image) {
        requireNonNull(image);
        toImport = image;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.importImage(toImport);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport));
    }
}
