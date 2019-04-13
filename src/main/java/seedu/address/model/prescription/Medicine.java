package seedu.address.model.prescription;

/**
 * Medicine class has an association with Prescription class, each prescription has one medicine attribute.
 */


public class Medicine {
    public static final String MESSAGE_CONSTRAINTS = "Medicine name should be short";
    private static int count = 0;

    private int id;
    private String name;

    public Medicine(String name) {
        this.id = count;
        count++;
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid medicine name.
     */
    public static boolean isValidName(String test) {
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Medicine
                && this.name.equals(((Medicine) other).getName());
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return this.name;
    }

}

