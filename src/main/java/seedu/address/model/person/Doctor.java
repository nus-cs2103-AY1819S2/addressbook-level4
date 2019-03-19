package seedu.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Specialisation;


/**
 * Represents a Doctor in docX.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Gender gender;
    private final Age age;
    private final Set<Specialisation> specList = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Doctor(Name name, Phone phone, Gender gender, Age age, Set<Specialisation> specList) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.specList.addAll(specList);
    }

    public Name getName() {
        return this.name;
    }

    public Phone getPhone() {
        return this.phone;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Age getAge() {
        return this.age;
    }

    /**
     * Returns an immutable specialisation set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Specialisation> getSpecs() {
        return Collections.unmodifiableSet(specList);
    }

    /**
     * Returns true if both doctors of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two doctors.
     */
    public boolean isSameDoctor(Doctor otherDoctor) {
        if (otherDoctor == this) {
            return true;
        }

        return otherDoctor != null
                && otherDoctor.getName().equals(getName())
                && (otherDoctor.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both doctors have the same identity and data fields.
     * This defines a stronger notion of equality between two doctors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Doctor)) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        return otherDoctor.getName().equals(getName())
                && otherDoctor.getPhone().equals(getPhone())
                && otherDoctor.getAge().equals(getAge())
                && otherDoctor.getGender().equals(getGender())
                && otherDoctor.getSpecs().equals(getSpecs());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, age, gender, specList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Gender: ")
                .append(getGender())
                .append(" Age: ")
                .append(getAge())
                .append(" Specialisations: ");
        getSpecs().forEach(builder::append);
        return builder.toString();
    }

}
