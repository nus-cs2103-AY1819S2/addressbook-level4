package seedu.address.model.apparel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalApparels.BOBYIN;
import static seedu.address.testutil.TypicalApparels.SHIRT1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.apparel.exceptions.ApparelNotFoundException;
import seedu.address.model.apparel.exceptions.DuplicateApparelException;
import seedu.address.testutil.ApparelBuilder;

public class UniqueApparelListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueApparelList uniqueApparelList = new UniqueApparelList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueApparelList.contains(SHIRT1));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueApparelList.add(SHIRT1);
        assertTrue(uniqueApparelList.contains(SHIRT1));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueApparelList.add(SHIRT1);
        Apparel editedAlice = new ApparelBuilder(SHIRT1).build();
        assertTrue(uniqueApparelList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueApparelList.add(SHIRT1);
        thrown.expect(DuplicateApparelException.class);
        uniqueApparelList.add(SHIRT1);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.setApparel(null, SHIRT1);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.setApparel(SHIRT1, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(ApparelNotFoundException.class);
        uniqueApparelList.setApparel(SHIRT1, SHIRT1);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueApparelList.add(SHIRT1);
        uniqueApparelList.setApparel(SHIRT1, SHIRT1);
        UniqueApparelList expectedUniqueApparelList = new UniqueApparelList();
        expectedUniqueApparelList.add(SHIRT1);
        assertEquals(expectedUniqueApparelList, uniqueApparelList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueApparelList.add(SHIRT1);
        Apparel editedAlice = new ApparelBuilder(SHIRT1).build();
        uniqueApparelList.setApparel(SHIRT1, editedAlice);
        UniqueApparelList expectedUniqueApparelList = new UniqueApparelList();
        expectedUniqueApparelList.add(editedAlice);
        assertEquals(expectedUniqueApparelList, uniqueApparelList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueApparelList.add(SHIRT1);
        uniqueApparelList.setApparel(SHIRT1, BOBYIN);
        UniqueApparelList expectedUniqueApparelList = new UniqueApparelList();
        expectedUniqueApparelList.add(BOBYIN);
        assertEquals(expectedUniqueApparelList, uniqueApparelList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueApparelList.add(SHIRT1);
        uniqueApparelList.add(BOBYIN);
        thrown.expect(DuplicateApparelException.class);
        uniqueApparelList.setApparel(SHIRT1, BOBYIN);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(ApparelNotFoundException.class);
        uniqueApparelList.remove(SHIRT1);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueApparelList.add(SHIRT1);
        uniqueApparelList.remove(SHIRT1);
        UniqueApparelList expectedUniqueApparelList = new UniqueApparelList();
        assertEquals(expectedUniqueApparelList, uniqueApparelList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.setApparels((UniqueApparelList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueApparelList.add(SHIRT1);
        UniqueApparelList expectedUniqueApparelList = new UniqueApparelList();
        expectedUniqueApparelList.add(BOBYIN);
        uniqueApparelList.setApparels(expectedUniqueApparelList);
        assertEquals(expectedUniqueApparelList, uniqueApparelList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueApparelList.setApparels((List<Apparel>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueApparelList.add(SHIRT1);
        List<Apparel> apparelList = Collections.singletonList(BOBYIN);
        uniqueApparelList.setApparels(apparelList);
        UniqueApparelList expectedUniqueApparelList = new UniqueApparelList();
        expectedUniqueApparelList.add(BOBYIN);
        assertEquals(expectedUniqueApparelList, uniqueApparelList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Apparel> listWithDuplicateApparels = Arrays.asList(SHIRT1, SHIRT1);
        thrown.expect(DuplicateApparelException.class);
        uniqueApparelList.setApparels(listWithDuplicateApparels);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueApparelList.asUnmodifiableObservableList().remove(0);
    }
}
