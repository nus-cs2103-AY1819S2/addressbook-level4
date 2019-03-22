package seedu.address.ui;

import static java.time.Duration.ofMillis;
<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBook;
=======
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.BookTypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.ui.testutil.BookGuiTestAssert.assertCardDisplaysBook;
import static seedu.address.ui.testutil.BookGuiTestAssert.assertCardEquals;
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.BookCardHandle;
import guitests.guihandles.BookListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;

public class BookListPanelTest extends GuiUnitTest {
    private static final ObservableList<Book> TYPICAL_BOOKS =
<<<<<<< HEAD
            FXCollections.observableList(getTypicalBooks());
=======
        FXCollections.observableList(getTypicalBooks());
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();
    private BookListPanelHandle bookListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_BOOKS);

        for (int i = 0; i < TYPICAL_BOOKS.size(); i++) {
            bookListPanelHandle.navigateToCard(TYPICAL_BOOKS.get(i));
            Book expectedBook = TYPICAL_BOOKS.get(i);
            BookCardHandle actualCard = bookListPanelHandle.getBookCardHandle(i);

            assertCardDisplaysBook(expectedBook, actualCard);
<<<<<<< HEAD
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /*
    @Test
    public void selection_modelSelectedBookChanged_selectionChanges() {
=======
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        initUi(TYPICAL_BOOKS);
        Book secondBook = TYPICAL_BOOKS.get(INDEX_SECOND_BOOK.getZeroBased());
        guiRobot.interact(() -> selectedBook.set(secondBook));
        guiRobot.pauseForHuman();

        BookCardHandle expectedBook = bookListPanelHandle.getBookCardHandle(INDEX_SECOND_BOOK.getZeroBased());
        BookCardHandle selectedBook = bookListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedBook, selectedBook);
    }
<<<<<<< HEAD
    */

    /**
     * Verifies that creating and deleting large number of books in {@code BookListPanel} requires lesser than
=======

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Book> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
<<<<<<< HEAD
        }, "Creation and deletion of book cards exceeded time limit");
=======
        }, "Creation and deletion of person cards exceeded time limit");
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }

    /**
     * Returns a list of books containing {@code bookCount} books that is used to populate the
     * {@code BookListPanel}.
     */
    private ObservableList<Book> createBackingList(int bookCount) {
        ObservableList<Book> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < bookCount; i++) {
<<<<<<< HEAD
            BookName name = new BookName(i + "a");
            Author author = new Author("abc");
            Rating rating = new Rating("1");
=======

            BookName name = new BookName(i + "a");
            Author author = new Author("john" + i);
            Rating rating = new Rating(Integer.toString(i % 10));

>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
            Book book = new Book(name, author, rating, Collections.emptySet());
            backingList.add(book);
        }
        return backingList;
    }

    /**
     * Initializes {@code bookListPanelHandle} with a {@code BookListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code BookListPanel}.
     */
    private void initUi(ObservableList<Book> backingList) {
        BookListPanel bookListPanel =
<<<<<<< HEAD
                new BookListPanel(backingList, selectedBook, selectedBook::set);
        uiPartRule.setUiPart(bookListPanel);

        bookListPanelHandle = new BookListPanelHandle(getChildNode(bookListPanel.getRoot(),
                BookListPanelHandle.BOOK_LIST_VIEW_ID));
=======
            new BookListPanel(backingList, selectedBook, selectedBook::set);
        uiPartRule.setUiPart(bookListPanel);

        bookListPanelHandle = new BookListPanelHandle(getChildNode(bookListPanel.getRoot(),
            BookListPanelHandle.BOOK_LIST_VIEW_ID));
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }
}
