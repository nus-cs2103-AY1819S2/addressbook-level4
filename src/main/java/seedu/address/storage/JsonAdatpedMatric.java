package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.MatricNumber;

/**
 * Jackson-friendly version of {@link MatricNumber}.
 */
class JsonAdaptedMatric {

    private final String matric;

    /**
     * Constructs a {@code JsonAdaptedMatric} with the given {@code matric}.
     */
    @JsonCreator
    public JsonAdaptedMatric(String matric) {
        this.matric = matric;
    }

    /**
     * Converts a given {@code MatricNumber} into this class for Jackson use.
     */
    public JsonAdaptedMatric(MatricNumber source) {
        matric = source.toString();
    }

    @JsonValue
    public String getMatric() {
        return matric;
    }

    /**
     * Converts this Jackson-friendly adapted matric object into the model's {@code MatricNumber} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted matricNumber.
     */
    public MatricNumber toModelType() throws IllegalValueException {
        if (!MatricNumber.isValidMatricNumber(matric)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_CONSTRAINTS);
        }
        return new MatricNumber(matric);
    }
}
