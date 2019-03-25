package seedu.address.model.person.healthworker;

import java.util.Objects;

import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

/**
 * Represents a Health Worker class that can handle requests.
 * Guarantees: details are present and not null, and field values are validated.
 */
public class HealthWorker extends Person {

    private Organization organization;
    private Skills skills;

    public HealthWorker(Name name, Nric nric, Phone phone, Organization organization, Skills skills) {
        super(name, nric, phone);
        this.organization = organization;
        this.skills = skills;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Skills getSkills() {
        return skills;
    }

    /**
     * Checks if the current HealthWorker object has the specified
     * specialisation
     * @param specialisation to check for
     * @return true if the HealthWorker object contains the specialisation in
     * Skills, false otherwise.
     */
    public boolean hasSkill(Specialisation specialisation) {
        return this.skills.contains(specialisation);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric())
                .append(" Organization: ")
                .append(getOrganization().toString())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Skills: ")
                .append(getSkills());
        return builder.toString();
    }

    /**
     * Returns true if both HealthWorkers have the same name, nric, phone and
     * organization.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameHealthWorker(HealthWorker other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getName().equals(this.getName())
                && other.getNric().equals(this.getNric());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HealthWorker)) {
            return false;
        }

        HealthWorker otherHealthWorker = (HealthWorker) other;
        return otherHealthWorker.getName().equals(getName())
                && otherHealthWorker.getPhone().equals(getPhone())
                && otherHealthWorker.getNric().equals(getNric())
                && otherHealthWorker.getOrganization().equals(getOrganization())
                && otherHealthWorker.getSkills().equals(getSkills());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNric(), getPhone(), getOrganization(), getSkills());
    }
}
