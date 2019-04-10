package seedu.address.testutil;

import seedu.address.model.datetime.RecordDate;
import seedu.address.model.description.Description;
import seedu.address.model.person.Name;
import seedu.address.model.record.Procedure;
import seedu.address.model.record.Record;

/**
 * A utility class to help with building Person objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_DOCTOR = "Kyler Wong";
    public static final String DEFAULT_PROCEDURE = "Consultation-others";
    public static final String DEFAULT_DESCRIPTION = "Patient is doing well";
    public static final String DEFAULT_DATE = "10-10-2010";

    private Name doctorName;
    private Procedure procedure;
    private Description description;
    private RecordDate recordDate;

    public RecordBuilder() {
        this.doctorName = new Name(DEFAULT_DOCTOR);
        this.procedure = new Procedure(DEFAULT_PROCEDURE);
        this.description = new Description(DEFAULT_DESCRIPTION);
        this.recordDate = new RecordDate(DEFAULT_DATE);
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(Record recordToCopy) {
        this.doctorName = recordToCopy.getDoctorName();
        this.procedure = recordToCopy.getProcedure();
        this.description = recordToCopy.getDescription();
        this.recordDate = recordToCopy.getRecordDate();
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withDocName(String name) {
        this.doctorName = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Procedure} of the {@code Record} that we are building.
     */
    public RecordBuilder withProcedure(String procedure) {
        this.procedure = new Procedure(procedure);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Record} that we are building.
     */
    public RecordBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code RecordDate} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.recordDate = new RecordDate(date);
        return this;
    }

    /**
     * Builds a new Record based on the given parameters.
     */
    public Record build() {
        return new Record(this.doctorName.fullName, this.description.value, this.recordDate.getRawFormat(),
                this.procedure.getProcedureType() + "-" + this.procedure.getTypeDetails());
    }
}
