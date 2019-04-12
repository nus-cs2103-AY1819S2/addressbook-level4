package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;

/**
 * An UI component that displays information of a {@code Medicine}.
 */
public class WarningCard extends UiPart<Region> {

    private static final String FXML = "WarningListCard.fxml";

    public final Medicine medicine;

    @FXML
    private VBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label field;

    private WarningPanelPredicateType type;

    public WarningCard(Medicine medicine, int displayedIndex,
                       WarningPanelPredicateType listType,
                       WarningPanelPredicateAccessor warningPanelPredicateAccessor) {
        super(FXML);
        this.medicine = medicine;
        this.type = listType;
        id.setText(displayedIndex + ". ");
        name.setText(medicine.getName().fullName);

        switch (listType) {
        case EXPIRY:
            FilteredList<Batch> filteredBatch = medicine
                    .getFilteredBatch(warningPanelPredicateAccessor.getBatchExpiryPredicate());
            field.setText(getFormattedBatch(filteredBatch));
            break;

        case LOW_STOCK:
            field.setText(String.format("Qty: %s\n", medicine.getTotalQuantity().toString()));
            break;

        default:
            break;
        }
    }

    public WarningPanelPredicateType getType() {
        return type;
    }

    private String getFormattedBatch(FilteredList<Batch> filteredBatch) {
        String formatted = "";
        for (Batch batch: filteredBatch) {
            formatted += String.format("%s [Exp: %s]\n",
                    batch.getBatchNumber().toString(),
                    batch.getExpiry().toString());
        }

        return formatted;
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
