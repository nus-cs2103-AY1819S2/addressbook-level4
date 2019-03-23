package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.RecModuleComparator;
import seedu.address.model.moduleinfo.ModuleInfo;

public class RecCommand extends Command {

    public static final String COMMAND_WORD = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Recommends a list of modules that can be taken " +
            "based on completed modules and course requirements.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateDisplayList(model.getRecModulePredicate());
        model.sortDisplayList(new RecModuleComparator());

        return new CommandResult(generateResultString(model));
    }

    private static String generateResultString(Model model) {
        String result = "Recommended modules to take (decreasing priority):\n";
        ObservableList<ModuleInfo> sortedList = model.getSortedDisplayList();
        for (ModuleInfo mod : sortedList) {
            result += mod.getCodeString() + "\n";
        }

        return result;
    }
}
