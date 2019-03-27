package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.request.Request;

/**
 * An UI component that displays information of a {@code Request}.
 */
public class RequestCard extends UiPart<Region> {

    private static final String FXML = "RequestListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Request request;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label nric;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private FlowPane conditions;
    @FXML
    private Label date;
    @FXML
    private Label status;

    public RequestCard(Request request, int displayedIndex) {
        super(FXML);
        this.request = request;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(request.getName().toString());
        this.nric.setText("(" + request.getNric().toString() + ")");
        this.phone.setText(request.getPhone().value);
        this.address.setText(request.getAddress().toString());
        this.request.getConditions().forEach(c ->
                this.conditions.getChildren().add(new Label(c.toString())));
        this.date.setText("Appt. Date: " + request.getRequestDate().getFormattedDate());

        String requestStatus = request.getRequestStatus().toString();
        this.status.setText(requestStatus);

        switch (requestStatus) {
        case "PENDING": // red light
            this.status.setTextFill(Color.web("#F22613"));
            break;
        case "ONGOING": // yellow light
            this.status.setTextFill(Color.web("#F7CA18"));
            break;
        case "COMPLETED": // green light
            this.status.setTextFill(Color.web("#00E640"));
            break;
        default:
            break;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RequestCard)) {
            return false;
        }

        // state check
        RequestCard card = (RequestCard) other;
        return id.getText().equals(card.id.getText())
                && request.equals(card.request);
    }
}
