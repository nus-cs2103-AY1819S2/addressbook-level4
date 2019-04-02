package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.restaurant.Restaurant;

/**
 * Panel containing the summary of the reviews belonging to a restaurant and the restaurant's details.
 */
public class RestaurantSummaryPanel extends UiPart<Region> {
    private static final String FXML = "RestaurantSummaryPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RestaurantSummaryPanel.class);

    @FXML
    private VBox infoVbox;
    @FXML
    private BorderPane summaryPane;

    public RestaurantSummaryPanel(ObservableValue<Restaurant> selectedRestaurant) {
        super(FXML);

        selectedRestaurant.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected restaurant changed to: " + newValue);

            if (newValue == null) {
                infoVbox.getChildren().clear();
            } else {
                // Reset infoVbox
                infoVbox.getChildren().clear();

                Restaurant restaurant = selectedRestaurant.getValue();

                infoVbox.getChildren().addAll((new RestaurantSummaryDisplay(restaurant)).getRoot());
            }
        });
    }

}
