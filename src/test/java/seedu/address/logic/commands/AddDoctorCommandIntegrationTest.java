package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Doctor;
import seedu.address.testutil.DoctorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddDoctorCommand}.
 */
public class AddDoctorCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newDoctor_success() {
        Doctor validDoctor = new DoctorBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addDoctor(validDoctor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddDoctorCommand(validDoctor), model, commandHistory,
                String.format(AddDoctorCommand.MESSAGE_SUCCESS, validDoctor), expectedModel);
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        Doctor doctorInList = model.getAddressBook().getDoctorList().get(0);
        assertCommandFailure(new AddDoctorCommand(doctorInList), model, commandHistory,
                AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);
    }

}
