package seedu.hms.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CustomerCardHandle;
import guitests.guihandles.CustomerListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.hms.model.customer.Customer;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(CustomerCardHandle expectedCard, CustomerCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getDateOfBirth(), actualCard.getDateOfBirth());
        assertEquals(expectedCard.getIdNum(), actualCard.getIdNum());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCustomer}.
     */
    public static void assertCardDisplaysCustomer(Customer expectedCustomer, CustomerCardHandle actualCard) {
        assertEquals(expectedCustomer.getName().fullName, actualCard.getName());
        assertEquals(expectedCustomer.getPhone().value, actualCard.getPhone());
        assertEquals(expectedCustomer.getDateOfBirth().value, actualCard.getDateOfBirth());
        assertEquals(expectedCustomer.getEmail().value, actualCard.getEmail());
        assertEquals(expectedCustomer.getIdNum().value, actualCard.getIdNum());
        assertEquals(expectedCustomer.getAddress().value, actualCard.getAddress());
        assertEquals(expectedCustomer.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
            actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code customerListPanelHandle} displays the details of {@code customers} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CustomerListPanelHandle customerListPanelHandle, Customer... customers) {
        for (int i = 0; i < customers.length; i++) {
            customerListPanelHandle.navigateToCard(i);
            assertCardDisplaysCustomer(customers[i], customerListPanelHandle.getCustomerCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code customerListPanelHandle} displays the details of {@code customers} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CustomerListPanelHandle customerListPanelHandle, List<Customer> customers) {
        assertListMatching(customerListPanelHandle, customers.toArray(new Customer[0]));
    }

    /**
     * Asserts the size of the list in {@code customerListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CustomerListPanelHandle customerListPanelHandle, int size) {
        int numberOfPeople = customerListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
