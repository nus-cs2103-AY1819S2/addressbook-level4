package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a doctor to the address book.
 */
public class AddDoctorCommand extends Command {

    public static final String COMMAND_WORD = "add-doctor";
    public int index;
    public String name;
    public String gender;
    public int age;
    public String number;
    public String specialisation;

    public AddDoctorCommand() {

    }

    public static void equals() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return new CommandResult(index + " " + name + " " + gender + " " + age + " " +
                number + " " + specialisation);
    }
}
