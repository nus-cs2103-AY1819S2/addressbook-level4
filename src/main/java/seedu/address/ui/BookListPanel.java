package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.book.Book;

/**
 * Panel containing the list of books.
 */
public class BookListPanel extends UiPart<Region> {
    private static final String FXML = "BookListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BookListPanel.class);

    @FXML
    private ListView<Book> bookListView;

    @FXML
    private Label title;

    public BookListPanel(ObservableList<Book> bookList, ObservableValue<Book> selectedbook,
                           Consumer<Book> onSelectedbookChange) {
        super(FXML);
        bookListView.setItems(bookList);
        bookListView.setCellFactory(listView -> new BookListViewCell());
        bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in book list panel changed to : '" + newValue + "'");
            onSelectedbookChange.accept(newValue);
        });
        title.setText("Book List");
        selectedbook.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected book changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected book,
            // otherwise we would have an infinite loop.
            if (Objects.equals(bookListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                bookListView.getSelectionModel().clearSelection();
            } else {
                int index = bookListView.getItems().indexOf(newValue);
                bookListView.scrollTo(index);
                bookListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code book} using a {@code bookCard}.
     */
    class BookListViewCell extends ListCell<Book> {
        @Override
        protected void updateItem(Book book, boolean empty) {
            super.updateItem(book, empty);

            if (empty || book == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BookCard(book, getIndex() + 1).getRoot());
            }
        }
    }

}
