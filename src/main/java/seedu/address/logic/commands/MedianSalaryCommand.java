package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;


import static java.util.Objects.requireNonNull;

public class MedianSalaryCommand extends Command{

    public static final String COMMAND_WORD = "medianSalary";

    public static final String MESSAGE_SUCCESS = "Showed the mean salary of all persons: %.2f";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        
        int sum = 0;
        for(int i = 0; i < model.getFilteredPersonList().size(); i++) {
            sum += Integer.parseInt(String.valueOf(model.getFilteredPersonList().get(i).getSalary()));
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, (double) sum / model.getFilteredPersonList().size()));


    }
}
