/** author: @siyingpoof **/
package seedu.address.model.person.doctor;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.specialisation.Specialisation;

/**
 * Tests that a {@code Doctor}'s {@code Specialisation} matches any of the keywords given.
 * Object is also used to store the user's inputs on the desired date and time of their next appt.
 */
public class DoctorSpecialisationMatchesPredicate implements Predicate<Doctor> {
    private final Specialisation specialisation;
    private final AppointmentDate date;
    private final AppointmentTime time;

    public DoctorSpecialisationMatchesPredicate(DoctorMatch doctorMatch) {
        this.specialisation = doctorMatch.getSpec();
        this.date = doctorMatch.getDate();
        this.time = doctorMatch.getTime();
    }

    public Specialisation getSpec() {
        return this.specialisation;
    }

    public AppointmentDate getDate() {
        return this.date;
    }

    public AppointmentTime getTime() {
        return this.time;
    }

    @Override
    public boolean test(Doctor doctor) {
        final String specs = doctor.getSpecs().toString()
                .replaceAll("\\[", "").replaceAll("\\]", "");
        return StringUtil.containsWordIgnoreCase(specs, specialisation.toString()
                .replaceAll("\\[", "").replaceAll("\\]", ""));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorSpecialisationMatchesPredicate // instanceof handles nulls
                && specialisation.equals(((DoctorSpecialisationMatchesPredicate) other).specialisation)
                && date.equals(((DoctorSpecialisationMatchesPredicate) other).date))
                && time.equals(((DoctorSpecialisationMatchesPredicate) other).time); // state check
    }

}
