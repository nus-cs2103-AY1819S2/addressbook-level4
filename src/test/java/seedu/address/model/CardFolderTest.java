package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalCardFolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class CardFolderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CardFolder cardFolder = new CardFolder();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cardFolder.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        cardFolder.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCardFolder_replacesData() {
        CardFolder newData = getTypicalCardFolder();
        cardFolder.resetData(newData);
        assertEquals(newData, cardFolder);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        CardFolderStub newData = new CardFolderStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        cardFolder.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        cardFolder.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInCardFolder_returnsFalse() {
        assertFalse(cardFolder.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInCardFolder_returnsTrue() {
        cardFolder.addPerson(ALICE);
        assertTrue(cardFolder.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInCardFolder_returnsTrue() {
        cardFolder.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(cardFolder.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        cardFolder.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        cardFolder.addListener(listener);
        cardFolder.addPerson(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        cardFolder.addListener(listener);
        cardFolder.removeListener(listener);
        cardFolder.addPerson(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyCardFolder whose persons list can violate interface constraints.
     */
    private static class CardFolderStub implements ReadOnlyCardFolder {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        CardFolderStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
