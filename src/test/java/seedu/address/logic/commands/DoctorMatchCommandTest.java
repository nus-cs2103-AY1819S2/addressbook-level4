package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDoctors.getTypicalDocX_doctor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.doctor.DoctorMatchCommand;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.doctor.AppointmentContainsDoctorPredicate;
import seedu.address.model.person.doctor.DoctorHasAppointmentPredicate;
import seedu.address.model.person.doctor.DoctorMatch;
import seedu.address.model.person.doctor.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.doctor.DoctorsMatch;
import seedu.address.model.person.specialisation.Specialisation;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DoctorMatchCommand.
 */
public class DoctorMatchCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();



    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDocX_doctor(), new UserPrefs());
        expectedModel = new ModelManager(model.getDocX(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecified_listRelevantDoctors() {
        DoctorMatch dm = new DoctorMatch(new Specialisation("general"),
                new AppointmentDate("2019-06-20"), new AppointmentTime("09:00"));
        DoctorSpecialisationMatchesPredicate pred1 = new DoctorSpecialisationMatchesPredicate(dm);
        DoctorMatchCommand command = new DoctorMatchCommand(pred1);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.updateFilteredDoctorList(pred1);
        DoctorsMatch dsm = new DoctorsMatch(expectedModel.getFilteredDoctorList(), pred1.getDate(), pred1.getTime());
        AppointmentContainsDoctorPredicate pred2 = new AppointmentContainsDoctorPredicate(dsm);
        expectedModel.updateFilteredAppointmentList(pred2);
        DoctorHasAppointmentPredicate pred3 = new DoctorHasAppointmentPredicate(
                expectedModel.getFilteredAppointmentList(), pred1.getSpec());
        expectedModel.updateFilteredDoctorList(pred3);

        String expectedMessage = String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW,
                expectedModel.getFilteredDoctorList().size());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Specialisation spec1 = new Specialisation("surgery");
        Specialisation spec2 = new Specialisation("general");

        AppointmentDate date1 = new AppointmentDate("2019-06-20");
        AppointmentDate date2 = new AppointmentDate("2019-07-01");

        AppointmentTime time1 = new AppointmentTime("09:00");
        AppointmentTime time2 = new AppointmentTime("14:00");

        DoctorMatch dm1 = new DoctorMatch(spec1, date1, time1);
        DoctorMatch dm2 = new DoctorMatch(spec2, date2, time2);


        DoctorSpecialisationMatchesPredicate firstPredicate =
                new DoctorSpecialisationMatchesPredicate(dm1);
        DoctorSpecialisationMatchesPredicate secondPredicate =
                new DoctorSpecialisationMatchesPredicate(dm2);

        DoctorMatchCommand doctorMatchFirstCommand = new DoctorMatchCommand(firstPredicate);
        DoctorMatchCommand doctorMatchSecondCOmmand = new DoctorMatchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(doctorMatchFirstCommand.equals(doctorMatchFirstCommand));

        // same values -> returns true
        DoctorMatchCommand doctorMatchFirstCommandCopy =
                new DoctorMatchCommand(firstPredicate);
        assertTrue(doctorMatchFirstCommand.equals(doctorMatchFirstCommandCopy));

        // different types -> returns false
        assertFalse(doctorMatchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doctorMatchFirstCommand.equals(null));

        // different doctor -> returns false
        assertFalse(doctorMatchFirstCommand.equals(doctorMatchSecondCOmmand));
    }
}
