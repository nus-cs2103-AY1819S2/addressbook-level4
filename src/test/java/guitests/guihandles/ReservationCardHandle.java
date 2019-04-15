package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.hms.model.reservation.Reservation;

/**
 * Provides a handle to a customer card in the customer list panel.
 */
public class ReservationCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String PAYER_NAME_FIELD_ID = "#payerName";
    private static final String ROOM_TYPE_FIELD_ID = "#roomType";
    private static final String DATE_FIELD_ID = "#date";
    private static final String IDENTIFICATION_NO_FIELD_ID = "#identificationNo";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String COMMENTS_FIELD_ID = "#comments";
    //private static final String ALLUSERS_FIELD_ID = "#allUsers";

    private Label roomTypeLabel;
    private Label dateLabel;
    private Label idLabel;
    private Label payerNameLabel;
    private Label phoneLabel;
    private Label identificationNoLabel;
    private Label commentsLabel;
    //private FlowPane allUsers;

    public ReservationCardHandle(Node cardNode) {
        super(cardNode);

        roomTypeLabel = getChildNode(ROOM_TYPE_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
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

    public String getRoomType() {
        return roomTypeLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
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
     * Returns true if this handle contains {@code customer}.
     */
    public boolean equals(Reservation reservation) {
        return getRoomType().equals(reservation.getRoom().getName())
                && getPayerName().equals("Payer Name: " + reservation.getPayer().getName().fullName)
                && getPhone().equals("Payer Phone: " + reservation.getPayer().getPhone().value)
                && getComments().equals(reservation.getComment().orElse("No Comment"))
                && getIdNum().equals("Payer ID: " + reservation.getPayer().getIdNum().value)
                && getDate().equals("Date: " + reservation.getDates().toString());
    }
}
