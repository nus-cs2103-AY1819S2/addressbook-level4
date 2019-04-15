package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Name;

/**
 * Client of the project. Basically who we are doing this project for.
 */

public class Client extends Name {

    public final String client;

    /**
     * Constructor for ProjectName.
     */
    public Client(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.client = name;
    }

    /**
     * Returns a clone of this client object.
     */
    public Client clone() {
        return new Client(this.client);
    }

    @Override
    public String toString() {
        return client;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Client // instanceof handles nulls
                && client.equals(((Client) other).client)); // state check
    }

    @Override
    public int hashCode() {
        return client.hashCode();
    }
}

