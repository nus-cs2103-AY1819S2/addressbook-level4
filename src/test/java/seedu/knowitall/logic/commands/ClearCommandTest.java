package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneName;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalIndexes;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCardFolder_success() {
        Model model = new ModelManager(this.getClass().getName());
        model.enterFolder(0);
        Model expectedModel = new ModelManager(this.getClass().getName());
        expectedModel.enterFolder(0);
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCardFolder_success() {
        Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.resetCardFolder(new CardFolder(getTypicalFolderOneName()));
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
