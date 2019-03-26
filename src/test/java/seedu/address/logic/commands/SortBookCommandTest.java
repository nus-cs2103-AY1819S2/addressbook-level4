package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_EXCEPTION;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITHOUT_PREFIX;

import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;


public class SortBookCommandTest {

    private final Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        ObservableList<Book> bookList = model.getBookShelf().getBookList();

        model.sortBook(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        checkAuthor(bookList, ORDER_ASC_WITHOUT_PREFIX);

        model.sortBook(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        checkAuthor(bookList, ORDER_DES_WITHOUT_PREFIX);

        model.sortBook(SORT_NAME_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        checkName(bookList, ORDER_ASC_WITHOUT_PREFIX);

        model.sortBook(SORT_NAME_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        checkName(bookList, ORDER_DES_WITHOUT_PREFIX);

        model.sortBook(SORT_RATING_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        checkRating(bookList, ORDER_ASC_WITHOUT_PREFIX);

        model.sortBook(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        checkRating(bookList, ORDER_DES_WITHOUT_PREFIX);
    }

    /**
     * Check the list sort by author is correctly sorted
     * @param bookList that need to be check
     * @param order either asc or des order
     * @throws Exception
     */
    private void checkAuthor(ObservableList<Book> bookList, String order) throws Exception {
        String firstAuthor = getAuthor(bookList, 0);
        for (int i = 0; i < bookList.size(); i++) {
            if (order.equals(ORDER_ASC_WITHOUT_PREFIX) && firstAuthor.compareTo(getAuthor(bookList, i)) > 0) {
                throw new Exception(SORT_EXCEPTION);
            } else if (order.equals(ORDER_DES_WITHOUT_PREFIX) && firstAuthor.compareTo(getAuthor(bookList, i)) < 0) {
                throw new Exception(SORT_EXCEPTION);
            }
        }
    }

    /**
     * Check the list sort by bookName is correctly sorted
     * @param bookList that need to be check
     * @param order either asc or des order
     * @throws Exception
     */
    private void checkName(ObservableList<Book> bookList, String order) throws Exception {
        String firstName = getName(bookList, 0);
        for (int i = 0; i < bookList.size(); i++) {
            if (order.equals(ORDER_ASC_WITHOUT_PREFIX) && firstName.compareTo(getName(bookList, i)) > 0) {
                throw new Exception(SORT_EXCEPTION);
            } else if (order.equals(ORDER_DES_WITHOUT_PREFIX) && firstName.compareTo(getName(bookList, i)) < 0) {
                throw new Exception(SORT_EXCEPTION);
            }
        }
    }

    /**
     * Check the list sort by rating is correctly sorted
     * @param bookList that need to be check
     * @param order either asc or des order
     * @throws Exception
     */
    private void checkRating(ObservableList<Book> bookList, String order) throws Exception {
        int firstRating = getRating(bookList, 0);
        for (int i = 0; i < bookList.size(); i++) {
            if (order.equals(ORDER_ASC_WITHOUT_PREFIX) && firstRating > getRating(bookList, i)) {
                throw new Exception(SORT_EXCEPTION);
            } else if (order.equals(ORDER_DES_WITHOUT_PREFIX) && firstRating < getRating(bookList, i)) {
                throw new Exception(SORT_EXCEPTION);
            }
        }
    }
    private String getAuthor(ObservableList<Book> bookList, int i) {
        return bookList.get(i).getAuthor().fullName.toLowerCase();
    }

    private String getName(ObservableList<Book> bookList, int i) {
        return bookList.get(i).getBookName().fullName.toLowerCase();
    }

    private int getRating(ObservableList<Book> bookList, int i) {
        return Integer.valueOf(bookList.get(i).getRating().value);
    }
}
