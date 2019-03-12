package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Imports resume txt files from a given directory into slavefinder().
 */
public class ImportResumesCommand extends Command {

    public static final String COMMAND_WORD = "importResumes";
    public static final String MESSAGE_SUCCESS = "Resumes has been imported";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return null;
    }
}
