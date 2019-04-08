package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueNricMapTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueNricMap uniqueNricMap = new UniqueNricMap();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.contains(null);
    }

    @Test
    public void contains_nricNotInList_returnsFalse() {
        assertFalse(uniqueNricMap.contains(ALICE.getNric()));
    }

    @Test
    public void contains_nricInList_returnsTrue() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        assertTrue(uniqueNricMap.contains(ALICE.getNric()));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueNricMap.contains(editedAlice.getNric()));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.add(null, null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueNricMap.add(ALICE.getNric(), ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.setPerson(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.setPerson(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueNricMap.setPerson(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        uniqueNricMap.setPerson(ALICE, ALICE);
        UniqueNricMap expectedUniqueNricMap = new UniqueNricMap();
        expectedUniqueNricMap.add(ALICE.getNric(), ALICE);
        assertEquals(expectedUniqueNricMap, uniqueNricMap);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueNricMap.setPerson(ALICE, editedAlice);
        UniqueNricMap expectedUniqueNricMap = new UniqueNricMap();
        expectedUniqueNricMap.add(editedAlice.getNric(), editedAlice);
        assertEquals(expectedUniqueNricMap, uniqueNricMap);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        uniqueNricMap.setPerson(ALICE, BOB);
        UniqueNricMap expectedUniqueNricMap = new UniqueNricMap();
        expectedUniqueNricMap.add(BOB.getNric(), BOB);
        assertEquals(expectedUniqueNricMap, uniqueNricMap);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        uniqueNricMap.add(BOB.getNric(), BOB);
        thrown.expect(DuplicatePersonException.class);
        uniqueNricMap.setPerson(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueNricMap.remove(ALICE.getNric());
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        uniqueNricMap.remove(ALICE.getNric());
        UniqueNricMap expectedUniqueNricMap = new UniqueNricMap();
        assertEquals(expectedUniqueNricMap, uniqueNricMap);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.setNricMap((UniqueNricMap) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        UniqueNricMap expectedUniqueNricMap = new UniqueNricMap();
        expectedUniqueNricMap.add(BOB.getNric(), BOB);
        uniqueNricMap.setNricMap(expectedUniqueNricMap);
        assertEquals(expectedUniqueNricMap, uniqueNricMap);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueNricMap.setNricMap((List<Person>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueNricMap.add(ALICE.getNric(), ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueNricMap.setNricMap(personList);
        UniqueNricMap expectedUniqueNricMap = new UniqueNricMap();
        expectedUniqueNricMap.add(BOB.getNric(), BOB);
        assertEquals(expectedUniqueNricMap, uniqueNricMap);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueNricMap.setNricMap(listWithDuplicatePersons);
    }
}
