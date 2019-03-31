package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_EXCEPTION;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITHOUT_PREFIX;

import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.parser.SortBookCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;

public class SortBookCommandTest {

    private static final int FIRST = 0;
    private final Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        ObservableList<Book> bookList = model.getBookShelf().getBookList();

        List<String> sortTypes = new ArrayList<>();
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);

        // check main order author
        checkOrder(bookList, sortTypes, ORDER_ASC_WITHOUT_PREFIX, null);
        checkOrder(bookList, sortTypes, ORDER_DES_WITHOUT_PREFIX, null);

        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_NAME_WITHOUT_PREFIX);

        // check main order author
        checkOrder(bookList, sortTypes, ORDER_ASC_WITHOUT_PREFIX, null);
        checkOrder(bookList, sortTypes, ORDER_DES_WITHOUT_PREFIX, null);

        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);

        // check main order rating
        checkOrder(bookList, sortTypes, ORDER_ASC_WITHOUT_PREFIX, null);
        checkOrder(bookList, sortTypes, ORDER_DES_WITHOUT_PREFIX, null);

        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);

        Map<String, String> map = new HashMap<>();
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        checkOrder(bookList, sortTypes, null, map);

        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        checkOrder(bookList, sortTypes, null, map);

        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);
        sortTypes.add(SORT_NAME_WITHOUT_PREFIX);


        map = new HashMap<>();
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_NAME_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        checkOrder(bookList, sortTypes, null, map);

        map = new HashMap<>();
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_NAME_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        checkOrder(bookList, sortTypes, null, map);

        map = new HashMap<>();
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        map.put(SORT_NAME_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        checkOrder(bookList, sortTypes, null, map);

        map = new HashMap<>();
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        map.put(SORT_NAME_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        checkOrder(bookList, sortTypes, null, map);

    }

    private void checkOrder(ObservableList<Book> bookList,
                            List<String> sortTypes,
                            String order,
                            Map<String, String> subOrder) throws Exception {

        model.sortBook(sortTypes, order, subOrder);
        checkSort(bookList, sortTypes, order, subOrder);
    }

    /**
     * check whether book list is in certain order.
     * @param bookList list that going to check
     * @param types specify how the list is sorted
     * @param mainOrder specify whether the list order in main order
     * @param subOrder specify individual order for different sortType
     * @throws Exception if the list is not in order
     */
    private void checkSort(ObservableList<Book> bookList,
                           List<String> types,
                           String mainOrder,
                           Map<String, String> subOrder) throws Exception {

        Iterator<String> iterator = types.iterator();
        String firstType = iterator.next().toLowerCase();

        Book firstBook = bookList.get(FIRST);
        for (int i = 1; i < bookList.size(); i++) {
            Book secondBook = bookList.get(i);
            if (firstType.equals(SortBookCommandParser.AUTHOR)) {

                int result = checkSort(firstBook, secondBook, subOrder, mainOrder, firstType, this::compareAuthor);

                if (result > 0) {
                    throw new Exception(SORT_EXCEPTION);
                }

                if (result < 0 || !iterator.hasNext()) {
                    firstBook = secondBook;
                    continue;
                }

                String secondType = iterator.next().toLowerCase();

                if (secondType.equals(SortBookCommandParser.BOOKNAME)) {

                    result = checkSort(firstBook, secondBook, subOrder,
                        mainOrder, secondType, this::compareBookName);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                    if (result < 0 || !iterator.hasNext()) {
                        firstBook = secondBook;
                        continue;
                    }

                    result = checkSort(firstBook, secondBook, subOrder, mainOrder,
                        iterator.next().toLowerCase(), this::compareRating);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                } else {

                    result = checkSort(firstBook, secondBook, subOrder,
                        mainOrder, secondType, this::compareRating);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                    if (result < 0 || !iterator.hasNext()) {
                        firstBook = secondBook;
                        continue;
                    }

                    result = checkSort(firstBook, secondBook, subOrder, mainOrder,
                        iterator.next().toLowerCase(), this::compareBookName);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                }

            } else if (firstType.equals(SortBookCommandParser.BOOKNAME)) {

                int result = checkSort(firstBook, secondBook, subOrder, mainOrder, firstType, this::compareBookName);

                if (result > 0) {
                    throw new Exception(SORT_EXCEPTION);
                }

                if (result < 0 || !iterator.hasNext()) {
                    firstBook = secondBook;
                    continue;
                }

                String secondOrder = iterator.next().toLowerCase();

                if (secondOrder.equals(SortBookCommandParser.AUTHOR)) {

                    result = checkSort(firstBook, secondBook, subOrder,
                        mainOrder, secondOrder, this::compareAuthor);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                    if (result < 0 || !iterator.hasNext()) {
                        firstBook = secondBook;
                        continue;
                    }

                    result = checkSort(firstBook, secondBook, subOrder, mainOrder,
                        iterator.next().toLowerCase(), this::compareRating);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                } else {

                    result = checkSort(firstBook, secondBook, subOrder,
                        mainOrder, secondOrder, this::compareRating);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                    if (result < 0 || !iterator.hasNext()) {
                        firstBook = secondBook;
                        continue;
                    }

                    result = checkSort(firstBook, secondBook, subOrder, mainOrder,
                        iterator.next().toLowerCase(), this::compareAuthor);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                }
            } else {

                int result = checkSort(firstBook, secondBook, subOrder, mainOrder, firstType, this::compareRating);

                if (result > 0) {
                    throw new Exception(SORT_EXCEPTION);
                }

                if (result < 0 || !iterator.hasNext()) {
                    firstBook = secondBook;
                    continue;
                }

                String secondOrder = iterator.next().toLowerCase();

                if (secondOrder.equals(SortBookCommandParser.BOOKNAME)) {

                    result = checkSort(firstBook, secondBook, subOrder,
                        mainOrder, secondOrder, this::compareBookName);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                    if (result < 0 || !iterator.hasNext()) {
                        firstBook = secondBook;
                        continue;
                    }

                    result = checkSort(firstBook, secondBook, subOrder, mainOrder,
                        iterator.next().toLowerCase(), this::compareAuthor);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                } else {

                    result = checkSort(firstBook, secondBook, subOrder,
                        mainOrder, secondOrder, this::compareAuthor);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                    if (result < 0 || !iterator.hasNext()) {
                        firstBook = secondBook;
                        continue;
                    }

                    result = checkSort(firstBook, secondBook, subOrder, mainOrder,
                        iterator.next().toLowerCase(), this::compareBookName);

                    if (result > 0) {
                        throw new Exception(SORT_EXCEPTION);
                    }

                }
            }
            firstBook = secondBook;
        }
    }

    /**
     * Compare two book.
     * @param b1 first book
     * @param b2 second book
     * @param subOrders whether the individual attribute has specify order
     * @param order main order for all attributes
     * @param currentType current sort type
     * @param compare bifunction that either be compare author, rating or book name
     * @return the compare result, -1, 0 or 1
     */
    private int checkSort(Book b1, Book b2,
                     Map<String, String> subOrders,
                     String order, String currentType,
                     BiFunction<Book, Book, Integer> compare) {
        if (order == null) {
            order = subOrders.getOrDefault(currentType, SortBookCommandParser.ASCENDING);
        }

        if (order.equals(SortBookCommandParser.ASCENDING)) {
            return compare.apply(b1, b2);

        } else {
            return compare.apply(b2, b1);
        }
    }

    private int compareAuthor(Book b1, Book b2) {
        return b1.getAuthor().fullName.compareTo(b2.getAuthor().fullName);
    }

    private int compareBookName(Book b1, Book b2) {

        return b1.getBookName().fullName.compareTo(b2.getBookName().fullName);
    }

    private int compareRating(Book b1, Book b2) {

        return Integer.valueOf(b1.getRating().value) - Integer.valueOf(b2.getRating().value);
    }
}
