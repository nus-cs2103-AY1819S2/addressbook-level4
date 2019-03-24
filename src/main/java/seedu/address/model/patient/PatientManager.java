package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.model.tag.Tag;

/**
 * Handle all operations involving the models of patient module
 */
public class PatientManager {

    private ArrayList<Patient> patientList;

    public PatientManager() {
        this.patientList = new ArrayList<Patient>();
    }

    public PatientManager(ArrayList<Patient> patients) {
        this.patientList = patients;
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


    // listing methods

    /**
     * find all patients stored in patientlist that fulfills search criteria
     */
    public String findPatientsByName(String searchSequence) {
        ArrayList<Patient> foundPatients = new ArrayList<>();
        ArrayList<Integer> foundPatientsIndexes = new ArrayList<>();
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);

            if (patient.getName().toString().toLowerCase()
                    .matches("^" + searchSequence.toLowerCase() + ".*")) {
                foundPatients.add(patient);
                foundPatientsIndexes.add(i + 1);
            }
        }

        if (foundPatients.size() == 0) {
            return "No patient record found\n";
        }

        if (foundPatients.size() > 1) {
            return formatMultiplePatients(foundPatients, foundPatientsIndexes);
        }

        return foundPatients.get(0).toString();

    }

    /**
     * find all patients stored in patientlist that fulfills search criteria
     */
    public String findPatientsByNric(String searchSequence) {
        ArrayList<Patient> foundPatients = new ArrayList<>();
        ArrayList<Integer> foundPatientsIndexes = new ArrayList<>();
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);

            if (patient.getNric().toString().toLowerCase()
                    .matches("^" + searchSequence.toLowerCase() + ".*")) {
                foundPatients.add(patient);
                foundPatientsIndexes.add(i + 1);
            }
        }

        if (foundPatients.size() == 0) {
            return "No patient record found\n";
        }

        if (foundPatients.size() > 1) {
            return formatMultiplePatients(foundPatients, foundPatientsIndexes);
        }

        return foundPatients.get(0).toString();

    }

    /**
     * for default list, try to list up to 50 patients
     */
    public String listFiftyPatients() {
        ArrayList<Patient> foundPatients = new ArrayList<>();
        ArrayList<Integer> foundPatientsIndexes = new ArrayList<>();
        for (int i = 0; i < 49; i++) {
            if (i >= patientList.size()) {
                break;
            }
            Patient patient = patientList.get(i);
            foundPatients.add(patient);
            foundPatientsIndexes.add(i + 1);
        }
        return formatMultiplePatients(foundPatients, foundPatientsIndexes);
    }

    /**
     * For multiple patient records, a list of their names, nric, gender and dob will be displayed instead
     *
     * @param patients       patient objects in modelmanager that fulfills the search criteria
     * @param patientIndexes index where object is stored in patientlist
     * @return formatted list to be displayed
     */
    public static String formatMultiplePatients(ArrayList<Patient> patients, ArrayList<Integer> patientIndexes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);

            sb.append(patientIndexes.get(i) + ") " + patient.getName()
                    + " " + patient.getNric()
                    + " " + patient.getGender()
                    + " " + patient.getDob()
                    + "\n"
            );
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * find all patients stored in patientlist that have the same tag defined by the user
     */
    public String findPatientsByTag(Tag tag) {
        ArrayList<Patient> foundPatients = new ArrayList<>();
        ArrayList<Integer> foundPatientsIndexes = new ArrayList<>();

        for (int i = 0; i < patientList.size(); i++) {
            Patient currentPatient = patientList.get(i);
            if (currentPatient.getTagList().contains(tag)) {
                foundPatients.add(currentPatient);
                foundPatientsIndexes.add(i + 1);
            }
        }

        if (foundPatients.size() == 0) {
            return "No patient record found\n";
        }

        if (foundPatients.size() > 1) {
            return formatMultiplePatients(foundPatients, foundPatientsIndexes);
        }

        return foundPatients.get(0).toString();
    }

    // for consultation
    public Patient getPatientByNric(String nric) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getNric().toString().equals(nric)) {
                return patientList.get(i);
            }
        }
        return null;
    }

    public Optional<Patient> getPatientWithNric(Nric nric) {
        for (Patient patient : patientList) {
            if (patient.getNric().equals(nric)) {
                return Optional.of(patient);
            }
        }
        return Optional.empty();
    }

    /**
     * Remove patient with nric specified
     * @param nric of the patient to be deleted
     */
    public void deletePatientByNric(String nric) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getNric().toString().equals(nric)) {
                patientList.remove(i);
                break;
            }
        }
    }
}
