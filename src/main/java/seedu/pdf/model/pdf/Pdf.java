package seedu.pdf.model.pdf;

import static seedu.pdf.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;

import seedu.pdf.model.tag.Tag;

/**
 * Represents a Pdf in the pdf book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Pdf {

    private final Name name;
    private final Directory directory;
    private final Size size;
    private final Deadline deadline;
    private final boolean isEncrypted;

    private final Set<Tag> tags = new HashSet<>();

    public Pdf(Name name, Directory directory, Size size, Set<Tag> tags) {
        requireAllNonNull(name, directory, size, tags);

        this.name = name;
        this.directory = directory;
        this.size = size;
        this.deadline = new Deadline();
        this.tags.addAll(tags);
        this.isEncrypted = isFileEncrypted(name, directory);
    }

    public Pdf(Name name, Directory directory, Size size, Set<Tag> tags, Deadline deadline) {
        requireAllNonNull(name, directory, size, tags, deadline);

        this.name = name;
        this.directory = directory;
        this.size = size;
        this.deadline = deadline;
        this.tags.addAll(tags);
        this.isEncrypted = isFileEncrypted(name, directory);
    }

    public Pdf(Name name, Directory directory, Size size, Set<Tag> tags, Deadline deadline, boolean isEncrypted) {
        requireAllNonNull(name, directory, size, tags, deadline, isEncrypted);

        this.name = name;
        this.directory = directory;
        this.size = size;
        this.deadline = deadline;
        this.tags.addAll(tags);
        this.isEncrypted = isEncrypted;
    }

    public Pdf(Pdf oldPdf, boolean isEncrypted) {
        requireAllNonNull(oldPdf, isEncrypted);

        this.name = oldPdf.name;
        this.directory = oldPdf.directory;
        this.size = new Size(Long.toString(Paths.get(oldPdf.getDirectory().getDirectory(),
                oldPdf.getName().getFullName()).toFile().length()));
        this.deadline = oldPdf.deadline;
        this.tags.addAll(oldPdf.tags);
        this.isEncrypted = isEncrypted;
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

    public boolean getIsEncrypted() {
        return isEncrypted;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both pdfs of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two pdfs.
     */
    public boolean isSamePdf(Pdf otherPdf) {
        if (otherPdf == this) {
            return true;
        }

        return otherPdf != null
                && otherPdf.getName().equals(getName())
                && otherPdf.getDirectory().equals(getDirectory());
    }

    public boolean isValidPdf() {
        return Paths.get(this.directory.getDirectory(), this.name.getFullName()).toAbsolutePath().toFile().exists();
    }

    /**
     * Returns true if the file at the given {code name} and {code directory} is encrypted. If the file can't be loaded
     * means that the file is already encrypted.
     */
    private boolean isFileEncrypted(Name name, Directory directory) {
        try (PDDocument pd = PDDocument.load(Paths.get(directory.getDirectory(), name.getFullName()).toFile())) {
            pd.close();
            return false;
        } catch (IOException ioe) {
            return true;
        }
    }

    /**
     * Returns true if both pdfs have the same identity and data fields.
     * This defines a stronger notion of equality between two pdfs.
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
                && otherPdf.getTags().equals(getTags())
                && otherPdf.getDeadline().equals(getDeadline());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, directory, size, tags);
    }

    /**
     * Returns a string representation of the pdf file that is fit for
     * user readability. Size parameter is converted to appropriate prefix.
     * @return user-friendly string representation of pdf.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(getDirectory())
                .append(getSize().toReadableString())
                .append(getDeadline());
        if (getTags().size() > 0) {
            builder.append("Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

}
