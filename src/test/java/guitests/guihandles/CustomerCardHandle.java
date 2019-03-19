package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.hms.model.customer.Customer;

/**
 * Provides a handle to a customer card in the customer list panel.
 */
public class CustomerCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String IDENTIFICATION_NO_FIELD_ID = "#identificationNo";
    private static final String DATE_OF_BIRTH_FIELD_ID = "#dob";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;

    private final Label addressLabel;
    private final Label dobLabel;

    private final Label identificationNoLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    public CustomerCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        identificationNoLabel = getChildNode(IDENTIFICATION_NO_FIELD_ID);

        dobLabel = getChildNode(DATE_OF_BIRTH_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

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

    public String getDateOfBirth() {
        return dobLabel.getText();
    }

    public String getIdNum() {
        return identificationNoLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code customer}.
     */
    public boolean equals(Customer customer) {
        return getName().equals(customer.getName().fullName)
            && getAddress().equals(customer.getAddress().value)
            && getDateOfBirth().equals(customer.getDateOfBirth().value)
            && getPhone().equals(customer.getPhone().value)
            && getEmail().equals(customer.getEmail().value)
            && getIdNum().equals(customer.getIdNum().value)
            && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(customer.getTags().stream()
            .map(tag -> tag.tagName)
            .collect(Collectors.toList())));
    }
}
