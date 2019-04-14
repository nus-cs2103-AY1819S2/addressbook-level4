package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.commons.core.Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW;
import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.ALICE_SINGLE_ROOM;
import static seedu.hms.testutil.TypicalCustomers.BENSON_DOUBLE_ROOM;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.util.Arrays;
import java.util.Calendar;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;
import seedu.hms.model.util.DateRange;

/**
 * Contains integration tests (interaction with the Model) for {@code FindReservationCommand}.
 */
public class FindReservationCommandTest {
    private ReservationModel model = new ReservationManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private ReservationModel expectedModel = new ReservationManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Reservation> reservationPredicate;
    private Calendar currentDate = Calendar.getInstance();
    private Calendar afterOneYearCurrentDate = Calendar.getInstance();

    @Test
    public void equals() throws ParseException {
        try {
            ReservationContainsPayerPredicate firstPredicateOfPayer =
                new ReservationContainsPayerPredicate("Z4264533");
            ReservationContainsPayerPredicate secondPredicateOfPayer =
                new ReservationContainsPayerPredicate("G1739843T");
            ReservationWithTypePredicate firstPredicateOfType =
                new ReservationWithTypePredicate("SINGLE ROOM");
            ReservationWithTypePredicate secondPredicateOfType =
                new ReservationWithTypePredicate("DOUBLE ROOM");
            DateRange firstDateRange = new DateRange("14/05/2019", "17/05/2019");
            DateRange secondDateRange = new DateRange("14/06/2019", "17/06/2019");

            ReservationWithDatePredicate firstPredicateOfDate =
                new ReservationWithDatePredicate(firstDateRange);

            ReservationWithDatePredicate secondPredicateOfDate = new ReservationWithDatePredicate(secondDateRange);

            FindReservationCommand findFirstCommand = new FindReservationCommand(firstPredicateOfPayer,
                firstPredicateOfType, firstPredicateOfDate);
            FindReservationCommand findSecondCommand = new FindReservationCommand(secondPredicateOfPayer,
                secondPredicateOfType, secondPredicateOfDate);

            // same object -> returns true
            assertTrue(findFirstCommand.equals(findFirstCommand));

            // same values -> returns true
            FindReservationCommand findFirstCommandCopy = new FindReservationCommand(firstPredicateOfPayer,
                firstPredicateOfType, firstPredicateOfDate);
            assertTrue(findFirstCommandCopy.equals(findFirstCommand));

            // different types -> returns false
            assertFalse(findFirstCommand.equals(1));

            // null -> returns false
            assertNotEquals(findFirstCommand, null);

            // different reservation -> returns false
            assertFalse(findFirstCommand.equals(findSecondCommand));
        } catch (ParseException e) {
            throw new ParseException("Date should be after current date and within one year after current date");
        }

    }

    @Test
    public void execute_zeroKeywords_allReservationsFound() {
        String expectedMessage = String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
            model.getFilteredReservationList().size());
        ReservationWithTypePredicate reservationWithTypePredicate = preparePredicateOfType("");
        ReservationContainsPayerPredicate reservationContainsPayerPredicate = preparePredicateOfPayer("");

        for (int i = 0; i < 15; i++) {
            afterOneYearCurrentDate.setTimeInMillis(
                afterOneYearCurrentDate.getTimeInMillis() + 20 * 24 * 60 * 60 * 1000);
        }

        ReservationWithDatePredicate reservationWithDatePredicate = preparePredicateOfDate(new DateRange(
            currentDate, afterOneYearCurrentDate));
        FindReservationCommand command = new FindReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate, reservationWithDatePredicate);
        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        expectedModel.updateFilteredReservationList(reservationPredicate);
        assertReservationCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_SINGLE_ROOM, BENSON_DOUBLE_ROOM), model.getFilteredReservationList());
    }

    @Test
    public void execute_multipleKeywords_particularReservationFound() {
        String expectedMessage = String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW, 1);
        ReservationWithTypePredicate reservationWithTypePredicate = preparePredicateOfType("SINGLE ROOM");
        ReservationContainsPayerPredicate reservationContainsPayerPredicate = preparePredicateOfPayer("");

        for (int i = 0; i < 15; i++) {
            afterOneYearCurrentDate.setTimeInMillis(
                afterOneYearCurrentDate.getTimeInMillis() + 20 * 24 * 60 * 60 * 1000);
        }

        ReservationWithDatePredicate reservationWithDatePredicate = preparePredicateOfDate(new DateRange(
            currentDate, afterOneYearCurrentDate));
        FindReservationCommand command = new FindReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate, reservationWithDatePredicate);
        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        expectedModel.updateFilteredReservationList(reservationPredicate);
        assertReservationCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
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
     * Parses {@code userInput} into a {@code ReservationWithTypePredicate}.
     */
    private ReservationWithDatePredicate preparePredicateOfDate(DateRange dateRange) {
        return new ReservationWithDatePredicate(dateRange);
    }

}
