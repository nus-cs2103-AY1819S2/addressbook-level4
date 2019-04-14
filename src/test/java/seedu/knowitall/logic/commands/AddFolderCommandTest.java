package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.ReadOnlyUserPrefs;
import seedu.knowitall.model.VersionedCardFolder;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.storage.csvmanager.CsvFile;
import seedu.knowitall.testutil.TypicalCards;
import seedu.knowitall.testutil.TypicalIndexes;

public class AddFolderCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullFolder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddFolderCommand(null);
    }

    @Test
    public void execute_folderNameAcceptedByModel_addEmptyFolderSuccessful() throws Exception {
        ModelStubAcceptingFolderAdded modelStub = new ModelStubAcceptingFolderAdded();
        CardFolder cardFolder = new CardFolder(TypicalCards.getTypicalFolderOne());

        Command addFolderCommand = new AddFolderCommand(cardFolder.getFolderName());
        CommandResult commandResult = addFolderCommand.execute(modelStub, commandHistory);

        assertEquals(String.format(AddFolderCommand.MESSAGE_SUCCESS, cardFolder), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(cardFolder), modelStub.foldersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateFolder_throwsCommandException() throws Exception {
        CardFolder cardFolder = new CardFolder(TypicalCards.getTypicalFolderOne());
        AddFolderCommand addFolderCommand = new AddFolderCommand(cardFolder.getFolderName());
        ModelStub modelStub = new ModelStubWithFolder(cardFolder);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddFolderCommand.MESSAGE_DUPLICATE_CARD_FOLDER);
        addFolderCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_inFolder_throwsCommandException() throws Exception {
        CardFolder cardFolder = new CardFolder(TypicalCards.getTypicalFolderOne());
        AddFolderCommand addFolderCommand = new AddFolderCommand(cardFolder.getFolderName());
        ModelStub modelStub = new ModelStubWithFolder(cardFolder);
        modelStub.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER);
        addFolderCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        AddFolderCommand addFolderOneCommand = new AddFolderCommand(TypicalCards.getTypicalFolderOneName());
        AddFolderCommand addFolderTwoCommand = new AddFolderCommand(TypicalCards.getTypicalFolderTwoName());

        // same object -> returns true
        assertTrue(addFolderOneCommand.equals(addFolderOneCommand));

        // same values -> returns true
        AddFolderCommand addFolderOneCommandCopy = new AddFolderCommand(TypicalCards.getTypicalFolderOneName());
        assertTrue(addFolderOneCommand.equals(addFolderOneCommandCopy));

        // different types -> returns false
        assertFalse(addFolderOneCommand.equals(1));

        // null -> returns false
        assertFalse(addFolderOneCommand.equals(null));

        // different card -> returns false
        assertFalse(addFolderOneCommand.equals(addFolderTwoCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getcardFolderFilesPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setcardFolderFilesPath(Path cardFolderFilesPath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCardFolder(ReadOnlyCardFolder newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getActiveCardFolderName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCardFolder getActiveCardFolder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<ReadOnlyCardFolder> getCardFolders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void startTestSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Card getCurrentTestedCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentTestedCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void endTestSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean testNextCard() {
            throw new AssertionError("This method should not be called.");
        }

        public boolean markAttemptedAnswer(Answer attemptedAnswer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean markAttemptedMcqAnswer(int answerIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Card createScoredCard(Card cardToMark, boolean markCorrect) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCardAsAnswered() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isCardAlreadyAnswered() {
            throw new AssertionError("This method should not be called.");
        }

        public void exportCardFolders(List<Integer> cardFolderExports) throws IOException {
            throw new AssertionError("This method should not be called.");
        }

        public boolean inReportDisplay() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void enterReportDisplay() {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void exitReportDisplay() {
            throw new AssertionError("This method should not be called.");
        };


        @Override
        public void importCardFolders(CsvFile csvFile) throws IOException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTestCsvPath(String path) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getDefaultPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCard(Card target, Card editedCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeSelectedCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFolder(String folderName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFolder(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFolder(CardFolder cardFolder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void renameFolder(int index, String newName) {

        }

        @Override
        public int getActiveCardFolderIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void enterFolder(int newIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void exitFolderToHome() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<FilteredList<Card>> getFilteredCardsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getActiveFilteredCards() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<VersionedCardFolder> getFilteredFolders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCard(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredCard(Comparator<Card> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoActiveCardFolder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoActiveCardFolder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoActiveCardFolder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoActiveCardFolder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitActiveCardFolder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Card> selectedCardProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Card getSelectedCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public State getState() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single folder.
     */
    private class ModelStubWithFolder extends ModelStub {
        private final CardFolder cardFolder;
        private State state = State.IN_HOMEDIR;

        ModelStubWithFolder(CardFolder cardFolder) {
            requireNonNull(cardFolder);
            this.cardFolder = cardFolder;
        }

        @Override
        public State getState() {
            return state;
        }

        @Override
        public void enterFolder(int newIndex) {
            state = State.IN_FOLDER;
        }

        @Override
        public void exitFolderToHome() {
            state = State.IN_HOMEDIR;
        }

        @Override
        public boolean hasFolder(String folderName) {
            requireNonNull(folderName);
            return cardFolder.getFolderName().equals(folderName);
        }

    }

    /**
     * A Model stub that always accept the folder being added.
     */
    private class ModelStubAcceptingFolderAdded extends ModelStub {
        final List<CardFolder> foldersAdded = new ArrayList<>();

        @Override
        public boolean hasFolder(String folderName) {
            requireNonNull(folderName);

            return foldersAdded.stream().anyMatch(folder -> folder.getFolderName().equals(folderName));
        }

        @Override
        public void addFolder(CardFolder cardFolder) {
            requireNonNull(cardFolder);

            foldersAdded.add(cardFolder);
        }

        @Override
        public State getState() {
            return State.IN_HOMEDIR;
        }

        public ObservableList<Card> getFirstFolderCardList() {
            return foldersAdded.get(0).getCardList();
        }
    }

}
