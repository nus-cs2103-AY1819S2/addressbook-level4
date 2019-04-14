package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.showReservationForPayer;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Before;
import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListReservationCommand.
 */
public class ListReservationCommandTest {

    private ReservationModel model;
    private ReservationModel expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
        expectedModel = new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
            new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertReservationCommandSuccess(new ListReservationCommand(), model, commandHistory,
            ListReservationCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showReservationForPayer(model, ALICE);
        assertReservationCommandSuccess(new ListReservationCommand(), model, commandHistory,
            ListReservationCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
