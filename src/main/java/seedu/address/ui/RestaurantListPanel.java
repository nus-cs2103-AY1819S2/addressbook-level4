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
import seedu.address.model.restaurant.Restaurant;

/**
 * Panel containing the list of restaurants.
 */
public class RestaurantListPanel extends UiPart<Region> {
    private static final String FXML = "RestaurantListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RestaurantListPanel.class);

    @FXML
    private ListView<Restaurant> restaurantListView;

    public RestaurantListPanel(ObservableList<Restaurant> restaurantList,
                               ObservableValue<Restaurant> selectedRestaurant,
                               Consumer<Restaurant> onSelectedRestaurantChange) {
        super(FXML);
        restaurantListView.setItems(restaurantList);
        restaurantListView.setCellFactory(listView -> new RestaurantListViewCell());
        restaurantListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in restaurant list panel changed to : '" + newValue + "'");
            onSelectedRestaurantChange.accept(newValue);
        });
        selectedRestaurant.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected restaurant changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected restaurant,
            // otherwise we would have an infinite loop.
            if (Objects.equals(restaurantListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                restaurantListView.getSelectionModel().clearSelection();
            } else {
                int index = restaurantListView.getItems().indexOf(newValue);
                restaurantListView.scrollTo(index);
                restaurantListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Restaurant} using a {@code RestaurantCard}.
     */
    class RestaurantListViewCell extends ListCell<Restaurant> {
        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);

            if (empty || restaurant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RestaurantCard(restaurant, getIndex() + 1).getRoot());
            }
        }
    }

}
