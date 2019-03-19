package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.hms.model.booking.ServiceType;

//import javafx.scene.layout.HBox;

/**
 * An UI component that displays information of a {@code Booking}.
 */
public class ServiceTypeCard extends UiPart<Region> {

    private static final String FXML = "BookingListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/hotelManagementSystem-level4/issues/336"> The issue on
     * HotelManagementSystem level 4</a>
     */

    public final ServiceType serviceType;

    @FXML
    private Label serviceTypeName;
    @FXML
    private Label capacity;
    @FXML
    private Label timeRange;
    @FXML
    private Label ratePerHour;

    public ServiceTypeCard(ServiceType serviceType) {
        super(FXML);
        this.serviceType = serviceType;
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
        ServiceTypeCard card = (ServiceTypeCard) other;
        return serviceType.equals(card.serviceType);
    }
}
