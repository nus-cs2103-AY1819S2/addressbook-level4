package seedu.equipment.ui;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.equipment.model.equipment.Equipment;

/**
 * An UI component that displays information of a {@code Equipment}.
 */
public class EquipmentCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final Map<String, String> COLORS = Map.of("ongoing", "8B008B", "urgent", "FF0000",
                                                             "north", "2196F3", "west", "00BCD4",
                                                             "south", "4CAF50", "east", "2E8B57");

    /**
     *
     *
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EquipmentManager </a>
     */

    public final Equipment equipment;

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
    private Label serialNumber;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

    public EquipmentCard(Equipment equipment, int displayedIndex) {
        super(FXML);
        this.equipment = equipment;
        id.setText(displayedIndex + ". ");
        name.setText(equipment.getName().name);
        phone.setText(equipment.getPhone().value);
        address.setText(equipment.getAddress().value);
        serialNumber.setText(equipment.getSerialNumber().serialNumber);
        date.setText(equipment.getDate().value);
        equipment.getTags().forEach(tag -> {
            String tagName = tag.tagName;
            tags.getChildren().add(new Label(tagName));
            if (COLORS.containsKey(tagName)) {
                tags.getChildren().get(tags.getChildren().size() - 1).setStyle("-fx-background-color: #"
                        + COLORS.get(tagName));
            }
        });
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
        EquipmentCard card = (EquipmentCard) other;
        return id.getText().equals(card.id.getText())
                && equipment.equals(card.equipment);
    }
}
