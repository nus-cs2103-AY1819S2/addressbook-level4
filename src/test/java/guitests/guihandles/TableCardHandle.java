package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.table.Table;

/**
 * Provides a handle to a table card in the table flow panel.
 */
public class TableCardHandle extends NodeHandle<Node> {
    private static final String TABLE_NUMBER_FIELD_ID = "#tableNumber";
    private static final String TABLE_STATUS_FIELD_ID = "#tableStatus";

    private final Label tableStatusLabel;
    private final Label tableNumberLabel;

    public TableCardHandle(Node cardNode) {
        super(cardNode);

        tableNumberLabel = getChildNode(TABLE_NUMBER_FIELD_ID);
        tableStatusLabel = getChildNode(TABLE_STATUS_FIELD_ID);
    }

    public String getTableNumber() {
        return tableNumberLabel.getText();
    }

    public String getTableStatus() {
        return tableStatusLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code table}.
     */
    public boolean equals(Table table) {
        return getTableNumber().equals(table.getTableNumber().toString())
                && getTableStatus().equals(table.getTableNumber().toString());
    }
}
