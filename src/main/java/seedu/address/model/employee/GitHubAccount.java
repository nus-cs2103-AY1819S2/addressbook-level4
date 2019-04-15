package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's git hub account in the pocket project.
 * Guarantees: immutable; is valid as declared in {@link #isValidAccount(String)}
 */
public class GitHubAccount {

    public static final String MESSAGE_CONSTRAINTS = "Github accounts represent the Github username "
        + "of the employee and thus accepts any alphanumeric characters without whitspaces.";

    /**
     * The github account cannot contain a whitespace
     * at all throughout the whole account name.
     * " " (a blank string) is not a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\S+[a-zA-Z0-9]*$";

    public final String value;

    /**
     * Constructs an {@code GitHubAccount}.
     *
     * @param account A valid account.
     */
    public GitHubAccount(String account) {
        requireNonNull(account);
        checkArgument(isValidAccount(account), MESSAGE_CONSTRAINTS);
        value = account;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAccount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a clone of this GitHubAccount Object.
     */
    public GitHubAccount clone() {
        return new GitHubAccount(this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GitHubAccount // instanceof handles nulls
                && value.equals(((GitHubAccount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
