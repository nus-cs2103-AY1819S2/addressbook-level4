package seedu.address.logic.commands;

import org.junit.Before;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SetPriceViaPathCommandTest {

    private static String[] typicalDirectoryName = new String[] {"TCM", "Cheap", "Expensive"};

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        model = new ModelManager();
        commandHistory = new CommandHistory();
        model.addDirectory(typicalDirectoryName[0], new String[] {"root"});
    }
}
