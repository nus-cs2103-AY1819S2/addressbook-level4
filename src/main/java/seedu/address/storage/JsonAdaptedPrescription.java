package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.model.prescription.Prescription;


/**
 * Jackson-friendly version of {@link Prescription}.
 */
public class JsonAdaptedPrescription {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Prescription's %s field is missing!";

    private final int patientId;
    private final int doctorId;
    private final String date;
    private final String medicineName;
    private final String description;


    /**
     * Constructs a {@code JsonAdaptedPrescription} with the given prescription details.
     */
    @JsonCreator
    public JsonAdaptedPrescription(@JsonProperty("patientId") int patientId,
                                   @JsonProperty("doctorId") int doctorId,
                                   @JsonProperty("date") String date,
                                   @JsonProperty("medicineName") String medicineName,
                                   @JsonProperty("description") String description) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.medicineName = medicineName;
        this.description = description;
    }

    /**
     *
     * Converts a given {@code MedicalHistory} into this class for Jackson use.
     */
    public JsonAdaptedPrescription(Prescription source) {
        doctorId = source.getDoctorId().personId;
        patientId = source.getPatientId().personId;
        date = source.getDate().toString();
        medicineName = source.getMedicine().getName();
        description = source.getDescription().toString();

    }

    /**
     * Converts this Jackson-friendly adapted prescription object into the model's {@code prescription} object.
     */
    public Prescription toModelType() throws IllegalValueException {
        if (!PersonId.isValidPersonId(Integer.toString(patientId))) {
            throw new IllegalValueException(PersonId.MESSAGE_CONSTRAINTS);
        }
        if (!PersonId.isValidPersonId(Integer.toString(doctorId))) {
            throw new IllegalValueException(PersonId.MESSAGE_CONSTRAINTS);
        }
        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ValidDate.class.getSimpleName()));
        }
        if (!ValidDate.isValidDate(this.date)) {
            throw new IllegalValueException(ValidDate.MESSAGE_CONSTRAINTS);
        }
        if (this.medicineName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Medicine.class.getSimpleName()));
        }
        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final PersonId patientId = new PersonId(this.patientId);
        final PersonId doctorId = new PersonId(this.doctorId);
        final ValidDate date = new ValidDate(this.date);
        final Medicine medicine = new Medicine(this.medicineName);
        final Description description = new Description(this.description);
        return new Prescription(patientId, doctorId, date, medicine, description);
    }

}


