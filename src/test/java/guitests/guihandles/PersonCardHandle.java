package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String EDUCATION_FIELD_ID = "#education";
    private static final String GPA_FIELD_ID = "#gpa";
    private static final String DEGREE_FIELD_ID = "#degree";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final Label educationLabel;
    private final Label gpaLabel;
    private final Label degreeLabel;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        educationLabel = getChildNode(EDUCATION_FIELD_ID);
        gpaLabel = getChildNode(GPA_FIELD_ID);
        degreeLabel = getChildNode(DEGREE_FIELD_ID);

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

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getEducation() {
        return educationLabel.getText();
    }

    public String getGpa() {
        return gpaLabel.getText();
    }

    public String getDegree() {
        return degreeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {

        return getName().equals(person.getName().fullName)
                && getAddress().equals("Address - " + person.getAddress().value)
                && getPhone().equals("Phone - " + person.getPhone().value)
                && getEmail().equals("Email - " + person.getEmail().value)
                && getEducation().equals("School - " + person.getEducation().university)
                && getDegree().equals("Degree - " + person.getDegree().value)
                && getGpa().equals("GPA - " + person.getGpa().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(person.getTags()
                .stream().map(tag -> tag.unlabelledTagName).collect(Collectors.toList())));
    }
}
