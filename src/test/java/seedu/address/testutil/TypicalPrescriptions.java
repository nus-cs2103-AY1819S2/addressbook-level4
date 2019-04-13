package seedu.address.testutil;

import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.model.prescription.Prescription;



/**
 * A utility class containing a list of {@code Prescript
 * ion} objects to be used in tests.
 */
public class TypicalPrescriptions {

    public static final Prescription PRESC1 = new Prescription(new PersonId("1"), new PersonId("2"),
            new ValidDate("2018-05-13"), new Medicine("testing"), new Description("testing"));
}
