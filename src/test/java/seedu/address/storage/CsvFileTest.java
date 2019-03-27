package seedu.address.logic.commands;

import org.junit.Test;
import org.junit.Assert;
import seedu.address.logic.CommandHistory;
import seedu.address.model.*;
import seedu.address.storage.csvmanager.CsvFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

public class CsvFileTest {

    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private final String TYPICAL_CARD_FOLDER = "Typical Cards.csv";

    public CsvFileTest() throws IOException {
    }


    @Test
    public void execute_importCsvFile_success() throws IOException {
        List<ReadOnlyCardFolder> readOnlyCardFolders = getTypicalCardFolders();
        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.setTestCsvPath();
        model.importCardFolders(new CsvFile(TYPICAL_CARD_FOLDER));

        assertEqual
    }
}
