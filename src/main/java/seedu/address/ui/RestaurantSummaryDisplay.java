package seedu.address.ui;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.restaurant.Restaurant;

/**
 * An UI component that displays information of a {@code RestaurantSummary}.
 */
public class RestaurantSummaryDisplay extends UiPart<Region> {

    private static final String FXML = "RestaurantSummaryDisplay.fxml";
    private static final DecimalFormat ONE_DP = new DecimalFormat("0.0");
    private static final String FIELD_NOT_ADDED = "N.A.";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/fooddiary-level4/issues/336">The issue on FoodDiary level 4</a>
     */

    @FXML
    private VBox infoVbox;
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

    public RestaurantSummaryDisplay(Restaurant restaurant) {
        super(FXML);

        name.setText(restaurant.getName().toString());

        // Check if Restaurant has been visited before
        if (restaurant.getSummary().getTotalVisits() > 0) {
            avgRating.setText(ONE_DP.format(restaurant.getSummary().getAvgRating()) + " / 5.0");
            totalVisits.setText("from " + restaurant.getSummary().getTotalVisits() + " visit(s)");
        } else {
            avgRating.setText(FIELD_NOT_ADDED);
            totalVisits.setText("from " + restaurant.getSummary().getTotalVisits() + " visit(s)");
        }

        address.setText(restaurant.getAddress().toString());
        phone.setText(restaurant.getPhone().toString());

        // Check if Restaurant has Opening Hours added
        if (restaurant.getOpeningHours() != null) {
            openingHours.setText(restaurant.getOpeningHours().toString());
        } else {
            openingHours.setText(FIELD_NOT_ADDED);
        }

        email.setText(restaurant.getEmail().toString());

        // Check if Restaurant has a Weblink
        if (restaurant.getWeblink() != null) {
            weblink.setText(restaurant.getWeblink().toString());
        } else {
            weblink.setText(FIELD_NOT_ADDED);
        }
    }

//    @Override
//    public boolean equals(Object other) {
//        // short circuit if same object
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof RestaurantSummaryDisplay)) {
//            return false;
//        }
//
//        // state check
//        RestaurantSummaryDisplay card = (RestaurantSummaryDisplay) other;
//        return id.getText().equals(card.id.getText())
//                && review.equals(card.review);
//    }
}
