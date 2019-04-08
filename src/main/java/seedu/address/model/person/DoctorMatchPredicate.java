/* @@author siyingpoof */

package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.tag.Specialisation;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Doctor}'s {@code Name} matches any of the keywords given.
 */
public class DoctorMatchPredicate implements Predicate<DoctorMatch> {
    private final Specialisation spec;
    private final AppointmentDate date;
    private final AppointmentTime time;

    public DoctorMatchPredicate(Specialisation spec, AppointmentDate date, AppointmentTime time) {
        this.spec = spec;
        this.date = date;
        this.time = time;
    }

    /**
     * Checks the spec, avail date and time of the doctor.
     * @param appt object to check date and time of all doctors.
     * @return true if the doctor of the relevant specialisation is available at that time on that day.
     */
    @Override
    public boolean test(DoctorMatch appt) {
        boolean date_test = appt.getDate().equals(date);
        boolean time_test = appt.getTime().equals(time);

        return (date_test && time_test);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorMatchPredicate // instanceof handles nulls
                && spec.equals(((DoctorMatchPredicate) other).spec)
                && date.equals(((DoctorMatchPredicate) other).date)
                && time.equals(((DoctorMatchPredicate) other).time)); // state check
    }

}
