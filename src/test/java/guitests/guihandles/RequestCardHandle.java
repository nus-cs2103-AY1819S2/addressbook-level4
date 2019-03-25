package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.request.Request;

/**
 * Provides a handle to a request card in the request list panel.
 */
public class RequestCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String NRIC_FIELD_ID = "#nric";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String CONDITIONS_FIELD_ID = "#conditions";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label nricLabel;
    private final Label phoneLabel;
    private final Label addressLabel;
    private final List<Label> conditionLabels;

    public RequestCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        nricLabel = getChildNode(NRIC_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        Region condContainer = getChildNode(CONDITIONS_FIELD_ID);
        conditionLabels = condContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() { return nameLabel.getText(); }

    public String getNric() {
        return nricLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getConditions() {
        return conditionLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code request}.
     */
    public boolean equals(Request request) {
        return getName().equals(request.getName().toString())
                && getPhone().equals(request.getNric().toString());
    }
}
