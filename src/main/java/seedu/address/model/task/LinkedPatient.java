package seedu.address.model.task;

import java.util.Objects;

import seedu.address.model.patient.Nric;
import seedu.address.model.person.Name;

/**
 * Represents a person linked to a task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLinkedPatient(String, String)} (String)}
 */
public class LinkedPatient {
    public static final String MESSAGE_CONSTRAINTS = "Index of patient should be a positive integer and should be "
                                                    + "a valid number next to the name of one of the patients in the "
                                                    + "patient list\n"
                                                    + "Example: pat/1 or pat/3";

    public static final String MESSAGE_ADDITIONAL_CONSTRAINT = "To link a patient to a task, please ensure you are in "
                                                    + "patient mode so that you can see the patient index. "
                                                    + "To go to patient mode, use the back command.\n";

    private Name fullname;
    private Nric nric;

    /**
     * Constructs a (@Code LinkedPatient)
     *
     * @param name A valid name
     * @param nric A valid nric
     */
    public LinkedPatient(Name name, Nric nric) {
        this.fullname = name;
        this.nric = nric;
    }

    /**
     * Uses the validity checks for Name and Nric classes to determine the validity
     * of a LinkedPatient. Returns false if any of the two fields are invalid.
     * Due to the way LinkedPatient is added to tasks, this should never be false.
     */
    public static boolean isValidLinkedPatient(String nametest, String nrictest) {
        if (nametest == null || nrictest == null) {
            return true;
        } else {
            return (Name.isValidName(nametest) && Nric.isValidNric(nrictest));
        }
    }

    public String getLinkedPatientName() {
        return fullname.fullName;
    }

    public String getLinkedPatientNric() {
        return nric.getNric();
    }

    @Override
    public String toString() {
        if (fullname == null || nric == null) {
            return "Not linked to a patient";
        } else {
            return fullname.fullName + " " + nric.getNric();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LinkedPatient // instanceof handles nulls
                && fullname.equals(((LinkedPatient) other).fullname)) // state check
                && nric.equals(((LinkedPatient) other).nric);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullname, nric);
    }


}
