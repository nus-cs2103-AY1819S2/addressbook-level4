package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DocX;
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
            new ValidDate("2018-05-13"), new Medicine("testing1"), new Description("testing1"));
    public static final Prescription PRESC2 = new Prescription(new PersonId("3"), new PersonId("2"),
            new ValidDate("2018-05-14"), new Medicine("testing2"), new Description("testing2"));
    public static final Prescription PRESC3 = new Prescription(new PersonId("5"), new PersonId("4"),
            new ValidDate("2018-05-15"), new Medicine("testing3"), new Description("testing3"));
    public static final Prescription PRESC4 = new Prescription(new PersonId("6"), new PersonId("4"),
            new ValidDate("2018-05-17"), new Medicine("testing4"), new Description("testing4"));
    public static final Prescription PRESC5 = new Prescription(new PersonId("7"), new PersonId("2"),
            new ValidDate("2018-06-14"), new Medicine("testing5"), new Description("testing5"));
    public static final Prescription PRESC6 = new Prescription(new PersonId("8"), new PersonId("4"),
            new ValidDate("2018-06-19"), new Medicine("testing6"), new Description("testing6"));

    /**
     * Returns an {@code docX} with all the typical doctors.
     */
    public static DocX getTypicalDocX_prescription() {
        DocX typicalDocX = new DocX();
        for (Prescription prescription : getTypicalPrescriptions()) {
            typicalDocX.addPrescription(prescription);
        }
        return typicalDocX;
    }

    public static List<Prescription> getTypicalPrescriptions() {
        return new ArrayList<>(Arrays.asList(PRESC1, PRESC2, PRESC3, PRESC4, PRESC5, PRESC6));
    }
}
