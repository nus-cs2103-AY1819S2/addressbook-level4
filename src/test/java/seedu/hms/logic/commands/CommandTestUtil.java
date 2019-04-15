package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BillModel;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.Model;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.NameContainsKeywordsPredicate;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.TimeRange;
import seedu.hms.testutil.EditBookingDescriptorBuilder;
import seedu.hms.testutil.EditCustomerDescriptorBuilder;
import seedu.hms.testutil.EditReservationDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_DATE_OF_BIRTH_AMY = "28/05/1989";
    public static final String VALID_DATE_OF_BIRTH_BOB = "28/05/1986";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ID_AMY = "A33425541";
    public static final String VALID_ID_BOB = "A4566474T";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String DATE_OF_BIRTH_DESC_AMY = " " + PREFIX_DATE_OF_BIRTH + VALID_DATE_OF_BIRTH_AMY;
    public static final String DATE_OF_BIRTH_DESC_BOB = " " + PREFIX_DATE_OF_BIRTH + VALID_DATE_OF_BIRTH_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ID_DESC_AMY = " " + PREFIX_IDENTIFICATION_NUMBER + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_IDENTIFICATION_NUMBER + VALID_ID_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_DATE_OF_BIRTH_DESC = " " + PREFIX_DATE_OF_BIRTH + "112/5/2000"; //incorrect
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ID_DESC = " " + PREFIX_IDENTIFICATION_NUMBER + "911a"; // 'a' not allowed in
    // identificaiton numbers
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for hmses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCustomerCommand.EditCustomerDescriptor DESC_AMY;
    public static final EditCustomerCommand.EditCustomerDescriptor DESC_BOB;
    public static final EditBookingCommand.EditBookingDescriptor DESC_ALICE_SPA;
    public static final EditBookingCommand.EditBookingDescriptor DESC_CARL_SPA;
    public static final EditReservationCommand.EditReservationDescriptor DESC_ALICE_SINGLE_ROOM;
    public static final EditReservationCommand.EditReservationDescriptor DESC_CARL_SINGLE_ROOM;

    static {
        DESC_AMY = new EditCustomerDescriptorBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).withEmail(VALID_EMAIL_AMY)
            .withIdNum(VALID_ID_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).withEmail(VALID_EMAIL_BOB)
            .withIdNum(VALID_ID_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
        ServiceType spa = new ServiceType(30, new TimeRange(10, 20), "Spa", 10.0);
        DESC_ALICE_SPA = new EditBookingDescriptorBuilder().withService(spa).withTiming(11, 12)
            .withPayer(ALICE).withOtherUsers().withComment("AliceSPA").build();
        DESC_CARL_SPA = new EditBookingDescriptorBuilder().withService(spa).withTiming(12, 13)
            .withPayer(CARL).withOtherUsers().withComment("CarlSPA").build();
        RoomType singleRoom = new RoomType(100, "Single Room", 500.0);
        DESC_ALICE_SINGLE_ROOM = new EditReservationDescriptorBuilder().withRoom(singleRoom)
            .withDates("08/12/2019", "10/12/2019").withPayer(ALICE).withOtherUsers().withComment("AliceSingle").build();
        DESC_CARL_SINGLE_ROOM = new EditReservationDescriptorBuilder().withRoom(singleRoom)
            .withDates("08/12/2019", "10/12/2019").withPayer(CARL).withOtherUsers().withComment("CarlSingle").build();
    }

    /* -------------------------- CUSTOMER ----------------------- */

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult, CustomerModel expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, String, CustomerModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, CustomerModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the hms book, filtered customer list and selected customer in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, CustomerModel actualModel,
                                            CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        HotelManagementSystem expectedHotelManagementSystem =
            new HotelManagementSystem(actualModel.getHotelManagementSystem());
        List<Customer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCustomerList());
        Customer expectedSelectedCustomer = actualModel.getSelectedCustomer();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedHotelManagementSystem, actualModel.getHotelManagementSystem());
            assertEquals(expectedFilteredList, actualModel.getFilteredCustomerList());
            assertEquals(expectedSelectedCustomer, actualModel.getSelectedCustomer());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Deletes the first customer in {@code model}'s filtered list from {@code model}'s hms book.
     */
    public static void deleteFirstCustomer(CustomerModel model) {
        Customer firstCustomer = model.getFilteredCustomerList().get(0);
        model.deleteCustomer(firstCustomer);
        model.commitHotelManagementSystem();
    }

    /**
     * Updates {@code model}'s filtered list to show only the customer at the given {@code targetIndex} in the
     * {@code model}'s hms book.
     */
    public static void showCustomerAtIndex(CustomerModel model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCustomerList().size());

        Customer customer = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        final String[] splitName = customer.getName().fullName.split("\\s+");
        model.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCustomerList().size());
    }

    /* ------------------------------------- BOOKING -------------------------------------*/

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertBookingCommandSuccess(Command command, BookingModel actualModel,
                                                   CommandHistory actualCommandHistory,
                                                   CommandResult expectedCommandResult,
                                                   BookingModel expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, String, CustomerModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertBookingCommandSuccess(Command command, BookingModel actualModel,
                                                   CommandHistory actualCommandHistory,
                                                   String expectedMessage,
                                                   BookingModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertBookingCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the hms book, filtered customer list and selected customer in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertBookingCommandFailure(Command command, BookingModel actualModel,
                                                   CommandHistory actualCommandHistory,
                                                   String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        HotelManagementSystem expectedHotelManagementSystem =
            new HotelManagementSystem(actualModel.getHotelManagementSystem());
        List<Booking> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookingList());
        Booking expectedSelectedBooking = actualModel.getSelectedBooking();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedHotelManagementSystem, actualModel.getHotelManagementSystem());
            assertEquals(expectedFilteredList, actualModel.getFilteredBookingList());
            assertEquals(expectedSelectedBooking, actualModel.getSelectedBooking());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }


    /**
     * Updates {@code model}'s filtered list to show only the booking at the given {@code targetIndex} in the
     * {@code model}'s hms book.
     */
    public static void showBookingForPayer(BookingModel model, Customer payer) {
        String id = payer.getIdNum().toString();
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        model.updateFilteredBookingList(new BookingContainsPayerPredicate(id));
    }


    /* ------------------------------------- RESERVATION -------------------------------------*/

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertReservationCommandSuccess(Command command, ReservationModel actualModel,
                                                       CommandHistory actualCommandHistory,
                                                       CommandResult expectedCommandResult,
                                                       ReservationModel expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, String, CustomerModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertReservationCommandSuccess(Command command, ReservationModel actualModel,
                                                       CommandHistory actualCommandHistory,
                                                       String expectedMessage,
                                                       ReservationModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertReservationCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult,
            expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the hms book, filtered customer list and selected customer in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertReservationCommandFailure(Command command, ReservationModel actualModel,
                                                       CommandHistory actualCommandHistory,
                                                       String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        HotelManagementSystem expectedHotelManagementSystem =
            new HotelManagementSystem(actualModel.getHotelManagementSystem());
        List<Reservation> expectedFilteredList = new ArrayList<>(actualModel.getFilteredReservationList());
        Reservation expectedSelectedReservation = actualModel.getSelectedReservation();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedHotelManagementSystem, actualModel.getHotelManagementSystem());
            assertEquals(expectedFilteredList, actualModel.getFilteredReservationList());
            assertEquals(expectedSelectedReservation, actualModel.getSelectedReservation());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the booking at the given {@code targetIndex} in the
     * {@code model}'s hms book.
     */
    public static void showReservationForPayer(ReservationModel model, Customer payer) {
        String id = payer.getIdNum().toString();
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        model.updateFilteredReservationList(new ReservationContainsPayerPredicate(id));
    }

    /* ------------------------------------- GENERATE BILL-------------------------------------*/


    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, String, CustomerModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertGenerateBillCommandSuccess(Command command, BillModel actualModel,
                                                        CommandHistory actualCommandHistory,
                                                        String expectedMessage,
                                                        BillModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertGenerateBillCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult,
            expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertGenerateBillCommandSuccess(Command command, BillModel actualModel,
                                                        CommandHistory actualCommandHistory,
                                                        CommandResult expectedCommandResult,
                                                        BillModel expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, String, CustomerModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertGenerateBillForCustomerCommandSuccess(Command command, BillModel actualModel,
                                                        CommandHistory actualCommandHistory,
                                                        String expectedMessage,
                                                        BillModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertGenerateBillForCustomerCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult,
            expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertGenerateBillForCustomerCommandSuccess(Command command, BillModel actualModel,
                                                        CommandHistory actualCommandHistory,
                                                        CommandResult expectedCommandResult,
                                                        BillModel expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
