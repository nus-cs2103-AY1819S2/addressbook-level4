package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.KnownProgLang;

/**
 * Jackson-friendly version of {@link KnownProgLang}.
 */
class JsonAdaptedKnownProgLang {

    private final String proglangName;

    /**
     * Constructs a {@code JsonAdaptedKnownProgLang} with the given {@code proglangName}.
     */
    @JsonCreator
    public JsonAdaptedKnownProgLang(String proglangName) {
        this.proglangName = proglangName;
    }

    /**
     * Converts a given {@code KnownProgLang} into this class for Jackson use.
     */
    public JsonAdaptedKnownProgLang(KnownProgLang source) {
        proglangName = source.value;
    }

    @JsonValue
    public String getKnownProgLangName() {
        return proglangName;
    }

    /**
     * Converts this Jackson-friendly adapted known prog lang object into the model's {@code KnownProgLang} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted proglang.
     */
    public KnownProgLang toModelType() throws IllegalValueException {
        if (!KnownProgLang.isValidKnownProgLang(proglangName)) {
            throw new IllegalValueException(KnownProgLang.MESSAGE_CONSTRAINTS);
        }
        return new KnownProgLang(proglangName);
    }

}
