package seedu.address.logic.options;

/**
 * Represents the options available for Sort command.
 */
public enum SortOption {
    // WARNING: OPTIONS must be the first.
    OPTIONS,
    NAME,
    COLOR,
    TYPE;

    /**
     * Return true if option supplied is valid, false otherwise.
     */
    public static boolean isValid(String optionString) {
        for (SortOption so : SortOption.values()) {
            String validOption = so.toString();
            if (validOption.equalsIgnoreCase(optionString.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a SortOption.
     */
    public static SortOption create(String optionString) throws IllegalArgumentException {
        String option = optionString.trim().toUpperCase();
        if (!isValid(option)) {
            throw new IllegalArgumentException("Invalid option.");
        }

        return SortOption.valueOf(option);
    }

    /**
     * Return all the available sorting options in String format.
     */
    public static String allOptions() {
        StringBuilder sb = new StringBuilder();
        SortOption[] sortOptions = SortOption.values();
        sb.append("All the valid sorting options:\n");
        for (int i = 1; i < sortOptions.length; i++) {
            sb.append(sortOptions[i].toString().toLowerCase() + ", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
