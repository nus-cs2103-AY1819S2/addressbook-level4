package seedu.address.storage;

import seedu.address.model.tag.Tag;

/**
 * PDF-friendly version of {@link Tag}.
 */
class PdfAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code PdfAdaptedTag} with the given {@code tagName}.
     */
    public PdfAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for PDF use.
     */
    public PdfAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    public String getTagName() {
        return tagName;
    }

}
