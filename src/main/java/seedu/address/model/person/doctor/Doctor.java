package seedu.address.model.person.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.specialisation.Specialisation;


/**
 * Represents a Doctor in docX.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends Person {

    // Data fields
    private final Year year;
    private final Set<Specialisation> specList = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Doctor(Name name, Phone phone, Gender gender, Year year, Set<Specialisation> specList) {
        super(name, phone, gender);
        this.year = year;
        this.specList.addAll(specList);
    }

    /**
     * This is an existing doctor and does not need to generate a new ID.
     */
    public Doctor(PersonId id, Name name, Phone phone, Gender gender, Year year, Set<Specialisation> specList) {
        super(id, name, phone, gender);
        requireAllNonNull(name, phone, gender);
        this.year = year;
        this.specList.addAll(specList);
    }

    public Year getYear() {
        return this.year;
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
                && otherDoctor.getYear().equals(getYear())
                && otherDoctor.getGender().equals(getGender())
                && otherDoctor.getSpecs().equals(getSpecs());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(year, specList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Gender: ")
                .append(getGender())
                .append(" Years: ")
                .append(getYear())
                .append(" Specialisations: ");
        getSpecs().forEach(builder::append);
        return builder.toString();
    }

}
