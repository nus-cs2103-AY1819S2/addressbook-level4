package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalModuleTaken.CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalAddressBook;

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
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.exceptions.DuplicatePersonException;
import seedu.address.testutil.ModuleTakenBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final GradTrak addressBook = new GradTrak();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getModulesTakenList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        GradTrak newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two moduleTakens with the same identity fields
        ModuleTaken editedAlice = new ModuleTakenBuilder(CS2103T)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<ModuleTaken> newModuleTakens = Arrays.asList(CS2103T, editedAlice);
        GradTrakStub newData = new GradTrakStub(newModuleTakens);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasModuleTaken(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasModuleTaken(CS2103T));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addModuleTaken(CS2103T);
        assertTrue(addressBook.hasModuleTaken(CS2103T));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addModuleTaken(CS2103T);
        ModuleTaken editedAlice = new ModuleTakenBuilder(CS2103T)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasModuleTaken(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getModulesTakenList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addModuleTaken(CS2103T);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addModuleTaken(CS2103T);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyGradTrak whose moduleTakens list can violate interface constraints.
     */
    private static class GradTrakStub implements ReadOnlyGradTrak {
        private final ObservableList<ModuleTaken> moduleTakens = FXCollections.observableArrayList();

        GradTrakStub(Collection<ModuleTaken> moduleTakens) {
            this.moduleTakens.setAll(moduleTakens);
        }

        @Override
        public ObservableList<ModuleTaken> getModulesTakenList() {
            return moduleTakens;
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
