package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        expectedModel = new ModelManager(model.getCardFolders(), new UserPrefs());
        expectedModel.sortFilteredCard(Model.COMPARATOR_ASC_SCORE_CARDS);
    }

    @Test
    public void execute_sort() {
        assertCommandSuccess(new SortCommand(), model, commandHistory, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
