package seedu.address.logic.options;

/**
 * Represents the options available for Sort command.
 */
public enum ListOption {
    // WARNING: OPTIONS must be the first.
    OPTIONS,
    ALL,
    TOP,
    BOTTOM,
    BELT,
    SHOES,
    COLOR;
    // TODO; add option to see specific color

    /**
     * Return true if option supplied is valid, false otherwise.
     */
    public static boolean isValid(String optionString) {
        for (ListOption so : ListOption.values()) {
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
    public static ListOption create(String optionString) throws IllegalArgumentException {
        if (!isValid(optionString)) {
            throw new IllegalArgumentException("Invalid option.");
        }

        return ListOption.valueOf(optionString.toUpperCase());
    }

    /**
     * Return all the available sorting options in String format.
     */
    public static String allOptions() {
        StringBuilder sb = new StringBuilder();
        ListOption[] listOptions = ListOption.values();
        sb.append("All the valid list options:\n");
        for (int i = 1; i < listOptions.length; i++) {
            sb.append(listOptions[i].toString().toLowerCase() + ", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
