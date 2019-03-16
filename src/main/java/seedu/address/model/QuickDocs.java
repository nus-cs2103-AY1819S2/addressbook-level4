package seedu.address.model;

import seedu.address.model.consultation.ConsultationManager;
import seedu.address.model.patient.PatientManager;

/**
 * Manages all managers of QuickDocs
 */
public class QuickDocs {

    private PatientManager patientManager = new PatientManager();
    private ConsultationManager consultationManager = new ConsultationManager();

    private boolean isModified = false;

    public PatientManager getPatientManager() {
        return patientManager;
    }

    public void setPatientManager(PatientManager patientManager) {
        this.patientManager = patientManager;
    }

    public ConsultationManager getConsultationManager() {
        return consultationManager;
    }

    // indicate modification of quickdocs data

    public boolean isModified() {
        return isModified;
    }

    public void indicateModification(boolean state) {
        isModified = state;
    }
}
