package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class FindminCommand extends Command {

    public static final String COMMAND_WORD = "findminsa";

    public static final String MESSAGE_SUCCESS = "The min salary of all persons: %.2f";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        int min = 999999999;
        for(int i = 0; i < model.getFilteredPersonList().size(); i++) {
            if (Integer.parseInt(String.valueOf(model.getFilteredPersonList().get(i).getSalary())) < min) {
                min = Integer.parseInt(String.valueOf(model.getFilteredPersonList().get(i).getSalary()));
            }
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, (double) min));


    }
}



