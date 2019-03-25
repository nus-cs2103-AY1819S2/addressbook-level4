package seedu.address.model.moduletaken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalModuleTaken.CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS1010;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.moduletaken.exceptions.DuplicatePersonException;
import seedu.address.model.moduletaken.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModuleTakenBuilder;

public class UniqueModuleTakenListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueModuleTakenList uniqueModuleTakenList = new UniqueModuleTakenList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueModuleTakenList.contains(CS2103T));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueModuleTakenList.add(CS2103T);
        assertTrue(uniqueModuleTakenList.contains(CS2103T));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleTakenList.add(CS2103T);
        ModuleTaken editedAlice = new ModuleTakenBuilder(CS2103T)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueModuleTakenList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueModuleTakenList.add(CS2103T);
        thrown.expect(DuplicatePersonException.class);
        uniqueModuleTakenList.add(CS2103T);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.setPerson(null, CS2103T);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.setPerson(CS2103T, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueModuleTakenList.setPerson(CS2103T, CS2103T);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueModuleTakenList.add(CS2103T);
        uniqueModuleTakenList.setPerson(CS2103T, CS2103T);
        UniqueModuleTakenList expectedUniqueModuleTakenList = new UniqueModuleTakenList();
        expectedUniqueModuleTakenList.add(CS2103T);
        assertEquals(expectedUniqueModuleTakenList, uniqueModuleTakenList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueModuleTakenList.add(CS2103T);
        ModuleTaken editedAlice = new ModuleTakenBuilder(CS2103T)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueModuleTakenList.setPerson(CS2103T, editedAlice);
        UniqueModuleTakenList expectedUniqueModuleTakenList = new UniqueModuleTakenList();
        expectedUniqueModuleTakenList.add(editedAlice);
        assertEquals(expectedUniqueModuleTakenList, uniqueModuleTakenList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueModuleTakenList.add(CS2103T);
        uniqueModuleTakenList.setPerson(CS2103T, DEFAULT_MODULE_CS1010);
        UniqueModuleTakenList expectedUniqueModuleTakenList = new UniqueModuleTakenList();
        expectedUniqueModuleTakenList.add(DEFAULT_MODULE_CS1010);
        assertEquals(expectedUniqueModuleTakenList, uniqueModuleTakenList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueModuleTakenList.add(CS2103T);
        uniqueModuleTakenList.add(DEFAULT_MODULE_CS1010);
        thrown.expect(DuplicatePersonException.class);
        uniqueModuleTakenList.setPerson(CS2103T, DEFAULT_MODULE_CS1010);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueModuleTakenList.remove(CS2103T);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueModuleTakenList.add(CS2103T);
        uniqueModuleTakenList.remove(CS2103T);
        UniqueModuleTakenList expectedUniqueModuleTakenList = new UniqueModuleTakenList();
        assertEquals(expectedUniqueModuleTakenList, uniqueModuleTakenList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.setPersons((UniqueModuleTakenList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueModuleTakenList.add(CS2103T);
        UniqueModuleTakenList expectedUniqueModuleTakenList = new UniqueModuleTakenList();
        expectedUniqueModuleTakenList.add(DEFAULT_MODULE_CS1010);
        uniqueModuleTakenList.setPersons(expectedUniqueModuleTakenList);
        assertEquals(expectedUniqueModuleTakenList, uniqueModuleTakenList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleTakenList.setPersons((List<ModuleTaken>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueModuleTakenList.add(CS2103T);
        List<ModuleTaken> moduleTakenList = Collections.singletonList(DEFAULT_MODULE_CS1010);
        uniqueModuleTakenList.setPersons(moduleTakenList);
        UniqueModuleTakenList expectedUniqueModuleTakenList = new UniqueModuleTakenList();
        expectedUniqueModuleTakenList.add(DEFAULT_MODULE_CS1010);
        assertEquals(expectedUniqueModuleTakenList, uniqueModuleTakenList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<ModuleTaken> listWithDuplicateModuleTakens = Arrays.asList(CS2103T, CS2103T);
        thrown.expect(DuplicatePersonException.class);
        uniqueModuleTakenList.setPersons(listWithDuplicateModuleTakens);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueModuleTakenList.asUnmodifiableObservableList().remove(0);
    }
}
