package seedu.address.logic.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.storage.csvmanager.CsvFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

public class ImportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private File file;
    private final String validFilename = "Typical Cards.csv";
    private final String invalidFileName = "Fake Cards.csv";

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());

    public ImportCommandTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        file = new File("./Typical Cards.csv").getCanonicalFile();

    }

    @Test
    public void execute_importTypicalCards_success() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(validFilename));

        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        CommandResult commandResult = importCommand.execute(model, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, validFilename);
        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_importNonExistentCards_failure() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(invalidFileName));
        thrown.expect(CommandException.class);
        thrown.expectMessage(ImportCommand.MESSAGE_FILE_OPS_FAILURE);
        importCommand.execute(model, commandHistory);
    }

    @After
    public void deleteTempFile() {
        if (file.exists()) {
            assert(file.delete());
        }
    }

}
