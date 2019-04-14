package quickdocs.model.consultation;

import java.time.LocalDateTime;
import java.util.ArrayList;

import quickdocs.model.patient.Patient;

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
    public Consultation(Patient patient) {
        this.index = -1;
        this.session = LocalDateTime.now();
        this.patient = patient;
    }

    public Consultation() {
    }

    //for json records creation of consultation
    public Consultation(int index, Patient patient, LocalDateTime session, Diagnosis diagnosis,
                        ArrayList<Prescription> prescriptions) {
        this.index = index;
        this.patient = patient;
        this.session = session;
        this.diagnosis = diagnosis;
        this.prescriptions = new ArrayList<>();
        this.prescriptions.addAll(prescriptions);
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

    public void setSession(LocalDateTime session) {
        this.session = session;
    }

    public Patient getPatient() {
        return patient;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Consultation for: " + getPatient().getNric().getNric()
                + " Name: " + getPatient().getName().getName() + "\n");
        sb.append("====================\n");
        sb.append("\n");
        sb.append(getDiagnosis());
        sb.append("\n");
        sb.append("prescription:\n");
        sb.append("====================\n");
        for (Prescription prescription : prescriptions) {
            sb.append(prescription);
        }
        return sb.toString();
    }
}
