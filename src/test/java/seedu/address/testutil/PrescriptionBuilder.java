package seedu.address.testutil;

import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.model.prescription.Prescription;

/**
 * A utility class to help with building Prescription objects.
 */
public class PrescriptionBuilder {

    public static final String DEFAULT_PATIENT_ID = "1";
    public static final String DEFAULT_DOCTOR_ID = "2";
    public static final String DEFAULT_DATE = "2018-05-13";
    public static final String DEFAULT_MEDICINE = "Aspirin";
    public static final String DEFAULT_DESCRIPTION = "For relieving pain";

    private PersonId patientId;
    private PersonId doctorId;
    private ValidDate date;
    private Medicine medicine;
    private Description description;

    public PrescriptionBuilder() {
        patientId = new PersonId(DEFAULT_PATIENT_ID);
        doctorId = new PersonId(DEFAULT_DOCTOR_ID);
        date = new ValidDate(DEFAULT_DATE);
        medicine = new Medicine(DEFAULT_MEDICINE);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the PrescriptionBuilder with the data of {@code prescription}.
     */
    public PrescriptionBuilder(Prescription prescription) {
        patientId = prescription.getPatientId();
        doctorId = prescription.getDoctorId();
        date = prescription.getDate();
        medicine = prescription.getMedicine();
        description = prescription.getDescription();
    }

    /**
     * Sets the {@code PatientId} of the {@code Prescription} that we are building.
     */
    public PrescriptionBuilder withPid(String patientId) {
        this.patientId = new PersonId(patientId);
        return this;
    }

    /**
     * Sets the {@code DoctorId} of the {@code Prescription} that we are building.
     */
    public PrescriptionBuilder withDid(String doctorId) {
        this.doctorId = new PersonId(doctorId);
        return this;
    }

    /**
     * Sets the {@code ValidDate} of the {@code Prescription} that we are building.
     */
    public PrescriptionBuilder withDate(String date) {
        this.date = new ValidDate(date);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Prescription} that we are building.
     */
    public PrescriptionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Medicine} of the {@code Prescription} that we are building.
     */
    public PrescriptionBuilder withMedicine(String name) {
        this.medicine = new Medicine(name);
        return this;
    }

    public Prescription build() {
        return new Prescription(patientId, doctorId, date, medicine, description);
    }
}
