package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.equipment.model.equipment.Equipment;

/**
 * Provides a handle to a equipment card in the equipment list panel.
 */
public class EquipmentCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String SERIALNUMBER_FIELD_ID = "#serialNumber";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label dateLabel;
    private final Label serialNumberLabel;
    private final List<Label> tagLabels;

    public EquipmentCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        serialNumberLabel = getChildNode(SERIALNUMBER_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getSerialNumber() {
        return serialNumberLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code equipment}.
     */
    public boolean equals(Equipment equipment) {
        return getName().equals("Client Name: " + equipment.getName().name)
                && getAddress().equals("Address: " + equipment.getAddress().value)
                && getPhone().equals("Phone: " + equipment.getPhone().value)
                && getDate().equals("Date: " + equipment.getDate().toString())
                && getSerialNumber().equals("Serial Number: " + equipment.getSerialNumber().serialNumber)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(equipment.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
