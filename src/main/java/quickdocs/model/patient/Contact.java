package quickdocs.model.patient;

/**
 * Represents the local phone number of the patient, without country code
 */
public class Contact {

    public static final String REGEX_CONTACT = "[\\d]{8}";
    public static final String CONTACT_CONSTRAINTS = "Local phone number should be 8 digits only";

    private String contact;

    // empty constructor for json reconstruction
    public Contact() {
    }

    public Contact(String contact) {
        if (!contact.matches(REGEX_CONTACT)) {
            throw new IllegalArgumentException(CONTACT_CONSTRAINTS);
        }
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return contact;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contact // instanceof handles nulls
                && contact.equals(((Contact) other).getContact())); // state check
    }

    public static boolean isValidContact(String string) {
        return string.matches(REGEX_CONTACT);
    }
}
