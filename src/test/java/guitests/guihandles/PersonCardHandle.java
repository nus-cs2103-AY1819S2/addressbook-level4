package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

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
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String SELLINGPRICE_FIELD_ID = "#sellingPrice";
    private static final String RENTALPRICE_FIELD_ID = "#rentalPrice";
    private static final String CUSTOMER_FIELD_ID = "#customer";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label remarkLabel;
    private final Label addressLabel;
    private final Label sellingPriceLabel;
    private final Label rentalPriceLabel;
    private final Label customerLabel;

    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        remarkLabel = getChildNode(REMARK_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        customerLabel = getChildNode(CUSTOMER_FIELD_ID);
        sellingPriceLabel = getChildNode(SELLINGPRICE_FIELD_ID);
        rentalPriceLabel = getChildNode(RENTALPRICE_FIELD_ID);

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

    public String getCustomer() {
        return customerLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getSellingPrice() {
        return sellingPriceLabel.getText();
    }

    public String getRentalPrice() {
        return rentalPriceLabel.getText();
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
                && getRemark().equals(person.getRemark().value)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value);
    }

    /**
     * Returns true if this handle contains {@code buyer}.
     */
    public boolean equals(Buyer buyer) {
        return getName().equals(buyer.getName().fullName)
                && getRemark().equals(buyer.getRemark().value)
                && getPhone().equals(buyer.getPhone().value)
                && getEmail().equals(buyer.getEmail().value)
                && getCustomer().equals(Buyer.CUSTOMER_TYPE_BUYER);
    }

    /**
     * Returns true if this handle contains {@code tenant}.
     */
    public boolean equals(Tenant tenant) {
        return getName().equals(tenant.getName().fullName)
                && getRemark().equals(tenant.getRemark().value)
                && getPhone().equals(tenant.getPhone().value)
                && getEmail().equals(tenant.getEmail().value)
                && getCustomer().equals(Tenant.CUSTOMER_TYPE_TENANT);
    }

    /**
     * Returns true if this handle contains {@code seller}.
     */
    public boolean equals(Seller seller) {
        return getName().equals(seller.getName().fullName)
                && getCustomer().equals(Seller.CUSTOMER_TYPE_SELLER)
                && getRemark().equals(seller.getRemark().value)
                && getPhone().equals(seller.getPhone().value)
                && getEmail().equals(seller.getEmail().value)
                && getAddress().equals(seller.getAddress().value)
                && getSellingPrice().equals(seller.getSellingPrice().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(seller.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }

    /**
     * Returns true if this handle contains {@code landlord}.
     */
    public boolean equals(Landlord landlord) {
        return getName().equals(landlord.getName().fullName)
                && getCustomer().equals(Landlord.CUSTOMER_TYPE_LANDLORD)
                && getRemark().equals(landlord.getRemark().value)
                && getPhone().equals(landlord.getPhone().value)
                && getEmail().equals(landlord.getEmail().value)
                && getAddress().equals(landlord.getAddress().value)
                && getRentalPrice().equals(landlord.getRentalPrice().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(landlord.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
