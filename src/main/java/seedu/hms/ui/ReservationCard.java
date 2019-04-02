package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;

/**
 * An UI component that displays information of a {@code Booking}.
 */
public class ReservationCard extends UiPart<Region> {

    private static final String FXML = "ReservationListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/hotelManagementSystem-level4/issues/336"> The issue on
     * HotelManagementSystem level 4</a>
     */

    public final Reservation reservation;
    public final Customer payer;
    //@FXML
    //private HBox cardPane;
    @FXML
    private Label roomType;
    @FXML
    private Label date;
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

    public ReservationCard(Reservation reservation, int displayedIndex) {
        super(FXML);
        this.reservation = reservation;
        this.payer = reservation.getPayer();
        roomType.setText(reservation.getRoom().getName());
        date.setText("Date: " + reservation.getDates().toString());
        id.setText(displayedIndex + ". ");
        payerName.setText("Payer Name: " + payer.getName().fullName);
        phone.setText("Payer Phone: " + payer.getPhone().value);
        identificationNo.setText("Payer ID: " + payer.getIdNum().value);
        comments.setText(reservation.getComment().isPresent() ? reservation.getComment().get() : "No comment");
        reservation.getAllusers().forEach(user -> allUsers.getChildren().add(new Label(user.getName().fullName + "|")));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReservationCard)) {
            return false;
        }

        // state check
        ReservationCard card = (ReservationCard) other;
        return id.getText().equals(card.id.getText())
                && reservation.equals(card.reservation);
    }
}
