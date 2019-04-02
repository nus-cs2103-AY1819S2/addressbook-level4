package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.testutil.CardBuilder;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
    }

    @Test
    public void execute_newCard_success() {
        Card validCard = new CardBuilder().build();

        Model expectedModel = new ModelManager(model.getCardFolders(), new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());
        expectedModel.addCard(validCard);
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(new AddCommand(validCard), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validCard), expectedModel);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() {
        Card cardInList = model.getActiveCardFolder().getCardList().get(0);
        assertCommandFailure(new AddCommand(cardInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_CARD);
    }

}
