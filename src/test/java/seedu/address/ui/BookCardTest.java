package seedu.address.ui;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBook;
=======
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.BookGuiTestAssert.assertCardDisplaysBook;
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143

import org.junit.Test;

import guitests.guihandles.BookCardHandle;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

public class BookCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Book bookWithNoTags = new BookBuilder().withTags(new String[0]).build();
        BookCard bookCard = new BookCard(bookWithNoTags, 1);
        uiPartRule.setUiPart(bookCard);
<<<<<<< HEAD
        assertCardDisplay(bookCard, bookWithNoTags, 1);
=======
        assertCardDisplay(bookCard, bookWithNoTags);
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143

        // with tags
        Book bookWithTags = new BookBuilder().build();
        bookCard = new BookCard(bookWithTags, 2);
        uiPartRule.setUiPart(bookCard);
<<<<<<< HEAD
        assertCardDisplay(bookCard, bookWithTags, 2);
=======
        assertCardDisplay(bookCard, bookWithTags);
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }

    @Test
    public void equals() {
        Book book = new BookBuilder().build();
        BookCard bookCard = new BookCard(book, 0);

<<<<<<< HEAD
        // same book, same index -> returns true
=======
        // same person, same index -> returns true
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        BookCard copy = new BookCard(book, 0);
        assertTrue(bookCard.equals(copy));

        // same object -> returns true
        assertTrue(bookCard.equals(bookCard));

        // null -> returns false
        assertFalse(bookCard.equals(null));

        // different types -> returns false
        assertFalse(bookCard.equals(0));

<<<<<<< HEAD
        // different book, same index -> returns false
        Book differentBook = new BookBuilder().withBookName("differentName").build();
        assertFalse(bookCard.equals(new BookCard(differentBook, 0)));

        // same book, different index -> returns false
=======
        // different person, same index -> returns false
        Book differentBook = new BookBuilder().withBookName("differentName").build();
        assertFalse(bookCard.equals(new BookCard(differentBook, 0)));

        // same person, different index -> returns false
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        assertFalse(bookCard.equals(new BookCard(book, 1)));
    }

    /**
     * Asserts that {@code bookCard} displays the details of {@code expectedBook} correctly and matches
<<<<<<< HEAD
     * {@code expectedId}.
     */
    private void assertCardDisplay(BookCard bookCard, Book expectedBook, int expectedId) {
=======
     * {@code expectedName}.
     */
    private void assertCardDisplay(BookCard bookCard, Book expectedBook) {
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        guiRobot.pauseForHuman();

        BookCardHandle bookCardHandle = new BookCardHandle(bookCard.getRoot());

<<<<<<< HEAD
        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", bookCardHandle.getId());

        // verify book details are displayed correctly
=======
        // verify person details are displayed correctly
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        assertCardDisplaysBook(expectedBook, bookCardHandle);
    }
}
