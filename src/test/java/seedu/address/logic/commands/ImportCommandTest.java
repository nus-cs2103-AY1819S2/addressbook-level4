package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.logic.CommandHistory;
import seedu.address.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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



}
