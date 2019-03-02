package seedu.address.model.consultation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.model.patient.Patient;

/**
 * Stores the consultation details of a single session - both diagnosis and prescribed drugs
 */
public class Consultation {
    // for listing multiple consultations belonging to a single patient
    // default value is -1 until the consultation details are confirmed and the
    // consultation session is stored in the consultationManager's list of consultations
    private int index;

    private LocalDateTime session;
    private Patient patient;
    private Diagnosis diagnosis;
    private ArrayList<Prescription> prescriptions;

    // empty constructor is used, attributes are assigned later
    // when diagnosing a patient, the patient detail is set
    // after symptoms and assessments are parsed in, diagnosis is set
    // when drugs are prescribed, then prescriptions is set
    // once all the fields are not null, only then the consultation can be stored
    public Consultation() {
        index = -1;
        session = LocalDateTime.now();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LocalDateTime getSession() {
        return session;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
