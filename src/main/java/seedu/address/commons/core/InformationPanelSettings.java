package seedu.address.commons.core;

/**
 * Settings to control sorting of BatchTable information
 */
public class InformationPanelSettings {
    public static final SortProperty DEFAULT_SORT_PROPERTY = SortProperty.BATCHNUMBER;
    public static final SortDirection DEFAULT_SORT_DIRECTION = SortDirection.ASCENDING;

    private final SortProperty sortProperty;
    private final SortDirection sortDirection;

    public InformationPanelSettings() {
        sortProperty = DEFAULT_SORT_PROPERTY;
        sortDirection = DEFAULT_SORT_DIRECTION;
    }

    public InformationPanelSettings(SortProperty sortProperty, SortDirection sortDirection) {
        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
    }

    public SortProperty getSortProperty() {
        return sortProperty;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof InformationPanelSettings)) { //this handles null as well.
            return false;
        }

        InformationPanelSettings o = (InformationPanelSettings) other;

        return sortProperty == o.sortProperty
                && sortDirection == o.sortDirection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sort Property: ")
                .append(sortProperty)
                .append(" Sort Direction: ")
                .append(sortDirection);
        return sb.toString();
    }

    /**
     * Represents the possible properties that the batchtable can be sorted by.
     */
    public enum SortProperty {
        BATCHNUMBER, QUANTITY, EXPIRY;

        public static final String MESSAGE_CONSTRAINTS = "Sort property can only be batchnumber, quantity or expiry.";

        /**
         * Returns true if a given string is a valid sort property.
         */
        public static boolean isValidSortProperty(String sortProperty) {
            return sortProperty.toUpperCase().equals("BATCHNUMBER")
                    || sortProperty.toUpperCase().equals("QUANTITY")
                    || sortProperty.toUpperCase().equals("EXPIRY");
        }

        @Override
        public String toString() {
            return "'" + super.toString().toLowerCase() + "'";
        }
    }

    /**
     * Represents the possible direction that the batchtable can be sorted in.
     */
    public enum SortDirection {
        ASCENDING, DESCENDING;

        public static final String MESSAGE_CONSTRAINTS = "Sort direction can only be ascending or descending.";

        /**
         * Returns true if a given string is a valid sort direction.
         */
        public static boolean isValidSortDirection(String sortDirection) {
            return sortDirection.toUpperCase().equals("ASCENDING")
                    || sortDirection.toUpperCase().equals("DESCENDING");
        }

        @Override
        public String toString() {
            return "'" + super.toString().toLowerCase() + "'";
        }
    }

}
