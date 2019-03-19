package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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

    public static final String DEFAULT_NAME = "2103T Lecture Notes";
    public static final String DEFAULT_LOCATION = "TempValue";
    public static final String DEFAULT_SIZE = "10";

    private Name name;
    private Directory directory;
    private Size size;
    private Set<Tag> tags;

    public PdfBuilder() {
        name = new Name(DEFAULT_NAME);
        directory = new Directory(DEFAULT_LOCATION);
        size = new Size(DEFAULT_SIZE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PdfBuilder with the data of {@code pdfToCopy}.
     */
    public PdfBuilder(Pdf pdfToCopy) {
        name = pdfToCopy.getName();
        directory = pdfToCopy.getDirectory();
        size = pdfToCopy.getSize();
        tags = new HashSet<>(pdfToCopy.getTags());
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
     * Sets the {@code Directory} of the {@code Pdf} that we are building.
     */
    public PdfBuilder withLocation(String location) {
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


    public Pdf build() {
        return new Pdf(name, directory, size, tags);
    }

}
