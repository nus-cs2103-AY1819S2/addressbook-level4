package seedu.address.model.person.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.specialisation.Specialisation;

/**
 * Represents a DoctorMatch in the docX. Just an object to store the relevant information needed for match-doctor cmd.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DoctorMatch {

    private final Specialisation spec;
    private final AppointmentDate date;
    private final AppointmentTime time;

    /**
     * Every field must be present and not null.
     */
    public DoctorMatch(Specialisation spec, AppointmentDate date, AppointmentTime time) {
        requireAllNonNull(spec, date, time);
        this.spec = spec;
        this.date = date;
        this.time = time;
    }

    public Specialisation getSpec() {
        return spec;
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
        return Objects.hash(spec, date, time);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getSpec())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime());
        return builder.toString();
    }

}
