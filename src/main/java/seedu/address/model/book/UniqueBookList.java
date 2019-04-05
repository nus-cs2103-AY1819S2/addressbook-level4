package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.SortBookCommandParser;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;


/**
 * A list of books that enforces uniqueness between its elements and does not allow nulls.
 * A book is considered unique by comparing using {@code Book#isSameBook(Book)}. As such, adding and updating of
 * books uses Book#isSameBook(Book) for equality so as to ensure that the book being added or updated is
 * unique in terms of identity in the UniqueBookList. However, the removal of a book uses Book#equals(Object) so
 * as to ensure that the book with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Book#isSameBook(Book)
 */
public class UniqueBookList implements Iterable<Book> {

    private ObservableList<Book> internalList = FXCollections.observableArrayList();
    private final ObservableList<Book> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent book as the given argument.
     */
    public boolean contains(Book toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBook);
    }

    /**
     * Adds a book to the list.
     * The book must not already exist in the list.
     */
    public void add(Book toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the list.
     * The book identity of {@code editedBook} must not be the same as another existing book in the list.
     */
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }

        if (!target.isSameBook(editedBook) && contains(editedBook)) {
            throw new DuplicateBookException();
        }

        internalList.set(index, editedBook);
    }

    /**
     * Removes the equivalent book from the list.
     * The book must exist in the list.
     */
    public void remove(Book toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookNotFoundException();
        }
    }

    public void setBooks(UniqueBookList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code books}.
     * {@code books} must not contain duplicate books.
     */
    public void setBooks(List<Book> books) {
        requireAllNonNull(books);
        if (!booksAreUnique(books)) {
            throw new DuplicateBookException();
        }

        internalList.setAll(books);
    }

    /**
     * sort the books in book card in order
     * @param types need to be sort
     * @param mainOrder for all types
     * @param subOrder if mainOrder is not specify and subOrder will replace mainOrder
     */
    public void sortBooks(List<String> types,
                          String mainOrder,
                          Map<String, String> subOrder) {
        requireAllNonNull(types);

        Comparator<Book> bookComparator = (b1, b2) -> {
            Iterator<String> iterator = types.iterator();
            String firstType = iterator.next();
            List<BiFunction<Book, Book, Integer>> functions = new ArrayList<>();
            if (firstType.equalsIgnoreCase(SortBookCommandParser.AUTHOR)) {
                functions.add(this::compareAuthor);
                functions.add(this::compareBookName);
                functions.add(this::compareRating);
                return handleSort(b1, b2, iterator,
                    firstType,
                    SortBookCommandParser.BOOKNAME,
                    mainOrder, subOrder,
                    functions);

            } else if (firstType.equalsIgnoreCase(SortBookCommandParser.BOOKNAME)) {
                functions.add(this::compareBookName);
                functions.add(this::compareAuthor);
                functions.add(this::compareRating);
                return handleSort(b1, b2, iterator,
                    firstType,
                    SortBookCommandParser.AUTHOR,
                    mainOrder, subOrder,
                    functions);

            } else {

                functions.add(this::compareRating);
                functions.add(this::compareAuthor);
                functions.add(this::compareBookName);
                return handleSort(b1, b2, iterator,
                    firstType,
                    SortBookCommandParser.AUTHOR,
                    mainOrder, subOrder,
                    functions);

            }
        };

        internalList.sort(bookComparator);
    }

    /**
     * Compares two books with a sort types base on given bi-function
     * @param b1 first book that going to be compare
     * @param b2 second book that going to be compare
     * @param subOrders hash map that contains sub order
     * @param mainOrder main order if available
     * @param currentType current sort type
     * @param compare bifunction that will change base on different sort type
     * @return -1, 0 or 1 depend one value of b1 and b2
     */
    private int sort(Book b1, Book b2,
                     Map<String, String> subOrders,
                     String mainOrder, String currentType,
                     BiFunction<Book, Book, Integer> compare) {
        String order = mainOrder;
        if (order == null) {
            order = subOrders.getOrDefault(currentType, SortBookCommandParser.ASCENDING);
        }

        if (order.equalsIgnoreCase(SortBookCommandParser.ASCENDING)) {
            return compare.apply(b1, b2);
        } else {
            return compare.apply(b2, b1);
        }
    }

    /**
     * Handles the sort.
     * @param b1 first book that going to be compare
     * @param b2 second book that going to be compare
     * @param firstType that going to be sort
     * @param sortType the second type that going to be sort
     * @param mainOrder order that apply for all types
     * @param subOrder sub order for individual type
     * @param functions functions that provide sort function
     * @return sort result, -1, 0 or 1 depend one value of b1 and b2
     */
    private int handleSort(Book b1, Book b2,
                           Iterator<String> iterator,
                           String firstType, String sortType,
                           String mainOrder,
                           Map<String, String> subOrder,
                           List<BiFunction<Book, Book, Integer>> functions) {

        int result = sort(b1, b2, subOrder, mainOrder, firstType, functions.get(0));

        if (result != 0 || !iterator.hasNext()) {
            return result;
        }

        String secondType = iterator.next();
        if (secondType.equalsIgnoreCase(sortType)) {

            return sortByType(b1, b2, iterator, mainOrder, secondType,
                subOrder, functions.get(1), functions.get(2));

        } else {

            return sortByType(b1, b2, iterator, mainOrder, secondType,
                subOrder, functions.get(2), functions.get(1));
        }
    }

    /**
     * Sorts by the given order and type
     * @param b1 book 1
     * @param b2 book 2
     * @param iterator of list of sort type
     * @param mainOrder of all sort type
     * @param type of sort type
     * @param subOrder for individual
     * @param firstFunction first sort function
     * @param secondFunction second sort function
     * @return compare result -1 , 0 or 1
     */
    private int sortByType(Book b1, Book b2,
                           Iterator<String> iterator,
                           String mainOrder,
                           String type,
                           Map<String, String> subOrder,
                           BiFunction<Book, Book, Integer> firstFunction,
                           BiFunction<Book, Book, Integer> secondFunction) {

        int result = sort(b1, b2, subOrder, mainOrder, type, firstFunction);

        if (result != 0 || !iterator.hasNext()) {
            return result;
        }
        return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), secondFunction);
    }

    private int compareAuthor(Book b1, Book b2) {

        return b1.getAuthor().fullName.compareToIgnoreCase(b2.getAuthor().fullName);
    }

    private int compareBookName(Book b1, Book b2) {

        return b1.getBookName().fullName.compareToIgnoreCase(b2.getBookName().fullName);
    }

    /**
     * Compares rating between two books
     * @param b1 first book
     * @param b2 second book
     * @return -1, 0 or 1
     */
    private int compareRating(Book b1, Book b2) {

        int result = Integer.valueOf(b1.getRating().value) - Integer.valueOf(b2.getRating().value);
        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return -1;
        }
        return result;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Book> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Book> iterator() {
        return internalList.iterator();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBookList // instanceof handles nulls
                        && internalList.equals(((UniqueBookList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code books} contains only unique books.
     */
    private boolean booksAreUnique(List<Book> books) {
        for (int i = 0; i < books.size() - 1; i++) {
            for (int j = i + 1; j < books.size(); j++) {
                if (books.get(i).isSameBook(books.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
