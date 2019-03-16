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
import seedu.address.model.deck.Deck;

/**
 * Panel containing the list of decks.
 */
public class DeckListPanel extends UiPart<Region> {
    private static final String FXML = "DeckListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeckListPanel.class);

    @FXML
    private ListView<Deck> deckListView;


    public DeckListPanel(ObservableList<Deck> deckList, ObservableValue<Deck> selectedDeck,
                         Consumer<Deck> onSelectedDeckChange) {
        super(FXML);
        deckListView.setItems(deckList);
        deckListView.setCellFactory(listView -> new DeckListPanel.DeckListViewCell());
        deckListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in deck list panel changed to : '" + newValue + "'");
            onSelectedDeckChange.accept(newValue);
        });
        selectedDeck.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected deck changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected deck,
            // otherwise we would have an infinite loop.
            if (Objects.equals(deckListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                deckListView.getSelectionModel().clearSelection();
            } else {
                int index = deckListView.getItems().indexOf(newValue);
                deckListView.scrollTo(index);
                deckListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Deck} using a {@code DeckCard}.
     */
    class DeckListViewCell extends ListCell<Deck> {
        @Override
        protected void updateItem(Deck card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeckCard(card, getIndex() + 1).getRoot());
            }
        }
    }

}

