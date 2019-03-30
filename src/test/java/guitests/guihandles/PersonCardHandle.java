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
    private static final String MATRICNUMBER_FIELD_ID = "#matricNumber";
    private static final String YEAROFSTUDY_FIELD_ID = "#yearOfStudy";
    private static final String MAJOR_FIELD_ID = "#major";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label matricNumberLabel;
    private final Label yearOfStudyLabel;
    private final Label majorLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        matricNumberLabel = getChildNode(MATRICNUMBER_FIELD_ID);
        yearOfStudyLabel = getChildNode(YEAROFSTUDY_FIELD_ID);
        majorLabel = getChildNode(MAJOR_FIELD_ID);

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

    public String getMatricNumber() {
        return matricNumberLabel.getText();
    }

    public String getYearOfStudy() {
        return yearOfStudyLabel.getText();
    }

    public String getMajor() {
        return majorLabel.getText();
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
                && getMatricNumber().equals(person.getMatricNumber().value)
                && getYearOfStudy().equals(person.getYearOfStudy().value)
                && getMajor().equals(person.getMajor().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(person.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
