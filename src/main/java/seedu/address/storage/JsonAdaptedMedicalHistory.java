package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;

/**
 * Jackson-friendly version of {@link MedicalHistory}.
 */
public class JsonAdaptedMedicalHistory {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medical history's %s field is missing!";

    private final String medHistId;
    private final String patientId;
    private final String doctorId;
    private final String date;
    private final String writeUp;

    /**
     * Constructs a {@code JsonAdaptedMedicalHistory} with the given medical history details.
     */
    @JsonCreator
    public JsonAdaptedMedicalHistory(@JsonProperty("medHistId") String medHistId,
                                     @JsonProperty("patientId") String patientId,
                                     @JsonProperty("doctorId") String doctorId,
                                     @JsonProperty("date") String date,
                                     @JsonProperty("writeUp") String writeUp) {
        this.medHistId = medHistId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.writeUp = writeUp;
    }

    /**
     * Converts a given {@code MedicalHistory} into this class for Jackson use.
     */
    public JsonAdaptedMedicalHistory(MedicalHistory source) {
        medHistId = source.getMedHistId().medHistId;
        patientId = String.valueOf(source.getPatientId().personId);
        doctorId = String.valueOf(source.getDoctorId().personId);
        date = source.getDate().toString();
        writeUp = source.getWriteUp().value;
    }

    /**
     * Converts this Jackson-friendly adapted medical history object into the model's {@code MedicalHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medical history.
     */
    public MedicalHistory toModelType() throws IllegalValueException {
        if (patientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonId.class.getSimpleName()));
        }

        if (!PersonId.isValidPersonId(patientId)) {
            throw new IllegalValueException(PersonId.MESSAGE_CONSTRAINTS);
        }

        final PersonId modelPatientId = new PersonId(this.patientId);

        if (doctorId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonId.class.getSimpleName()));
        }

        if (!PersonId.isValidPersonId(doctorId)) {
            throw new IllegalValueException(PersonId.MESSAGE_CONSTRAINTS);
        }

        final PersonId modelDoctorId = new PersonId(this.doctorId);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ValidDate.class.getSimpleName()));
        }

        if (!ValidDate.isValidDate(date)) {
            throw new IllegalValueException(ValidDate.MESSAGE_CONSTRAINTS);
        }

        final ValidDate modelDate = new ValidDate(date);

        if (writeUp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, WriteUp.class.getSimpleName()));
        }

        if (!WriteUp.isValidWriteUp(writeUp)) {
            throw new IllegalValueException(WriteUp.MESSAGE_CONSTRAINTS);
        }
        final WriteUp modelWriteUp = new WriteUp(this.writeUp);

        return new MedicalHistory(modelPatientId, modelDoctorId, modelDate, modelWriteUp);
    }


}


