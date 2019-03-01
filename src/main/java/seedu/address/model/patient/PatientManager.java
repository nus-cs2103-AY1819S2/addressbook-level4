package seedu.address.model.patient;

import java.util.ArrayList;

/**
 * Handle all operations involving the models of patient module
 */
public class PatientManager {

    private ArrayList<Patient> patientList;

    public PatientManager() {
        this.patientList = new ArrayList<Patient>();
    }

    //==========Patient addition methods=================================================

    /**
     * Checks whether current patient to add have an conflict Nric entry
     */
    public boolean duplicatePatient(Patient patient) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getNric().equals(patient.getNric())) {
                return true;
            }
        }
        return false;
    }

    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }
}
