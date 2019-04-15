package seedu.equipment.model.equipment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.equipment.model.equipment.exceptions.EquipmentNotFoundException;
import seedu.equipment.testutil.EquipmentBuilder;

public class UniqueEquipmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEquipmentList uniqueEquipmentList = new UniqueEquipmentList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.contains(null);
    }

    @Test
    public void contains_nullSerialNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.containsWithSerialNumber(null);
    }

    @Test
    public void contains_equipmentNotInList_returnsFalse() {
        assertFalse(uniqueEquipmentList.contains(ANCHORVALECC));
    }

    @Test
    public void contains_equipmentWithSerialNumberNotInList_returnsFalse() {
        assertFalse(uniqueEquipmentList.containsWithSerialNumber(ANCHORVALECC.getSerialNumber()));
    }

    @Test
    public void contains_equipmentInList_returnsTrue() {
        uniqueEquipmentList.add(ANCHORVALECC);
        assertTrue(uniqueEquipmentList.contains(ANCHORVALECC));
    }

    @Test
    public void contains_equipmentWithSerialNumberInList_returnsTrue() {
        uniqueEquipmentList.add(ANCHORVALECC);
        assertTrue(uniqueEquipmentList.containsWithSerialNumber(ANCHORVALECC.getSerialNumber()));
    }

    @Test
    public void getEquipment_notFound() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueEquipmentList.getEquipment(TECKGHEECC.getSerialNumber());
    }

    @Test
    public void getEquipment_haveSuchEquipment_success() {
        uniqueEquipmentList.add(TECKGHEECC);
        assertEquals(uniqueEquipmentList.getEquipment(TECKGHEECC.getSerialNumber()), TECKGHEECC);
    }

    @Test
    public void contains_equipmentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEquipmentList.add(ANCHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueEquipmentList.contains(editedAlice));
    }

    @Test
    public void add_nullEquipment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.add(null);
    }

    @Test
    public void add_duplicateEquipment_throwsDuplicatePersonException() {
        uniqueEquipmentList.add(ANCHORVALECC);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.add(ANCHORVALECC);
    }

    @Test
    public void setEquipment_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipment(null, ANCHORVALECC);
    }

    @Test
    public void setEquipment_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, null);
    }

    @Test
    public void setEquipment_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, ANCHORVALECC);
    }

    @Test
    public void setEquipment_editedEquipmentIsSamePerson_success() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, ANCHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(ANCHORVALECC);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setEquipment_editedPersonHasSameIdentity_success() {
        uniqueEquipmentList.add(ANCHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueEquipmentList.setEquipment(ANCHORVALECC, editedAlice);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(editedAlice);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setEquipment_editedPersonHasDifferentIdentity_success() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, BOB);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setEquipment_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.add(BOB);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, BOB);
    }

    @Test
    public void remove_nullEquipment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.remove(null);
    }

    @Test
    public void remove_equipmentDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueEquipmentList.remove(ANCHORVALECC);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.remove(ANCHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipments((UniqueEquipmentList) null);
    }

    @Test
    public void setEquipment_uniqueEquipmentList_replacesOwnListWithProvidedUniqueEquipmentList() {
        uniqueEquipmentList.add(ANCHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        uniqueEquipmentList.setEquipments(expectedUniqueEquipmentList);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setEquipment_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipments((List<Equipment>) null);
    }

    @Test
    public void setEquipment_list_replacesOwnListWithProvidedList() {
        uniqueEquipmentList.add(ANCHORVALECC);
        List<Equipment> equipmentList = Collections.singletonList(BOB);
        uniqueEquipmentList.setEquipments(equipmentList);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setEquipment_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Equipment> listWithDuplicateEquipments = Arrays.asList(ANCHORVALECC, ANCHORVALECC);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.setEquipments(listWithDuplicateEquipments);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEquipmentList.asUnmodifiableObservableList().remove(0);
    }
}
