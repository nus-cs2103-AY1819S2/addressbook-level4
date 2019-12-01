package seedu.address.logic.commands.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.AppointmentContainsDoctorPredicate;
import seedu.address.model.person.doctor.DoctorHasAppointmentPredicate;
import seedu.address.model.person.doctor.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.doctor.DoctorsMatch;

/**
 * Searches and lists all doctors in docX record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DoctorMatchCommand extends Command {

    public static final String COMMAND_WORD = "match-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all doctors whose specialisations "
            + "contain the specified specialisation (case-insensitive) "
            + "and checks whether the doctor is available at the date and time\n"
            + "Parameters: "
            + PREFIX_SPECIALISATION + "SPECIALISATION "
            + PREFIX_DATE_OF_APPT + "DATE "
            + PREFIX_START_TIME + "START_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SPECIALISATION + "general "
            + PREFIX_DATE_OF_APPT + "2019-05-20 "
            + PREFIX_START_TIME + "09:00";

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
