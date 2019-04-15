package seedu.equipment.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.equipment.model.WorkList;

/**
 * An UI component that displays information of a {@code WorkList}.
 */
public class WorkListCard extends UiPart<Region> {

    private static final String FXML = "WorkListListCard.fxml";

    /**
     *
     *
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EquipmentManager </a>
     */

    public final WorkList workList;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label worklistid;
    @FXML
    private Label assignee;
    @FXML
    private FlowPane equipments;

    public WorkListCard(WorkList workList, int displayedIndex) {

        super(FXML);
        this.workList = workList;
        id.setText(displayedIndex + ". ");
        assignee.setText("Assignee: " + workList.getAssignee());
        date.setText("Date: " + workList.getDate());
        worklistid.setText("WorkList Id: " + workList.getId().value);
        if (workList.getEquipments().isEmpty()) {
            equipments.getChildren().add(new Label("No equipment in this WorkList."));
        } else {
            equipments.getChildren().add(new Label("Equipments: "));
            workList.getEquipments().forEach(equipment -> {
                String equipmentSerialNumber = equipment.getSerialNumber().serialNumber + " ";
                equipments.getChildren().add(new Label(equipmentSerialNumber));
            });
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkListCard)) {
            return false;
        }

        // state check
        WorkListCard card = (WorkListCard) other;
        return id.getText().equals(card.id.getText())
                && workList.equals(card.workList);
    }
}
