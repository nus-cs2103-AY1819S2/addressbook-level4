package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Abort the current consultation
 */
public class AbortConsultationCommand extends Command {

    public static final String COMMAND_WORD = "abort";
    public static final String COMMAND_ALIAS = "ab";

    public static final String NO_CURRENT_CONSULTATION = "No ongoing session to abort\n";
    public static final String ABORT_CONSULT_FEEDBACK = "Consultation session aborted\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.getCurrentConsultation() == null) {
            throw new CommandException(NO_CURRENT_CONSULTATION);
        }

        model.abortConsultation();

        return new CommandResult(ABORT_CONSULT_FEEDBACK);
    }
}
