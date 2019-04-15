/* @@author siyingpoof */

package seedu.address.model.person.doctor;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.DoctorUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Tests that {@code Doctor}s whose {@code specialisation} matches, matches any appointment.
 */
public class AppointmentContainsDoctorPredicate implements Predicate<Appointment> {
    private final List<Doctor> specMatchedDoctors;
    private final AppointmentDate date;
    private final AppointmentTime time;

    public AppointmentContainsDoctorPredicate(DoctorsMatch doctorsMatch) {
        this.specMatchedDoctors = doctorsMatch.getDoctors();

        // patients' inputs
        this.date = doctorsMatch.getDate();
        this.time = doctorsMatch.getTime();
    }

    /**
     * Checks the spec, avail date and time of the doctor.
     * @param appt object to check date and time of all doctors.
     * @return true if the appt does not contain the doctors in specMatchedDoctors list.
     */
    @Override
    public boolean test(Appointment appt) {
        boolean doctorIsBusy = specMatchedDoctors.stream()
                .anyMatch(doctor -> DoctorUtil.containsDoctor(appt.getDoctorId().personId, doctor.getId().personId));
        if (doctorIsBusy) {
            boolean dateIsOccupied = appt.getDate().equals(date);
            boolean timeIsOccupied = appt.getTime().equals(time);

            return dateIsOccupied && timeIsOccupied;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentContainsDoctorPredicate // instanceof handles nulls
                && specMatchedDoctors.equals(((AppointmentContainsDoctorPredicate) other).specMatchedDoctors)
                && date.equals(((AppointmentContainsDoctorPredicate) other).date)
                && time.equals(((AppointmentContainsDoctorPredicate) other).time)); // state check
    }

}
