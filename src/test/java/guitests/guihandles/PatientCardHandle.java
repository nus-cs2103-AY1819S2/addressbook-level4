package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Patient;

/**
 * Provides a handle to a patient card in the patient list panel.
 */
public class PatientCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String GENDER_FIELD_ID = "#gender";
    private static final String AGE_FIELD_ID = "#age";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label genderLabel;
    private final Label ageLabel;
    private final Label phoneLabel;
    private final Label addressLabel;
    private final List<Label> tagLabels;

    public PatientCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        genderLabel = getChildNode(GENDER_FIELD_ID);
        ageLabel = getChildNode(AGE_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }

    public String getAge() {
        return ageLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code patient}.
     */
    public boolean equals(Patient patient) {
        return getName().equals(patient.getName().fullName)
                && getGender().equals(patient.getGender().value)
                && getAge().equals(patient.getAge().value)
                && getPhone().equals(patient.getPhone().value)
                && getAddress().equals(patient.getAddress().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(patient.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
