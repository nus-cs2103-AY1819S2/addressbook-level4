package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.appointment.Appointment;

/**
 * Represents a Person in the docX.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final PersonId id;
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Gender gender;
    private final List<Appointment> appointments = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Gender gender) {
        requireAllNonNull(name, phone, gender);
        this.id = PersonIdCounter.getInstance().generateNewId();
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }

    /**
     * This is an existing person and does not need to generate a new ID.
     */
    public Person(PersonId id, Name name, Phone phone, Gender gender) {
        requireAllNonNull(name, phone, gender);
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }

    public PersonId getId() {
        return id;
    }

    public String getIdToString() {
        return String.valueOf(this.id);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Appointment> getAppointments() {
        return this.appointments;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && (otherPerson.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getGender().equals(getGender());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, gender);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Gender: ")
                .append(getGender());
        return builder.toString();
    }

}
