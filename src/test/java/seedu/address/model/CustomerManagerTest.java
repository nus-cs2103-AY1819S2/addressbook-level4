package seedu.hms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.BENSON;
import static seedu.hms.testutil.TypicalCustomers.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.commons.core.GuiSettings;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.NameContainsKeywordsPredicate;
import seedu.hms.model.customer.exceptions.CustomerNotFoundException;
import seedu.hms.testutil.CustomerBuilder;
import seedu.hms.testutil.HotelManagementSystemBuilder;

public class CustomerManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CustomerManager customerManager = new CustomerManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), customerManager.getUserPrefs());
        assertEquals(new GuiSettings(), customerManager.getGuiSettings());
        assertEquals(new HotelManagementSystem(),
            new HotelManagementSystem(customerManager.getHotelManagementSystem()));
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
        userPrefs.setHotelManagementSystemFilePath(Paths.get("hms/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        customerManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, customerManager.getUserPrefs());

        // Modifying userPrefs should not modify customerManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setHotelManagementSystemFilePath(Paths.get("new/hms/book/file/path"));
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
    public void setHotelManagementSystemFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        customerManager.setHotelManagementSystemFilePath(null);
    }

    @Test
    public void setHotelManagementSystemFilePath_validPath_setsHotelManagementSystemFilePath() {
        Path path = Paths.get("hms/book/file/path");
        customerManager.setHotelManagementSystemFilePath(path);
        assertEquals(path, customerManager.getHotelManagementSystemFilePath());
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        customerManager.hasCustomer(null);
    }

    @Test
    public void hasCustomer_customerNotInHotelManagementSystem_returnsFalse() {
        assertFalse(customerManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInHotelManagementSystem_returnsTrue() {
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
        HotelManagementSystem hotelManagementSystem =
            new HotelManagementSystemBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        HotelManagementSystem differentHotelManagementSystem = new HotelManagementSystem();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        customerManager = new CustomerManager(new VersionedHotelManagementSystem(hotelManagementSystem), userPrefs);
        CustomerManager customerManagerCopy =
            new CustomerManager(new VersionedHotelManagementSystem(hotelManagementSystem), userPrefs);
        assertTrue(customerManager.equals(customerManagerCopy));

        // same object -> returns true
        assertTrue(customerManager.equals(customerManager));

        // null -> returns false
        assertFalse(customerManager == null);

        // different types -> returns false
        assertFalse(customerManager.equals(5));

        // different hotelManagementSystem -> returns false
        assertFalse(customerManager
            .equals(new CustomerManager(new VersionedHotelManagementSystem(differentHotelManagementSystem),
                userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        customerManager.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(customerManager
            .equals(new CustomerManager(new VersionedHotelManagementSystem(hotelManagementSystem), userPrefs)));

        // resets customerManager to initial state for upcoming tests
        customerManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setHotelManagementSystemFilePath(Paths.get("differentFilePath"));
        assertFalse(customerManager
            .equals(new CustomerManager(new VersionedHotelManagementSystem(hotelManagementSystem),
                differentUserPrefs)));
    }
}
