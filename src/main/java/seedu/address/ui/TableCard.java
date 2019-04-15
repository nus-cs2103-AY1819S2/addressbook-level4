package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.table.Table;

/**
 * An UI component that displays information of a {@code Table}.
 */
public class TableCard extends UiPart<Region> {

    private static final String FXML = "TableCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RestOrRant level 4</a>
     */

    public final Table table;

    @FXML
    private HBox tableCardPane;
    @FXML
    private Label id;
    @FXML
    private Label tableNumber;
    @FXML
    private Label tableStatus;

    public TableCard(Table table) {
        super(FXML);
        this.table = table;

        tableNumber.setText("Table " + this.table.getTableNumber());
        tableStatus.setText("Status: " + this.table.getTableStatus());
        if (table.getTableStatus().getNumberOfSeats().equals("0")) {
            tableCardPane.setStyle("-fx-background-color: #121e23;");
        } else if (!table.isOccupied()) {
            tableCardPane.setStyle("-fx-background-color: #6cadc9;");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TableCard)) {
            return false;
        }

        // state check
        TableCard card = (TableCard) other;
        return table.equals(card.table);
    }

}
