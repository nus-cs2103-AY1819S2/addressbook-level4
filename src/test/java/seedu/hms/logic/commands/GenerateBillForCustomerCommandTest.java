package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertGenerateBillCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.ALICE_GAMES_WITH_CARL;
import static seedu.hms.testutil.TypicalCustomers.ALICE_GYM;
import static seedu.hms.testutil.TypicalCustomers.ALICE_SINGLE_ROOM;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BillManager;
import seedu.hms.model.BillModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code GenerateBillForCustomerCommand}.
 */
public class GenerateBillForCustomerCommandTest {
    private BillModel model = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BillModel expectedModel = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BillModel billModel = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private CustomerModel customerModel =
        new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());


    @Test
    public void equals() {

        Customer customer = new CustomerBuilder().build();

        BookingContainsPayerPredicate firstBookingContainsPayerPredicate =
            new BookingContainsPayerPredicate("Z4264533");

        final Predicate<Booking> firstBookingPredicate;
        firstBookingPredicate = firstBookingContainsPayerPredicate;

        billModel.updateFilteredBookingList(firstBookingPredicate);
        ObservableList<Booking> firstBookingObservableList = billModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> firstBookingBill =
            billModel.generateHashMapForBooking(firstBookingObservableList);

        ReservationContainsPayerPredicate firstReservationContainsPayerPredicate =
            new ReservationContainsPayerPredicate("Z4264533");

        final Predicate<Reservation> firstReservationPredicate;
        firstReservationPredicate = firstReservationContainsPayerPredicate;

        billModel.updateFilteredReservationList(firstReservationPredicate);
        ObservableList<Reservation> firstReservationObservableList = billModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> firstReservationBill =
            billModel.generateHashMapForReservation(firstReservationObservableList);

        Bill firstBill = new Bill(customer, firstBookingBill, firstReservationBill);

        GenerateBillForCustomerCommand generateBillFirstCommand =
            new GenerateBillForCustomerCommand(firstBookingContainsPayerPredicate,
                firstReservationContainsPayerPredicate, firstBill);

        BookingContainsPayerPredicate secondBookingContainsPayerPredicate =
            new BookingContainsPayerPredicate("A0176884J");

        final Predicate<Booking> secondBookingPredicate;
        secondBookingPredicate = secondBookingContainsPayerPredicate;

        billModel.updateFilteredBookingList(secondBookingPredicate);
        ObservableList<Booking> secondBookingObservableList = billModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> secondBookingBill =
            billModel.generateHashMapForBooking(secondBookingObservableList);

        ReservationContainsPayerPredicate secondReservationContainsPayerPredicate =
            new ReservationContainsPayerPredicate("A0176884J");

        final Predicate<Reservation> secondReservationPredicate;
        secondReservationPredicate = secondReservationContainsPayerPredicate;

        billModel.updateFilteredReservationList(secondReservationPredicate);
        ObservableList<Reservation> secondReservationObservableList = billModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> secondReservationBill =
            billModel.generateHashMapForReservation(secondReservationObservableList);

        Bill secondBill = new Bill(customer, secondBookingBill, secondReservationBill);

        GenerateBillForCustomerCommand generateBillSecondCommand =
            new GenerateBillForCustomerCommand(secondBookingContainsPayerPredicate,
                secondReservationContainsPayerPredicate, secondBill);

        // same object -> returns true
        assertTrue(generateBillFirstCommand.equals(generateBillFirstCommand));

        // same values -> returns true
        GenerateBillForCustomerCommand generateBillFirstCommandCopy =
            new GenerateBillForCustomerCommand(firstBookingContainsPayerPredicate,
                firstReservationContainsPayerPredicate, firstBill);
        assertTrue(generateBillFirstCommand.equals(generateBillFirstCommandCopy));

        // different types -> returns false
        assertFalse(generateBillFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(generateBillFirstCommand, null);

        // different bill -> returns false
        assertFalse(generateBillFirstCommand.equals(generateBillSecondCommand));
    }

    @Test
    public void executeOnlyIndexAliceTotalBillGenerated() {

        Customer customer = new CustomerBuilder().build();

        BookingContainsPayerPredicate bookingContainsPayerPredicate =
            preparePredicateOfPayerBooking(customer.getIdNum().toString());

        Predicate<Booking> bookingPredicate = bookingContainsPayerPredicate;
        expectedModel.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = expectedModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> bookingBill =
            expectedModel.generateHashMapForBooking(bookingObservableList);

        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            preparePredicateOfPayerReservation(customer.getIdNum().toString());
        Predicate<Reservation> reservationPredicate = reservationContainsPayerPredicate;
        expectedModel.updateFilteredReservationList(reservationPredicate);
        ObservableList<Reservation> reservationObservableList = expectedModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> reservationBill =
            expectedModel.generateHashMapForReservation(reservationObservableList);


        Bill bill = new Bill(customer, bookingBill, reservationBill);
        String expectedMessage = String.format(GenerateBillForCustomerCommand.MESSAGE_GENERATE_BILL_SUCCESS,
            bill);

        GenerateBillForCustomerCommand command = new GenerateBillForCustomerCommand(bookingContainsPayerPredicate,
            reservationContainsPayerPredicate, bill);

        assertGenerateBillCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_GYM, ALICE_GAMES_WITH_CARL), model.getFilteredBookingList());
        assertEquals(Arrays.asList(ALICE_SINGLE_ROOM), model.getFilteredReservationList());
    }

    /**
     * Parses {@code userInput} into a {@code BookingContainsPayerPredicate}.
     */
    private BookingContainsPayerPredicate preparePredicateOfPayerBooking(String userInput) {
        return new BookingContainsPayerPredicate(userInput);
    }

    /**
     * Parses {@code userInput} into a {@code BookingContainsPayerPredicate}.
     */
    private ReservationContainsPayerPredicate preparePredicateOfPayerReservation(String userInput) {
        return new ReservationContainsPayerPredicate(userInput);
    }

}
