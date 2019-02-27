package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Imports the records.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the file \n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " records1.txt";

    public static final String MESSAGE_SUCCESS = "Imported the records!";

    private final String path;

    public ImportCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        System.out.println(path);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
