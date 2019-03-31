package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Sorts data in accordance to the given parameter
 */
public class SortPatientCommand extends SortCommand<Patient> {

    public SortPatientCommand(Comparator<Patient> chosenCompare, String paraType, boolean sortOrder) {
        super(chosenCompare, paraType, sortOrder);
    }

    /**
     * Sets default sortOrder to ascending
     */
    public SortPatientCommand(Comparator<Patient> chosenCompare, String paraType) {
        this(chosenCompare, paraType, false);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.sortAddressBook(this.attrCompare, this.isReverse);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, paraType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof SortPatientCommand
            && attrCompare.equals(((SortPatientCommand) other).attrCompare)
            && paraType.equals(((SortPatientCommand) other).paraType)
            && isReverse == ((SortPatientCommand) other).isReverse);
    }
}
