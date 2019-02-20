package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.medicine.Medicine;

/**
 * An UI component that displays information of a {@code Medicine}.
 */
public class MedicineCard extends UiPart<Region> {

    private static final String FXML = "MedicineListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Medicine medicine;

    @FXML
    private HBox cardPane;
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
    private FlowPane tags;

    public MedicineCard(Medicine medicine, int displayedIndex) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        name.setText(medicine.getName().fullName);
        phone.setText(medicine.getPhone().value);
        address.setText(medicine.getAddress().value);
        email.setText(medicine.getEmail().value);
        medicine.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicineCard)) {
            return false;
        }

        // state check
        MedicineCard card = (MedicineCard) other;
        return id.getText().equals(card.id.getText())
                && medicine.equals(card.medicine);
    }
}
