package seedu.address.model.pdf;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Pdf in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Pdf {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Directory directory;
    private final Size size;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    public Pdf(Name name, Directory directory, Size size, Set<Tag> tags) {
        requireAllNonNull(name, directory, size, tags);
        this.name = name;
        this.directory = directory;
        this.size = size;
        this.tags.addAll(tags);

        //dummy
        this.phone = null;
        this.email = null;
        this.address = null;

    }

    /**
     * Every field must be present and not null.
     */
    public Pdf(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        //dummy
        this.directory = null;
        this.size = null;
    }

    public Name getName() {
        return name;
    }

    public Directory getDirectory() {
        return directory;
    }

    public Size getSize() {
        return size;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePdf(Pdf otherPdf) {
        if (otherPdf == this) {
            return true;
        }

        return otherPdf != null
                && otherPdf.getName().equals(getName());
    }

    public boolean isValidPdf() {
        return Paths.get(this.directory.getDirectory(), this.name.getFullName()).toAbsolutePath().toFile().exists();
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

        if (!(other instanceof Pdf)) {
            return false;
        }

        Pdf otherPdf = (Pdf) other;
        return otherPdf.getName().equals(getName())
                && otherPdf.getDirectory().equals(getDirectory())
                && otherPdf.getSize().equals(getSize())
                //&& otherPdf.getPhone().equals(getPhone())
                //&& otherPdf.getEmail().equals(getEmail())
                //&& otherPdf.getAddress().equals(getAddress())
                && otherPdf.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, directory, size, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Directory: ")
                .append(getDirectory())
                .append(" Size: ")
                .append(getSize())
                .append(" Tags: ");
        //.append(" Phone: ")
        //.append(getPhone())
        //.append(" Email: ")
        //.append(getEmail())
        //.append(" Address: ")
        //.append(getAddress())
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
