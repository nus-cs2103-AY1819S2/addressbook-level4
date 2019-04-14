package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.showBookingForPayer;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Before;
import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListBookingCommand.
 */
public class ListBookingCommandTest {

    private BookingModel model;
    private BookingModel expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
        expectedModel = new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
            new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertBookingCommandSuccess(new ListBookingCommand(), model, commandHistory,
            ListBookingCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showBookingForPayer(model, ALICE);
        assertBookingCommandSuccess(new ListBookingCommand(), model, commandHistory,
            ListBookingCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
