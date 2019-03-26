package seedu.address.logic.commands;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

public class ExportCommandTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_exportSingleValidCardFolderIndex_success() {
        List<Integer> myList = new ArrayList<>(Arrays.asList(1));
        ExportCommand exportCommand = new ExportCommand(myList);


    }


    @Test
    public void testXYZ(File expected, File actual) throws IOException {
        byte[] f1 = Files.readAllBytes(expected.toPath());
        byte[] f2 = Files.readAllBytes(actual.toPath());
    }
}
