package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.hms.model.BillModel;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * An UI component that displays information of a {@code Booking}.
 */
public class BillPanel extends UiPart<Region> {

    private static final String FXML = "BillPanel.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final BillModel billModel;

    @FXML
    private TextArea textArea;

    public BillPanel(BillModel billModel) {
        super(FXML);
        this.billModel = billModel;
        final StringBuilder stringBuilder = new StringBuilder();
        billModel.getBill().addListener(((observable, oldValue, newValue) -> {
            stringBuilder.setLength(0);
            if (newValue.getReservationCount() + newValue.getBookingCount() > 0) {
                stringBuilder.append("-----------------Bill-----------------\n");
            }
            if (newValue.getBookingCount() > 0) {
                stringBuilder.append("Service Booking(s) :\n");
                for (ServiceType st: newValue.getServiceTypes()) {
                    if (newValue.getServiceTypeBookingCount(st) > 0) {
                        stringBuilder.append(String.format("%14s", "[" + st.getName() + "]")
                                + String.format("  Rate per hour:%2.1f", st.getRatePerHour()) + "\n"
                                + String.format("  Number of hour(s):%2d", newValue.getServiceTypeBookingCount(st))
                                + String.format("  Amount:%4.1f", newValue.getServiceTypeBill(st)) + "\n\n");
                    }
                }
                stringBuilder.append(String.format("---------------------Sub-Total: %6.1f",
                        newValue.getAmountBooking()) + "\n");
                stringBuilder.append("--------------------------------------\n");
            } //if count of bookings is greater than 0

            if (newValue.getReservationCount() > 0) {
                stringBuilder.append("Room Reservation(s):\n");

                for (RoomType rt: newValue.getRoomTypes()) {
                    if (newValue.getRoomTypeCount(rt) > 0) {
                        stringBuilder.append(String.format("%14s", "[" + rt.getName() + "]")
                                + String.format("  Rate per day:%2.1f", rt.getRatePerDay()) + "\n"
                                + String.format("  Number of day(s):%2d", newValue.getRoomTypeCount(rt))
                                + String.format("  Amount:%4.1f", newValue.getRoomTypeBill(rt)) + "\n\n");
                    }
                }
                stringBuilder.append(String.format("---------------------Sub-Total: %6.1f",
                        newValue.getAmountReservation()) + "\n");
                stringBuilder.append("--------------------------------------\n");
            } //if count of reservation is greater than 0

            if (newValue.getReservationCount() + newValue.getBookingCount() > 0) {
                stringBuilder.append(String.format("-------------------------Total: %6.1f", newValue.getAmountTotal()));
            } else {
                stringBuilder.append(String.format("%34s", "No available Service Booking") + "\n"
                        + String.format("%34s", "and/or Room Reservation for") + "\n"
                        + String.format("%34s", "selected customer detected"));
            }
            textArea.setText(stringBuilder.toString());
            textArea.setEditable(false);
        }));
    }
}
