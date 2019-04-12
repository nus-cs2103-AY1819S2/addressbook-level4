package seedu.address.model.prescription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPrescriptions.PRESC1;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.prescription.exceptions.DuplicatePrescriptionException;
import seedu.address.model.prescription.exceptions.PrescriptionNotFoundException;

public class UniquePrescriptionListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniquePrescriptionList uniquePrescriptionList = new UniquePrescriptionList();

    @Test
    public void contains_nullMedHist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePrescriptionList.contains(null);
    }

    @Test
    public void contains_prescriptionNotInList_returnsFalse() {
        assertFalse(uniquePrescriptionList.contains(PRESC1));
    }

    @Test
    public void contains_medHistInList_returnsTrue() {
        uniquePrescriptionList.addPrescription(PRESC1);
        assertTrue(uniquePrescriptionList.contains(PRESC1));
    }

    @Test
    public void add_nullPrescription_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePrescriptionList.addPrescription(null);
    }

    @Test
    public void add_duplicatePrescription_throwsDuplicatePrescriptionException() {
        uniquePrescriptionList.addPrescription(PRESC1);
        thrown.expect(DuplicatePrescriptionException.class);
        uniquePrescriptionList.addPrescription(PRESC1);
    }

    @Test
    public void setPrescription_nullTargetPrescription_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePrescriptionList.setPrescription(null, PRESC1);
    }

    @Test
    public void setPrescription_nullEditedPrescription_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePrescriptionList.setPrescription(PRESC1, null);
    }

    @Test
    public void setPrescription_targetPrescriptionNotInList_throwsPrescriptionNotFoundException() {
        thrown.expect(PrescriptionNotFoundException.class);
        uniquePrescriptionList.setPrescription(PRESC1, PRESC1);
    }

    @Test
    public void setPrescription_editedPrescriptionIsSamePrescription_success() {
        uniquePrescriptionList.addPrescription(PRESC1);
        uniquePrescriptionList.setPrescription(PRESC1, PRESC1);
        UniquePrescriptionList expectedUniquePrescriptionList = new UniquePrescriptionList();
        expectedUniquePrescriptionList.addPrescription(PRESC1);
        assertEquals(expectedUniquePrescriptionList, uniquePrescriptionList);
    }

    @Test
    public void remove_nullPrescription_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePrescriptionList.remove(null);
    }

    @Test
    public void remove_prescriptionDoesNotExist_throwsPrescriptionNotFoundException() {
        thrown.expect(PrescriptionNotFoundException.class);
        uniquePrescriptionList.remove(PRESC1);
    }

    @Test
    public void remove_existingPrescription_removesMedHist() {
        uniquePrescriptionList.addPrescription(PRESC1);
        uniquePrescriptionList.remove(PRESC1);
        UniquePrescriptionList expectedUniquePrescriptionList = new UniquePrescriptionList();
        assertEquals(expectedUniquePrescriptionList, uniquePrescriptionList);
    }

    @Test
    public void setPrescriptions_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePrescriptionList.setPrescriptions((List<Prescription>) null);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePrescriptionList.asUnmodifiableObservableList().remove(0);
    }

}
