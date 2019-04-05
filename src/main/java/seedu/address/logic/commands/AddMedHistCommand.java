package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
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
            + ": Adds a medical history of a patient to the docX."
            + " Parameters: "
            + PREFIX_PATIENT_ID + "PATIENT-ID "
            + PREFIX_DOCTOR_ID + "DOCTOR-ID "
            + PREFIX_DATE_OF_MEDHIST + "DATE "
            + PREFIX_WRITEUP + "SHORT-WRITE-UP \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_ID + "1 "
            + PREFIX_DOCTOR_ID + "2 "
            + PREFIX_DATE_OF_MEDHIST + "2019-03-02 "
            + PREFIX_WRITEUP + "The patient got a fever.";
    public static final String MESSAGE_SUCCESS = "New medical history added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEDHIST = "This medical history already exists in the docX";

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
        requireNonNull(model);

        if (model.hasMedHist(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEDHIST);
        }

        model.addMedHist(toAdd);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMedHistCommand // instanceof handles nulls
                && toAdd.equals(((AddMedHistCommand) other).toAdd));
    }

}
