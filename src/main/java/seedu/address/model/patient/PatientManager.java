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

    //==========Patient edit methods=================================================

    public boolean isPatientListEmpty() {
        return patientList.size() < 1;
    }

    /**
     * Check if index entered to edit patient exceeds the bounds of the
     * list of patients
     */
    public boolean checkValidIndex(int index) {
        if (index - 1 >= patientList.size()) {
            return false;
        }

        if (index - 1 < 0) {
            return false;
        }

        return true;
    }

    public Patient getPatientAtIndex(int index) {
        // index for patientlist should be 1 based
        return patientList.get(index - 1);
    }

    /**
     * Check for patient records with same nric as the newly edited patient
     */
    public boolean checkDuplicatePatientAfterEdit(int index, Patient editedPatient) {
        for (int i = 0; i < patientList.size(); i++) {
            if (i == index - 1) {
                continue;
            }

            if (patientList.get(i).getNric().equals(editedPatient.getNric())) {
                return true;
            }
        }
        return false;
    }

    public void replacePatient(int index, Patient editedPatient) {
        patientList.set(index - 1, editedPatient);
    }

}
