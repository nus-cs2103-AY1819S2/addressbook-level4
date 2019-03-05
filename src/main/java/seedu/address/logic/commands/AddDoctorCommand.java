package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Age;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds a doctor to the address book.
 */
public class AddDoctorCommand extends Command {

    public static final String COMMAND_WORD = "add-doctor";

    public AddDoctorCommand() {

    }

    public AddDoctorCommand(Name name, Gender gender, Age age, Phone phone, Set<Tag> specialisation) {

    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        return new CommandResult("test");

    }
}
