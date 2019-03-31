package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
    public void sortBooks(List<String> types, String mainOrder, Map<String, String> subOrder) {
        requireAllNonNull(types);

        Comparator<Book> bookComparator = (b1, b2) -> {
            Iterator<String> iterator = types.iterator();
            String firstType = iterator.next().toLowerCase();
            int result;

            if (firstType.equals(SortBookCommandParser.AUTHOR)) {

                result = sort(b1, b2, subOrder, mainOrder, firstType, this::compareAuthor);

                if (result != 0 || !iterator.hasNext()) {
                    return result;
                }

                String secondType = iterator.next().toLowerCase();

                if (secondType.equals(SortBookCommandParser.BOOKNAME)) {

                    result = sort(b1, b2, subOrder, mainOrder, secondType, this::compareBookName);

                    if (result != 0 || !iterator.hasNext()) {
                        return result;
                    }

                    return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), this::compareRating);

                } else {

                    result = sort(b1, b2, subOrder, mainOrder, secondType, this::compareRating);

                    if (result != 0 || !iterator.hasNext()) {
                        return result;
                    }

                    return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), this::compareBookName);

                }
            } else if (firstType.equals(SortBookCommandParser.BOOKNAME)) {

                result = sort(b1, b2, subOrder, mainOrder, firstType, this::compareBookName);

                if (result != 0 || !iterator.hasNext()) {
                    return result;
                }

                String secondOrder = iterator.next().toLowerCase();

                if (secondOrder.equals(SortBookCommandParser.AUTHOR)) {

                    result = sort(b1, b2, subOrder, mainOrder, secondOrder, this::compareAuthor);

                    if (result != 0 || !iterator.hasNext()) {
                        return result;
                    }

                    return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), this::compareRating);

                } else {

                    result = sort(b1, b2, subOrder, mainOrder, secondOrder, this::compareRating);

                    if (result != 0 || !iterator.hasNext()) {
                        return result;
                    }

                    return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), this::compareAuthor);
                }

            } else {

                result = sort(b1, b2, subOrder, mainOrder, firstType, this::compareRating);

                if (result != 0 || !iterator.hasNext()) {
                    return result;
                }

                String secondOrder = iterator.next().toLowerCase();

                if (secondOrder.equals(SortBookCommandParser.AUTHOR)) {

                    result = sort(b1, b2, subOrder, mainOrder, secondOrder, this::compareAuthor);

                    if (result != 0 || !iterator.hasNext()) {
                        return result;
                    }

                    return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), this::compareBookName);

                } else {

                    result = sort(b1, b2, subOrder, mainOrder, secondOrder, this::compareBookName);

                    if (result != 0 || !iterator.hasNext()) {
                        return result;
                    }

                    return sort(b1, b2, subOrder, mainOrder, iterator.next().toLowerCase(), this::compareAuthor);
                }
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
