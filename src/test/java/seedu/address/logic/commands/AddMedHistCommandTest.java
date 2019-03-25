package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.Name;

public class AddMedHistCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void testThrowException() throws CommandException {
        /*
        thrown.expect(CommandException.class);
        new AddMedHistCommand(new MedicalHistory(null, null, new Name("t"), new WriteUp("t")))
                .execute(null, commandHistory);
        */
    }
}
