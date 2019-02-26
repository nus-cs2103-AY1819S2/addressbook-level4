package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCustomer;

import org.junit.Test;

import guitests.guihandles.CustomerCardHandle;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.CustomerBuilder;

public class CustomerCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Customer customerWithNoTags = new CustomerBuilder().withTags(new String[0]).build();
        CustomerCard customerCard = new CustomerCard(customerWithNoTags, 1);
        uiPartRule.setUiPart(customerCard);
        assertCardDisplay(customerCard, customerWithNoTags, 1);

        // with tags
        Customer customerWithTags = new CustomerBuilder().build();
        customerCard = new CustomerCard(customerWithTags, 2);
        uiPartRule.setUiPart(customerCard);
        assertCardDisplay(customerCard, customerWithTags, 2);
    }

    @Test
    public void equals() {
        Customer customer = new CustomerBuilder().build();
        CustomerCard customerCard = new CustomerCard(customer, 0);

        // same customer, same index -> returns true
        CustomerCard copy = new CustomerCard(customer, 0);
        assertTrue(customerCard.equals(copy));

        // same object -> returns true
        assertTrue(customerCard.equals(customerCard));

        // null -> returns false
        assertFalse(customerCard == null);

        // different types -> returns false
        assertFalse(customerCard.equals(0));

        // different customer, same index -> returns false
        Customer differentCustomer = new CustomerBuilder().withName("differentName").build();
        assertFalse(customerCard.equals(new CustomerCard(differentCustomer, 0)));

        // same customer, different index -> returns false
        assertFalse(customerCard.equals(new CustomerCard(customer, 1)));
    }

    /**
     * Asserts that {@code customerCard} displays the details of {@code expectedCustomer} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CustomerCard customerCard, Customer expectedCustomer, int expectedId) {
        guiRobot.pauseForHuman();

        CustomerCardHandle customerCardHandle = new CustomerCardHandle(customerCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", customerCardHandle.getId());

        // verify customer details are displayed correctly
        assertCardDisplaysCustomer(expectedCustomer, customerCardHandle);
    }
}
