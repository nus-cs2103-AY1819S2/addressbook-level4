package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.commons.core.Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW;
import static seedu.hms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.CARL;
import static seedu.hms.testutil.TypicalCustomers.ELLE;
import static seedu.hms.testutil.TypicalCustomers.FIONA;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.customer.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindNameCommand}.
 */
public class FindNameCommandTest {
    private CustomerModel model = new CustomerManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CustomerModel expectedModel = new CustomerManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNameCommand findFirstCommand = new FindNameCommand(firstPredicate);
        FindNameCommand findSecondCommand = new FindNameCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindNameCommand findFirstCommandCopy = new FindNameCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCustomerFound() {
        String expectedMessage = String.format(MESSAGE_CUSTOMERS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindNameCommand command = new FindNameCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleKeywords_multipleCustomersFound() {
        String expectedMessage = String.format(MESSAGE_CUSTOMERS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindNameCommand command = new FindNameCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCustomerList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
