package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String tagColor;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName} and
     * {@code tagColor}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagNameAndColor) {
        String[] attributes = tagNameAndColor.split("/");
        this.tagName = attributes[0];
        if (attributes.length == 2) {
            this.tagColor = attributes[1];
        } else {
            this.tagColor = null;
        }
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        tagColor = source.tagColor;
    }

    @JsonValue
    public String getTagNameAndColor() {
        return tagName + "/" + tagColor;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        if (tagColor == null) {
            return new Tag(tagName);
        }

        return new Tag(tagName, tagColor);
    }

}
