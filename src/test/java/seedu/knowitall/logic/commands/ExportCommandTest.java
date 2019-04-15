package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.knowitall.testutil.SampleBloodCards.getBloodFolder;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOne;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.storage.csvmanager.CsvUtils;


public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(Arrays.asList(getTypicalFolderOne(), getBloodFolder()),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.setTestCsvPath(CsvUtils.DEFAULT_EXPORT_TEST_PATH);
    }


    @Test
    public void execute_exportSingleMultipleFolderIndex_success() throws Exception {
        List<Integer> singleList = new ArrayList<>(Arrays.asList(1));
        List<Integer> multipleList = new ArrayList<>(Arrays.asList(1, 2));
        execute_exportValidCardFolderIndexes_success(singleList);
        execute_exportValidCardFolderIndexes_success(multipleList);
        deleteExportFile();
    }


    private void execute_exportValidCardFolderIndexes_success(List<Integer> folderIndexes) throws Exception {
        ExportCommand exportCommand = new ExportCommand(folderIndexes);
        CommandResult commandResult = exportCommand.execute(model, commandHistory);
        assertEquals(ExportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    /**
     * Deletes files exported after testing.
     */
    private void deleteExportFile() {
        File[] files = new File[] {new File(model.getDefaultPath() + "/" + "Typical Folder 1.csv"),
            new File(model.getDefaultPath() + "/" + "Blood.csv")};
        for (File file : files) {
            if (file.exists()) {
                boolean isDeleted = file.delete();
                assert (isDeleted);
            }
        }
    }


    @Test
    public void execute_exportSingleInvalidCardFolderIndex_failure() throws Exception {
        List<Integer> myList = new ArrayList<>(Arrays.asList(1, 3));
        ExportCommand exportCommand = new ExportCommand(myList);
        thrown.expect(CommandException.class);
        thrown.expectMessage(ExportCommand.MESSAGE_MISSING_CARD_FOLDERS);
        CommandResult commandResult = exportCommand.execute(model, commandHistory);
    }

}
