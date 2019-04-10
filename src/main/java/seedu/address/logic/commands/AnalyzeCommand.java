package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class AnalyzeCommand extends Command{

    public static final String COMMAND_WORD = "analyze";

    public static final String MESSAGE_SUCCESS = "Showed the salary scatter chart for all persons";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, false, false, true));


    }
}