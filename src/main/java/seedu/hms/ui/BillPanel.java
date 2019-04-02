package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import seedu.hms.model.BillModel;

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
    private ScrollPane cardPane;
    @FXML
    private Label billHead;
    @FXML
    private Label serviceBookingBillHead;
    @FXML
    private Label roomReservationBillHead;

    public BillPanel(BillModel billModel) {
        super(FXML);
        this.billModel = billModel;
        billHead.setText("Bill");
        serviceBookingBillHead.setText("Service Booking(s)  -------Subtotal:" + "100"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131"
                + "\n100 111\n 12313 131");
        roomReservationBillHead.setText("Room Reservation(s)-------Subtotal:" + "100");
        //forEach(user -> allUsers.getChildren().add(new Label(user.getName().fullName + "|")));
    }
}
