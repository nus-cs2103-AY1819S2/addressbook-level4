/* @@author siyingpoof */

package seedu.address.model.person.doctor;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.DoctorUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.specialisation.Specialisation;

/**
 * Tests that {@code Doctor}s whose {@code specialisation} matches and don't match any appointment.
 */
public class DoctorHasAppointmentPredicate implements Predicate<Doctor> {
    private final List<Appointment> relevantAppts;
    private final Specialisation specialisation;

    public DoctorHasAppointmentPredicate(List<Appointment> relevantAppts, Specialisation specialisation) {
        this.relevantAppts = relevantAppts;
        this.specialisation = specialisation;
    }

    /**
     * Checks the spec, then check against the filtered appt list.
     * @param doctor object to check spec of the doctor.
     * @return true if the spec matches and appt does not contain the doctor.
     */
    @Override
    public boolean test(Doctor doctor) {
        final String specs = doctor.getSpecs().toString()
                .replaceAll("\\[", "").replaceAll("\\]", "");
        boolean specMatches = StringUtil.containsWordIgnoreCase(specs, specialisation.toString()
                .replaceAll("\\[", "").replaceAll("\\]", ""));
        boolean apptMatches = relevantAppts.stream()
                .anyMatch(appt -> DoctorUtil.containsDoctor(doctor.getId().personId, appt.getDoctorId().personId));
        return specMatches && !apptMatches;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorHasAppointmentPredicate // instanceof handles nulls
                && relevantAppts.equals(((DoctorHasAppointmentPredicate) other).relevantAppts)); // state check
    }

}
