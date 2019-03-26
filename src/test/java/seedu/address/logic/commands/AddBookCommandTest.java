package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class AddBookCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddBookCommand(null);
    }

    @Test
    public void execute_bookAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();

        CommandResult commandResult = new AddBookCommand(validBook).execute(modelStub, commandHistory);

        assertEquals(String.format(AddBookCommand.MESSAGE_SUCCESS, validBook), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBook), modelStub.booksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() throws Exception {
        Book validBook = new BookBuilder().build();
        AddBookCommand addCommand = new AddBookCommand(validBook);
        ModelStub modelStub = new ModelStubWithBook(validBook);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddBookCommand.MESSAGE_DUPLICATE_BOOK);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Book alice = new BookBuilder().withBookName("Alice").build();
        Book bob = new BookBuilder().withBookName("Bob").build();
        AddBookCommand addAliceCommand = new AddBookCommand(alice);
        AddBookCommand addBobCommand = new AddBookCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddBookCommand addAliceCommandCopy = new AddBookCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different book -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public Path getBookShelfFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookShelfFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addReview(Review review) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookShelf(ReadOnlyBookShelf newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBookShelf getBookShelf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReview(Review review) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReview(Review review) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBook(Book target, Book editedBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Review> getFilteredReviewList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReviewList(Predicate<Review> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoBookShelf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoBookShelf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoBookShelf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoBookShelf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitBookShelf() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Book> selectedBookProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Review> selectedReviewProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Book getSelectedBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Review getSelectedReview() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedReview(Review review) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single book.
     */
    private class ModelStubWithBook extends ModelStub {
        private final Book book;

        ModelStubWithBook(Book book) {
            requireNonNull(book);
            this.book = book;
        }

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return this.book.isSameBook(book);
        }
    }

    /**
     * A Model stub that always accept the book being added.
     */
    private class ModelStubAcceptingBookAdded extends ModelStub {
        final ArrayList<Book> booksAdded = new ArrayList<>();

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return booksAdded.stream().anyMatch(book::isSameBook);
        }

        @Override
        public void addBook(Book book) {
            requireNonNull(book);
            booksAdded.add(book);
        }

        @Override
        public void commitBookShelf() {
            // called by {@code AddBookCommand#execute()}
        }

        @Override
        public ReadOnlyBookShelf getBookShelf() {
            return new BookShelf();
        }
    }
}
