package seedu.address.ui;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private static final DecimalFormat ONE_DP = new DecimalFormat("0.0");
    private static final String FIELD_NOT_ADDED = "N.A.";
    private static final String ADDRESS_PLACEHOLDER = "Address: \n";
    private static final String PHONE_PLACEHOLDER = "Contact No.: \n";
    private static final String OPENING_HOURS_PLACEHOLDER = "Opening Hours: \n";
    private static final String EMAIL_PLACEHOLDER = "Email: \n";
    private static final String WEBLINK_PLACEHOLDER = "Website: \n";
    private final Logger logger = LogsCenter.getLogger(RestaurantSummaryPanel.class);

    // Panes
    @FXML
    private VBox titleVbox;
    @FXML
    private VBox infoVbox;
    @FXML
    private BorderPane summaryPane;

    // Fields to be added to infoVbox
    @FXML
    private Label placeholder;
    @FXML
    private Label name;
    @FXML
    private Label avgRating;
    @FXML
    private Label totalVisits;
    @FXML
    private Label address;
    @FXML
    private Label phone;
    @FXML
    private Label openingHours;
    @FXML
    private Label email;
    @FXML
    private Label weblink;


    public RestaurantSummaryPanel(ObservableValue<Restaurant> selectedRestaurant) {
        super(FXML);

        loadPlaceholder();

        selectedRestaurant.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected restaurant changed to: " + newValue);

            if (newValue == null) {
                loadPlaceholder();
            } else {
                // Get selectedRestaurant and load its Summary
                Restaurant restaurant = selectedRestaurant.getValue();
                loadSummary(restaurant);
            }
        });
    }

    /**
     * Loads placeholder into RestaurantSummaryPanel.
     */
    public void loadPlaceholder() {
        // Create placeholder label
        Label placeholder = new Label("Restaurant Summary");
        placeholder.setStyle("-fx-font-size: 16px; -fx-text-fill: #606060");

        // Add placeholder into summaryPane
        summaryPane.setTop(null);
        summaryPane.setCenter(placeholder);
    }

    /**
     * Loads data into RestaurantSummaryPanel.
     */
    public void loadSummary(Restaurant restaurant) {

        name.setText(restaurant.getName().toString());

        // Check if Restaurant has been visited before
        if (restaurant.getSummary().getTotalVisits() > 0) {
            avgRating.setText(ONE_DP.format(restaurant.getSummary().getAvgRating()) + " / 5.0");
            totalVisits.setText("from " + restaurant.getSummary().getTotalVisits() + " visit(s)");
        } else {
            avgRating.setText(FIELD_NOT_ADDED);
            totalVisits.setText("from " + restaurant.getSummary().getTotalVisits() + " visit(s)");
        }

        address.setText(ADDRESS_PLACEHOLDER + restaurant.getAddress().toString());
        phone.setText(PHONE_PLACEHOLDER + restaurant.getPhone().toString());

        // Check if Restaurant has Opening Hours added
        if (restaurant.getOpeningHours().toString().equals("No opening hours added")) {
            openingHours.setText(OPENING_HOURS_PLACEHOLDER + FIELD_NOT_ADDED);
        } else {
            openingHours.setText(OPENING_HOURS_PLACEHOLDER + restaurant.getOpeningHours().toString());
        }

        email.setText(EMAIL_PLACEHOLDER + restaurant.getEmail().toString());

        // Check if Restaurant has a Weblink
        if (restaurant.getWeblink().toString().equals("No weblink added")) {
            weblink.setText(WEBLINK_PLACEHOLDER + FIELD_NOT_ADDED);
        } else {
            weblink.setText(WEBLINK_PLACEHOLDER + restaurant.getWeblink().toString());
        }

        // Add all Labels for the fields of a Summary into infoVbox and display it in summaryPane
        titleVbox.getChildren().setAll(name, avgRating, totalVisits);
        infoVbox.getChildren().setAll(address, phone, openingHours, email, weblink);
        summaryPane.setTop(titleVbox);
        summaryPane.setCenter(infoVbox);
    }

}
