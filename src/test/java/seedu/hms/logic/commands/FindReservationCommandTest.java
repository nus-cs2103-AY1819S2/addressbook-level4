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
import java.util.function.Predicate;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindNameCommand}.
 */
public class FindReservationCommandTest {
    private ReservationModel model = new ReservationManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private ReservationModel expectedModel = new ReservationManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Reservation> reservationPredicate;

    @Test
    public void equals() {
        ReservationContainsPayerPredicate firstPredicateOfPayer =
            new ReservationContainsPayerPredicate("Z4264533");
        ReservationContainsPayerPredicate secondPredicateOfPayer =
            new ReservationContainsPayerPredicate("G1739843T");
        ReservationWithTypePredicate firstPredicateOfType =
            new ReservationWithTypePredicate("SINGLE ROOM");
        ReservationWithTypePredicate secondPredicateOfType =
            new ReservationWithTypePredicate("DOUBLE ROOM");


        FindReservationCommand findFirstCommand = new FindReservationCommand(firstPredicateOfPayer,
            firstPredicateOfType);
        FindReservationCommand findSecondCommand = new FindReservationCommand(secondPredicateOfPayer,
            secondPredicateOfType);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindReservationCommand findFirstCommandCopy = new FindReservationCommand(firstPredicateOfPayer,
            firstPredicateOfType);
        assertTrue(findFirstCommandCopy.equals(findFirstCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different customer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_allCustomerFound() {
        String expectedMessage = String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
            model.getFilteredReservationList().size());
        ReservationWithTypePredicate reservationWithTypePredicate = preparePredicateOfType("");
        ReservationContainsPayerPredicate reservationContainsPayerPredicate = preparePredicateOfPayer("");
        FindReservationCommand command = new FindReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate);
        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested);
        expectedModel.updateFilteredReservationList(reservationPredicate);
        assertReservationCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_SINGLE_ROOM, BENSON_DOUBLE_ROOM), model.getFilteredReservationList());
    }

    @Test
    public void execute_multipleKeywords_multipleCustomersFound() {
        String expectedMessage = String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW, 1);
        ReservationWithTypePredicate reservationWithTypePredicate = preparePredicateOfType("SINGLE ROOM");
        ReservationContainsPayerPredicate reservationContainsPayerPredicate = preparePredicateOfPayer("");
        FindReservationCommand command = new FindReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate);
        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested);
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

//    /**
//     * Parses {@code userInput} into a {@code ReservationWithinTimePredicate}.
//     */
//    private ReservationWithinTimePredicate preparePredicateOfTiming(String userInput) {
//        if (userInput.equals(" ")) {
//            return new ReservationWithinTimePredicate(new TimeRange(0, 23));
//        }
//        String time[] = userInput.split("-");
//        return new ReservationWithinTimePredicate(new TimeRange(Integer.parseInt(time[0]), Integer.parseInt
// (time[1])));
//    }


}
