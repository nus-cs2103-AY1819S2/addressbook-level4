package seedu.address.storage;

import seedu.address.model.nextofkin.NextOfKin;

/**
 * PDF-friendly version of {@link NextOfKin}
 */
public class PdfAdaptedNextOfKin {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "NextOfKin's %s field is missing!";

    private final String kinName;
    private final String kinRelation;
    private final String kinEmail;
    private final String kinPhone;
    private final String kinAddress;

    /**
     * Constructs a {@code JsonAdaptedNextOfKin} with the given details.
     */
    public PdfAdaptedNextOfKin(String kinName, String kinRelation, String kinEmail, String kinPhone,
                               String kinAddress) {
        this.kinName = kinName;
        this.kinRelation = kinRelation;
        this.kinPhone = kinPhone;
        this.kinAddress = kinAddress;
        this.kinEmail = kinEmail;
    }

    /**
     * Converts a given {@code NextOfKin} into this class for PDF use.
     */
    public PdfAdaptedNextOfKin(NextOfKin source) {
        kinName = source.getName().toString();
        kinRelation = source.getKinRelation().getRelationship();
        kinPhone = source.getPhone().toString();
        kinAddress = source.getAddress().toString();
        kinEmail = source.getEmail().toString();
    }

    public String getKinName() {
        return kinName;
    }

    public String getKinRelation() {
        return kinRelation;
    }

    public String getKinPhone() {
        return kinPhone;
    }

    public String getKinAddress() {
        return kinAddress;
    }

    public String getKinEmail() {
        return kinEmail;
    }
}
