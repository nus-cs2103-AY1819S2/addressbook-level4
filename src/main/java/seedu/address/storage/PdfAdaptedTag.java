package seedu.address.storage;

import seedu.address.model.tag.StatusTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TeethTag;
import seedu.address.model.tag.exceptions.TagsIsNotSpecificException;

/**
 * PDF-friendly version of {@link Tag}.
 */
class PdfAdaptedTag {
    private static final String TEETH = "teeth";
    private static final String STATUS = "status";

    private final String type;
    private final String tagName;

    /**
     * Constructs a {@code PdfAdaptedTag} with the given {@code tagName}.
     */
    public PdfAdaptedTag(String tagName) {
        String[] sb = tagName.split(StorageConstants.DIVIDER);
        if (sb.length == 2) {
            type = sb[0];
            this.tagName = sb[1];
        } else {
            throw new TagsIsNotSpecificException();
        }
    }

    /**
     * Converts a given {@code Tag} into this class for PDF use.
     */
    public PdfAdaptedTag(Tag source) {
        tagName = source.tagName;
        if (source instanceof TeethTag) {
            type = TEETH;
        } else if (source instanceof StatusTag) {
            type = STATUS;
        } else {
            throw new TagsIsNotSpecificException();
        }
    }

    public String getTagName() {
        return type + StorageConstants.DIVIDER + tagName;
    }
}
