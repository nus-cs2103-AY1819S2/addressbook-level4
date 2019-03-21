package seedu.address.storage;

import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class PdfAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
    public PdfAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public PdfAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    public String getTagName() {
        return tagName;
    }

}
