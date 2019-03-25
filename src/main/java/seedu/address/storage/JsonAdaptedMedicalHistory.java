package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.Name;

/**
 * Jackson-friendly version of {@link MedicalHistory}.
 */
public class JsonAdaptedMedicalHistory {

    private final String name;
    private final String writeUp;

    /**
     * Constructs a {@code JsonAdaptedMedicalHistory} with the given medical history details.
     */
    @JsonCreator
    public JsonAdaptedMedicalHistory(@JsonProperty("name") String name, @JsonProperty("writeUp") String writeUp) {
        this.name = name;
        this.writeUp = writeUp;
    }

    /**
     * Converts a given {@code MedicalHistory} into this class for Jackson use.
     */
    public JsonAdaptedMedicalHistory(MedicalHistory source) {
        name = source.getName().fullName;
        writeUp = source.getWriteUp().value;
    }

    /**
     * Converts this Jackson-friendly adapted medical history object into the model's {@code MedicalHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medical history.
     */
    public MedicalHistory toModelType() throws IllegalValueException {
        /*
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        */
        final Name modelName = new Name(this.name);
        final WriteUp modelWriteUp = new WriteUp(this.writeUp);
        return new MedicalHistory(null, null, modelName, modelWriteUp);
    }


}


