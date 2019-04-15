package seedu.hms.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.BookingCardHandle;
import guitests.guihandles.CustomerCardHandle;
import guitests.guihandles.CustomerListPanelHandle;
import guitests.guihandles.ReservationCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCustomerCardEquals(CustomerCardHandle expectedCard, CustomerCardHandle actualCard) {
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
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertBookingCardEquals(BookingCardHandle expectedCard, BookingCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getServiceType(), actualCard.getServiceType());
        assertEquals(expectedCard.getComments(), actualCard.getComments());
        assertEquals(expectedCard.getPayerName(), actualCard.getPayerName());
        assertEquals(expectedCard.getIdNum(), actualCard.getIdNum());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertReservationCardEquals(ReservationCardHandle expectedCard,
                                                   ReservationCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getRoomType(), actualCard.getRoomType());
        assertEquals(expectedCard.getComments(), actualCard.getComments());
        assertEquals(expectedCard.getPayerName(), actualCard.getPayerName());
        assertEquals(expectedCard.getIdNum(), actualCard.getIdNum());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCustomer}.
     */
    public static void assertCustomerCardDisplaysCustomer(Customer expectedCustomer, CustomerCardHandle actualCard) {
        assertEquals(expectedCustomer.getName().fullName, actualCard.getName());
        assertEquals("Phone: " + expectedCustomer.getPhone().value, actualCard.getPhone());
        assertEquals("Date of Birth: " + expectedCustomer.getDateOfBirth().value, actualCard.getDateOfBirth());
        assertEquals("Email: " + expectedCustomer.getEmail().value, actualCard.getEmail());
        assertEquals("Identification No: " + expectedCustomer.getIdNum().value, actualCard.getIdNum());
        assertEquals("Address: " + expectedCustomer.getAddress().value, actualCard.getAddress());
        assertEquals(expectedCustomer.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
            actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCustomer}.
     */
    public static void assertBookingCardDisplaysBooking(Booking expectedBooking, BookingCardHandle actualCard) {
        assertEquals("Payer Name: " + expectedBooking.getPayer().getName().fullName, actualCard.getPayerName());
        assertEquals(expectedBooking.getService().getName(), actualCard.getServiceType());
        assertEquals("Time: " + expectedBooking.getTiming().toString(), actualCard.getTime());
        assertEquals("Payer Phone: " + expectedBooking.getPayer().getPhone().value, actualCard.getPhone());
        assertEquals("Payer ID: " + expectedBooking.getPayer().getIdNum().value, actualCard.getIdNum());
        assertEquals(expectedBooking.getComment().orElse("No comment"), actualCard.getComments());
    }


    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCustomer}.
     */
    public static void assertReservationCardDisplaysReservation(Reservation expectedReservation,
                                                                ReservationCardHandle actualCard) {
        assertEquals("Payer Name: " + expectedReservation.getPayer().getName().fullName, actualCard.getPayerName());
        assertEquals(expectedReservation.getRoom().getName(), actualCard.getRoomType());
        assertEquals("Date: " + expectedReservation.getDates().toString(), actualCard.getDate());
        assertEquals("Payer Phone: " + expectedReservation.getPayer().getPhone().value, actualCard.getPhone());
        assertEquals("Payer ID: " + expectedReservation.getPayer().getIdNum().value, actualCard.getIdNum());
        assertEquals(expectedReservation.getComment().orElse("No comment"), actualCard.getComments());
    }


    /**
     * Asserts that the list in {@code customerListPanelHandle} displays the details of {@code customers} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CustomerListPanelHandle customerListPanelHandle, Customer... customers) {
        for (int i = 0; i < customers.length; i++) {
            customerListPanelHandle.navigateToCard(i);
            assertCustomerCardDisplaysCustomer(customers[i], customerListPanelHandle.getCustomerCardHandle(i));
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
