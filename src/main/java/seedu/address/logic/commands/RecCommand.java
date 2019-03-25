package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Lists modules that the user is recommended to take based on completed modules and course requirements.
 */
public class RecCommand extends Command {

    public static final String COMMAND_WORD = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Recommends a list of modules that can be taken "
            + "based on completed modules and course requirements.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateRecModuleList();

        return new CommandResult(generateResultString(model));
    }

    /**
     * Returns a String representing a list of module codes.
     * @param model The model.
     * @return a String.
     */
    private static String generateResultString(Model model) {
        String result = "Recommended modules to take (decreasing priority):\n";
        ObservableList<ModuleInfoCode> sortedList = model.getRecModuleListSorted();
        for (ModuleInfoCode moduleInfoCode : sortedList) {
            result += moduleInfoCode.toString() + "\n";
        }

        return result;
    }
}
