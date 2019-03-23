package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.request.Request;

/**
 * An UI component that displays information of a {@code Person}.
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
    private Label date;
    @FXML
    private Label nric;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private FlowPane conditions;

    public RequestCard(Request request, int displayedIndex) {
        super(FXML);
        this.request = request;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(request.getName().toString());
        this.date.setText(request.getRequestDate().getDate().toString());
        this.nric.setText(request.getNric().toString());
        this.phone.setText(request.getPhone().value);
        this.address.setText(request.getAddress().toString());
        this.request.getConditions().forEach(c ->
                this.conditions.getChildren().add(new Label(c.toString())));
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
