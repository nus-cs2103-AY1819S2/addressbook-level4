package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Doctor;


/**
 * Provides a handle to a doctor card in the doctor list panel.
 */
public class DoctorCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String AGE_FIELD_ID = "#age";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String GENDER_FIELD_ID = "#gender";
    private static final String SPECS_FIELD_ID = "#specialisations";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label ageLabel;
    private final Label phoneLabel;
    private final Label genderLabel;
    private final List<Label> specsLabels;

    public DoctorCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        ageLabel = getChildNode(AGE_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        genderLabel = getChildNode(GENDER_FIELD_ID);

        Region tagsContainer = getChildNode(SPECS_FIELD_ID);
        specsLabels = tagsContainer
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

    public String getAge() {
        return ageLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }

    public List<String> getSpecialisations() {
        return specsLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code doctor}.
     */
    public boolean equals(Doctor doctor) {
        return getName().equals(doctor.getName().fullName)
                && getAge().equals(doctor.getAge().value)
                && getPhone().equals(doctor.getPhone().value)
                && getGender().equals(doctor.getGender().value)
                && ImmutableMultiset.copyOf(getSpecialisations()).equals(ImmutableMultiset
                        .copyOf(doctor.getSpecs().stream()
                        .map(specialisation -> specialisation.specialisation)
                        .collect(Collectors.toList())));
    }
}
