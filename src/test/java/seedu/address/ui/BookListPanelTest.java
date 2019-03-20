package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.BookTypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.ui.testutil.BookGuiTestAssert.assertCardDisplaysBook;
import static seedu.address.ui.testutil.BookGuiTestAssert.assertCardEquals;

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
        FXCollections.observableList(getTypicalBooks());

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
            assertEquals(expectedBook + ". ", actualCard.getName());
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
        initUi(TYPICAL_BOOKS);
        Book secondBook = TYPICAL_BOOKS.get(INDEX_SECOND_BOOK.getZeroBased());
        guiRobot.interact(() -> selectedBook.set(secondBook));
        guiRobot.pauseForHuman();

        BookCardHandle expectedBook = bookListPanelHandle.getBookCardHandle(INDEX_SECOND_BOOK.getZeroBased());
        BookCardHandle selectedBook = bookListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedBook, selectedBook);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Book> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of books containing {@code bookCount} books that is used to populate the
     * {@code BookListPanel}.
     */
    private ObservableList<Book> createBackingList(int bookCount) {
        ObservableList<Book> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < bookCount; i++) {

            BookName name = new BookName(i + "a");
            Author author = new Author("john" + i);
            Rating rating = new Rating(Integer.toString(i % 10));

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
            new BookListPanel(backingList, selectedBook, selectedBook::set);
        uiPartRule.setUiPart(bookListPanel);

        bookListPanelHandle = new BookListPanelHandle(getChildNode(bookListPanel.getRoot(),
            BookListPanelHandle.BOOK_LIST_VIEW_ID));
    }
}
