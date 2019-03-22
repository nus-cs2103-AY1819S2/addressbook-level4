package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the book shelf data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedBookShelf versionedBookShelf;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Book> filteredBooks;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given book shelf and userPrefs.
     */
    public ModelManager(ReadOnlyBookShelf bookShelf, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(bookShelf, userPrefs);

        logger.fine("Initializing with book shelf: " + bookShelf + " and user prefs " + userPrefs);

        versionedBookShelf = new VersionedBookShelf(bookShelf);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedBookShelf.getPersonList());
        filteredBooks = new FilteredList<>(versionedBookShelf.getBookList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
        filteredBooks.addListener(this::ensureSelectedBookIsValid);
    }

    public ModelManager() {
        this(new BookShelf(), new UserPrefs());
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
    public Path getBookShelfFilePath() {
        return userPrefs.getBookShelfFilePath();
    }

    @Override
    public void setBookShelfFilePath(Path bookShelfFilePath) {
        requireNonNull(bookShelfFilePath);
        userPrefs.setBookShelfFilePath(bookShelfFilePath);
    }

    //=========== BookShelf ================================================================================

    @Override
    public void setBookShelf(ReadOnlyBookShelf bookShelf) {
        versionedBookShelf.resetData(bookShelf);
    }

    @Override
    public ReadOnlyBookShelf getBookShelf() {
        return versionedBookShelf;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedBookShelf.hasPerson(person);
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return versionedBookShelf.hasBook(book);
    }

    @Override
    public void deletePerson(Person target) {
        versionedBookShelf.removePerson(target);
    }

    public void deleteBook(Book target) {
        versionedBookShelf.removeBook(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedBookShelf.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addBook(Book book) {
        versionedBookShelf.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedBookShelf.setPerson(target, editedPerson);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);
        versionedBookShelf.setBook(target, editedBook);
    }

    @Override
    public void deleteTag(Tag tag) {
        versionedBookShelf.removeTag(tag);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedBookShelf}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return filteredBooks;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoBookShelf() {
        return versionedBookShelf.canUndo();
    }

    @Override
    public boolean canRedoBookShelf() {
        return versionedBookShelf.canRedo();
    }

    @Override
    public void undoBookShelf() {
        versionedBookShelf.undo();
    }

    @Override
    public void redoBookShelf() {
        versionedBookShelf.redo();
    }

    @Override
    public void commitBookShelf() {
        versionedBookShelf.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public ReadOnlyProperty<Book> selectedBookProperty() {
        return selectedBook;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public Book getSelectedBook() {
        return selectedBook.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    @Override
    public void setSelectedBook(Book book) {
        if (book != null && !filteredBooks.contains(book)) {
            throw new BookNotFoundException();
        }
        selectedBook.setValue(book);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedBook} is a valid book in {@code filteredBooks}.
     */
    private void ensureSelectedBookIsValid(ListChangeListener.Change<? extends Book> change) {
        while (change.next()) {
            if (selectedBook.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedBookReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedBook.getValue());
            if (wasSelectedBookReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedBook.getValue());
                selectedBook.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedBookRemoved = change.getRemoved().stream()
                    .anyMatch(removedBook -> selectedBook.getValue().isSameBook(removedBook));
            if (wasSelectedBookRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedBook.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
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
        return versionedBookShelf.equals(other.versionedBookShelf)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }
}
