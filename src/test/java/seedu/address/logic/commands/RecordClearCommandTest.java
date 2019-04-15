package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RecordBuilder;
import seedu.address.ui.MainWindow;

public class RecordClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyRecordList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();
        Patient patient = (Patient) new PersonBuilder().build();
        model.addPerson(patient);
        MainWindow.setRecordPatient(patient);

        String output = new RecordClearCommand().execute(model, commandHistory).getFeedbackToUser();
        String expected = String.format(output, MainWindow.getRecordPatient().getName().fullName);

        assertEquals(expected, output);
    }

    @Test
    public void execute_nonemptyRecordList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();
        Patient patient = (Patient) new PersonBuilder().build();
        model.addPerson(patient);
        patient.addRecord(new RecordBuilder().build());
        MainWindow.setRecordPatient(patient);

        String output = new RecordClearCommand().execute(model, commandHistory).getFeedbackToUser();
        String expected = String.format(output, MainWindow.getRecordPatient().getName().fullName);

        assertEquals(expected, output);
    }
}
