package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.EmptyDeckException;


/**
 * Represents the in-memory model of top deck data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTopDeck versionedTopDeck;
    private final UserPrefs userPrefs;
    private ViewState viewState;

    /**
     * Initializes a ModelManager with the given topDeck and userPrefs.
     */
    public ModelManager(ReadOnlyTopDeck topDeck, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(topDeck, userPrefs);

        logger.fine("Initializing with address book: " + topDeck + " and user prefs " + userPrefs);

        versionedTopDeck = new VersionedTopDeck(topDeck);
        this.userPrefs = new UserPrefs(userPrefs);
        viewState = new DecksView(new FilteredList<>(versionedTopDeck.getDeckList()));
    }

    public ModelManager() {
        this(new TopDeck(), new UserPrefs());
    }

    public ModelManager(Model model) {
        versionedTopDeck = new VersionedTopDeck(model.getTopDeck());
        userPrefs = new UserPrefs(model.getUserPrefs());
        ViewState viewState = model.getViewState();
        if (viewState instanceof DecksView) {
            this.viewState = new DecksView((DecksView) viewState);
        } else if (viewState instanceof CardsView) {
            this.viewState = new CardsView((CardsView) viewState);
        } else if (viewState instanceof StudyView) {
            this.viewState = new StudyView((StudyView) viewState);
        }
    }

    public Command parse(String commandWord, String arguments) throws ParseException {
        return viewState.parse(commandWord, arguments);
    }

    /**
     * Updates the given ViewState to CardsView with the given deck.
     */
    public void changeDeck(Deck deck) {
        viewState = new CardsView(deck);
    }

    /**
     * Changes view state to show a single card at a time
     */
    public void studyDeck(Deck deck) throws EmptyDeckException {
        if (deck.isEmpty()) {
            throw new EmptyDeckException("Empty deck cannot be studied");
        }
        viewState = new StudyView(deck);
    }

    @Override
    public void goToDecksView() {
        viewState = new DecksView(new FilteredList<>(versionedTopDeck.getDeckList()));
    }

    @Override
    public boolean isAtDecksView() {
        return (viewState instanceof DecksView);
    }

    @Override
    public boolean isAtCardsView() {
        return (viewState instanceof CardsView);
    }

    @Override
    public boolean isAtStudyView() {
        return (viewState instanceof StudyView);
    }

    @Override
    public ViewState getViewState() {
        return viewState;
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTopDeckFilePath() {
        return userPrefs.getTopDeckFilePath();
    }

    @Override
    public void setTopDeckFilePath(Path topDeckFilePath) {
        requireNonNull(topDeckFilePath);
        userPrefs.setTopDeckFilePath(topDeckFilePath);
    }

    //=========== TopDeck ================================================================================

    @Override
    public ReadOnlyTopDeck getTopDeck() {
        return versionedTopDeck;
    }

    @Override
    public void setTopDeck(ReadOnlyTopDeck topDeck) {
        versionedTopDeck.resetData(topDeck);
    }

    @Override
    public boolean hasCard(Card card, Deck deck) {
        requireAllNonNull(card, deck);

        return deck.hasCard(card);
    }

    @Override
    public void deleteCard(Card target, Deck deck) {
        requireAllNonNull(target, deck);

        Deck editedDeck = versionedTopDeck.deleteCard(target, deck);
        changeDeck(editedDeck);
    }

    @Override
    public void addCard(Card card, Deck deck) {
        requireAllNonNull(card, deck);

        Deck editedDeck = versionedTopDeck.addCard(card, deck);
        changeDeck(editedDeck);
    }

    @Override
    public void setCard(Card target, Card editedCard, Deck deck) {
        requireAllNonNull(target, editedCard, deck);

        Deck editedDeck = versionedTopDeck.setCard(target, editedCard, deck);
        changeDeck(editedDeck);
    }

    @Override
    public void setDeck(Deck target, Deck editedDeck) {
        requireAllNonNull(target, editedDeck);

        versionedTopDeck.updateDeck(target, editedDeck);
        changeDeck(editedDeck);
    }

    @Override
    public Deck getDeck(Deck target) {
        return versionedTopDeck.getDeck(target);
    }

    @Override
    public void addDeck(Deck deck) {
        logger.info("Added a new deck to TopDeck.");
        versionedTopDeck.addDeck(deck);
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireAllNonNull(deck);
        return versionedTopDeck.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck deck) {
        logger.info("Deleted a deck.");

        versionedTopDeck.deleteDeck(deck);
    }

    @Override
    public void updateDeck(Deck target, Deck editedDeck) {
        requireAllNonNull(target, editedDeck);
        logger.info("Updated a deck's name in TopDeck.");
        versionedTopDeck.updateDeck(target, editedDeck);
    }

    @Override
    public Deck importDeck (String filepath) {
        logger.info("Imported a deck from json file.");
        Deck imported = versionedTopDeck.importDeck(filepath);
        addDeck(imported);
        return imported;
    }

    @Override
    public String exportDeck(Deck deck) {
        logger.info("Exported a deck.");
        return versionedTopDeck.exportDeck(deck);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTopDeck() {
        return versionedTopDeck.canUndo();
    }

    @Override
    public boolean canRedoTopDeck() {
        return versionedTopDeck.canRedo();
    }

    @Override
    public void undoTopDeck() {
        versionedTopDeck.undo();
    }

    @Override
    public void redoTopDeck() {
        versionedTopDeck.redo();
    }

    @Override
    public void commitTopDeck() {
        versionedTopDeck.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedTopDeck.equals(other.versionedTopDeck) && userPrefs.equals(other.userPrefs)
                && viewState.equals(other.viewState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionedTopDeck, userPrefs, viewState);
    }
}
