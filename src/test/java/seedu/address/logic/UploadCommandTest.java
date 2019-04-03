package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UploadCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_share_success() {
        // TODO
        Path testDataFolder = Paths.get("src", "test", "data", "uploadCommandTest");
        File file = testDataFolder.resolve("upload.txt").toFile();

        try {
            new UploadCommand(file).execute(model, commandHistory);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
