package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    private static final String MEESAGE_COPY_EXIST = "!eExists unedited copies in addressbook.\n"
                                                   + "No copies will be saved.\n"
                                                   + "Use exit! to exit anyway";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (model.checkNoCopy()) {
            return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        } else {
            return new CommandResult(MEESAGE_COPY_EXIST);
        }
    }

}
