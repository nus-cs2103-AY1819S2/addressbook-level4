package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagNameAndColor;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName} and
     * {@code tagColor}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagNameAndColor) {
        this.tagNameAndColor = tagNameAndColor;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedTag(Tag source) {
        this.tagNameAndColor = source.tagName + "/" + source.tagColor;
    }

    @JsonValue
    public String getTagNameAndColor() {
        return tagNameAndColor;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagNameAndColor)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Tag(tagNameAndColor);
    }

}
