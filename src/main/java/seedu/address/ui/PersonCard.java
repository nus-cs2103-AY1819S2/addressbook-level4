package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label customer;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private Label sellingPrice;
    @FXML
    private Label rentalPrice;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        remark.setText(person.getRemark().value);
        email.setText(person.getEmail().value);
        if (person instanceof Buyer) { customer.setText("buyer"); }
        if (person instanceof Seller) {
            final Seller seller = (Seller) person;
            customer.setText("seller");
            address.setText(seller.getAddress().value);
            sellingPrice.setText(seller.getSellingPrice().toString());
            seller.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        }
        if (person instanceof Landlord) {
            final Landlord landlord = (Landlord) person;
            customer.setText("landlord");
            address.setText(landlord.getAddress().value);
            rentalPrice.setText(landlord.getRentalPrice().toString());
            landlord.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        }
        if (person instanceof Tenant) { customer.setText("tenant"); }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
