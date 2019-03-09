package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows statistics for the given patient
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + " : Shows statistics for the identified patient. Patient can be identified either by the index number in "
        + "the displayed person list OR by keyword.\n"
        + "Parameters: INDEX (must be positive integer) or KEYWORD \n"
        + "Example 1: " + COMMAND_WORD + "3\n"
        + "Example 2: " + COMMAND_WORD + "alice\n";

    private final Person toStat;

    StatsCommand(Person person) {
        toStat = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
