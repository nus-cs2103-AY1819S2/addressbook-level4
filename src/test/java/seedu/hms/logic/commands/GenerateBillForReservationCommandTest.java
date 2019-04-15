package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertGenerateBillCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.ALICE_SINGLE_ROOM;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillManager;
import seedu.hms.model.BillModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.DateRange;
import seedu.hms.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code GenerateBillForReservationCommand}.
 */
public class GenerateBillForReservationCommandTest {
    private BillModel model = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BillModel expectedModel = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BillModel billModel = new BillManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Reservation> reservationPredicate;
    private Calendar currentDate = Calendar.getInstance();
    private Calendar afterOneYearCurrentDate = Calendar.getInstance();

    @Test
    public void equals() throws ParseException {
        try {
            Customer customer = new CustomerBuilder().build();

            ReservationContainsPayerPredicate firstPredicateOfPayer =
                new ReservationContainsPayerPredicate("Z4264533");
            ReservationContainsPayerPredicate secondPredicateOfPayer =
                new ReservationContainsPayerPredicate("G1739843T");
            ReservationWithTypePredicate firstPredicateOfType =
                new ReservationWithTypePredicate("GYM");
            ReservationWithTypePredicate secondPredicateOfType =
                new ReservationWithTypePredicate("SPA");
            DateRange firstDateRange = new DateRange("14/05/2019", "17/05/2019");
            DateRange secondDateRange = new DateRange("14/06/2019", "17/06/2019");

            ReservationWithDatePredicate firstPredicateOfDate =
                new ReservationWithDatePredicate(firstDateRange);

            ReservationWithDatePredicate secondPredicateOfDate = new ReservationWithDatePredicate(secondDateRange);

            final Predicate<Reservation> firstReservationPredicate;
            firstReservationPredicate = (reservationTested) -> firstPredicateOfPayer.test(reservationTested)
                && firstPredicateOfType.test(reservationTested)
                && firstPredicateOfDate.test(reservationTested);
            billModel.updateFilteredReservationList(firstReservationPredicate);
            ObservableList<Reservation> firstReservationObservableList = billModel.getFilteredReservationList();
            HashMap<RoomType, Pair<Double, Long>> firstReservationBill =
                billModel.generateHashMapForReservation(firstReservationObservableList);

            Bill firstBill = new Bill(customer, new HashMap<>(), firstReservationBill);

            GenerateBillForReservationCommand generateBillFirstCommand =
                new GenerateBillForReservationCommand(firstPredicateOfPayer, firstPredicateOfType,
                    firstPredicateOfDate, firstBill);

            final Predicate<Reservation> secondReservationPredicate;
            secondReservationPredicate = (reservationTested) -> secondPredicateOfPayer.test(reservationTested)
                && secondPredicateOfType.test(reservationTested);
            billModel.updateFilteredReservationList(secondReservationPredicate);
            ObservableList<Reservation> secondReservationObservableList = billModel.getFilteredReservationList();
            HashMap<RoomType, Pair<Double, Long>> secondReservationBill =
                billModel.generateHashMapForReservation(secondReservationObservableList);

            Bill secondBill = new Bill(customer, new HashMap<>(), secondReservationBill);

            GenerateBillForReservationCommand generateBillSecondCommand =
                new GenerateBillForReservationCommand(secondPredicateOfPayer, secondPredicateOfType,
                    secondPredicateOfDate, secondBill);


            // same object -> returns true
            assertTrue(generateBillFirstCommand.equals(generateBillFirstCommand));

            // same values -> returns true
            GenerateBillForReservationCommand generateBillFirstCommandCopy =
                new GenerateBillForReservationCommand(firstPredicateOfPayer, firstPredicateOfType,
                    firstPredicateOfDate, firstBill);
            assertTrue(generateBillFirstCommand.equals(generateBillFirstCommandCopy));

            // different types -> returns false
            assertFalse(generateBillFirstCommand.equals(1));

            // null -> returns false
            assertNotEquals(generateBillFirstCommand, null);

            // different customer -> returns false
            assertFalse(generateBillFirstCommand.equals(generateBillSecondCommand));
        } catch (ParseException e) {
            throw new ParseException("Date should be after current date and within one year after current date");
        }

    }

