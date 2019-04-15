/** author: @siyingpoof **/
package seedu.address.model.person.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Represents a DoctorsMatch in the docX.
 * Just an object to store the relevant information needed for match-doctor cmd.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DoctorsMatch {

    private final List<Doctor> specMatchedDoctors;
    private final AppointmentDate date;
    private final AppointmentTime time;

    /**
     * Every field must be present and not null.
     */
    public DoctorsMatch(List<Doctor> specMatchedDoctors, AppointmentDate date, AppointmentTime time) {
        requireAllNonNull(specMatchedDoctors, date, time);
        this.specMatchedDoctors = specMatchedDoctors;
        this.date = date;
        this.time = time;
    }

    public List<Doctor> getDoctors() {
        return specMatchedDoctors;
    }

    public AppointmentDate getDate() {
        return date;
    }

    public AppointmentTime getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(specMatchedDoctors, date, time);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDoctors())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime());
        return builder.toString();
    }

}
