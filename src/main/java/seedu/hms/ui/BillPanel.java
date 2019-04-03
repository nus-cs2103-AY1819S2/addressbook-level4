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
            stringBuilder.append("----------------------Bill----------------------\n");
            if (newValue.getBookingCount() > 0) {
                stringBuilder.append("Service Booking(s) :\n");

                stringBuilder.append(newValue.getGymBookingCount() <= 0
                        ? ""
                        : "  " + ServiceType.GYM.getName()
                        + "                      Rate per hour: " + ServiceType.GYM.getRatePerHour() + "\n"
                        + "    Number of hour(s): " + newValue.getGymBookingCount() + "  "
                        + "    Amount: " + newValue.getGymBill() + "\n");
                stringBuilder.append(newValue.getSwimmingPoolBookingCount() <= 0
                        ? ""
                        : "  " + ServiceType.POOL.getName()
                        + "  Rate per hour: " + ServiceType.POOL.getRatePerHour() + "\n"
                        + "    Number of hour(s): " + newValue.getSwimmingPoolBookingCount() + "  "
                        + "    Amount: " + newValue.getSwimmingPoolBill() + "\n");
                stringBuilder.append(newValue.getSpaBookingCount() <= 0
                        ? ""
                        : "  " + ServiceType.SPA.getName()
                        + "                      Rate per hour: " + ServiceType.SPA.getRatePerHour() + "\n"
                        + "    Number of hour(s): " + newValue.getSpaBookingCount() + "  "
                        + "    Amount: " + newValue.getSpaBill() + "\n");
                stringBuilder.append(newValue.getGamesRoomBookingCount() <= 0
                        ? ""
                        : "  " + ServiceType.GAMES.getName()
                        + "                    Rate per hour: " + ServiceType.GAMES.getRatePerHour() + "\n"
                        + "    Number of hour(s): " + newValue.getGamesRoomBookingCount() + "  "
                        + "    Amount: " + newValue.getGamesRoomBill() + "\n");

                stringBuilder.append("--------------------------------Sub-Total: "
                        + newValue.getAmountBooking() + "\n");
                stringBuilder.append("------------------------------------------------\n");
            } //if count of bookings is greater than 0

            if (newValue.getReservationCount() > 0) {
                stringBuilder.append("Room Reservation(s):\n");

                stringBuilder.append(newValue.getSingleRoomCount() <= 0
                        ? ""
                        : "  " + RoomType.SINGLE.getName() + "  Rate per hour: "
                        + RoomType.SINGLE.getRatePerDay() + "\n"
                        + "    Number of day(s): " + newValue.getSingleRoomCount() + "  "
                        + "    Amount: " + newValue.getSingleRoomBill() + "\n");
                stringBuilder.append(newValue.getDoubleRoomCount() <= 0
                        ? ""
                        : "  " + RoomType.DOUBLE.getName() + "  Rate per hour: "
                        + RoomType.DOUBLE.getRatePerDay() + "\n"
                        + "    Number of day(s): " + newValue.getDoubleRoomCount() + "  "
                        + "    Amount: " + newValue.getDoubleRoomBill() + "\n");
                stringBuilder.append(newValue.getDeluxeRoomCount() <= 0
                        ? ""
                        : "  " + RoomType.DELUXE.getName() + "  Rate per hour: "
                        + RoomType.DELUXE.getRatePerDay() + "\n"
                        + "    Number of day(s): " + newValue.getDeluxeRoomCount() + "  "
                        + "    Amount: " + newValue.getDeluxeRoomBill() + "\n");
                stringBuilder.append(newValue.getFamilySuiteCount() <= 0
                        ? ""
                        : "  " + RoomType.SUITE.getName() + "  Rate per Day : "
                        + RoomType.SUITE.getRatePerDay() + "\n"
                        + "    Number of day(s): " + newValue.getFamilySuiteCount() + "  "
                        + "    Amount: " + newValue.getFamilySuiteBill() + "\n");

                stringBuilder.append("--------------------------------Sub-Total: "
                        + newValue.getAmountReservation() + "\n");
                stringBuilder.append("------------------------------------------------\n");
            } //if count of reservation is greater than 0

            stringBuilder.append("-------------------------------------Total: " + newValue.getAmountTotal());
            textArea.setText(stringBuilder.toString());
            textArea.setEditable(false);
        }));
    }
}
