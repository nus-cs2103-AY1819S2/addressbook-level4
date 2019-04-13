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
import seedu.address.model.person.Person;
import seedu.address.storage.ParsedInOut;
import seedu.address.testutil.TypicalPersons;

/**
 * These tests only check if ExportCommand is exporting .json properly, and not testing if user input is correct.
 * For PDF testing, go to PdfSerializableAddressBookTest
 */
public class ExportCommandTest {

    private File file = new File("data/test.json");

    /**
     * Exports the file with the patients specified in {@code HashSet<Integer< range}.
     */
    private void exportFile(Model exportModel, String type, HashSet<Integer> range) throws Exception {
        // Export the selected patients to {@code File file}
        exportModel.setPatientList(TypicalPersons.getTypicalPersons());
        exportModel.commitAddressBook();
        ExportCommand exportCommand = new ExportCommand(new ParsedInOut(file, type, range));
        try {
            exportCommand.execute(exportModel, new CommandHistory());
        } catch (CommandException ce) {
            throw new CommandException(ce.getMessage());
        }
    }

    /**
     * Reads the file specified by {@code File file}
     */
    private void readFile(Model actualModel, String type) throws Exception {
        OpenCommand openCommand = new OpenCommand(new ParsedInOut(file, type));
        openCommand.execute(actualModel, new CommandHistory());
    }

    /**
     * Adds the the patients specified in {@code HashSet<Integer< range} from exportModel to expectedModel.
     */
    private void createExpected(Model expectedModel, Model exportModel, HashSet<Integer> range) {
        List<Person> expectedPatients = new ArrayList<>();
        for (Integer i : range) {
            if (i < exportModel.getFilteredPersonList().size()) {
                expectedPatients.add(exportModel.getFilteredPersonList().get(i));
            }
        }
        expectedModel.setPatientList(expectedPatients);
    }

    /**
     * Compares the person list in actualModel and expectedModel.
     */
    private void executeExportTest(String type, HashSet<Integer> range) throws Exception {
        Model exportModel = new ModelManager();
        Model expectedModel = new ModelManager();
        Model actualModel = new ModelManager();

        exportFile(exportModel, type, range);
        readFile(actualModel, type);
        createExpected(expectedModel, exportModel, range);

        assertEquals(expectedModel.getFilteredPersonList(), actualModel.getFilteredPersonList());
        file.delete();
    }

    @Test
    public void executeExportSimple() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1)));
    }

    @Test
    public void executeExport1Comma2() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1, 2)));
    }

    @Test
    public void executeExport1Comma5() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1, 5)));
    }

    @Test
    public void executeExport1Dash5() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void executeExport12Comma16() throws Exception {
        executeExportTest("json", new HashSet<>(Arrays.asList(12, 16)));
    }
}
