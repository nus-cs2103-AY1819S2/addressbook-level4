package seedu.address.model.consultation;

import java.util.ArrayList;

import seedu.address.model.patient.Patient;

/**
 * Handle all model operations concerning with consultations
 */
public class ConsultationManager {

    private ArrayList<Consultation> consultationList;
    private Consultation currentConsultation;

    public ConsultationManager() {
        this.consultationList = new ArrayList<Consultation>();
    }

    // add diagnosis methods

    /**
     * Create a consultation session with the patient indicated
     */
    public void createConsultation(Patient patient) {
        if (currentConsultation != null) {
            throw new IllegalArgumentException("There is already an ongoing consultation session");
        }
        currentConsultation = new Consultation(patient);
    }

    /**
     * Adds or replace diagnosis on current consultation session
     */
    public void diagnosePatient(Diagnosis diagnosis) {
        if (currentConsultation == null) {
            throw new IllegalArgumentException("There is no ongoing consultation session");
        }
        currentConsultation.setDiagnosis(diagnosis);
    }
}
