package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.customer.Customer;

//import javafx.scene.layout.HBox;

/**
 * An UI component that displays information of a {@code Booking}.
 */
public class BookingCard extends UiPart<Region> {

    private static final String FXML = "BookingListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/hotelManagementSystem-level4/issues/336"> The issue on
     * HotelManagementSystem level 4</a>
     */

    public final Booking booking;
    public final Customer payer;
    //@FXML
    //private HBox cardPane;
    @FXML
    private Label serviceType;
    @FXML
    private Label time;
    @FXML
    private Label id;
    @FXML
    private Label payerName;
    @FXML
    private Label phone;
    @FXML
    private Label identificationNo;
    @FXML
    private Label comments;
    @FXML
    private FlowPane allUsers;

    public BookingCard(Booking booking, int displayedIndex) {
        super(FXML);
        this.booking = booking;
        this.payer = booking.getPayer();
        serviceType.setText(booking.getService().getName());
        time.setText(booking.getTiming().toString());
        id.setText(displayedIndex + ". ");
        payerName.setText("Payer Name: " + payer.getName().fullName);
        phone.setText("Payer Phone: " + payer.getPhone().value);
        identificationNo.setText("Payer ID: " + payer.getIdNum().value);
        comments.setText(booking.getComment().isPresent() ? booking.getComment().get() : "No comment");
        booking.getAllusers().forEach(user -> allUsers.getChildren().add(new Label(user.getName().fullName + "|")));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingCard)) {
            return false;
        }

        // state check
        BookingCard card = (BookingCard) other;
        return id.getText().equals(card.id.getText())
            && booking.equals(card.booking);
    }
}
