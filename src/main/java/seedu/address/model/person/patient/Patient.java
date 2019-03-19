package seedu.address.model.person.patient;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient class that can handle requests.
 * Guarantees: details are present and not null, and field values are validated.
 */
public class Patient extends Person {

    private Conditions conditions;

    public Patient(Name name, Phone phone, Email email, Nric nric, Address
        address, Set<Tag> tags) {
        super(name, phone, email, nric, address, tags);
        this.conditions = new Conditions();
    }

    public Patient(Name name, Phone phone, Email email, Nric nric, Address
        address, Set<Tag> tags, Conditions conditions) {
        super(name, phone, email, nric, address, tags);
        this.conditions = conditions;
    }

    public Patient(Name name, Phone phone, Address address, Set<Tag> conditions) {
        super(name, phone, address, conditions);
        HashSet<ConditionTag> set = new HashSet<>();
        conditions.forEach(tag -> {
            set.add(new ConditionTag(tag.tagName));
        });
        this.conditions = new Conditions(set);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Nric: ")
            .append(getNric())
            .append(" Phone: ")
            .append(getPhone())
            .append(" Email: ")
            .append(getEmail())
            .append(" Address: ")
            .append(getAddress())
            .append(" Conditions: ")
            .append(getConditions());
        return builder.toString();
    }

    /**
     * Returns true if both Patients have the same name, nric, and phone number
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePatient(Patient other) {
        if (other == this) {
            return true;
        }

        return other != null
            && other.getName().equals(this.getName())
            && other.getPhone().equals(this.getPhone())
            && other.getNric().equals(this.getNric());
    }

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
            && otherPatient.getPhone().equals(getPhone())
            && otherPatient.getNric().equals(getNric())
            && otherPatient.getTags().equals(getTags())
            && otherPatient.getEmail().equals(getEmail())
            && otherPatient.getAddress().equals(getAddress());
        // && otherPatient.getConditions().equals(getConditions());
        //there is a bug with the .equals in conditions rohan pls resolve
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNric(), getAddress(), getPhone(),
            getEmail(), getTags(), getConditions());
    }

    public Conditions getConditions() {
        return this.conditions;
    }
}
