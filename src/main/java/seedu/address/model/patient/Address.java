package seedu.address.model.patient;

/**
 * Represents the address of the patient
 */
public class Address {
    //public static final String REGEX_ADDRESS = "[\\p{Alnum}][\\p{Alnum} ]*";
    //public static final String REGEX_ADDRESS = "[^\\s].*";
    public static final String REGEX_ADDRESS = "^[-0-9a-zA-Z \\-#,\\s][-0-9a-zA-Z \\-#, \\s ]+$";
    public static final String ADDRESS_CONSTRAINTS =
            "Addresses should only contain alphanumeric characters and spaces but not blank";

    private String address;

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
}
