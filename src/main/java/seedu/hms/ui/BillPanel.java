package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.hms.model.bill.Bill;

//import javafx.scene.layout.HBox;

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

    public final Bill bill;

    @FXML
    private HBox cardPane;
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

    public BillPanel(Bill bill) {
        super(FXML);
        this.bill = bill;
        identificationNo.setText("");
        comments.setText("");
        //forEach(user -> allUsers.getChildren().add(new Label(user.getName().fullName + "|")));
    }
}
