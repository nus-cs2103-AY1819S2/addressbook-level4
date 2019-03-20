package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CardsView;
import seedu.address.logic.DecksView;
import seedu.address.logic.ListItem;
import seedu.address.logic.StudyView;
import seedu.address.logic.ViewState;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.IllegalOperationWhileReviewingDeckException;

/**
 * Represents the in-memory model of top deck data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTopDeck versionedTopDeck;
    private final UserPrefs userPrefs;
    private FilteredList<? extends ListItem> filteredItems;
    private final SimpleObjectProperty<ListItem> selectedItem = new SimpleObjectProperty<>();
    private ViewState viewState;
    private Card currentCard;

    private studyState currentStudyState = studyState.QUESTION;


    private final SimpleObjectProperty<String> textShown = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTopDeck topDeck, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(topDeck, userPrefs);

        logger.fine("Initializing with address book: " + topDeck + " and user prefs " + userPrefs);

        versionedTopDeck = new VersionedTopDeck(topDeck);
        this.userPrefs = new UserPrefs(userPrefs);
        viewState = new DecksView(this, new FilteredList<>(versionedTopDeck.getDeckList()));
        // viewState = new CardsView(this, new FilteredList<>(versionedTopDeck.getCardList()));
        // TODO: move filteredItems into viewState
        filteredItems = ((DecksView) viewState).filteredDecks;
        // filteredItems = ((CardsView) viewState).filteredCards;

        currentCard = null;
    }

    public ModelManager() {
        this(new TopDeck(), new UserPrefs());
    }

    public Command parse(String commandWord, String arguments) throws ParseException {
        return viewState.parse(commandWord, arguments);
    }

    public void changeDeck(Deck deck) {
        viewState = new CardsView(this, deck);
        // TODO: change this to above after migrating global cards list
        //viewState = new CardsView(this, new FilteredList<>(versionedTopDeck.getCardList()));

        filteredItems = ((CardsView) viewState).filteredCards;
        setSelectedItem(null);
    }

    public void studyDeck(Deck deck) {
        viewState = new StudyView(this, deck);
        setCurrentCard(deck.generateCard());
        setCurrentStudyState(studyState.QUESTION);
    }

    @Override
    public void goToDecksView() {
        viewState = new DecksView(this, new FilteredList<>(versionedTopDeck.getDeckList()));
        filteredItems = ((DecksView) viewState).filteredDecks;
        setSelectedItem(null);
    }

    @Override
    public boolean isAtDecksView() {
        return (viewState instanceof DecksView);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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
    public void setTopDeck(ReadOnlyTopDeck topDeck) {
        versionedTopDeck.resetData(topDeck);
    }

    @Override
    public ReadOnlyTopDeck getTopDeck() {
        return versionedTopDeck;
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        //return versionedTopDeck.hasCard(card);
        //TODO: Implement hasCard
        return false;
    }

    @Override
    public void deleteCard(Card target) {
        if (!(viewState instanceof CardsView)) {
            throw new IllegalOperationWhileReviewingDeckException();
        }

        CardsView cardsView = (CardsView)viewState;
        versionedTopDeck.deleteCard(target, cardsView.getActiveDeck());
        updateFilteredList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void addCard(Card card) {
        if (!(viewState instanceof CardsView)) {
            throw new IllegalOperationWhileReviewingDeckException();
        }

        CardsView cardsView = (CardsView)viewState;
        versionedTopDeck.addCard(card, cardsView.getActiveDeck());
        updateFilteredList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);
        //TODO: Implement setCard
        //versionedTopDeck.setCard(target, editedCard);
    }

    @Override
    public void addDeck(Deck deck) {
        logger.info("Added a new deck to TopDeck.");
        versionedTopDeck.addDeck(deck);
        updateFilteredList(PREDICATE_SHOW_ALL_DECKS); // TODO: show all decks after adding a deck
        commitTopDeck();
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireAllNonNull(deck);
        return versionedTopDeck.hasDeck(deck);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTopDeck}
     */
    @Override
    public ObservableList<ListItem> getFilteredList() {
        return (ObservableList<ListItem>) filteredItems;
    }

    @Override
    public void updateFilteredList(Predicate<? extends ListItem> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate((Predicate<ListItem>) predicate);
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

    //=========== Selected item ===========================================================================

    @Override
    public ReadOnlyProperty<ListItem> selectedItemProperty() {
        return selectedItem;
    }

    @Override
    public ListItem getSelectedItem() {
        return selectedItem.getValue();
    }

    @Override
    public void setSelectedItem(ListItem card) {
        if (card != null && !filteredItems.contains(card)) {
            throw new CardNotFoundException();
        }
        selectedItem.setValue(card);
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
        return versionedTopDeck.equals(other.versionedTopDeck)
                && userPrefs.equals(other.userPrefs)
                && filteredItems.equals(other.filteredItems)
                && Objects.equals(selectedItem.get(), other.selectedItem.get());
    }

    //=========== Study states ===========================================================================

    @Override
    public void setCurrentCard(Card card) {
        currentCard = card;
    }

    @Override
    public Card getCurrentCard() {
        return currentCard;
    }

    @Override
    public void setCurrentStudyState(studyState state) {
        currentStudyState = state;
    }

    @Override
    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    @Override
    public studyState getCurrentStudyState() {
        return currentStudyState;
    }

    @Override
    public void updateTextShown(){
        String text =  (currentStudyState == studyState.QUESTION)
                ? currentCard.getQuestion()
                : currentCard.getAnswer();
        textShown.setValue(text);
    }
}
