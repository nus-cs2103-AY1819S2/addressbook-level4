package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;

/**
 * Provides a handle to a restaurant card in the restaurant list panel.
 */
public class SummaryInfoVBoxHandle extends NodeHandle<Node> {
    private static final String ADDRESS_POSTAL_FIELD_ID = "#addressPostal";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String OPENING_HOURS_FIELD_ID = "#openingHours";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String WEBLINK_FIELD_ID = "#weblink";
    private static final String FIELD_NOT_ADDED = "N.A.";
    private static final String ADDRESS_POSTAL_PLACEHOLDER = "Address: \n";
    private static final String PHONE_PLACEHOLDER = "Contact No.: \n";
    private static final String OPENING_HOURS_PLACEHOLDER = "Opening Hours: \n";
    private static final String EMAIL_PLACEHOLDER = "Email: \n";
    private static final String WEBLINK_PLACEHOLDER = "Website: \n";

    private final Label addressPostalLabel;
    private final Label phoneLabel;
    private final Label openingHoursLabel;
    private final Label emailLabel;
    private final Label weblinkLabel;

    public SummaryInfoVBoxHandle(VBox vBox) {
        super(vBox);

        addressPostalLabel = getChildNode(ADDRESS_POSTAL_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        openingHoursLabel = getChildNode(OPENING_HOURS_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        weblinkLabel = getChildNode(WEBLINK_FIELD_ID);
    }


    public String getOpeningHours() {
        String label = openingHoursLabel.getText();
        String openingHours;

        if (label.equals(OPENING_HOURS_PLACEHOLDER + FIELD_NOT_ADDED)) {
            openingHours = OPENING_HOURS_PLACEHOLDER + "No opening hours added";
        } else {
            openingHours = label;
        }

        return openingHours;
    }

    public String getWeblink() {
        String label = weblinkLabel.getText();
        String weblink;

        if (label.equals(WEBLINK_PLACEHOLDER + FIELD_NOT_ADDED)) {
            weblink = WEBLINK_PLACEHOLDER + Weblink.NO_WEBLINK_STRING;
        } else {
            weblink = label;
        }

        return weblink;
    }

    public String getAddressPostal() {
        return addressPostalLabel.getText();
    }

    public String getPhone() {
        String label = phoneLabel.getText();
        String phone;

        if (label.equals(PHONE_PLACEHOLDER + FIELD_NOT_ADDED)) {
            phone = PHONE_PLACEHOLDER + "No phone added";
        } else {
            phone = label;
        }

        return phone;
    }

    public String getEmail() {
        String label = emailLabel.getText();
        String email;

        if (label.equals(EMAIL_PLACEHOLDER + FIELD_NOT_ADDED)) {
            email = EMAIL_PLACEHOLDER + "No email added";
        } else {
            email = label;
        }

        return email;
    }

    /**
     * Returns true if this handle contains {@code restaurant}.
     */
    public boolean equals(Restaurant restaurant) {
        return getAddressPostal().equals(ADDRESS_POSTAL_PLACEHOLDER + restaurant.getAddress().value
                + " S(" + restaurant.getPostal().value + ")")
                && getPhone().equals(PHONE_PLACEHOLDER + restaurant.getPhone().value)
                && getEmail().equals(EMAIL_PLACEHOLDER + restaurant.getEmail().value)
                && getOpeningHours().equals(OPENING_HOURS_PLACEHOLDER + restaurant.getOpeningHours().value)
                && getWeblink().equals(WEBLINK_PLACEHOLDER + restaurant.getWeblink().toString());
    }
}
