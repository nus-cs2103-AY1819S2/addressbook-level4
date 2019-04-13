package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;

/**
 * Provides a handle to a medicine card in the medicine list panel.
 */
public class WarningCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String FIELD_FIELD_ID = "#field"; // medicine details, total qty or batch exp

    private final Label idText;
    private final Label nameText;
    private final Label fieldText;

    private WarningPanelPredicateType type;

    public WarningCardHandle(Node cardNode, WarningPanelPredicateType listType) {
        super(cardNode);

        idText = getChildNode(ID_FIELD_ID);
        nameText = getChildNode(NAME_FIELD_ID);
        fieldText = getChildNode(FIELD_FIELD_ID);
        type = listType;
    }

    public String getId() {
        return idText.getText();
    }

    public String getName() {
        return nameText.getText();
    }

    public String getDetail() {
        return fieldText.getText();
    }

    /**
     * Returns true if this handle contains {@code medicine}.
     */
    public boolean equals(Medicine medicine) {
        if (!getName().equals(medicine.getName().fullName)) {
            return false;
        }

        if (type.equals(WarningPanelPredicateType.EXPIRY)) {
            String expectedDetail = "";
            for (Batch batch: medicine.getBatches().values()) {
                expectedDetail += String.format("%s [Exp: %s]\n",
                        batch.getBatchNumber().toString(),
                        batch.getExpiry().toString());
            }

            return getDetail().equals(expectedDetail);
        } else {
            // List is for medicines with low stock
            return getDetail().equals(String.format("Qty: %s\n", medicine.getTotalQuantity().toString()));
        }

    }
}
