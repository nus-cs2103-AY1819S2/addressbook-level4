package quickdocs.model.patient;

/**
 * Represents the address of the patient
 */
public class Address {
    public static final String REGEX_ADDRESS = "[^\\s\\W].*";
    public static final String ADDRESS_CONSTRAINTS =
            "Addresses should not be blank or only contain only spaces or symbols";

    private String address;

    // empty constructor for json reconstruction
    public Address() {
    }

    public Address(String address) {
        if (!address.matches(REGEX_ADDRESS)) {
            throw new IllegalArgumentException(ADDRESS_CONSTRAINTS);
        }
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && address.equals(((Address) other).getAddress())); // state check
    }

    public static boolean isValidAddress(String string) {
        return string.matches(REGEX_ADDRESS);
    }
}
