package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.storage.csvmanager.CardFolderExport;
import seedu.address.storage.csvmanager.CsvFile;

/**
 * The API of the Model component.
 */
public interface Model extends Observable {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;
    /** {@code Comparator} that sorts cards by ascending percentage score */
    Comparator<Card> COMPARATOR_ASC_SCORE_CARDS = Comparator.comparing(Card::getScore);
    /** {@code Comparator} that sorts cards by lexicographic order of questions */
    Comparator<Card> COMPARATOR_LEXICOGRAPHIC_CARDS = Comparator.comparing(Card::getQuestion);

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' folder folder file path.
     */
    Path getcardFolderFilesPath();

    /**
     * Sets the user prefs' folder folder file path.
     */
    void setcardFolderFilesPath(Path cardFolderFilesPath);

    /**
     * Replaces folder folder data with the data in {@code cardFolder}.
     */
    void resetCardFolder(ReadOnlyCardFolder cardFolder);

    /** Returns the active CardFolder */
    ReadOnlyCardFolder getActiveCardFolder();

    /** Returns all CardFolders */
    List<ReadOnlyCardFolder> getCardFolders();

    /**
     * Returns true if a folder with the same identity as {@code folder} exists in the folder folder.
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given folder.
     * The folder must exist in the folder folder.
     */
    void deleteCard(Card target);

    /**
     * Adds the given folder.
     * {@code folder} must not already exist in the folder folder.
     */
    void addCard(Card card);

    /**
     * Replaces the given folder {@code target} with {@code editedCard}.
     * {@code target} must exist in the folder folder.
     * The folder identity of {@code editedCard} must not be the same as another existing folder in the folder folder.
     */
    void setCard(Card target, Card editedCard);

    // TODO: Implement hasFolder and setFolder

    /**
     * Returns true if a folder folder with the same identity as {@code cardFolder} exists.
     */
    boolean hasFolder(CardFolder cardFolder);

    /**
     * Deletes the folder at the given index.
     * The folder must exist.
     */
    void deleteFolder(int index);

    /**
     * Adds the given folder.
     * {@code cardFolder} must not already exist.
     */
    void addFolder(CardFolder cardFolder);

    /**
     * Gets the index of the current active {@code CardFolder}.
     */
    int getActiveCardFolderIndex();

    /**
     * Sets the index of the current active {@code CardFolder}.
     */
    void setActiveCardFolderIndex(int newIndex);

    /**
     * Sets the Model back to the home directory, outside of any cardfolder.
     */
    void exitFoldersToHome();

    boolean isInFolder();

    /** Returns an unmodifiable view of the filtered folder list */
    ObservableList<Card> getFilteredCards();

    /** Returns an unmodifiable view of the filtered folders list */
    ObservableList<VersionedCardFolder> getFilteredFolders();

    /**
     * Updates the filter of the filtered folder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCard(Predicate<Card> predicate);

    /**
     * Updates the filter of the filtered folder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void sortFilteredCard(Comparator<Card> cardComparator);

    /**
     * Returns true if the model has previous folder folder states to restore.
     */
    boolean canUndoActiveCardFolder();

    /**
     * Returns true if the model has undone folder folder states to restore.
     */
    boolean canRedoActiveCardFolder();

    /**
     * Restores the model's folder folder to its previous state.
     */
    void undoActiveCardFolder();

    /**
     * Restores the model's folder folder to its previously undone state.
     */
    void redoActiveCardFolder();

    /**
     * Saves the current folder folder state for undo/redo.
     */
    void commitActiveCardFolder();

    /**
     * Selected folder in the filtered folder list.
     * null if no folder is selected.
     */
    ReadOnlyProperty<Card> selectedCardProperty();

    /**
     * Returns the selected folder in the filtered folder list.
     * null if no folder is selected.
     */
    Card getSelectedCard();

    /**
     * Sets the selected folder in the filtered folder list.
     */
    void setSelectedCard(Card card);

    /**
     * Checks whether list of folder folder names specified is found inside model
     */
    List<ReadOnlyCardFolder> returnValidCardFolders(Set<CardFolderExport> cardFolders);

    /**
     * Enters a test session using the specified folder folder index.
     */
    void testCardFolder(int cardFolderToTestIndex);

    /**
     * Sets the current folder in the test session.
     */
    void setCurrentTestedCard(Card card);

    /**
     * Returns the current folder in the test session
     * null if there is no cards in folder or user is not in a test session.
     */
    Card getCurrentTestedCard();

    /**
     * Returns true if user is in a test session
     * false user is not.
     */
    boolean checkIfInsideTestSession();

    /**
     * End the current test session.
     */
    void endTestSession();

    /**
     * Returns true if the given answer is right
     * false if answer is wrong
     */
    boolean markAttemptedAnswer(Answer attemptedAnswer);

    /**
     * Set cardAlreadyAnswered variable to true to indicate current folder as answered
     */
    void setCardAsAnswered();

    /**
     * Returns true if the answer has already been input for that folder
     * false if otherwise
     */
    boolean checkIfCardAlreadyAnswered();

    void exportCardFolders(Set<CardFolderExport> cardFolderExports, CsvFile csvFile) throws IOException;

    void importCardFolders(CsvFile csvFile) throws IOException;
}
