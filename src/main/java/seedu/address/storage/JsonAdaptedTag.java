package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.StatusTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TeethTag;
import seedu.address.model.tag.exceptions.TagsIsNotSpecificException;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {
    private static final String TEETH = "teeth";
    private static final String STATUS = "status";

    private final String type;
    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        String[] sb = tagName.split(JsonAdaptedConstants.DIVIDER);
        if (sb.length == 2) {
            type = sb[0];
            this.tagName = sb[1];
        } else {
            throw new TagsIsNotSpecificException();
        }
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        if (source instanceof TeethTag) {
            type = TEETH;
        } else if (source instanceof StatusTag) {
            type = STATUS;
        } else {
            throw new TagsIsNotSpecificException();
        }
    }

    @JsonValue
    public String getTagName() {
        return type + JsonAdaptedConstants.DIVIDER + tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        } else if (type.equals(TEETH)) {
            return new TeethTag(tagName);
        } else if (type.equals(STATUS)) {
            return new StatusTag(tagName);
        } else {
            throw new TagsIsNotSpecificException();
        }
    }

}
