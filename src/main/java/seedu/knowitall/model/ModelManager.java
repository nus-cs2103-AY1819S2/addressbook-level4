package seedu.knowitall.model;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.commons.core.LogsCenter;
import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.commons.util.InvalidationListenerManager;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Score;
import seedu.knowitall.model.card.exceptions.CardNotFoundException;
import seedu.knowitall.storage.csvmanager.CsvFile;
import seedu.knowitall.storage.csvmanager.CsvManager;
import seedu.knowitall.storage.csvmanager.exceptions.CsvManagerNotInitialized;
import seedu.knowitall.storage.csvmanager.exceptions.IncorrectCsvHeadersException;

/**
 * Represents the in-memory model of the card folder data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    private final UserPrefs userPrefs;

    private State state;

    //Card related
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();

    // CardFolder related
    private ObservableList<VersionedCardFolder> folders;
    private final FilteredList<VersionedCardFolder> filteredFolders;
    private final List<FilteredList<Card>> filteredCardsList;
    private int activeCardFolderIndex; // set to -1 when in home directory

    // Test Session related
    private final SimpleObjectProperty<Card> currentTestedCard = new SimpleObjectProperty<>();
    private ObservableList<Card> currentTestedCardFolder;
    private int currentTestedCardFolderSize;
    private int currentTestedCardIndex;
    private boolean cardAlreadyAnswered = false;
    private int numAnsweredCorrectly = 0;
    private int numAnsweredTotal = 0;

    // Export related
    private CsvManager csvManager;
    {
        try {
            csvManager = new CsvManager();
        } catch (IOException e) {
            csvManager = null;
            logger.warning("Unable to carry out import and export of card folders");
        }
    }

    /**
     * Initializes a ModelManager with the given {@code cardFolders} and {@code userPrefs}.
     */
    public ModelManager(List<ReadOnlyCardFolder> cardFolders, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardFolders, userPrefs);

        logger.fine("Initializing with card folder: " + cardFolders + " and user prefs " + userPrefs);

        List<VersionedCardFolder> versionedCardFolders = new ArrayList<>();
        for (ReadOnlyCardFolder cardFolder : cardFolders) {
            versionedCardFolders.add(new VersionedCardFolder(cardFolder));
        }
        folders = FXCollections.observableArrayList(versionedCardFolders);
        filteredFolders = new FilteredList<>(folders);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredCardsList = new ArrayList<>();
        for (VersionedCardFolder filteredFolder : filteredFolders) {
            FilteredList<Card> filteredCards = new FilteredList<>(filteredFolder.getCardList());
            filteredCardsList.add(filteredCards);
            filteredCards.addListener(this::ensureSelectedCardIsValid);
        }


        // ModelManager initialises to home directory
        activeCardFolderIndex = -1;
        state = State.IN_HOMEDIR;
    }

    /**
     * Initalizes a ModelManager with the given {@code cardFolders} and default {@code UserPrefs}.
     */
    public ModelManager(List<ReadOnlyCardFolder> cardFolders) {
        this(cardFolders, new UserPrefs());
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
    public Path getcardFolderFilesPath() {
        return userPrefs.getcardFolderFilesPath();
    }

    @Override
    public void setcardFolderFilesPath(Path cardFolderFilesPath) {
        requireNonNull(cardFolderFilesPath);
        userPrefs.setcardFolderFilesPath(cardFolderFilesPath);
    }

    //=========== CardFolder ================================================================================

    @Override
    public void resetCardFolder(ReadOnlyCardFolder cardFolder) {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.resetData(cardFolder);
    }

    @Override
    public String getActiveCardFolderName() {
        return getActiveCardFolder().getFolderName();
    }

    @Override
    public ReadOnlyCardFolder getActiveCardFolder() {
        return getActiveVersionedCardFolder();
    }

    @Override
    public List<ReadOnlyCardFolder> getCardFolders() {
        return new ArrayList<>(filteredFolders);
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);

        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        return versionedCardFolder.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.removeCard(target);
    }

    @Override
    public void addCard(Card card) {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.addCard(card);
        updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.setCard(target, editedCard);
    }

    @Override
    public boolean hasFolder(String name) {
        requireNonNull(name);

        return folders.stream()
                .map(folder -> folder.getFolderName().toLowerCase())
                .anyMatch(folderName -> folderName.equals(name.toLowerCase()));
    }

    @Override
    public void deleteFolder(int index) {
        assert(index < folders.size());

        folders.remove(index);
        filteredCardsList.remove(index);
        indicateModified();
    }

    @Override
    public void addFolder(CardFolder cardFolder) {
        requireNonNull(cardFolder);

        if (hasFolder(cardFolder.getFolderName())) {
            throw new DuplicateCardFolderException();
        }

        VersionedCardFolder versionedCardFolder = new VersionedCardFolder(cardFolder);
        folders.add(versionedCardFolder);
        FilteredList<Card> filteredCards = new FilteredList<>(versionedCardFolder.getCardList());
        filteredCardsList.add(filteredCards);
        filteredCards.addListener(this::ensureSelectedCardIsValid);
        indicateModified();
    }

    @Override
    public void renameFolder(int index, String newName) {
        CardFolder folderToRename = folders.get(index);
        folderToRename.rename(newName);
        indicateModified();
    }

    @Override
    public void enterFolder(int index) {
        state = State.IN_FOLDER;
        activeCardFolderIndex = index;
    }

    @Override
    public void exitFolderToHome() {
        state = State.IN_HOMEDIR;
        activeCardFolderIndex = -1;
        removeSelectedCard();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    @Override
    public int getActiveCardFolderIndex() {
        return activeCardFolderIndex;
    }

    /**
     * Returns the active {@code CardFolder}
     */
    private VersionedCardFolder getActiveVersionedCardFolder() {
        return folders.get(activeCardFolderIndex);
    }

    /**
     * Notifies listeners that the list of card folders has been modified.
     */
    private void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //=========== Filtered Card List Accessors =============================================================

    @Override
    public List<FilteredList<Card>> getFilteredCardsList() {
        List<FilteredList<Card>> copy = new ArrayList<>();
        for (FilteredList<Card> filteredList : filteredCardsList) {
            copy.add(new FilteredList<>(filteredList));
        }
        return copy;
    }

    @Override
    public FilteredList<Card> getActiveFilteredCards() {
        return new FilteredList<>(filteredCardsList.get(activeCardFolderIndex));
    }

    @Override
    public ObservableList<VersionedCardFolder> getFilteredFolders() {
        return new FilteredList<>(filteredFolders);
    }

    @Override
    public void updateFilteredCard(Predicate<Card> predicate) {
        requireNonNull(predicate);
        FilteredList<Card> filteredCards = filteredCardsList.get(activeCardFolderIndex);
        filteredCards.setPredicate(predicate);
    }
    @Override
    public void sortFilteredCard(Comparator<Card> cardComparator) {
        requireNonNull(cardComparator);
        folders.get(activeCardFolderIndex).sortCards(cardComparator);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoActiveCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        return versionedCardFolder.canUndo();
    }

    @Override
    public boolean canRedoActiveCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        return versionedCardFolder.canRedo();
    }

    @Override
    public void undoActiveCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.undo();
    }

    @Override
    public void redoActiveCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.redo();
    }

    @Override
    public void commitActiveCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.commit();
    }

    //=========== Report Displayed =======================================================================

    @Override
    public void enterReportDisplay() {
        state = State.IN_REPORT;
        this.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);
    }

    @Override
    public void exitReportDisplay() {
        state = State.IN_FOLDER;
    }



    //=========== Test Session ===========================================================================

    @Override
    public void startTestSession() {
        currentTestedCardFolder = getActiveCardFolder().getCardList();
        if (currentTestedCardFolder.isEmpty()) {
            throw new EmptyCardFolderException();
        }
        currentTestedCardFolderSize = currentTestedCardFolder.size();

        sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);

        currentTestedCardIndex = 0;
        Card cardToTest = currentTestedCardFolder.get(currentTestedCardIndex);
        setCurrentTestedCard(cardToTest);
        state = State.IN_TEST;
        numAnsweredCorrectly = 0;
        numAnsweredTotal = 0;
    }

    public ObservableList<Card> getCurrentTestedCardFolder() {
        return currentTestedCardFolder;
    }

    public int getNumAnsweredCorrectly() {
        return numAnsweredCorrectly;
    }

    @Override
    public void setCurrentTestedCard(Card card) {
        if (card != null && !getActiveFilteredCards().contains(card)) {
            throw new CardNotFoundException();
        }
        currentTestedCard.setValue(card);
    }

    @Override
    public Card getCurrentTestedCard() {
        return currentTestedCard.getValue();
    }

    @Override
    public void endTestSession() {
        int minimumNumberAnswered = getActiveCardFolder().getCardList().size() / MIN_FRACTION_ANSWERED_TO_COUNT;
        System.out.println("min num to be ans" + minimumNumberAnswered);
        System.out.println("num answered" + numAnsweredTotal);
        if (numAnsweredTotal >= minimumNumberAnswered) {
            getActiveVersionedCardFolder()
                    .addFolderScore((double) numAnsweredCorrectly / numAnsweredTotal);
        }
        getActiveVersionedCardFolder().resetStates();
        state = State.IN_FOLDER;
        setCardAsNotAnswered();
        numAnsweredCorrectly = 0;
        numAnsweredTotal = 0;
        setCurrentTestedCard(null);
        currentTestedCardFolder = null;
        currentTestedCardFolderSize = 0;
    }

    @Override
    public boolean markAttemptedAnswer(Answer attemptedAnswer) {
        Answer correctAnswer = currentTestedCard.getValue().getAnswer();
        String correctAnswerInCapitals = correctAnswer.toString().toUpperCase();
        String attemptedAnswerInCapitals = attemptedAnswer.toString().toUpperCase();
        numAnsweredTotal++;
        if (correctAnswerInCapitals.equals(attemptedAnswerInCapitals)) {
            numAnsweredCorrectly++;
            return true;
        }
        return false;
    }

    @Override
    public boolean markAttemptedMcqAnswer(int answerIndex) {
        numAnsweredTotal++;
        if (answerIndex == currentTestedCard.getValue().getAnswerIndex()) {
            numAnsweredCorrectly++;
            return true;
        }
        return false;
    }

    @Override
    public Card createScoredCard(Card cardToMark, boolean markCorrect) {
        Score newScore;
        if (markCorrect) {
            newScore = new Score(cardToMark.getScore().correctAttempts + 1,
                    cardToMark.getScore().totalAttempts + 1);
        } else {
            newScore = new Score(cardToMark.getScore().correctAttempts,
                    cardToMark.getScore().totalAttempts + 1);
        }
        return new Card(cardToMark.getQuestion(), cardToMark.getAnswer(), newScore, cardToMark.getOptions(),
                cardToMark.getHints());
    }

    @Override
    public void setCardAsAnswered() {
        cardAlreadyAnswered = true;
    }

    private void setCardAsNotAnswered() {
        cardAlreadyAnswered = false;
    }

    @Override
    public boolean isCardAlreadyAnswered() {
        return cardAlreadyAnswered;
    }

    @Override
    public boolean testNextCard() {
        currentTestedCardIndex += 1;
        if (currentTestedCardIndex == currentTestedCardFolderSize) {
            return false;
        }
        Card cardToTest = currentTestedCardFolder.get(currentTestedCardIndex);
        setCurrentTestedCard(cardToTest);
        setCardAsNotAnswered();
        return true;
    }

    //=========== Selected card ===========================================================================

    @Override
    public ReadOnlyProperty<Card> selectedCardProperty() {
        return selectedCard;
    }

    @Override
    public Card getSelectedCard() {
        return selectedCard.getValue();
    }

    @Override
    public void setSelectedCard(Card card) {
        if (card != null && !getActiveFilteredCards().contains(card)) {
            throw new CardNotFoundException();
        }
        selectedCard.setValue(card);
    }

    @Override
    public void removeSelectedCard() {
        selectedCard.setValue(null);
    }

    /**
     * Ensures {@code selectedCard} is a valid card in {@code filteredCardsList}.
     */
    private void ensureSelectedCardIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedCard.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedCardReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedCard.getValue());
            if (wasSelectedCardReplaced) {
                // Update selectedCard to its new value.
                int index = change.getRemoved().indexOf(selectedCard.getValue());
                selectedCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedCardRemoved = change.getRemoved().stream()
                    .anyMatch(removedCard -> selectedCard.getValue().isSameCard(removedCard));
            if (wasSelectedCardRemoved) {
                // Select the card that came before it in the list,
                // or clear the selection if there is no such card.
                selectedCard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public State getState() {
        return state;
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
        return filteredFolders.equals(other.filteredFolders)
                && userPrefs.equals(other.userPrefs)
                && filteredCardsList.equals(other.filteredCardsList)
                && Objects.equals(selectedCard.get(), other.selectedCard.get())
                && state == other.state
                && currentTestedCardIndex == other.currentTestedCardIndex
                && cardAlreadyAnswered == other.cardAlreadyAnswered
                && activeCardFolderIndex == other.activeCardFolderIndex;
    }


    //=========== Export / Import card folders ========================================================================
    @Override
    public void exportCardFolders(List<Integer> cardFolderExports) throws IOException, CsvManagerNotInitialized {
        if (csvManager == null) {
            throw new CsvManagerNotInitialized(Messages.MESSAGE_CSV_MANAGER_NOT_INITIALIZED);
        }
        List<ReadOnlyCardFolder> cardFolders = returnValidCardFolders(cardFolderExports);
        csvManager.writeFoldersToCsv(cardFolders);
    }

    @Override
    public void importCardFolders(CsvFile csvFile) throws IOException, CommandException, IllegalArgumentException,
            IncorrectCsvHeadersException {
        String cardFolderName = csvFile.getFileNameWithoutExt();

        if (isCardFolderExists(cardFolderName)) {
            throw new DuplicateCardFolderException();
        }

        CardFolder cardFolder = csvManager.readFoldersFromCsv(csvFile);
        addFolder(cardFolder);
    }

    /**
     * checks whether cardfolder already exists in the model when importing file
     */
    private boolean isCardFolderExists(String cardFolderName) {
        for (CardFolder cardFolder : folders) {
            if (cardFolderName.equals(cardFolder.getFolderName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setTestCsvPath(String path) {
        csvManager.setTestDefaultPath(path);
    }

    @Override
    public String getDefaultPath() {
        return csvManager.getDefaultPath();
    }

    /**
     * returns the corresponding {@code List<ReadOnlyCardFolder>} from the list of integer indexes
     */
    private List<ReadOnlyCardFolder> returnValidCardFolders(List<Integer> cardFolderExports) {
        List<ReadOnlyCardFolder> readOnlyCardFolders = new ArrayList<>();
        List<Index> indexList = cardFolderExports.stream().map(Index::fromOneBased).collect(Collectors.toList());
        for (Index index : indexList) {
            try {
                ReadOnlyCardFolder cardFolder = folders.get(index.getZeroBased());
                readOnlyCardFolders.add(cardFolder);
            } catch (IndexOutOfBoundsException e) {
                throw new CardFolderNotFoundException(index.displayIndex());
            }
        }
        return readOnlyCardFolders;
    }
}
