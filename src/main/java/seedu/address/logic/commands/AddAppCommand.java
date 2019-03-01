package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to quickdocs.
 */
public class AddAppCommand extends Command {

    public static final String COMMAND_WORD = "appadd";
    //public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to quickdocs. "
            + "Parameters: INDEX DATE START END"
            + "Example: " + COMMAND_WORD + " 3 231019 1600 1700";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APP = "The time slot has already been taken";

    private final Index targetIndex;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddAppCommand(Index index, LocalDateTime start, LocalDateTime end) {
        this.targetIndex = index;
        this.start = start;
        this.end = end;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAdd = lastShownList.get(targetIndex.getZeroBased());
        Appointment toAdd = new Appointment(personToAdd, start, end);

        if (model.duplicateApp(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APP);
        }

        model.addApp(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppCommand // instanceof handles nulls
                && targetIndex.equals(((AddAppCommand) other).targetIndex));
    }
}
