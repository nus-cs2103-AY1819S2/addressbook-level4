package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Tag;

/**
 * Represents a Health Worker class that can handle requests.
 * Guarantees: details are present and not null, and field values are validated.
 */
public class HealthWorker extends Person {

    // TODO: Add fields for skills/certification.
    private Organization organization;

    public HealthWorker(Name name, Phone phone, Email email, Nric nric, Address
                        address, Set<Tag> tags, Organization organization) {
        super(name, phone, email, nric, address, tags);
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
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
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress());
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
                && other.getNric().equals(this.getNric())
                && other.getPhone().equals(this.getPhone())
                && other.getOrganization().equals(this.getOrganization());
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
                && otherHealthWorker.getAddress().equals(getAddress())
                && otherHealthWorker.getOrganization().equals(getOrganization());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNric(), getAddress(), getPhone(),
                getOrganization());
    }
}
