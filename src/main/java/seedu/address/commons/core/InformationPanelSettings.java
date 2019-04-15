package seedu.address.commons.core;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Settings to control sorting of BatchTable in Information Panel.
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
        requireAllNonNull(sortProperty, sortDirection);
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
        return other == this // short circuit if same object
                || (other instanceof InformationPanelSettings // instanceof handles nulls
                && sortProperty.equals(((InformationPanelSettings) other).sortProperty)
                && sortDirection.equals(((InformationPanelSettings) other).sortDirection));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sort Property: '")
                .append(sortProperty)
                .append("' Sort Direction: '")
                .append(sortDirection)
                .append("'");
        return sb.toString();
    }

    /**
     * Represents the possible properties that the batch table can be sorted by.
     */
    public enum SortProperty {
        BATCHNUMBER, QUANTITY, EXPIRY;

        public static final String MESSAGE_CONSTRAINTS = "Sort property can only be batchnumber, quantity or expiry.";

        /**
         * Returns true if a given string is a valid sort property.
         */
        public static boolean isValidSortProperty(String sortProperty) {
            return sortProperty.equalsIgnoreCase(BATCHNUMBER.toString())
                    || sortProperty.equalsIgnoreCase(QUANTITY.toString())
                    || sortProperty.equalsIgnoreCase(EXPIRY.toString());
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * Represents the possible direction that the batch table can be sorted in.
     */
    public enum SortDirection {
        ASCENDING, DESCENDING;

        public static final String MESSAGE_CONSTRAINTS = "Sort direction can only be ascending or descending.";

        /**
         * Returns true if a given string is a valid sort direction.
         */
        public static boolean isValidSortDirection(String sortDirection) {
            return sortDirection.equalsIgnoreCase(ASCENDING.toString())
                    || sortDirection.equalsIgnoreCase(DESCENDING.toString());
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

}
