package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneAsList;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneName;

import java.util.Collections;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalCards;
import seedu.knowitall.testutil.TypicalIndexes;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCardFolder_success() {
        CardFolder emptyCardFolder = TypicalCards.getEmptyCardFolder();

        Model model = new ModelManager(Collections.singletonList(emptyCardFolder));
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        Model expectedModel = new ModelManager(Collections.singletonList(emptyCardFolder));
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCardFolder_success() {
        Model model = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        Model expectedModel = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.resetCardFolder(new CardFolder(getTypicalFolderOneName()));
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
