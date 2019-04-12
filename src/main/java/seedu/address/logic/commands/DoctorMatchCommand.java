package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.AppointmentContainsDoctorPredicate;
import seedu.address.model.person.DoctorHasAppointmentPredicate;
import seedu.address.model.person.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.DoctorsMatch;

/**
 * Searches and lists all doctors in docX record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DoctorMatchCommand extends Command {

    public static final String COMMAND_WORD = "match-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all doctors whose specialisations "
            + "contain the specified specialisation (case-insensitive) "
            + "and checks whether the doctor is available at the date and time\n"
            + "Parameters: s/SPECIALISATION d/DATE t/START_TIME\n"
            + "Example: " + COMMAND_WORD + " s/general d/2019-05-20 t/09:00";

    private final DoctorSpecialisationMatchesPredicate specPredicate;

    public DoctorMatchCommand(DoctorSpecialisationMatchesPredicate specPredicate) {
        this.specPredicate = specPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDoctorList(specPredicate);
        // now filtered doctor list contains doctors with specs the same as user input

        AppointmentContainsDoctorPredicate apptPred = new AppointmentContainsDoctorPredicate(
                new DoctorsMatch(model.getFilteredDoctorList(), specPredicate.getDate(), specPredicate.getTime()));
        model.updateFilteredAppointmentList(apptPred);
        // now filtered appointment list contains appts of doctors who are occupied during the date and time

        DoctorHasAppointmentPredicate finalPred = new DoctorHasAppointmentPredicate(
                model.getFilteredAppointmentList(), specPredicate.getSpec());
        model.updateFilteredDoctorList(finalPred);
        // now filtered doctor list contains doctors who do not appear in the filtered appt list

        return new CommandResult(
                String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW, model.getFilteredDoctorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorMatchCommand // instanceof handles nulls
                && specPredicate.equals(((DoctorMatchCommand) other).specPredicate)); // state check
    }
}
