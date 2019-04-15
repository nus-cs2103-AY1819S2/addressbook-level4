package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.hms.model.reservation.roomType.RoomType;

//import javafx.scene.layout.HBox;

/**
 * An UI component that displays information of a {@code Booking}.
 */
public class RoomTypeListCard extends UiPart<Region> {

    private static final String FXML = "RoomTypeCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/hotelManagementSystem-level4/issues/336"> The issue on
     * HotelManagementSystem level 4</a>
     */

    public final RoomType roomType;

    @FXML
    private Label roomTypeName;
    @FXML
    private Label number;
    @FXML
    private Label ratePerDay;
    @FXML
    private Label id;

    public RoomTypeListCard(RoomType roomType, int displayedIndex) {
        super(FXML);
        this.roomType = roomType;
        roomTypeName.setText(roomType.getName());
        id.setText(displayedIndex + ". ");
        number.setText("Number of rooms: " + Integer.toString(roomType.getNumberOfRooms()));
        ratePerDay.setText("Rate per day: " + roomType.getRatePerDay());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerCard)) {
            return false;
        }

        // state check
        RoomTypeListCard card = (RoomTypeListCard) other;
        return roomType.equals(card.roomType);
    }
}
