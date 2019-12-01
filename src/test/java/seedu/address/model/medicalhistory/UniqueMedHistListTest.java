package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMedHists.MED_HIST1;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.medicalhistory.exceptions.DuplicateMedHistException;
import seedu.address.model.medicalhistory.exceptions.MedHistNotFoundException;
import seedu.address.testutil.MedHistBuilder;

public class UniqueMedHistListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMedHistList uniqueMedHistList = new UniqueMedHistList();

    @Test
    public void contains_nullMedHist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedHistList.contains(null);
    }

    @Test
    public void contains_medHistNotInList_returnsFalse() {
        assertFalse(uniqueMedHistList.contains(MED_HIST1));
    }

    @Test
    public void contains_medHistInList_returnsTrue() {
        uniqueMedHistList.add(MED_HIST1);
        assertTrue(uniqueMedHistList.contains(MED_HIST1));
    }

    @Test
    public void contains_medHistWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMedHistList.add(MED_HIST1);
        MedicalHistory editedMedHist = new MedHistBuilder(MED_HIST1).withWriteUp("Test").build();
        assertTrue(uniqueMedHistList.contains(editedMedHist));
    }

    @Test
    public void add_nullMedHist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedHistList.add(null);
    }

    @Test
    public void add_duplicateMedHist_throwsDuplicateMedHistException() {
        uniqueMedHistList.add(MED_HIST1);
        thrown.expect(DuplicateMedHistException.class);
        uniqueMedHistList.add(MED_HIST1);
    }

    @Test
    public void setMedHist_nullTargetMedHist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedHistList.setMedHist(null, MED_HIST1);
    }

    @Test
    public void setMedHist_nullEditedMedHist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedHistList.setMedHist(MED_HIST1, null);
    }

    @Test
    public void setMedHist_targetMedHistNotInList_throwsMedHistNotFoundException() {
        thrown.expect(MedHistNotFoundException.class);
        uniqueMedHistList.setMedHist(MED_HIST1, MED_HIST1);
    }

    @Test
    public void setMedHist_editedMedHistIsSameMedHist_success() {
        uniqueMedHistList.add(MED_HIST1);
        uniqueMedHistList.setMedHist(MED_HIST1, MED_HIST1);
        UniqueMedHistList expectedUniqueMedHistList = new UniqueMedHistList();
        expectedUniqueMedHistList.add(MED_HIST1);
        assertEquals(expectedUniqueMedHistList, uniqueMedHistList);
    }

    @Test
    public void remove_nullMedHist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedHistList.remove(null);
    }

    @Test
    public void remove_medHistDoesNotExist_throwsMedHistNotFoundException() {
        thrown.expect(MedHistNotFoundException.class);
        uniqueMedHistList.remove(MED_HIST1);
    }

    @Test
    public void remove_existingMedHist_removesMedHist() {
        uniqueMedHistList.add(MED_HIST1);
        uniqueMedHistList.remove(MED_HIST1);
        UniqueMedHistList expectedUniqueMedHistList = new UniqueMedHistList();
        assertEquals(expectedUniqueMedHistList, uniqueMedHistList);
    }

    @Test
    public void setPatients_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedHistList.setMedHists((List<MedicalHistory>) null);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMedHistList.asUnmodifiableObservableList().remove(0);
    }

}
