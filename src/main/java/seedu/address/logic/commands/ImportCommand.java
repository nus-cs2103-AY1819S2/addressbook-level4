package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the image specified in the current directory.\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " paris.jpg";

    public static final String MESSAGE_SUCCESS = "Photo successfully imported.";

    private final Image toImport;

    public ImportCommand(Image image){
        requireNonNull(image);
        toImport = image;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        //TODO - Throw error if image is not of certain format.
        //model.importImage(toImport);
        //TODO - Sync with FomoFoto/AddressBook()
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport));
    }
}
