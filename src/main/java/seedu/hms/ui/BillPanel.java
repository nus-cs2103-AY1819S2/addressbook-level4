package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private TextArea textArea;
    @FXML
    private StackPane stackPane;

    public BillPanel(BillModel billModel) {
        super(FXML);
        this.billModel = billModel;
        String billString = "";
        billString += "----------Bill----------\n";
        billString += "Service Booking(s):\n";
        billString += "------------Sub-Total: ";
        billString += "Room Reservation(s)\n";
        billString += "------------------------\n";

        textArea.setText(billString);
        textArea.setEditable(false);
        //forEach(user -> allUsers.getChildren().add(new Label(user.getName().fullName + "|")));
    }
}
