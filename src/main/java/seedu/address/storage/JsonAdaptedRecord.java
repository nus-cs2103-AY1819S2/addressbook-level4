package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateBase;
import seedu.address.model.datetime.RecordDate;
import seedu.address.model.description.Description;
import seedu.address.model.person.Name;
import seedu.address.model.record.Procedure;
import seedu.address.model.record.Record;

/**
 * Jackson-friendly version of {@link Record}.
 */
class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private final String procedure;

    private final String doctorName;

    private final String description;

    private final String recordDate;

    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("procedure") String procedure,
                             @JsonProperty("doctor") String doctorName,
                             @JsonProperty("recordDate") String recordDate,
                             @JsonProperty("description") String description) {
        this.procedure = procedure;
        this.doctorName = doctorName;
        this.recordDate = recordDate;
        this.description = description;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        this.doctorName = source.getDoctorName().toString();
        this.recordDate = source.getRecordDate().getRawFormat();
        this.description = source.getDescription().toString();
        this.procedure = source.getProcedure().toString();
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Record} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     * Limitations: If there are multiple constraints violated, only the first will be reported.
     */
    public Record toModelType() throws IllegalValueException {

        if (procedure == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Procedure.class.getSimpleName()));
        }
        if (!Procedure.isValidProcedure(procedure)) {
            throw new IllegalValueException(Procedure.MESSAGE_CONSTRAINTS);
        }

        if (doctorName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "doctor"));
        }
        if (!Name.isValidName(doctorName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        if (recordDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RecordDate.class.getSimpleName()));
        }
        if (!DateBase.isValidDate(recordDate)) {
            throw new IllegalValueException(DateBase.MESSAGE_CONSTRAINTS);
        }

        return new Record(doctorName, description, recordDate, procedure);
    }

}
