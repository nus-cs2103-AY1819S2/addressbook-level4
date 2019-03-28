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
    private final Directory directory;
    private final Size size;
    private final Deadline deadline;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    public Pdf(Name name, Directory directory, Size size, Set<Tag> tags) {
        requireAllNonNull(name, directory, size, tags);

        this.name = name;
        this.directory = directory;
        this.size = size;
        this.deadline = new Deadline();
        this.tags.addAll(tags);
    }

    public Pdf(Name name, Directory directory, Size size, Set<Tag> tags, Deadline deadline) {
        requireAllNonNull(name, directory, size, tags, deadline);

        this.name = name;
        this.directory = directory;
        this.size = size;
        this.deadline = deadline;
        this.tags.addAll(tags);
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

    public Deadline getDeadline() {
        return deadline;
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
                .append("\nDirectory: ")
                .append(getDirectory())
                .append("\nSize: ")
                .append(getSize())
                .append("\nDeadline: ")
                .append(getDeadline())
                .append("\nTags: ");
        getTags().forEach(builder::append);

        return builder.toString();
    }

}
