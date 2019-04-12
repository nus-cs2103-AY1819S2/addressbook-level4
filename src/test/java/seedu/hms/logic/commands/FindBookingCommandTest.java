package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.commons.core.Messages.MESSAGE_BOOKINGS_LISTED_OVERVIEW;
import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.ALICE_GAMES_WITH_CARL;
import static seedu.hms.testutil.TypicalCustomers.ALICE_GYM;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.util.TimeRange;

/**
 * Contains integration tests (interaction with the Model) for {@code FindBookingCommand}.
 */
public class FindBookingCommandTest {
    private BookingModel model = new BookingManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private BookingModel expectedModel = new BookingManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Booking> bookingPredicate;

    @Test
    public void equals() {
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

        FindBookingCommand findFirstCommand = new FindBookingCommand(firstPredicateOfPayer, firstPredicateOfType,
            firstPredicateOfTiming);
        FindBookingCommand findSecondCommand = new FindBookingCommand(secondPredicateOfPayer, secondPredicateOfType,
            secondPredicateOfTiming);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindBookingCommand findFirstCommandCopy = new FindBookingCommand(firstPredicateOfPayer, firstPredicateOfType,
            firstPredicateOfTiming);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different booking -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_allBookingsFound() {
        String expectedMessage = String.format(MESSAGE_BOOKINGS_LISTED_OVERVIEW, model.getFilteredBookingList().size());
        BookingWithTypePredicate bookingWithTypePredicate = preparePredicateOfType("");
        BookingWithinTimePredicate bookingWithinTimePredicate = preparePredicateOfTiming(" ");
        BookingContainsPayerPredicate bookingContainsPayerPredicate = preparePredicateOfPayer("");
        FindBookingCommand command = new FindBookingCommand(bookingContainsPayerPredicate, bookingWithTypePredicate,
            bookingWithinTimePredicate);
        bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested);
        expectedModel.updateFilteredBookingList(bookingPredicate);
        assertBookingCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_GYM, ALICE_GAMES_WITH_CARL), model.getFilteredBookingList());
    }

    @Test
    public void execute_multipleKeywords_particularBookingFound() {
        String expectedMessage = String.format(MESSAGE_BOOKINGS_LISTED_OVERVIEW, 1);
        BookingWithTypePredicate bookingWithTypePredicate = preparePredicateOfType("GYM");
        BookingWithinTimePredicate bookingWithinTimePredicate = preparePredicateOfTiming("14-15");
        BookingContainsPayerPredicate bookingContainsPayerPredicate = preparePredicateOfPayer("");
        FindBookingCommand command = new FindBookingCommand(bookingContainsPayerPredicate, bookingWithTypePredicate,
            bookingWithinTimePredicate);
        bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested);
        expectedModel.updateFilteredBookingList(bookingPredicate);
        assertBookingCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
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
