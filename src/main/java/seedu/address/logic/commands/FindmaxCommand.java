package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class FindmaxCommand extends Command {

    public static final String COMMAND_WORD = "findmaxsa";

    public static final String MESSAGE_SUCCESS = "The max salary of all persons: %.2f";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        int max = 0;
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            if (Integer.parseInt(String.valueOf(model.getFilteredPersonList().get(i).getSalary())) > max) {
                max = Integer.parseInt(String.valueOf(model.getFilteredPersonList().get(i).getSalary()));
            }
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, (double) max));


    }
}



