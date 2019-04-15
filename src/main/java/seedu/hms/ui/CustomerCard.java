package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.hms.model.customer.Customer;

/**
 * An UI component that displays information of a {@code Customer}.
 */
public class CustomerCard extends UiPart<Region> {

    private static final String FXML = "CustomerListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/hotelManagementSystem-level4/issues/336">The issue on
     * HotelManagementSystem level 4</a>
     */

    public final Customer customer;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label dob;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label identificationNo;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public CustomerCard(Customer customer, int displayedIndex) {
        super(FXML);
        this.customer = customer;
        id.setText(displayedIndex + ". ");
        name.setText(customer.getName().toString());
        phone.setText("Phone: " + customer.getPhone().toString());
        dob.setText(customer.getDateOfBirth().toString().equals("")
            ? "Date of Birth: Not provided" : "Date of Birth: " + customer.getDateOfBirth().value);
        identificationNo.setText("Identification No: " + customer.getIdNum().value);
        address.setText(customer.getAddress().toString().equals("")
            ? "Address: Not provided" : "Address: " + customer.getAddress().toString());
        email.setText("Email: " + customer.getEmail().toString());
        customer.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerCard)) {
            return false;
        }

        // state check
        CustomerCard card = (CustomerCard) other;
        return id.getText().equals(card.id.getText())
            && customer.equals(card.customer);
    }
}
