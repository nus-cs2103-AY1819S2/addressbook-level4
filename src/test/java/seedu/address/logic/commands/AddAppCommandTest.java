package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;


public class AddAppCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");
        String comment = "This is a comment";
        Person personToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Appointment toAdd = new Appointment(personToAdd, date, start, end, comment);

        model.addApp(toAdd);
    }

    @Test
    public void executeValidAddAppointment() throws Exception {
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");
        String comment = "This is a comment";

        CommandResult commandResult = new AddAppCommand(INDEX_FIRST_PERSON, date, start, end, comment)
                .execute(model, commandHistory);
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Appointment toAdd = new Appointment(personToAdd, date, start, end, comment);

        StringBuilder sb = new StringBuilder();
        sb.append("Appointment added:\n")
                .append(toAdd.toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());

    }

    @Test
    public void executeDuplicateAddAppointment() throws Exception {
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");
        String comment = "This is a comment";
        AddAppCommand addAppCommand = new AddAppCommand(INDEX_SECOND_PERSON, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_DUPLICATE_APP);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");
        String comment = "This is a comment";

        AddAppCommand addAppFirst = new AddAppCommand(INDEX_FIRST_PERSON, date, start, end, comment);
        AddAppCommand addAppSecond = new AddAppCommand(INDEX_SECOND_PERSON, date, start, end, comment);

        // same object -> returns true
        assertTrue(addAppFirst.equals(addAppFirst));

        // same values -> returns true
        AddAppCommand addAppFirstCopy = new AddAppCommand(INDEX_FIRST_PERSON, date, start, end, comment);
        assertTrue(addAppFirst.equals(addAppFirstCopy));

        // different types -> returns false
        assertFalse(addAppFirst.equals(1));

        // null -> returns false
        assertFalse(addAppFirst.equals(null));

        // different person -> returns false
        assertFalse(addAppFirst.equals(addAppSecond));
    }
}
