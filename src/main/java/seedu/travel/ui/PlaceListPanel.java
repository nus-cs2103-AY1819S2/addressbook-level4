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
public class PlaceListPanel extends UiPart<Region> {
    private static final String FXML = "PlaceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PlaceListPanel.class);

    @FXML
    private ListView<Place> placeListView;

    public PlaceListPanel(ObservableList<Place> placeList, ObservableValue<Place> selectedPlace,
            Consumer<Place> onSelectedPlaceChange) {
        super(FXML);
        placeListView.setItems(placeList);
        placeListView.setCellFactory(listView -> new PlaceListViewCell());
        placeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in place list panel changed to : '" + newValue + "'");
            onSelectedPlaceChange.accept(newValue);
        });
        selectedPlace.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected place changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected place,
            // otherwise we would have an infinite loop.
            if (Objects.equals(placeListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                placeListView.getSelectionModel().clearSelection();
            } else {
                int index = placeListView.getItems().indexOf(newValue);
                placeListView.scrollTo(index);
                placeListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Place} using a {@code PlaceCard}.
     */
    class PlaceListViewCell extends ListCell<Place> {
        @Override
        protected void updateItem(Place place, boolean empty) {
            super.updateItem(place, empty);

            if (empty || place == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PlaceCard(place, getIndex() + 1).getRoot());
            }
        }
    }

}
