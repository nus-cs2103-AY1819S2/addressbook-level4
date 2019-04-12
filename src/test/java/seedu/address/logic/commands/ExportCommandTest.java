package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.storage.ParsedInOut;
import seedu.address.testutil.TypicalPersons;

/**
 * These tests only check if ExportCommand is exporting .json properly, and not testing if user input is correct.
 * For PDF testing, go to PdfSerializableAddressBookTest
 */
public class ExportCommandTest {

    private File file = new File("data" + File.separator + "test.json");

    private void executeExportTest(String type, HashSet<Integer> range) throws Exception {
        Model exportModel = new ModelManager();
        Model expectedModel = new ModelManager();
        Model actualModel = new ModelManager();

        // Export the selected patients to {@code File file}
        exportModel.setPatientList(TypicalPersons.getTypicalPersons());
        exportModel.commitAddressBook();
        ExportCommand exportCommand = new ExportCommand(new ParsedInOut(file, type, range));
        try {
            exportCommand.execute(exportModel, new CommandHistory());
        } catch (CommandException ce) {
            throw new CommandException(ce.getMessage());
        }

        // Read the file that was just exported
        OpenCommand openCommand = new OpenCommand(new ParsedInOut(file, type, range));
        openCommand.execute(actualModel, new CommandHistory());

        // Create list of expected patients
        List<Person> expectedPatients = new ArrayList<>();
        for (Integer i : range) {
            if (i < exportModel.getFilteredPersonList().size()) {
                expectedPatients.add(exportModel.getFilteredPersonList().get(i));
            }
        }
        expectedModel.setPatientList(expectedPatients);

        assertEquals(expectedModel.getFilteredPersonList(), actualModel.getFilteredPersonList());
        file.delete();
    }

    @Test
    public void executeExport_Simple() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1)));
    }

    @Test
    public void executeExport_1Comma2() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1, 2)));
    }

    @Test
    public void executeExport_1Comma5() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1, 5)));
    }

    @Test
    public void executeExport_1Dash5() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void executeExport_12Comma16() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(12, 16)));
    }
}
