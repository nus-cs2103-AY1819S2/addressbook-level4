package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.equipment.model.WorkList;

/**
 * Provides a handle to a WorkList card in the WorkList list panel.
 */
public class WorkListCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ASSIGNEE_FIELD_ID = "#assignee";
    private static final String WORKLISTID_FIELD_ID = "#worklistid";
    private static final String DATE_FIELD_ID = "#date";
    private static final String EQUIPMENTSS_FIELD_ID = "#equipments";

    private final Label idLabel;
    private final Label assigneeLabel;
    private final Label worklistidLabel;
    private final Label dateLabel;
    private final List<Label> equipmentLabels;

    public WorkListCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        assigneeLabel = getChildNode(ASSIGNEE_FIELD_ID);
        worklistidLabel = getChildNode(WORKLISTID_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);

        Region tagsContainer = getChildNode(EQUIPMENTSS_FIELD_ID);
        equipmentLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public List<String> getEquipmentsStyleClasses(String equipments) {
        return equipmentLabels
                .stream()
                .filter(label -> label.getText().equals(equipments))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such equipment."));
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getAssignee() {
        return assigneeLabel.getText();
    }

    public String getworklistid() {
        return worklistidLabel.getText();
    }

    public List<String> getEquipments() {
        return equipmentLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code workList}.
     */
    public boolean equals(WorkList workList) {
        return getDate().equals(workList.getDate())
                && getAssignee().equals(workList.getAssignee())
                && getworklistid().equals(workList.getId().value)
                && ImmutableMultiset.copyOf(getEquipments()).equals(ImmutableMultiset.copyOf(workList
                .getEquipments().stream()
                .map(equipment -> equipment.getName())
                .collect(Collectors.toList())));
    }
}
