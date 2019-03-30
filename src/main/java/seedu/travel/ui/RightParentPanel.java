package seedu.travel.ui;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.Place;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Panel containing the list of places.
 */
public class RightParentPanel extends UiPart<Region> {
    private static final String FXML = "RightParentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RightParentPanel.class);

    private ListView<Place> placeListView; // not in use

    @FXML
    private VBox displayListPanelPlaceholder;

    private DisplayListPanel displayListPanel;

    public RightParentPanel(ObservableList<Place> placeList, ObservableValue<Place> selectedPlace,
                            Consumer<Place> onSelectedPlaceChange, ReadOnlyProperty<Boolean> chartDisplayed) {
        super(FXML);

        /*
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

        */


        chartDisplayed.addListener((observable, oldValue, newValue) -> {
            // display the chart
            logger.info("old Value"+oldValue+"New value "+newValue);
            if (true) {
                // pass through
                logger.info("SimpleBooleanValue Changed");
                displayListPanel = new DisplayListPanel(placeList, selectedPlace, onSelectedPlaceChange);
                displayListPanelPlaceholder.getChildren().add(displayListPanel.getRoot());
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
