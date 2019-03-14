package seedu.address.model.medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;
import seedu.address.testutil.MedicineBuilder;

public class UniqueMedicineListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMedicineList uniqueMedicineList = new UniqueMedicineList();

    @Test
    public void contains_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.contains(null);
    }

    @Test
    public void contains_medicineNotInList_returnsFalse() {
        assertFalse(uniqueMedicineList.contains(PARACETAMOL));
    }

    @Test
    public void contains_medicineInList_returnsTrue() {
        uniqueMedicineList.add(PARACETAMOL);
        assertTrue(uniqueMedicineList.contains(PARACETAMOL));
    }

    @Test
    public void contains_medicineWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMedicineList.add(PARACETAMOL);
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withExpiry(VALID_EXPIRY_GABAPENTIN)
                .withTags(VALID_TAG_PAINKILLER).build();
        assertTrue(uniqueMedicineList.contains(editedParacetamol));
    }

    @Test
    public void add_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.add(null);
    }

    @Test
    public void add_duplicateMedicine_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(PARACETAMOL);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.add(PARACETAMOL);
    }

    @Test
    public void setMedicine_nullTargetMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicine(null, PARACETAMOL);
    }

    @Test
    public void setMedicine_nullEditedMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicine(PARACETAMOL, null);
    }

    @Test
    public void setMedicine_targetMedicineNotInList_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.setMedicine(PARACETAMOL, PARACETAMOL);
    }

    @Test
    public void setMedicine_editedMedicineIsSameMedicine_success() {
        uniqueMedicineList.add(PARACETAMOL);
        uniqueMedicineList.setMedicine(PARACETAMOL, PARACETAMOL);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(PARACETAMOL);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasSameIdentity_success() {
        uniqueMedicineList.add(PARACETAMOL);
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN)
                .withTags(VALID_TAG_PAINKILLER).build();
        uniqueMedicineList.setMedicine(PARACETAMOL, editedParacetamol);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(editedParacetamol);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasDifferentIdentity_success() {
        uniqueMedicineList.add(PARACETAMOL);
        uniqueMedicineList.setMedicine(PARACETAMOL, GABAPENTIN);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(GABAPENTIN);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasNonUniqueIdentity_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(PARACETAMOL);
        uniqueMedicineList.add(GABAPENTIN);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicine(PARACETAMOL, GABAPENTIN);
    }

    @Test
    public void remove_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.remove(null);
    }

    @Test
    public void remove_medicineDoesNotExist_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.remove(PARACETAMOL);
    }

    @Test
    public void remove_existingMedicine_removesMedicine() {
        uniqueMedicineList.add(PARACETAMOL);
        uniqueMedicineList.remove(PARACETAMOL);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_nullUniqueMedicineList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicines((UniqueMedicineList) null);
    }

    @Test
    public void setMedicines_uniqueMedicineList_replacesOwnListWithProvidedUniqueMedicineList() {
        uniqueMedicineList.add(PARACETAMOL);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(GABAPENTIN);
        uniqueMedicineList.setMedicines(expectedUniqueMedicineList);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicines((List<Medicine>) null);
    }

    @Test
    public void setMedicines_list_replacesOwnListWithProvidedList() {
        uniqueMedicineList.add(PARACETAMOL);
        List<Medicine> medicineList = Collections.singletonList(GABAPENTIN);
        uniqueMedicineList.setMedicines(medicineList);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(GABAPENTIN);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_listWithDuplicateMedicines_throwsDuplicateMedicineException() {
        List<Medicine> listWithDuplicateMedicines = Arrays.asList(PARACETAMOL, PARACETAMOL);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicines(listWithDuplicateMedicines);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMedicineList.asUnmodifiableObservableList().remove(0);
    }
}
