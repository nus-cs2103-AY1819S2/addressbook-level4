package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.*;
import seedu.address.storage.csvmanager.CsvFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;
import static seedu.address.testutil.TypicalCards.getTypicalCards;

public class ImportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private File file;

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
        String filename = "Typical Cards.csv";
        ImportCommand importCommand = new ImportCommand(new CsvFile(filename));

        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        CommandResult commandResult = importCommand.execute(model, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, filename);
        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }



}
