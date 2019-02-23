package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class HealthWorker extends Person {

    // TODO: Add fields for skills/certification.
    private Organization organization;

    public HealthWorker(Name name, Phone phone, Email email, Address address,
                        Set<Tag> tags, Organization organization) {
        super(name, phone, email, address, tags);
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
