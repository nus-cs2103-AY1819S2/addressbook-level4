package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.ListItem;
import seedu.address.model.deck.Card;

/**
 * Panel containing the list of persons.
 */
public class CardListPanel extends UiPart<Region> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private ListView<ListItem> cardListView;

    public CardListPanel(ObservableList<ListItem> list, ObservableValue<ListItem> selectedItem, Consumer<ListItem> onSelectedItemChange) {
        super(FXML);

        cardListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in card list panel changed to : '" + newValue + "'");
            onSelectedItemChange.accept(newValue);
        });

        cardListView.setItems(list);
        cardListView.setCellFactory(listView -> new CardListViewCell());

        selectedItem.addListener((observable, oldValue, newValue) -> {
            logger.info("Selected item changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected card,
            // otherwise we would have an infinite loop.
            if (Objects.equals(cardListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                cardListView.getSelectionModel().clearSelection();
            } else {
                int index = cardListView.getItems().indexOf(newValue);
                cardListView.scrollTo(index);
                cardListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ListItem}.
     */
    class CardListViewCell extends ListCell<ListItem> {
        @Override
        protected void updateItem(ListItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (item instanceof Card) {
                    setGraphic(new CardDisplay(item, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
