package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.util.warning.WarningPanelListType;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;

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
    private Text field;

    public WarningCard(Medicine medicine, int displayedIndex,
                       WarningPanelListType listType, WarningPanelPredicateAccessor warningPanelPredicateAccessor) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        name.setText(medicine.getName().fullName);

        switch (listType) {
            case EXPIRY:
                FilteredList<Batch> filteredBatch = medicine.
                        getFilteredBatch(warningPanelPredicateAccessor.getBatchExpiringPredicate());
                field.setText(getFormattedBatch(filteredBatch));
                break;

            case LOW_STOCK:
                field.setText("Qty: " + medicine.getTotalQuantity().value);
                break;

            default:
                break;
        }

        setStyle(new ArrayList<>(Arrays.asList(id, name, field)));
    }

    private String getFormattedBatch(FilteredList<Batch> filteredBatch) {
        ArrayList<String> formatted = new ArrayList<>();
        for (Batch batch: filteredBatch) {
            formatted.add(batch.getBatchNumber().toString() + " [Exp: " + batch.getExpiry().toString() + "]");
        }

        return String.join("\n", formatted);
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
