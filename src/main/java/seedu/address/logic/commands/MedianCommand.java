package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class MedianCommand extends Command{

    public static final String COMMAND_WORD = "median";

    public static final String MESSAGE_SUCCESS = "Showed the mean salary of all persons: %.1f";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        int median = 0;
        int[] person = new int[model.getFilteredPersonList().size()];

        for(int i = 0; i < model.getFilteredPersonList().size(); i++) {
            person[i] = Integer.parseInt(String.valueOf(model.getFilteredPersonList().get(i).getSalary()));
        }

        Arrays.sort(person);
        if (person.length % 2 == 0) {
            median = (person[person.length / 2] + person[person.length / 2 - 1]) / 2;
        } else {
            median = person[(person.length - 1) / 2];
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, (double) median));


    }
}