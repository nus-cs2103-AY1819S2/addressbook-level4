package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.ALICE_REVIEW;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.book.exceptions.DuplicateReviewException;
import seedu.address.model.person.Person;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.ReviewBuilder;

public class BookShelfTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BookShelf bookShelf = new BookShelf();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), bookShelf.getBookList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookShelf.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyBookShelf_replacesData() {
        BookShelf newData = getTypicalBookShelf();
        bookShelf.resetData(newData);
        assertEquals(newData, bookShelf);
    }

    @Test
    public void resetData_withDuplicateBooks_throwsDuplicateBookException() {
        // Two books with the same identity fields
        Book editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_ALICE)
                .withBookName(VALID_BOOKNAME_ALICE)
                .withTags(VALID_TAG_FANTASY)
                .build();
        List<Book> newBooks = Arrays.asList(ALI, editedAlice);
        List<Review> newReviews = new ArrayList<>();
        BookShelfStub newData = new BookShelfStub(newBooks, newReviews, 1);

        thrown.expect(DuplicateBookException.class);
        bookShelf.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateReviews_throwsDuplicateReviewwException() {
        // Two reviews with the same identity fields
        Review editedAliceReview = new ReviewBuilder().withReviewTitle(VALID_REVIEWTITLE_ALICE)
                .withBookName(VALID_BOOKNAME_ALICE)
                .withReviewMessage(VALID_REVIEWMESSAGE_ALICE)
                .build();
        List<Book> newBooks = new ArrayList<>();
        List<Review> newReviews = Arrays.asList(ALICE_REVIEW, editedAliceReview);
        BookShelfStub newData = new BookShelfStub(newBooks, newReviews, 1);

        thrown.expect(DuplicateReviewException.class);
        bookShelf.resetData(newData);
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookShelf.hasBook(null);
    }

    @Test
    public void hasReview_nullReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookShelf.hasReview(null);
    }

    @Test
    public void hasBook_bookNotInBookShelf_returnsFalse() {
        assertFalse(bookShelf.hasBook(ALI));
    }

    @Test
    public void hasReview_reviewNotInBookShelf_returnsFalse() {
        assertFalse(bookShelf.hasReview(ALICE_REVIEW));
    }

    @Test
    public void hasBook_bookInBookShelf_returnsTrue() {
        bookShelf.addBook(ALI);
        assertTrue(bookShelf.hasBook(ALI));
    }

    @Test
    public void hasReview_reviewInBookShelf_returnsTrue() {
        bookShelf.addReview(ALICE_REVIEW);
        assertTrue(bookShelf.hasReview(ALICE_REVIEW));
    }

    @Test
    public void hasBook_bookWithSameIdentityFieldsInBookShelf_returnsTrue() {
        bookShelf.addBook(ALI);
        Book editedAlice = new BookBuilder(ALI).withAuthor(VALID_AUTHOR_CS).withTags(VALID_TAG_TEXTBOOK)
                .build();
        assertTrue(bookShelf.hasBook(editedAlice));
    }

    @Test
    public void hasReview_reviewWithSameIdentityFieldsInBookShelf_returnsTrue() {
        bookShelf.addReview(ALICE_REVIEW);
        Review editedAliceReview = new ReviewBuilder().withReviewTitle(VALID_REVIEWTITLE_ALICE)
                .withBookName(VALID_BOOKNAME_ALICE).withReviewMessage(VALID_REVIEWMESSAGE_ALICE).build();
        assertTrue(bookShelf.hasReview(editedAliceReview));
    }

    @Test
    public void getBookShelf_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        bookShelf.getBookList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        bookShelf.addListener(listener);
        bookShelf.addBook(ALI);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        bookShelf.addListener(listener);
        bookShelf.removeListener(listener);
        bookShelf.addBook(ALI);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyBookShelf whose persons / books list can violate interface constraints.
     */
    private static class BookShelfStub implements ReadOnlyBookShelf {
        private final ObservableList<Book> books = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Review> reviews = FXCollections.observableArrayList();

        BookShelfStub(Collection<Book> books, Collection<Review> reviews, int distinguish) {
            this.books.setAll(books);
            this.reviews.setAll(reviews);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }

        @Override
        public ObservableList<Review> getReviewList() {
            return reviews;
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

}
