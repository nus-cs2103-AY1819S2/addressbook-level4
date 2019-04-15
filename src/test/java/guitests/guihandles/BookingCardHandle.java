package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.hms.model.booking.Booking;

/**
 * Provides a handle to a booking card in the booking list panel.
 */
public class BookingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String PAYER_NAME_FIELD_ID = "#payerName";
    private static final String SERVICE_TYPE_FIELD_ID = "#serviceType";
    private static final String TIME_FIELD_ID = "#time";
    private static final String IDENTIFICATION_NO_FIELD_ID = "#identificationNo";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String COMMENTS_FIELD_ID = "#comments";
    //private static final String ALLUSERS_FIELD_ID = "#allUsers";

    private Label serviceTypeLabel;
    private Label timeLabel;
    private Label idLabel;
    private Label payerNameLabel;
    private Label phoneLabel;
    private Label identificationNoLabel;
    private Label commentsLabel;
    //private FlowPane allUsers;

    public BookingCardHandle(Node cardNode) {
        super(cardNode);

        serviceTypeLabel = getChildNode(SERVICE_TYPE_FIELD_ID);
        timeLabel = getChildNode(TIME_FIELD_ID);
        idLabel = getChildNode(ID_FIELD_ID);

        payerNameLabel = getChildNode(PAYER_NAME_FIELD_ID);

        phoneLabel = getChildNode(PHONE_FIELD_ID);
        identificationNoLabel = getChildNode(IDENTIFICATION_NO_FIELD_ID);

        commentsLabel = getChildNode(COMMENTS_FIELD_ID);

        //allUsers = getChildNode(ALLUSERS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPayerName() {
        return payerNameLabel.getText();
    }

    public String getServiceType() {
        return serviceTypeLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    public String getIdNum() {
        return identificationNoLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getComments() {
        return commentsLabel.getText();
    }

    //public String getAllusers() {
    //    return allUsers.getAccessibleText();
    //}

    /**
     * Returns true if this handle contains {@code booking}.
     */
    public boolean equals(Booking booking) {
        return getServiceType().equals(booking.getService().getName())
                && getPayerName().equals("Payer Name: " + booking.getPayer().getName().fullName)
                && getPhone().equals("Payer Phone: " + booking.getPayer().getPhone().value)
                && getComments().equals(booking.getComment().orElse("No Comment"))
                && getIdNum().equals("Payer ID: " + booking.getPayer().getIdNum().value)
                && getTime().equals("Time: " + booking.getTiming().toString());
    }
}
