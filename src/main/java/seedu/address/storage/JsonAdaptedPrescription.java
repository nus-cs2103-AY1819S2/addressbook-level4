package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.PersonId;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.model.prescription.Prescription;


/**
 * Jackson-friendly version of {@link Prescription}.
 */
public class JsonAdaptedPrescription {

    private final int patientId;
    private final int doctorId;
    private final String medicineName;
    private final String description;


    /**
     * Constructs a {@code JsonAdaptedPrescription} with the given prescription details.
     */
    @JsonCreator
    public JsonAdaptedPrescription(@JsonProperty("patientId") int patientId,
                                   @JsonProperty("doctorId") int doctorId,
                                   @JsonProperty("medcineName") String medicineName,
                                   @JsonProperty("description") String description) {
        this.patientId = patientId;
        this.doctorId = doctorId;
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
        medicineName = source.getMedicine().getName();
        description = source.getDescription().toString();

    }

    /**
     * Converts this Jackson-friendly adapted prescription object into the model's {@code prescription} object.
     */
    public Prescription toModelType() {
        final PersonId patientId = new PersonId(this.patientId);
        final PersonId doctorId = new PersonId(this.doctorId);
        final Medicine medicine = new Medicine(this.medicineName);
        final Description description = new Description(this.description);
        return new Prescription(patientId, doctorId, medicine, description);
    }

}


