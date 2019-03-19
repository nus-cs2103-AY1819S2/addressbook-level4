package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.patient.Patient;

/**
 * Adds a Patient to the address book.
 *
 * @author Rohan
 */
public class AddPatientCommand extends AddCommand implements PatientCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_OPTION
        + ": Add a patient to the address book. Parameters: " + COMMAND_PARAMETERS + "\n"
        + "Example: " + COMMAND_WORD + " " + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";

    private final Patient toAdd;

    public AddPatientCommand(Patient toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(DUPLICATE_PATIENT);
        }

        add(model, toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public void add(Model model, Object toAdd) {
        model.addPatient((Patient) toAdd);
        model.commitAddressBook();
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) || (other instanceof AddPatientCommand
            && (this.toAdd.equals(((AddPatientCommand) other).toAdd)));
    }
}
