package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertGenerateBillCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.ALICE_GAMES_WITH_CARL;
import static seedu.hms.testutil.TypicalCustomers.ALICE_GYM;
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
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;
import seedu.hms.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code GenerateBillForBookingCommand}.
 */
public class GenerateBillForBookingCommandTest {
    private BillModel model = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BillModel expectedModel = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BillModel billModel = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Booking> bookingPredicate;

    @Test
    public void equals() {

        Customer customer = new CustomerBuilder().build();

        BookingContainsPayerPredicate firstPredicateOfPayer =
            new BookingContainsPayerPredicate("Z4264533");
        BookingContainsPayerPredicate secondPredicateOfPayer =
            new BookingContainsPayerPredicate("G1739843T");
        BookingWithTypePredicate firstPredicateOfType =
            new BookingWithTypePredicate("GYM");
        BookingWithTypePredicate secondPredicateOfType =
            new BookingWithTypePredicate("SPA");
        BookingWithinTimePredicate firstPredicateOfTiming =
            new BookingWithinTimePredicate(new TimeRange(14, 16));
        BookingWithinTimePredicate secondPredicateOfTiming =
            new BookingWithinTimePredicate(new TimeRange(14, 18));

        final Predicate<Booking> firstBookingPredicate;
        firstBookingPredicate = (bookingTested) -> firstPredicateOfPayer.test(bookingTested)
            && firstPredicateOfType.test(bookingTested)
            && firstPredicateOfTiming.test(bookingTested);
        billModel.updateFilteredBookingList(firstBookingPredicate);
        ObservableList<Booking> firstBookingObservableList = billModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> firstBookingBill =
            billModel.generateHashMapForBooking(firstBookingObservableList);

        Bill firstBill = new Bill(customer, firstBookingBill, new HashMap<>());

        GenerateBillForBookingCommand generateBillFirstCommand =
            new GenerateBillForBookingCommand(firstPredicateOfPayer, firstPredicateOfType,
                firstPredicateOfTiming, firstBill);

        final Predicate<Booking> secondBookingPredicate;
        secondBookingPredicate = (bookingTested) -> secondPredicateOfPayer.test(bookingTested)
            && secondPredicateOfType.test(bookingTested)
            && secondPredicateOfTiming.test(bookingTested);
        billModel.updateFilteredBookingList(secondBookingPredicate);
        ObservableList<Booking> secondBookingObservableList = billModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> secondBookingBill =
            billModel.generateHashMapForBooking(secondBookingObservableList);

        Bill secondBill = new Bill(customer, secondBookingBill, new HashMap<>());

        GenerateBillForBookingCommand generateBillSecondCommand =
            new GenerateBillForBookingCommand(secondPredicateOfPayer, secondPredicateOfType,
                secondPredicateOfTiming, secondBill);


        // same object -> returns true
        assertTrue(generateBillFirstCommand.equals(generateBillFirstCommand));

        // same values -> returns true
        GenerateBillForBookingCommand generateBillFirstCommandCopy =
            new GenerateBillForBookingCommand(firstPredicateOfPayer, firstPredicateOfType,
                firstPredicateOfTiming, firstBill);
        assertTrue(generateBillFirstCommand.equals(generateBillFirstCommandCopy));

        // different types -> returns false
        assertFalse(generateBillFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(generateBillFirstCommand, null);

        // different bill -> returns false
        assertFalse(generateBillFirstCommand.equals(generateBillSecondCommand));
    }

    @Test
    public void executeOnlyIndexZeroKeywordsAllAliceBookingBillGenerated() {

        Customer customer = new CustomerBuilder().build();

        String expectedMessage = String.format(GenerateBillForBookingCommand.MESSAGE_GENERATE_BILL_SUCCESS,
            customer.getName());


        BookingWithTypePredicate bookingWithTypePredicate = preparePredicateOfType("");
        BookingWithinTimePredicate bookingWithinTimePredicate = preparePredicateOfTiming(" ");
        BookingContainsPayerPredicate bookingContainsPayerPredicate =
            preparePredicateOfPayer(customer.getIdNum().toString());

        bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested);
        expectedModel.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = expectedModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> bookingBill =
            expectedModel.generateHashMapForBooking(bookingObservableList);

        Bill bill = new Bill(customer, bookingBill, new HashMap<>());

        GenerateBillForBookingCommand command = new GenerateBillForBookingCommand(bookingContainsPayerPredicate,
            bookingWithTypePredicate,
            bookingWithinTimePredicate, bill);


        assertGenerateBillCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_GYM, ALICE_GAMES_WITH_CARL), model.getFilteredBookingList());
    }

    @Test
    public void executeIndexMultipleKeywordsBookingBillGenerated() {

        Customer customer = new CustomerBuilder().build();

        String expectedMessage = String.format(GenerateBillForBookingCommand.MESSAGE_GENERATE_BILL_SUCCESS,
            customer.getName());

        BookingWithTypePredicate bookingWithTypePredicate = preparePredicateOfType("");
        BookingWithinTimePredicate bookingWithinTimePredicate = preparePredicateOfTiming("14-15");
        BookingContainsPayerPredicate bookingContainsPayerPredicate =
            preparePredicateOfPayer(customer.getIdNum().toString());

        bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested);
        expectedModel.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = expectedModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> bookingBill =
            expectedModel.generateHashMapForBooking(bookingObservableList);

        Bill bill = new Bill(customer, bookingBill, new HashMap<>());

        GenerateBillForBookingCommand command = new GenerateBillForBookingCommand(bookingContainsPayerPredicate,
            bookingWithTypePredicate,
            bookingWithinTimePredicate, bill);

        assertGenerateBillCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_GYM), model.getFilteredBookingList());
    }


    /**
     * Parses {@code userInput} into a {@code BookingContainsPayerPredicate}.
     */
    private BookingContainsPayerPredicate preparePredicateOfPayer(String userInput) {
        return new BookingContainsPayerPredicate(userInput);
    }

    /**
     * Parses {@code userInput} into a {@code BookingWithinTimePredicate}.
     */
    private BookingWithinTimePredicate preparePredicateOfTiming(String userInput) {
        if ((" ").equals(userInput)) {
            return new BookingWithinTimePredicate(new TimeRange(0, 23));
        }
        String[] time = userInput.split("-");
        return new BookingWithinTimePredicate(new TimeRange(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
    }

    /**
     * Parses {@code userInput} into a {@code BookingWithTypePredicate}.
     */
    private BookingWithTypePredicate preparePredicateOfType(String userInput) {
        return new BookingWithTypePredicate(userInput);
    }

}
