package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_FILEPATH_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_IMPORTED_DECK_INVALID;
import static seedu.address.storage.JsonSerializableTopDeck.MESSAGE_DUPLICATE_DECK;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ViewState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.DeckImportException;
import seedu.address.storage.portmanager.PortManager;
import seedu.address.storage.portmanager.Porter;

public class ImportDeckCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void importDeck_success() throws Exception {
        Model testModel = new ModelAlwaysImports();
        Deck deckToImport = DECK_WITH_CARDS;
        ImportDeckCommand importCommand = new ImportDeckCommand("Unused");
        CommandResult commandResult = importCommand.execute(testModel, commandHistory);

        assertEquals(String.format(ImportDeckCommand.MESSAGE_IMPORT_DECK_SUCCESS, deckToImport),
                commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void importFileCantBeFound_throwsException() throws Exception {
        Model testModel = new ModelCantFindFile();
        String filepath = "test filepath.json";
        ImportDeckCommand importCommand = new ImportDeckCommand(filepath);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_FILEPATH_INVALID, filepath));

        importCommand.execute(testModel, commandHistory);
    }

    @Test
    public void importDuplicate_throwsException() throws Exception {
        Model testModel = new ModelThrowsDe();
        Deck deckToImport = DECK_WITH_CARDS;
        ImportDeckCommand importCommand = new ImportDeckCommand("Unused");

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_DECK);

        importCommand.execute(testModel, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public Path getTopDeckFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTopDeckFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTopDeck getTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTopDeck(ReadOnlyTopDeck newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void deleteCard(Card target, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCard(Card target, Card editedCard, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck getDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeck(Deck deck) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Command parse(String commandWord, String arguments) throws ParseException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void goToDecksView() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void studyDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAtDecksView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ViewState getViewState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAtCardsView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAtStudyView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String filepath) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always imports the same deck.
     */

    private class ModelAlwaysImports extends ModelStub {
        private final Porter porter = new PortManagerStub();

        @Override
        public Deck importDeck(String filepath) {
            try {
                return porter.importDeck(filepath);
            } catch (DataConversionException e) {
                throw new DeckImportException(String.format(MESSAGE_IMPORTED_DECK_INVALID, filepath));
            }
        }

        @Override
        public void commitTopDeck() {
            // called by {@code ImportDeckCommand#execute()}
        }

    }

    private class ModelCantFindFile extends ModelStub {
        final Porter porter = new PortManagerFileNotFound();

        @Override
        public Deck importDeck(String filepath) {
            try {
                return porter.importDeck(filepath);
            } catch (DataConversionException e) {
                throw new DeckImportException(String.format(MESSAGE_IMPORTED_DECK_INVALID, filepath));
            }
        }

        @Override
        public void commitTopDeck() {
            // called by {@code ImportDeckCommand#execute()}
        }
    }

    private class ModelThrowsDe extends ModelStub {
        final Porter porter = new PortManager();

        @Override
        public Deck importDeck(String filepath) {
            throw new DeckImportException(MESSAGE_DUPLICATE_DECK);
        }

        @Override
        public void commitTopDeck() {
            // called by {@code ImportDeckCommand#execute()}
        }
    }


    private static class PortManagerStub implements Porter {
        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String stringPath) throws DeckImportException {
            return DECK_WITH_CARDS;
        }
    }

    private static class PortManagerFileNotFound implements Porter {

        private static Path baseFilePath = Paths.get("");

        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String stringPath) throws DeckImportException {
            Path filepath = makeFilePath(stringPath);
            throw new DeckImportException(String.format(MESSAGE_FILEPATH_INVALID, filepath));
        }

        /**
         * Makes a string into a path.
         */

        private Path makeFilePath(String name) {
            if (name.substring(name.length() - 5).equals(".json")) {
                return baseFilePath.resolve(name);
            } else {
                return baseFilePath.resolve(name + ".json");
            }
        }
    }
}

