package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * Provides a handle to a health worker card in the health worker list panel.
 */
public class HealthWorkerCardHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ORGANISATION_FIELD_ID = "#organisation";
    private static final String SPECIALISATION_FIELD_ID = "#specialisations";

    private final Label nameLabel;
    private final Label organisationLabel;
    private final List<Label> specialisationLabels;

    public HealthWorkerCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        organisationLabel = getChildNode(ORGANISATION_FIELD_ID);
        Region condContainer = getChildNode(SPECIALISATION_FIELD_ID);
        specialisationLabels = condContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getOrganisation() {
        return organisationLabel.getText();
    }

    public List<String> getSpecialisations() {
        return specialisationLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code healthWorker}.
     */
    public boolean equals(HealthWorker healthWorker) {
        return getName().equals(healthWorker.getName().toString())
                && getOrganisation().equals(healthWorker.getOrganization().toString());
    }
}
