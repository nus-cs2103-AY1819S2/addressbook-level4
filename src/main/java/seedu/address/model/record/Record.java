package seedu.address.model.record;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.storage.JsonAdaptedConstants.DIVIDER;

import java.util.Objects;

import seedu.address.model.datetime.RecordDate;
import seedu.address.model.dentist.Dentist;
import seedu.address.model.description.Description;
import seedu.address.model.person.Name;


/**
 * Represents a Record
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    private final Name doctorName;

    private final Description description;

    private final RecordDate recordDate;

    /**
     * Used by add command.
     * @param desc the description of the record.
     */
    public Record(Description desc) {
        requireAllNonNull(desc);
        this.doctorName = new Name(Dentist.getDentistName());
        this.description = desc;
        this.recordDate = new RecordDate();
    }

    /**
     * Used by JSON.
     * @param doctorName the name of the doctor.
     * @param description the description of the record.
     * @param recordDate the date of the record.
     */
    public Record(String doctorName, String description, String recordDate) {
        this.doctorName = new Name(doctorName);
        this.description = new Description(description);
        this.recordDate = new RecordDate(recordDate);
    }

    /**
     * Used by patient class when creating a new patient.
     * Creates a "patient added today" record.
     */
    public Record() {
        this.doctorName = new Name("NA");
        this.description = new Description("Patient is added today");
        this.recordDate = new RecordDate();
    }

    public Name getDoctorName() {
        return doctorName;
    }

    public Description getDescription() {
        return description;
    }

    public RecordDate getRecordDate() {
        return recordDate;
    }

    public String getRecord() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Doctor: ")
                .append(getDoctorName())
                .append(", Description: ")
                .append(getDescription())
                .append(", on ")
                .append(getRecordDate());

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;

        return otherRecord.getDoctorName().equals(getDoctorName())
                && otherRecord.getDescription().equals(getDescription())
                && otherRecord.getRecordDate().equals(getRecordDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDoctorName())
                .append(DIVIDER)
                .append(getDescription())
                .append(DIVIDER)
                .append(getRecordDate());

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorName, description, recordDate);
    }
}
