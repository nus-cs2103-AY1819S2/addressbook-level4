package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.*;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

public class CsvFileTest {

    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_importCsvFile_success() {
        List<ReadOnlyCardFolder> readOnlyCardFolders = getTypicalCardFolders();
        model.importCardFolders();
    }
}
