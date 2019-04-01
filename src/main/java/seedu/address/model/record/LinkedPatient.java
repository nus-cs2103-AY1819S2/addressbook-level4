package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;

/**
 * Represents a person linked to a task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLinkedPerson(String)}
 */
public class LinkedPatient {
    public static final String MESSAGE_CONSTRAINTS = "Index of patient should be a positive integer and should be "
                                                    + "a valid number next to the name of one of the patients in the "
                                                    + "patient list\n"
                                                    + "Example: pat/1 or pat/3";

    private Name fullname;
    private Nric nric;

    public LinkedPatient(Name name, Nric nric) {
        this.fullname = name;
        this.nric = nric;
    }

    public static boolean isValidLinkedPatient(String nametest, String nrictest) {
        return (Name.isValidName(nametest) && Nric.isValidNric(nrictest));
    }

    public String getLinkedPatientName() {
        return fullname.fullName;
    }

    public String getLinkedPatientNric() {
        return nric.getNric();
    }

    @Override
    public String toString() {
        return fullname.fullName + " " + nric.getNric();
    }


}
