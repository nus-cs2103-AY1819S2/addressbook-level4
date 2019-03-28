package seedu.address.storage;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import seedu.address.logic.CommandHistory;
import seedu.address.model.*;
import seedu.address.storage.csvmanager.CsvFile;
import seedu.address.storage.csvmanager.Exceptions.CsvManagerNotInitialized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.storage.csvmanager.CsvManager.DEFAULT_TEST_PATH;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

public class CsvFileTest {

    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private final String TYPICAL_CARD_FOLDER = "Typical Cards.csv";
    private final String TYPICAL_CARD_FOLDER_TEST = "Typical Cards test.csv";

    public CsvFileTest() throws IOException {
        model.setTestCsvPath();
    }


    @Test
    public void execute_importCsvFile_correctFormat() throws Exception {
        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.importCardFolders(new CsvFile(TYPICAL_CARD_FOLDER));

        assertEquals(model.getCardFolders(), expectedModel.getCardFolders());
    }

    @Test
    public void execute_exportCsvFile_correctFile() throws IOException, CsvManagerNotInitialized {
        model.exportCardFolders(new ArrayList<>(Arrays.asList(1)));
        File testFile = new File(DEFAULT_TEST_PATH + TYPICAL_CARD_FOLDER_TEST);
        assert(testFile.exists());
        byte[] f1 = Files.readAllBytes(Paths.get(new File(DEFAULT_TEST_PATH + TYPICAL_CARD_FOLDER)
                .getCanonicalPath()));
        byte[] f2 = Files.readAllBytes(Paths.get(new File(DEFAULT_TEST_PATH + TYPICAL_CARD_FOLDER_TEST)
            .getCanonicalPath()));
        assertEquals(f1, f2);
    }
}
