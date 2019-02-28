package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Customer> PREDICATE_MATCHING_NO_CUSTOMERS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Customer> toDisplay) {
        Optional<Predicate<Customer>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredCustomerList(predicate.orElse(PREDICATE_MATCHING_NO_CUSTOMERS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Customer... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Customer} equals to {@code other}.
     */
    private static Predicate<Customer> getPredicateMatching(Customer other) {
        return customer -> customer.equals(other);
    }
}
