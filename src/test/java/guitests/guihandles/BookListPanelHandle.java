package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.book.Book;

/**
<<<<<<< HEAD
 * Provides a handle for {@code BookListPanel} containing the list of {@code BookCard}.
=======
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
 */
public class BookListPanelHandle extends NodeHandle<ListView<Book>> {
    public static final String BOOK_LIST_VIEW_ID = "#bookListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Book> lastRememberedSelectedBookCard;

    public BookListPanelHandle(ListView<Book> bookListPanelNode) {
        super(bookListPanelNode);
    }

    /**
<<<<<<< HEAD
     * Returns a handle to the selected {@code BookCardHandle}.
=======
     * Returns a handle to the selected {@code PersonCardHandle}.
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BookCardHandle getHandleToSelectedCard() {
        List<Book> selectedBookList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedBookList.size() != 1) {
<<<<<<< HEAD
            throw new AssertionError("Book list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(BookCardHandle::new)
                .filter(handle -> handle.equals(selectedBookList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
=======
            throw new AssertionError("Person list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(BookCardHandle::new)
            .filter(handle -> handle.equals(selectedBookList.get(0)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Book> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
<<<<<<< HEAD
     * Navigates the listview to display {@code book}.
     */
    public void navigateToCard(Book book) {
        if (!getRootNode().getItems().contains(book)) {
            throw new IllegalArgumentException("Book does not exist.");
=======
     * Navigates the listview to display {@code person}.
     */
    public void navigateToCard(Book book) {
        if (!getRootNode().getItems().contains(book)) {
            throw new IllegalArgumentException("Person does not exist.");
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(book);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
<<<<<<< HEAD
     * Selects the {@code BookCard} at {@code index} in the list.
=======
     * Selects the {@code PersonCard} at {@code index} in the list.
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
<<<<<<< HEAD
     * Returns the book card handle of a book associated with the {@code index} in the list.
=======
     * Returns the person card handle of a person associated with the {@code index} in the list.
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BookCardHandle getBookCardHandle(int index) {
        return getAllCardNodes().stream()
<<<<<<< HEAD
                .map(BookCardHandle::new)
                .filter(handle -> handle.equals(getBook(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
=======
            .map(BookCardHandle::new)
            .filter(handle -> handle.equals(getBook(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }

    private Book getBook(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code BookCard} in the list.
     */
    public void rememberSelectedBookCard() {
        List<Book> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedBookCard = Optional.empty();
        } else {
            lastRememberedSelectedBookCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
<<<<<<< HEAD
     * Returns true if the selected {@code BookCard} is different from the value remembered by the most recent
     * {@code rememberSelectedBookCard()} call.
=======
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
     */
    public boolean isSelectedBookCardChanged() {
        List<Book> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedBookCard.isPresent();
        } else {
            return !lastRememberedSelectedBookCard.isPresent()
<<<<<<< HEAD
                    || !lastRememberedSelectedBookCard.get().equals(selectedItems.get(0));
=======
                || !lastRememberedSelectedBookCard.get().equals(selectedItems.get(0));
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
