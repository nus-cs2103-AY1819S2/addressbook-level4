package seedu.travel.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.Place;

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


        chartDisplayed.addListener((observable, oldValue, newValue) -> {
            // display the chart
            logger.info("old Value" + oldValue + "New value " + newValue);
            if (newValue) {
                // pass through
                logger.info("SimpleBooleanValue Changed");
                displayListPanel = new DisplayListPanel(placeList, selectedPlace, onSelectedPlaceChange);
                displayListPanelPlaceholder.getChildren().add(displayListPanel.getRoot());
            } else {

                ExpandedPlacePanel expandedPlacePanel = new ExpandedPlacePanel(selectedPlace.getValue());
                displayListPanelPlaceholder.getChildren().removeAll();
                displayListPanelPlaceholder.getChildren().add(expandedPlacePanel.getRoot());
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
