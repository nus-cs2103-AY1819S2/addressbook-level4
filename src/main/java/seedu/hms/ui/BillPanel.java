package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.hms.model.BillModel;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.reservation.RoomType;

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
            stringBuilder.append("-----------------Bill-----------------\n");
            if (newValue.getBookingCount() > 0) {
                stringBuilder.append("Service Booking(s) :\n");

                stringBuilder.append(newValue.getGymBookingCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + ServiceType.GYM.getName() + "]")
                        + String.format("  Rate per hour:%2.1f", ServiceType.GYM.getRatePerHour()) + "\n"
                        + String.format("  Number of hour(s):%2d", newValue.getGymBookingCount())
                        + String.format("  Amount:%4.1f", newValue.getGymBill()) + "\n\n");
                stringBuilder.append(newValue.getSwimmingPoolBookingCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + ServiceType.POOL.getName() + "]")
                        + String.format("  Rate per hour:%2.1f", ServiceType.POOL.getRatePerHour()) + "\n"
                        + String.format("  Number of hour(s):%2d", newValue.getSwimmingPoolBookingCount())
                        + String.format("  Amount:%4.1f", newValue.getSwimmingPoolBill()) + "\n\n");
                stringBuilder.append(newValue.getSpaBookingCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + ServiceType.SPA.getName() + "]")
                        + String.format("  Rate per hour:%2.1f", ServiceType.SPA.getRatePerHour()) + "\n"
                        + String.format("  Number of hour(s):%2d", newValue.getSpaBookingCount())
                        + String.format("  Amount:%4.1f", newValue.getSpaBill()) + "\n\n");
                stringBuilder.append(newValue.getGamesRoomBookingCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + ServiceType.GAMES.getName() + "]")
                        + String.format("  Rate per hour:%2.1f", ServiceType.GAMES.getRatePerHour()) + "\n"
                        + String.format("  Number of hour(s):%2d", newValue.getGamesRoomBookingCount())
                        + String.format("  Amount:%4.1f", newValue.getGamesRoomBill()) + "\n\n");

                stringBuilder.append(String.format("---------------------Sub-Total: %6.1f",
                        newValue.getAmountBooking()) + "\n");
                stringBuilder.append("--------------------------------------\n");
            } //if count of bookings is greater than 0

            if (newValue.getReservationCount() > 0) {
                stringBuilder.append("Room Reservation(s):\n");

                stringBuilder.append(newValue.getSingleRoomCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + RoomType.SINGLE.getName() + "]")
                        + String.format("  Rate per day:%5.1f", RoomType.SINGLE.getRatePerDay()) + "\n"
                        + String.format("  Number of day(s):%2d", newValue.getSingleRoomCount())
                        + String.format("  Amount:%5.1f", newValue.getSingleRoomBill()) + "\n\n");
                stringBuilder.append(newValue.getDoubleRoomCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + RoomType.DOUBLE.getName() + "]")
                        + String.format("  Rate per day:%5.1f", RoomType.DOUBLE.getRatePerDay()) + "\n"
                        + String.format("  Number of day(s):%2d", newValue.getDoubleRoomCount())
                        + String.format("  Amount:%5.1f", newValue.getDoubleRoomBill()) + "\n\n");
                stringBuilder.append(newValue.getDeluxeRoomCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + RoomType.DELUXE.getName() + "]")
                        + String.format("  Rate per day:%5.1f", RoomType.DELUXE.getRatePerDay()) + "\n"
                        + String.format("  Number of day(s):%2d", newValue.getDeluxeRoomCount())
                        + String.format("  Amount:%5.1f", newValue.getDeluxeRoomBill()) + "\n\n");
                stringBuilder.append(newValue.getFamilySuiteCount() <= 0
                        ? ""
                        : String.format("%14s", "[" + RoomType.SUITE.getName() + "]")
                        + String.format("  Rate per day:%5.1f", RoomType.SUITE.getRatePerDay()) + "\n"
                        + String.format("  Number of day(s):%2d", newValue.getFamilySuiteCount())
                        + String.format("  Amount:%5.1f", newValue.getFamilySuiteBill()) + "\n\n");

                stringBuilder.append(String.format("---------------------Sub-Total: %6.1f",
                        newValue.getAmountReservation()) + "\n");
                stringBuilder.append("--------------------------------------\n");
            } //if count of reservation is greater than 0

            stringBuilder.append(String.format("-------------------------Total: %6.1f", newValue.getAmountTotal()));
            textArea.setText(stringBuilder.toString());
            textArea.setEditable(false);
        }));
    }
}
