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

    public final Medicine medicine;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label company;
    @FXML
    private Label quantity;
    @FXML
    private Label expiry;
    @FXML
    private FlowPane tags;

    public MedicineCard(Medicine medicine, int displayedIndex) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        name.setText(medicine.getName().fullName);
        company.setText(medicine.getCompany().companyName);
        quantity.setText(medicine.getTotalQuantity().toString());
        expiry.setText(medicine.getNextExpiry().toString());
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
