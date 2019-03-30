package seedu.address.logic.commands;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ReviewBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_ALICE;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelfWithReview;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

public class AddReviewCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReviewCommand(null);
    }

    @Test
    public void execute_reviewAcceptedByModel_addSuccessful() throws Exception {
        Book bookToAddReview = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Review toAdd = new Review(new ReviewTitle(VALID_REVIEWTITLE_ALICE),
                bookToAddReview.getBookName(), VALID_REVIEWMESSAGE_ALICE);
        AddReviewCommand addReviewCommand = new AddReviewCommand(toAdd);

        String expectedMessage = String.format(AddReviewCommand.MESSAGE_SUCCESS, toAdd.getTitle().fullName);
        ModelManager expectedModel = new ModelManager(getTypicalBookShelfWithReview(toAdd), new UserPrefs());
        expectedModel.commitBookShelf();

        CommandResult commandResult = new AddReviewCommand(toAdd).execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateReview_throwsCommandException() throws Exception {
        Book bookToAddReview = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Review toAdd = new Review(new ReviewTitle(VALID_REVIEWTITLE_ALICE),
                bookToAddReview.getBookName(), VALID_REVIEWMESSAGE_ALICE);

        AddReviewCommand addReviewCommand = new AddReviewCommand(toAdd);
        addReviewCommand.execute(model, commandHistory);


        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReviewCommand.MESSAGE_DUPLICATE_REVIEW);
        addReviewCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Review alice = new ReviewBuilder().withBookName("Alice").build();
        Review bob = new ReviewBuilder().withBookName("Bob").build();
        AddReviewCommand addAliceCommand = new AddReviewCommand(alice);
        AddReviewCommand addBobCommand = new AddReviewCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddReviewCommand addAliceCommandCopy = new AddReviewCommand(alice);
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
        public void setBookShelfFilePath(Path bookShelfFilePath) {
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
        public boolean hasBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReview(Review review) {
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
        public void setBook(Book target, Book editedBook) {
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
        public ReadOnlyProperty<Book> selectedBookProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Review> selectedReviewProperty() {
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

        @Override
        public void sortBook(String type, String order) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single review.
     */
    private class ModelStubWithReview extends AddReviewCommandTest.ModelStub {
        private final Review review;

        ModelStubWithReview(Review review) {
            requireNonNull(review);
            this.review = review;
        }

        @Override
        public boolean hasReview(Review review) {
            requireNonNull(review);
            return this.review.equals(review);
        }
    }

    /**
     * A Model stub that always accept the review being added.
     */
    private class ModelStubAcceptingReviewAdded extends AddReviewCommandTest.ModelStub {
        final ArrayList<Review> reviewsAdded = new ArrayList<>();

        @Override
        public boolean hasReview(Review review) {
            requireNonNull(review);
            return reviewsAdded.stream().anyMatch(review::equals);
        }

        @Override
        public void addReview(Review review) {
            requireNonNull(review);
            reviewsAdded.add(review);
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
