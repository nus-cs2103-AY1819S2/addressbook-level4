package seedu.equipment.model.equipment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.BOB;

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
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueEquipmentList.contains(ANCHORVALECC));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueEquipmentList.add(ANCHORVALECC);
        assertTrue(uniqueEquipmentList.contains(ANCHORVALECC));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEquipmentList.add(ANCHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueEquipmentList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueEquipmentList.add(ANCHORVALECC);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.add(ANCHORVALECC);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipment(null, ANCHORVALECC);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, ANCHORVALECC);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, ANCHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(ANCHORVALECC);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueEquipmentList.add(ANCHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ANCHORVALECC).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueEquipmentList.setEquipment(ANCHORVALECC, editedAlice);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(editedAlice);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, BOB);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEquipmentList.add(ANCHORVALECC);
        uniqueEquipmentList.add(BOB);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.setEquipment(ANCHORVALECC, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
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
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueEquipmentList.add(ANCHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        uniqueEquipmentList.setEquipments(expectedUniqueEquipmentList);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipments((List<Equipment>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEquipmentList.add(ANCHORVALECC);
        List<Equipment> equipmentList = Collections.singletonList(BOB);
        uniqueEquipmentList.setEquipments(equipmentList);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
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
