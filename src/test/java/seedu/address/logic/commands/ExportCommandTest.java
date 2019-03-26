package seedu.address.logic.commands;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.*;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



public class ExportCommandTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();



    @Test
    public void execute_exportSingleValidCardFolderIndex_success() throws Exception {
        System.out.println(tmpFolder.getRoot());
        List<Integer> myList = new ArrayList<>(Arrays.asList(1));
        ExportCommand exportCommand = new ExportCommand(myList);


        CommandResult commandResult = exportCommand.execute(model, commandHistory);
        assertEquals(ExportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    @After
    public void deleteExportFile() {
        File file = new File("./Typical Cards.csv");
        if (file.exists()) {
            boolean isDeleted = file.delete();
            assert (isDeleted);
        }
    }

    @Test
    public void execute_exportSingleInvalidCardFolderIndex_failure() throws Exception {
        List<Integer> myList = new ArrayList<>(Arrays.asList(1,3));
        ExportCommand exportCommand = new ExportCommand(myList);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ExportCommand.MESSAGE_MISSING_CARD_FOLDERS);
        CommandResult commandResult = exportCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_exportMultipleValidCardFolderIndex_success() throws Exception {

    }

}
