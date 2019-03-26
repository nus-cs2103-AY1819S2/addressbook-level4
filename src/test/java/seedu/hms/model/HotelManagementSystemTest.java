package seedu.hms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.util.ArrayList;
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
import seedu.hms.model.booking.Booking;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.exceptions.DuplicateCustomerException;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.testutil.CustomerBuilder;

public class HotelManagementSystemTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final HotelManagementSystem hotelManagementSystem = new HotelManagementSystem();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), hotelManagementSystem.getCustomerList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        hotelManagementSystem.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyHotelManagementSystem_replacesData() {
        HotelManagementSystem newData = new HotelManagementSystem(getTypicalHotelManagementSystem());
        hotelManagementSystem.resetData(newData);
        assertEquals(newData, hotelManagementSystem);
    }

    @Test
    public void resetData_withDuplicateCustomers_throwsDuplicateCustomerException() {
        // Two customers with the same identity fields
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        List<Customer> newCustomers = Arrays.asList(ALICE, editedAlice);
        HotelManagementSystemStub newData = new HotelManagementSystemStub(newCustomers, new ArrayList<>(),
            new ArrayList<>());

        thrown.expect(DuplicateCustomerException.class);
        hotelManagementSystem.resetData(newData);
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        hotelManagementSystem.hasCustomer(null);
    }

    @Test
    public void hasCustomer_customerNotInHotelManagementSystem_returnsFalse() {
        assertFalse(hotelManagementSystem.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInHotelManagementSystem_returnsTrue() {
        hotelManagementSystem.addCustomer(ALICE);
        assertTrue(hotelManagementSystem.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerWithSameIdentityFieldsInHotelManagementSystem_returnsTrue() {
        hotelManagementSystem.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(hotelManagementSystem.hasCustomer(editedAlice));
    }

    @Test
    public void getCustomerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        hotelManagementSystem.getCustomerList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        hotelManagementSystem.addListener(listener);
        hotelManagementSystem.addCustomer(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        hotelManagementSystem.addListener(listener);
        hotelManagementSystem.removeListener(listener);
        hotelManagementSystem.addCustomer(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyHotelManagementSystem whose customers list can violate interface constraints.
     */
    private static class HotelManagementSystemStub implements ReadOnlyHotelManagementSystem {
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();
        private final ObservableList<Booking> bookings = FXCollections.observableArrayList();
        private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        HotelManagementSystemStub(Collection<Customer> customers, Collection<Booking> bookings,
                                  Collection<Reservation> reservations) {
            this.customers.setAll(customers);
            this.bookings.setAll(bookings);
            this.reservations.setAll(reservations);
        }

        @Override
        public ObservableList<Customer> getCustomerList() {
            return customers;
        }

        @Override
        public ObservableList<Booking> getBookingList() {
            return bookings;
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
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
