package seedu.travel.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.Place;

/**
 * Panel containing the list of places.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayListPanel.class);

    @FXML
    private ListView<Place> displayListView;

    public DisplayListPanel(ObservableList<Place> displayList, ObservableValue<Place> selectedDisplay,
                          Consumer<Place> onSelectedDisplayChange) {
        super(FXML);
        displayListView.setItems(displayList);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());
        displayListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in display list panel changed to : '" + newValue + "'");
            onSelectedDisplayChange.accept(newValue);
        });
        selectedDisplay.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected display changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected display,
            // otherwise we would have an infinite loop.
            if (Objects.equals(displayListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                displayListView.getSelectionModel().clearSelection();
            } else {
                int index = displayListView.getItems().indexOf(newValue);
                displayListView.scrollTo(index);
                displayListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Place} using a {@code DisplayCard}.
     */
    class DisplayListViewCell extends ListCell<Place> {
        @Override
        protected void updateItem(Place place, boolean empty) {
            super.updateItem(place, empty);

            if (empty || place == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(place, getIndex() + 1).getRoot());
            }
        }
    }

}
