package seedu.address.logic.commands;

import static seedu.address.model.util.SamplePatientsUtil.getSamplePatients;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

public class ListAppCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getSamplePatients());
    private CommandHistory commandHistory = new CommandHistory();

    private Nric nric = new Nric("S9234568C");
    private LocalDate dateA = LocalDate.parse("2019-10-23");
    private LocalDate dateB = LocalDate.parse("2019-10-24");
    private LocalDate dateC = LocalDate.parse("2019-10-25");
    private LocalTime start = LocalTime.parse("16:00");
    private LocalTime end = LocalTime.parse("17:00");
    private String comment = "This is a comment";
    private Patient patientToAdd = model.getPatientWithNric(nric);

    private Appointment toAddA = new Appointment(patientToAdd, dateA, start, end, comment);
    private Appointment toAddB = new Appointment(patientToAdd, dateB, start, end, comment);
    private Appointment toAddC = new Appointment(patientToAdd, dateC, start, end, comment);

    @Before
    public void init() {
        model.addApp(toAddA);
        model.addApp(toAddB);
        model.addApp(toAddC);
    }

    @Test
    public void executeListApp() {
        CommandResult result = new ListAppCommand().execute(model, commandHistory);
        StringBuilder expected = new StringBuilder();
        expected.append(ListAppCommand.MESSAGE_SUCCESS)
                .append(model.listApp());

        Assert.assertTrue(result.getFeedbackToUser().equals(expected.toString()));
    }

    @Test
    public void equals() {
        ListAppCommand listApp = new ListAppCommand();

        // same object -> returns true
        Assert.assertTrue(listApp.equals(listApp));

        // different types -> returns false
        Assert.assertFalse(listApp.equals(1));

        // null -> returns false
        Assert.assertFalse(listApp.equals(null));
    }
}
