package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AlbumTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    public final Album album = Album.getInstance();

    @Before
    public void init() {
        album.clearAlbum();
    }

    @Test
    public void constructor() {
        assertEquals(album.getImageList(), Collections.emptyList());
    }

    @Test
    public void a(){

    }

    //    @Test
    //    public void resetData_null_throwsNullPointerException() {
    //        thrown.expect(NullPointerException.class);
    //        addressBook.resetData(null);
    //    }
    //
    //    @Test
    //    public void resetData_withValidReadOnlyAddressBook_replacesData() {
    //        AddressBook newData = getTypicalAddressBook();
    //        addressBook.resetData(newData);
    //        assertEquals(newData, addressBook);
    //    }
    //
    //    @Test
    //    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
    //        // Two persons with the same identity fields
    //        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //                .build();
    //        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
    //        AddressBookStub newData = new AddressBookStub(newPersons);
    //
    //        thrown.expect(DuplicatePersonException.class);
    //        addressBook.resetData(newData);
    //    }
    //
    //    @Test
    //    public void hasPerson_nullPerson_throwsNullPointerException() {
    //        thrown.expect(NullPointerException.class);
    //        addressBook.hasPerson(null);
    //    }
    //
    //    @Test
    //    public void hasPerson_personNotInAddressBook_returnsFalse() {
    //        assertFalse(addressBook.hasPerson(ALICE));
    //    }
    //
    //    @Test
    //    public void hasPerson_personInAddressBook_returnsTrue() {
    //        addressBook.addPerson(ALICE);
    //        assertTrue(addressBook.hasPerson(ALICE));
    //    }
    //
    //    @Test
    //    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
    //        addressBook.addPerson(ALICE);
    //        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //                .build();
    //        assertTrue(addressBook.hasPerson(editedAlice));
    //    }
    //
    //    @Test
    //    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
    //        thrown.expect(UnsupportedOperationException.class);
    //        addressBook.getPersonList().remove(0);
    //    }
    //
    //    @Test
    //    public void addListener_withInvalidationListener_listenerAdded() {
    //        SimpleIntegerProperty counter = new SimpleIntegerProperty();
    //        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
    //        addressBook.addListener(listener);
    //        addressBook.addPerson(ALICE);
    //        assertEquals(1, counter.get());
    //    }
    //
    //    @Test
    //    public void removeListener_withInvalidationListener_listenerRemoved() {
    //        SimpleIntegerProperty counter = new SimpleIntegerProperty();
    //        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
    //        addressBook.addListener(listener);
    //        addressBook.removeListener(listener);
    //        addressBook.addPerson(ALICE);
    //        assertEquals(0, counter.get());
    //    }
    //
    //    /**
    //     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
    //     */
    //    private static class AddressBookStub implements ReadOnlyAddressBook {
    //        private final ObservableList<Person> persons = FXCollections.observableArrayList();
    //
    //        AddressBookStub(Collection<Person> persons) {
    //            this.persons.setAll(persons);
    //        }
    //
    //        @Override
    //        public ObservableList<Person> getPersonList() {
    //            return persons;
    //        }
    //
    //        @Override
    //        public void addListener(InvalidationListener listener) {
    //            throw new AssertionError("This method should not be called.");
    //        }
    //
    //        @Override
    //        public void removeListener(InvalidationListener listener) {
    //            throw new AssertionError("This method should not be called.");
    //        }
    //    }
}
