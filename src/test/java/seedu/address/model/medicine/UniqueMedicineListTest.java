package seedu.address.model.medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalMedicines.ALICE;
import static seedu.address.testutil.TypicalMedicines.BOB;

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
        assertFalse(uniqueMedicineList.contains(ALICE));
    }

    @Test
    public void contains_medicineInList_returnsTrue() {
        uniqueMedicineList.add(ALICE);
        assertTrue(uniqueMedicineList.contains(ALICE));
    }

    @Test
    public void contains_medicineWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMedicineList.add(ALICE);
        Medicine editedAlice = new MedicineBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueMedicineList.contains(editedAlice));
    }

    @Test
    public void add_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.add(null);
    }

    @Test
    public void add_duplicateMedicine_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(ALICE);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.add(ALICE);
    }

    @Test
    public void setMedicine_nullTargetMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicine(null, ALICE);
    }

    @Test
    public void setMedicine_nullEditedMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicine(ALICE, null);
    }

    @Test
    public void setMedicine_targetMedicineNotInList_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.setMedicine(ALICE, ALICE);
    }

    @Test
    public void setMedicine_editedMedicineIsSameMedicine_success() {
        uniqueMedicineList.add(ALICE);
        uniqueMedicineList.setMedicine(ALICE, ALICE);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(ALICE);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasSameIdentity_success() {
        uniqueMedicineList.add(ALICE);
        Medicine editedAlice = new MedicineBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMedicineList.setMedicine(ALICE, editedAlice);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(editedAlice);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasDifferentIdentity_success() {
        uniqueMedicineList.add(ALICE);
        uniqueMedicineList.setMedicine(ALICE, BOB);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(BOB);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasNonUniqueIdentity_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(ALICE);
        uniqueMedicineList.add(BOB);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicine(ALICE, BOB);
    }

    @Test
    public void remove_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.remove(null);
    }

    @Test
    public void remove_medicineDoesNotExist_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.remove(ALICE);
    }

    @Test
    public void remove_existingMedicine_removesMedicine() {
        uniqueMedicineList.add(ALICE);
        uniqueMedicineList.remove(ALICE);
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
        uniqueMedicineList.add(ALICE);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(BOB);
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
        uniqueMedicineList.add(ALICE);
        List<Medicine> medicineList = Collections.singletonList(BOB);
        uniqueMedicineList.setMedicines(medicineList);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(BOB);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_listWithDuplicateMedicines_throwsDuplicateMedicineException() {
        List<Medicine> listWithDuplicateMedicines = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicines(listWithDuplicateMedicines);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMedicineList.asUnmodifiableObservableList().remove(0);
    }
}
