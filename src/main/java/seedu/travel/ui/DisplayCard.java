package seedu.travel.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.travel.model.place.Place;

/**
 * An UI component that displays information of a {@code Place}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayListCard.fxml";
    private static final String[] TAG_COLOR_STYLES =
        {"teal", "red", "yellow", "blue", "orange", "brown", "green", "salmon", "black", "grey" };

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Place place;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label countryCode;
    @FXML
    private Label dateVisited;
    @FXML
    private Label rating;
    @FXML
    private Label address;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public DisplayCard(Place place, int displayedIndex) {
        super(FXML);
        this.place = place;
        id.setText(displayedIndex + ". ");
        name.setText(place.getName().fullName);
        countryCode.setText(place.getCountryCode().code);
        dateVisited.setText(place.getDateVisited().getDate());
        rating.setText(place.getRating().value);
        address.setText(place.getAddress().value);
        description.setText(place.getDescription().value);
        initTags(place);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // Hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code place}.
     */
    private void initTags(Place place) {
        place.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayCard)) {
            return false;
        }

        // state check
        DisplayCard card = (DisplayCard) other;
        return id.getText().equals(card.id.getText())
                && place.equals(card.place);
    }
}
