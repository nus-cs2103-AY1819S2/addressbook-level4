package seedu.address.testutil;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Size;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Pdf objects.
 */
public class PdfBuilder {

    private static final File DEFAULT_FILE = Paths.get(
            "src", "test", "data", "JsonAdaptedPdfTest", "CS2103T_Lecture3.pdf").toFile();
    private static final String DEFAULT_NAME = DEFAULT_FILE.getName();
    private static final String DEFAULT_DIRECTORY = DEFAULT_FILE.getParent();
    private static final String DEFAULT_SIZE = Long.toString(DEFAULT_FILE.length());
    private static final String DEFAULT_DEADLINE = CommandTestUtil.DEADLINE_JSON_READY;

    private Name name;
    private Directory directory;
    private Size size;
    private Set<Tag> tags;
    private Deadline deadline;
    private boolean isEncrypted;

    public PdfBuilder() {
        name = new Name(DEFAULT_NAME);
        directory = new Directory(DEFAULT_DIRECTORY);
        size = new Size(DEFAULT_SIZE);
        tags = new HashSet<>();
        deadline = new Deadline(DEFAULT_DEADLINE);
        isEncrypted = false;
    }

    /**
     * Initializes the PdfBuilder with the data of {@code pdfToCopy}.
     */
    public PdfBuilder(Pdf pdfToCopy) {
        name = pdfToCopy.getName();
        directory = pdfToCopy.getDirectory();
        size = pdfToCopy.getSize();
        tags = new HashSet<>(pdfToCopy.getTags());
        deadline = pdfToCopy.getDeadline();
        isEncrypted = pdfToCopy.getIsEncryted();
    }

    /**
     * Sets the {@code Name} of the {@code Pdf} that we are building.
     */
    public PdfBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pdf} that we are building.
     */
    public PdfBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     *  Sets the {@code Directory} of the {@code Pdf} that we are building.
     */
    public PdfBuilder withDirectory(String location) {
        this.directory = new Directory(location);
        return this;
    }

    /**
     * Sets the {@code Size} of the {@code Pdf} that we are building.
     */
    public PdfBuilder withSize(String size) {
        this.size = new Size(size);
        return this;
    }

    /**
     * Sets the {@code Size} of the {@code Pdf} that we are building.
     */
    public PdfBuilder withEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Pdf} that we are building.
     */
    public PdfBuilder withDeadline(String date) {
        if (date.equals("NEWLY ADDED")) {
            this.deadline = new Deadline();
        } else {
            this.deadline = new Deadline(date);
        }
        return this;
    }

    public Pdf build() {
        return new Pdf(new Pdf(name, directory, size, tags, deadline), isEncrypted);
    }
}
