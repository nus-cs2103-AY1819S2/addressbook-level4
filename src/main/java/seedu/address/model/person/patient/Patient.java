/* @@author wayneswq */
package seedu.address.model.person.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in docX record.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {

    // Data fields
    private final Age age;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Additional details
    private AppointmentStatus appointmentStatus;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Gender gender, Age age, Phone phone, Address address, Set<Tag> tags) {
        super(name, phone, gender);
        requireAllNonNull(age, address, tags);
        this.age = age;
        this.address = address;
        this.tags.addAll(tags);
        this.appointmentStatus = AppointmentStatus.COMPLETED;
    }

    /**
     * For id counter.
     * This is an existing patient and does not need to generate a new ID.
     */
    public Patient(PersonId id, Name name, Gender gender, Age age, Phone phone, Address address, Set<Tag> tags) {
        super(id, name, phone, gender);
        requireAllNonNull(name, phone, gender);
        this.age = age;
        this.address = address;
        this.tags.addAll(tags);
        this.appointmentStatus = AppointmentStatus.COMPLETED;
    }

    /**
     * For mark-appt command.
     * This is an existing patient and does not need to generate a new ID.
     */
    public Patient(PersonId id, Name name, Gender gender, Age age, Phone phone, Address address, Set<Tag> tags,
                   AppointmentStatus appointmentStatus) {
        super(id, name, phone, gender);
        requireAllNonNull(name, phone, gender);
        this.age = age;
        this.address = address;
        this.tags.addAll(tags);
        this.appointmentStatus = appointmentStatus;
    }

    public Age getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the same patient with the changed appointment status
     * This is to facilitate mark-appt command
     */
    public Patient changeAppointmentStatus(AppointmentStatus status) {
        this.appointmentStatus = status;
        return this;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both patients have the same phone number.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && (otherPatient.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getGender().equals(getGender())
                && otherPatient.getAge().equals(getAge())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getTags().equals(getTags());
    }

    /**
     * Returns a string of the full details of the patient, excluding pid information
     * This is to facilitate search-advanced command
     */
    public String toAdvancedSearchString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
                .append(" ")
                .append(getName())
                .append(" ")
                .append(getGender())
                .append(" ")
                .append(getAge())
                .append(" ")
                .append(getPhone())
                .append(" ")
                .append(getAddress())
                .append(" ")
                .append(getAppointmentStatus())
                .append(" ");
        for (Tag tag : getTags()) {
            String tagName = tag.toString().replaceAll("^\\[|\\]$", "");
            builder.append(tagName);
            builder.append(" ");
        }

        return builder.toString().trim();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(age, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Gender: ")
                .append(getGender())
                .append(" Age: ")
                .append(getAge())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Address: ")
                .append(getAddress())
                .append(" Appointment Status: ")
                .append(getAppointmentStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
