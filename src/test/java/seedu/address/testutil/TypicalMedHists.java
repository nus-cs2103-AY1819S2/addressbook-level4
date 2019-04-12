package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DocX;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;

/**
 * A utility class containing a list of {@code Medical History} objects to be used in tests.
 */
public class TypicalMedHists {

    public static final MedicalHistory MED_HIST1 = new MedicalHistory(new PersonId("1"), new PersonId("7"),
            new ValidDate("2019-03-03"), new WriteUp("The patient got a high fever."));
    public static final MedicalHistory MED_HIST2 = new MedicalHistory(new PersonId("2"), new PersonId("7"),
                    new ValidDate("2019-03-03"), new WriteUp("The patient had a sneeze."));
    public static final MedicalHistory MED_HIST3 = new MedicalHistory(new PersonId("2"), new PersonId("8"),
            new ValidDate("2019-01-30"), new WriteUp("The patient had a stomachache. "
            + "I gave him some medicine to release the pain."));
    public static final MedicalHistory MED_HIST4 = new MedicalHistory(new PersonId("3"), new PersonId("9"),
            new ValidDate("2019-04-03"), new WriteUp("Had a fever with sore throat. Sleeps late."));
    public static final MedicalHistory MED_HIST5 = new MedicalHistory(new PersonId("4"), new PersonId("10"),
            new ValidDate("2019-02-25"),
            new WriteUp("Came down with a stomach flu, possibly due to eating expired food"));

    private TypicalMedHists() {} // prevents instantiation

    /**
     * Returns an {@code DocX} with all the typical Medical History.
     */
    public static DocX getTypicalDocX() {
        DocX ab = new DocX();
        for (MedicalHistory medicalHistory : getTypicalMedHists()) {
            ab.addMedHist(medicalHistory);
        }
        return ab;
    }

    public static List<MedicalHistory> getTypicalMedHists() {
        return new ArrayList<>(Arrays.asList(MED_HIST1, MED_HIST2, MED_HIST3, MED_HIST4, MED_HIST5));
    }

}
