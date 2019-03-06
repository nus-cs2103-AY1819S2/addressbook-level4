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

public class CustomerManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CustomerManager customerManager = new CustomerManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), customerManager.getUserPrefs());
        assertEquals(new GuiSettings(), customerManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(customerManager.getAddressBook()));
        assertEquals(null, customerManager.getSelectedCustomer());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        customerManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        customerManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, customerManager.getUserPrefs());

        // Modifying userPrefs should not modify customerManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, customerManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        customerManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        customerManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, customerManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        customerManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        customerManager.setAddressBookFilePath(path);
        assertEquals(path, customerManager.getAddressBookFilePath());
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        customerManager.hasCustomer(null);
    }

    @Test
    public void hasCustomer_customerNotInAddressBook_returnsFalse() {
        assertFalse(customerManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInAddressBook_returnsTrue() {
        customerManager.addCustomer(ALICE);
        assertTrue(customerManager.hasCustomer(ALICE));
    }

    @Test
    public void deleteCustomer_customerIsSelectedAndFirstCustomerInFilteredCustomerList_selectionCleared() {
        customerManager.addCustomer(ALICE);
        customerManager.setSelectedCustomer(ALICE);
        customerManager.deleteCustomer(ALICE);
        assertEquals(null, customerManager.getSelectedCustomer());
    }

    @Test
    public void deleteCustomer_customerIsSelectedAndSecondCustomerInFilteredCustomerList_firstCustomerSelected() {
        customerManager.addCustomer(ALICE);
        customerManager.addCustomer(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), customerManager.getFilteredCustomerList());
        customerManager.setSelectedCustomer(BOB);
        customerManager.deleteCustomer(BOB);
        assertEquals(ALICE, customerManager.getSelectedCustomer());
    }

    @Test
    public void setCustomer_customerIsSelected_selectedCustomerUpdated() {
        customerManager.addCustomer(ALICE);
        customerManager.setSelectedCustomer(ALICE);
        Customer updatedAlice = new CustomerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        customerManager.setCustomer(ALICE, updatedAlice);
        assertEquals(updatedAlice, customerManager.getSelectedCustomer());
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        customerManager.getFilteredCustomerList().remove(0);
    }

    @Test
    public void setSelectedCustomer_customerNotInFilteredCustomerList_throwsCustomerNotFoundException() {
        thrown.expect(CustomerNotFoundException.class);
        customerManager.setSelectedCustomer(ALICE);
    }

    @Test
    public void setSelectedCustomer_customerInFilteredCustomerList_setsSelectedCustomer() {
        customerManager.addCustomer(ALICE);
        assertEquals(Collections.singletonList(ALICE), customerManager.getFilteredCustomerList());
        customerManager.setSelectedCustomer(ALICE);
        assertEquals(ALICE, customerManager.getSelectedCustomer());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        customerManager = new CustomerManager(new VersionedAddressBook(addressBook), userPrefs);
        CustomerManager customerManagerCopy = new CustomerManager(new VersionedAddressBook(addressBook), userPrefs);
        assertTrue(customerManager.equals(customerManagerCopy));

        // same object -> returns true
        assertTrue(customerManager.equals(customerManager));

        // null -> returns false
        assertFalse(customerManager == null);

        // different types -> returns false
        assertFalse(customerManager.equals(5));

        // different addressBook -> returns false
        assertFalse(customerManager.equals(new CustomerManager(new VersionedAddressBook(differentAddressBook),
            userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        customerManager.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(customerManager.equals(new CustomerManager(new VersionedAddressBook(addressBook), userPrefs)));

        // resets customerManager to initial state for upcoming tests
        customerManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(customerManager.equals(new CustomerManager(new VersionedAddressBook(addressBook),
            differentUserPrefs)));
    }
}
