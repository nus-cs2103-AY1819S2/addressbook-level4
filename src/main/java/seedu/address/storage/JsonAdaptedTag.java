package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.SkillsTag;

/**
 * Jackson-friendly version of {@link SkillsTag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String tagType;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */

    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        String parsed = tagName.substring(2);
        String prefix = tagName.substring(0, 1);
        this.tagName = parsed;
        if (prefix.equals("s")){
            this.tagType = "skill";
        } else if (prefix.equals("p")){
            this.tagType = "pos";
        } else {
            this.tagType = "endorse";
        }
    }

    /**
     * Converts a given {@code SkillsTag} into this class for Jackson use.
     */
    public JsonAdaptedTag(SkillsTag source) {
        this.tagName = source.tagName;
        this.tagType = source.tagType;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code SkillsTag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public SkillsTag toModelType() throws IllegalValueException {
        if (!SkillsTag.isValidTagName(tagName)) {
            throw new IllegalValueException(SkillsTag.MESSAGE_CONSTRAINTS);
        }

        return new SkillsTag(tagName, tagType);
    }

}
