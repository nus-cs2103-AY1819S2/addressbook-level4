package seedu.equipment.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.equipment.model.equipment.Name;

/**
 * An UI component that displays information of a {@code WorkList}.
 */
public class ClientListCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     *
     *
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EquipmentManager </a>
     */

    public final Name clientName;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;


    public ClientListCard(Name clientName, int displayedIndex) {
        super(FXML);
        this.clientName = clientName;
        id.setText(displayedIndex + ". ");
        name.setText(clientName.name);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EquipmentCard)) {
            return false;
        }

        // state check
        ClientListCard card = (ClientListCard) other;
        return name.getText().equals(card.name.getText())
                && clientName.equals(card.clientName);
    }
}
