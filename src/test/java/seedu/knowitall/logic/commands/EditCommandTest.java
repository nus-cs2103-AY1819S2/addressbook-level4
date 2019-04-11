package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.knowitall.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.EditCommand.EditCardDescriptor;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.testutil.CardBuilder;
import seedu.knowitall.testutil.EditCardDescriptorBuilder;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Card editedCard = new CardBuilder().build();
        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(Collections.singletonList(new CardFolder(model.getActiveCardFolder())),
                new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());
        expectedModel.setCard(model.getActiveFilteredCards().get(0), editedCard);
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCard = Index.fromOneBased(model.getActiveFilteredCards().size());
        Card lastCard = model.getActiveFilteredCards().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList.withQuestion(VALID_QUESTION_2).withAnswer(VALID_ANSWER_2)
                .withHint(VALID_HINT_HUSBAND).build();

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_2)
                .withAnswer(VALID_ANSWER_2).withHint(VALID_HINT_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(Collections.singletonList(new CardFolder(model.getActiveCardFolder())),
                new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());
        expectedModel.setCard(lastCard, editedCard);
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, new EditCommand.EditCardDescriptor());
        Card editedCard = model.getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(Collections.singletonList(new CardFolder(model.getActiveCardFolder())),
                new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardInFilteredList = model.getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withQuestion(VALID_QUESTION_2).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_2).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(Collections.singletonList(new CardFolder(model.getActiveCardFolder())),
                new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());
        expectedModel.setCard(model.getActiveFilteredCards().get(0), editedCard);
        expectedModel.commitActiveCardFolder();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEditedAnswerSameAsOption_failure() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, new EditCardDescriptorBuilder()
                .withAnswer(VALID_ANSWER_1).withOptions(VALID_ANSWER_1).build());
        String expectedMessage = Messages.MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER;
        assertCommandFailure(editCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_duplicateCardUnfilteredList_failure() {
        Card firstCard = model.getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstCard).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateCardFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        // edit card in filtered list into a duplicate in card folder
        Card cardInList = model.getActiveCardFolder().getCardList().get(INDEX_SECOND_CARD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder(cardInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getActiveFilteredCards().size() + 1);
        EditCommand.EditCardDescriptor descriptor =
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_2).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of card folder
     */
    @Test
    public void execute_invalidCardIndexFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of card folder list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getActiveCardFolder().getCardList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_2).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Card editedCard = new CardBuilder().build();
        Card cardToEdit = model.getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased());
        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, descriptor);
        Model expectedModel = new ModelManager(Collections.singletonList(new CardFolder(model.getActiveCardFolder())),
                new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());
        expectedModel.setCard(cardToEdit, editedCard);
        expectedModel.commitActiveCardFolder();

        // edit -> first card edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts cardfolder back to previous state and filtered card list to show all cards
        expectedModel.undoActiveCardFolder();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card edited again
        expectedModel.redoActiveCardFolder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getActiveFilteredCards().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_2).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> card folder state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // single card folder state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Card} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardEdited() throws Exception {
        Card editedCard = new CardBuilder().build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, descriptor);
        Model expectedModel = new ModelManager(Collections.singletonList(new CardFolder(model.getActiveCardFolder())),
                new UserPrefs());
        expectedModel.enterFolder(model.getActiveCardFolderIndex());

        showCardAtIndex(model, INDEX_SECOND_CARD);
        Card cardToEdit = model.getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased());
        expectedModel.setCard(cardToEdit, editedCard);
        expectedModel.commitActiveCardFolder();

        // edit -> edits second card in unfiltered card list / first card in filtered card list
        editCommand.execute(model, commandHistory);

        // undo -> reverts cardfolder back to previous state and filtered card list to show all cards
        expectedModel.undoActiveCardFolder();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased()), cardToEdit);
        // redo -> edits same second card in unfiltered card list
        expectedModel.redoActiveCardFolder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CARD, DESC_AMY);

        // same values -> returns true
        EditCardDescriptor copyDescriptor = new EditCommand.EditCardDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CARD, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CARD, DESC_BOB)));
    }

}
