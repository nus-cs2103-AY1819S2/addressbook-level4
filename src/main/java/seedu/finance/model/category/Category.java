package seedu.finance.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.AppUtil.checkArgument;

/**
 * Represents a category in the finance tracker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCategoryName(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS = "Category names should be alphanumeric.\n"
            + "Category name should not be longer than 40 characters.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String categoryName;

    /**
     * Constructs a {@code Category}.
     *
     * @param categoryName A valid category name.
     */
    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryName(categoryName), MESSAGE_CONSTRAINTS);
        this.categoryName = formatCategory(categoryName);
    }

    /**
     * This methods formats the input String so that user input can be case-insensitive
     * @param categoryName the theme to change to
     * @return the theme to change to with first character being in upper-case and rest in lower-case
     */
    private String formatCategory(String categoryName) {
        categoryName = (categoryName.trim()).toLowerCase();
        return categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 40;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Category)) {
            return false;
        }

        Category e = (Category) other;
        return this.categoryName.equals(((Category) other).categoryName);
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return categoryName;
    }

}
