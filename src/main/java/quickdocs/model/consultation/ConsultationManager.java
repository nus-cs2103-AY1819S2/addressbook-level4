package quickdocs.model.consultation;

import java.util.ArrayList;

import quickdocs.model.patient.Patient;

/**
 * Handle all model operations involving the steps in consultation
 */
public class ConsultationManager {

    private ArrayList<Consultation> consultationList;
    private Consultation currentConsultation;

    public ConsultationManager() {
        this.consultationList = new ArrayList<Consultation>();
    }


    /**
     * Check if there is an ongoing consultation session
     */
    public boolean checkConsultation() {
        return currentConsultation != null;
    }

    /**
     * Adds a consultation record into the consultationList
     * This method is primarily used in the reading of consultation records from json storage
     */
    public void addConsultation(Consultation consultation) {
        this.consultationList.add(consultation);
    }

    /**
     * Starts a consultation session with the specified patient.
     * QuickDocs only permit one ongoing consultation session at any one time.
     *
     * @param patient patient record to start the consultation session with
     * @throws IllegalArgumentException when the user attempt to start another session when there is an
     * ongoing consultation session
     */
    public void createConsultation(Patient patient) {
        if (currentConsultation != null) {
            throw new IllegalArgumentException("There is already an ongoing consultation session\n");
        }
        currentConsultation = new Consultation(patient);
    }

    public void diagnosePatient(Diagnosis diagnosis) {
        currentConsultation.setDiagnosis(diagnosis);
    }

    public void prescribeMedicine(ArrayList<Prescription> prescriptions) {
        currentConsultation.setPrescriptions(prescriptions);
    }

    public Consultation getCurrentConsultation() {
        return currentConsultation;
    }

    public ArrayList<Consultation> getConsultationList() {
        return consultationList;
    }

    /**
     * Stores current consultation record into consultationList and ends the current
     * session
     */
    public void endConsultation() {
        currentConsultation.setIndex(consultationList.size());
        consultationList.add(currentConsultation);
        currentConsultation = null;
    }

    /**
     * List past consultation records belonging to a single patient
     */
    public ArrayList<Consultation> listConsultation(String value) {

        ArrayList<Consultation> consultationsFound = new ArrayList<>();

        for (Consultation consult : consultationList) {
            if (consult.getPatient().getNric().getNric().equals(value)) {
                consultationsFound.add(consult);
            }
        }
        return consultationsFound;
    }

    public Consultation listConsultation(int index) {
        return consultationList.get(index - 1);
    }

    public void abortConsultation() {
        currentConsultation = null;
    }
}
