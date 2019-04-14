package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists modules that the user is recommended to take based on passed modules and course requirements.
 */
public class RecCommand extends Command {

    public static final String COMMAND_WORD = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Recommends a list of modules that can be taken "
            + "based on GradTrak modules and course requirements.";

    public static final String MESSAGE_REC = "Recommended modules found: %d";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateRecModuleList();

        return new CommandResult(String.format(MESSAGE_REC, model.getRecModuleListSorted().size()));
    }
}
