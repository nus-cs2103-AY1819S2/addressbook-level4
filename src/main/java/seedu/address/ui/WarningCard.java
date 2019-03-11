package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.medicine.Medicine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An UI component that displays information of a {@code Medicine}.
 */
public class WarningCard extends UiPart<Region> {

    private static final String FXML = "WarningListCard.fxml";

    public final Medicine medicine;

    @FXML
    private VBox cardPane;

    @FXML
    private Text name;

    @FXML
    private Text id;

    @FXML
    private Text filter;

    public WarningCard(Medicine medicine, int displayedIndex, String listType) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        name.setText(medicine.getName().fullName);

        if (listType.equals("expiry")) {
            // filtered by expiry date
            filter.setText("Exp: " + medicine.getExpiry().toString());
        } else {
            // filtered by quantity
            filter.setText("Qty: " + medicine.getQuantity().value);
        }

        setStyle(new ArrayList<>(Arrays.asList(id, name, filter)));
    }

    /**
     * Set style id for Text object(s).
     * @param textHolders
     */
    private void setStyle(ArrayList<Text> textHolders) {
        // temporary fix to increase visibility
        for (Text text : textHolders) {
            text.setId("warningPanel");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WarningCard)) {
            return false;
        }

        // state check
        WarningCard card = (WarningCard) other;
        return id.getText().equals(card.id.getText())
                && medicine.equals(card.medicine);
    }

}
