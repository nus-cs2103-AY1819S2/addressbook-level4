package seedu.hms.logic.commands;

//import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Before;
import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.testutil.BookingBuilder;
import seedu.hms.testutil.TypicalCustomers;

public class AddBookingCommandIntegrationTest {
    private BookingModel model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                new UserPrefs());
    }

    @Test
    public void executeNewBookingSuccess() {
        Booking validBooking = new BookingBuilder()
                .withService(ServiceType.GAMES)
                .withTiming(14, 15)
                .withPayer(TypicalCustomers.BOB)
                .withOtherUsers()
                .withComment("CoolComment")
                .build();
        BookingModel expectedModel = new BookingManager(
                new VersionedHotelManagementSystem(model.getHotelManagementSystem()), new UserPrefs());
        expectedModel.addBooking(validBooking);
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(new AddBookingCommand(validBooking), model, commandHistory,
                String.format(AddBookingCommand.MESSAGE_SUCCESS, validBooking), expectedModel);
    }
}
