package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.BENSON;
import static seedu.address.testutil.TypicalCustomers.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.customer.exceptions.CustomerNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CustomerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedCustomer());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCustomer(null);
    }

    @Test
    public void hasCustomer_customerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInAddressBook_returnsTrue() {
        modelManager.addCustomer(ALICE);
        assertTrue(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void deleteCustomer_customerIsSelectedAndFirstCustomerInFilteredCustomerList_selectionCleared() {
        modelManager.addCustomer(ALICE);
        modelManager.setSelectedCustomer(ALICE);
        modelManager.deleteCustomer(ALICE);
        assertEquals(null, modelManager.getSelectedCustomer());
    }

    @Test
    public void deleteCustomer_customerIsSelectedAndSecondCustomerInFilteredCustomerList_firstCustomerSelected() {
        modelManager.addCustomer(ALICE);
        modelManager.addCustomer(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredCustomerList());
        modelManager.setSelectedCustomer(BOB);
        modelManager.deleteCustomer(BOB);
        assertEquals(ALICE, modelManager.getSelectedCustomer());
    }

    @Test
    public void setCustomer_customerIsSelected_selectedCustomerUpdated() {
        modelManager.addCustomer(ALICE);
        modelManager.setSelectedCustomer(ALICE);
        Customer updatedAlice = new CustomerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setCustomer(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedCustomer());
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCustomerList().remove(0);
    }

    @Test
    public void setSelectedCustomer_customerNotInFilteredCustomerList_throwsCustomerNotFoundException() {
        thrown.expect(CustomerNotFoundException.class);
        modelManager.setSelectedCustomer(ALICE);
    }

    @Test
    public void setSelectedCustomer_customerInFilteredCustomerList_setsSelectedCustomer() {
        modelManager.addCustomer(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredCustomerList());
        modelManager.setSelectedCustomer(ALICE);
        assertEquals(ALICE, modelManager.getSelectedCustomer());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
