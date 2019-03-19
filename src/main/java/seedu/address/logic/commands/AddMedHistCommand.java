package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRITEUP;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Adds a medical history.
 */
public class AddMedHistCommand extends Command {

    public static final String COMMAND_WORD = "add-med-hist";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a medical history of a patient to the address book."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_WRITEUP + "SHORT-WRITE-UP \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_WRITEUP + "Fever";
    public static final String MESSAGE_SUCCESS = "New medical history added: %1$s";

    private final MedicalHistory toAdd;

    /**
     * Creates an addPatientCommand to add the specified {@code Patient}
     */
    public AddMedHistCommand(MedicalHistory medHist) {
        requireNonNull(medHist);
        toAdd = medHist;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("Medical History:\n " + "Name: " + toAdd.getName()
                + "\nShort Write Up: " + toAdd.getWriteUp());
    }

    /*
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
    */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMedHistCommand // instanceof handles nulls
                && toAdd.equals(((AddMedHistCommand) other).toAdd));
    }

}
