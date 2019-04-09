package quickdocs.model.patient;

import java.util.ArrayList;
import java.util.Optional;

import quickdocs.model.tag.Tag;

/**
 * Handle all operations involving the models of patient module
 * including adding, editing, listing and deleting of patient records
 */
public class PatientManager {

    private ArrayList<Patient> patientList;

    public PatientManager() {
        this.patientList = new ArrayList<Patient>();
    }


    //==========Patient addition methods=================================================

    /**
     * Returns true if patient record to be added have a conflicting NRIC with
     * another existing patient record.
     *
     * @param patient the record to be added
     * @return boolean value of the NRIC check
     */
    public boolean isDuplicatePatient(Patient patient) {
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
     * Returns true if index entered by user can be used to retrieve patient records from
     * the patientList.
     * If the index is out of patientList's bounds or is negative, returns false.
     *
     * @param index index entered by user to view patient record
     */
    public boolean checkValidIndex(int index) {
        if (index >= patientList.size()) {
            return false;
        }

        if (index < 0) {
            return false;
        }

        return true;
    }

    public Patient getPatientAtIndex(int index) {
        return patientList.get(index);
    }

    /**
     * Returns true if the edited patient will have the same NRIC as another
     * patient record.
     *
     * @param index         index of the patient record to be edited, used to prevent checking the NRIC of
     *                      the current edited patient record
     * @param editedPatient patient record that is currently being edited
     * @return true if NRIC of the selected patient can be edited without causing a conflict
     */
    public boolean checkDuplicatePatientAfterEdit(int index, Patient editedPatient) {
        for (int i = 0; i < patientList.size(); i++) {
            if (i == index) {
                continue;
            }

            if (patientList.get(i).getNric().equals(editedPatient.getNric())) {
                return true;
            }
        }
        return false;
    }

    public void replacePatient(int index, Patient editedPatient) {
        patientList.set(index, editedPatient);
    }


    // listing methods

    /**
     * Returns the patient record details whose name matches the search sequence the user have
     * entered. If there are more than 1 patient whose name matches the search sequence, then
     * return the patients' index, name and nric so that the user can filter down the search
     * even more
     *
     * @param searchSequence full name or part of a name to narrow down searching of patient records
     * @return either the full patient record of a single patient, or a list of patients
     */
    public String findPatientsByName(String searchSequence) {

        // the foundPatients are used to store all
        // the patient records whose name contains the searchSequence.
        ArrayList<Patient> foundPatients = new ArrayList<>();

        // store the index of the patient records that are found
        ArrayList<Integer> foundPatientsIndexes = new ArrayList<>();
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);

            if (patient.getName().toString().toLowerCase().contains(searchSequence.toLowerCase())) {
                foundPatients.add(patient);
                foundPatientsIndexes.add(i + 1);
            }

        }

        if (foundPatients.size() == 0) {
            return "No patient record found";
        }

        // if there are more than 1 patients, then list all the patients
        // whose name matches the searchSequence for the user to narrow down
        // the search even more
        if (foundPatients.size() > 1) {
            return formatMultiplePatients(foundPatients, foundPatientsIndexes);
        }

        return foundPatients.get(0).toString();

    }

    /**
     * Returns the details of a single patient record whose NRIC matches the searchSequence
     * or a list of patients along with their indexes, name and NRIC if their NRIC matches
     * the searchSequence
     *
     * @param searchSequence A part or the full NRIC sequence
     * @return either the full patient record of a single patient, or a list of patients
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
            return "No patient record found";
        }

        if (foundPatients.size() > 1) {
            return formatMultiplePatients(foundPatients, foundPatientsIndexes);
        }

        return foundPatients.get(0).toString();

    }

    /**
     * Returns the first fifty patient's index, nric and name
     * when a patient search have no parameters
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
     * Returns a list of patients that have the same tag defined by the user
     *
     * @param tag to filter patient records with
     * @return either a list of patients with the specified tag, or a single patient record
     * if it is the only one that have the specified tag
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
            return "No patient record found";
        }

        if (foundPatients.size() > 1) {
            return formatMultiplePatients(foundPatients, foundPatientsIndexes);
        }

        return foundPatients.get(0).toString();
    }

    public Patient getPatientByNric(String nric) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getNric().toString().equals(nric)) {
                return patientList.get(i);
            }
        }
        return null;
    }

    public Optional<Patient> getPatientByNric(Nric nric) {
        for (Patient patient : patientList) {
            if (patient.getNric().equals(nric)) {
                return Optional.of(patient);
            }
        }
        return Optional.empty();
    }

    public int getIndexByNric(Nric nric) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getNric().toString().equals(nric.toString())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Remove patient with nric specified
     *
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

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof PatientManager)) {
            return false;
        }

        // state check
        PatientManager other = (PatientManager) obj;
        return this.patientList.equals(other.patientList);

    }
}
