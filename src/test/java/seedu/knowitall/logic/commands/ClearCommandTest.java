package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderName;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCardFolder_success() {
        Model model = new ModelManager(this.getClass().getName());
        Model expectedModel = new ModelManager(this.getClass().getName());
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCardFolder_success() {
        Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        expectedModel.resetCardFolder(new CardFolder(getTypicalFolderName()));
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