    @Test
    public void executeOnlyIndexZeroKeywordsAllAliceReservationsBillGenerated() {

        Customer customer = new CustomerBuilder().build();

        String expectedMessage =
            String.format(GenerateBillForReservationCommand.MESSAGE_GENERATE_BILL_FOR_RESERVATION_SUCCESS,
                customer.getName());

        for (int i = 0; i < 15; i++) {
            afterOneYearCurrentDate.setTimeInMillis(
                afterOneYearCurrentDate.getTimeInMillis() + 20 * 24 * 60 * 60 * 1000);
        }
        ReservationWithDatePredicate reservationWithDatePredicate = preparePredicateOfDate(new DateRange(
            currentDate, afterOneYearCurrentDate));

        ReservationWithTypePredicate reservationWithTypePredicate = preparePredicateOfType("");
        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            preparePredicateOfPayer(customer.getIdNum().toString());

        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        expectedModel.updateFilteredReservationList(reservationPredicate);
        ObservableList<Reservation> reservationObservableList = expectedModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> reservationBill =
            expectedModel.generateHashMapForReservation(reservationObservableList);

        Bill bill = new Bill(customer, new HashMap<>(), reservationBill);

        GenerateBillForReservationCommand command =
            new GenerateBillForReservationCommand(reservationContainsPayerPredicate,
                reservationWithTypePredicate, reservationWithDatePredicate, bill);


        assertGenerateBillCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_SINGLE_ROOM), model.getFilteredReservationList());
    }

    @Test
    public void executeIndexMultipleKeywordsReservationBillGenerated() {

        Customer customer = new CustomerBuilder().build();

        String expectedMessage =
            String.format(GenerateBillForReservationCommand.MESSAGE_GENERATE_BILL_FOR_RESERVATION_SUCCESS,
                customer.getName());

        ReservationWithTypePredicate reservationWithTypePredicate = preparePredicateOfType("SINGLE ROOM");
        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            preparePredicateOfPayer(customer.getIdNum().toString());

        for (int i = 0; i < 15; i++) {
            afterOneYearCurrentDate.setTimeInMillis(
                afterOneYearCurrentDate.getTimeInMillis() + 20 * 24 * 60 * 60 * 1000);
        }
        ReservationWithDatePredicate reservationWithDatePredicate = preparePredicateOfDate(new DateRange(
            currentDate, afterOneYearCurrentDate));

        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        expectedModel.updateFilteredReservationList(reservationPredicate);
        ObservableList<Reservation> reservationObservableList = expectedModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> reservationBill =
            expectedModel.generateHashMapForReservation(reservationObservableList);

        Bill bill = new Bill(customer, new HashMap<>(), reservationBill);

        GenerateBillForReservationCommand command =
            new GenerateBillForReservationCommand(reservationContainsPayerPredicate,
                reservationWithTypePredicate, reservationWithDatePredicate, bill);

        assertGenerateBillCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_SINGLE_ROOM), model.getFilteredReservationList());
    }


    /**
     * Parses {@code userInput} into a {@code ReservationContainsPayerPredicate}.
     */
    private ReservationContainsPayerPredicate preparePredicateOfPayer(String userInput) {
        return new ReservationContainsPayerPredicate(userInput);
    }

    /**
     * Parses {@code userInput} into a {@code ReservationWithTypePredicate}.
     */
    private ReservationWithTypePredicate preparePredicateOfType(String userInput) {
        return new ReservationWithTypePredicate(userInput);
    }

    /**
     * Parses {@code userInput} into a {@code ReservationContainsPayerPredicate}.
     */
    private ReservationWithDatePredicate preparePredicateOfDate(DateRange dateRange) {
        return new ReservationWithDatePredicate(dateRange);
    }

}
