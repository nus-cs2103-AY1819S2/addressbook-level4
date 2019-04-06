package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.comparators.SortRating;
import seedu.address.model.Model;

/**
 * Lists all restaurants in The Food Diary in ascending or descending order of
 * its aggregate ratings.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DES = "DES";
    public static final String FEEDBACK_ASC = "ascending";
    public static final String FEEDBACK_DES = "descending";
    public static final String VALIDATION_REGEX = "(?i:^(ASC)$|^(DES)$)";

    public static final String MESSAGE_USAGE = COMMAND_WORD +
            ": Sorts all restaurants in ascending or descending order of its aggregate ratings.\n"
            + "The list of restaurants will be sorted in descending order of aggregate ratings by default, "
            + "unless its order (ASC/DES) is specified.\n"
            + "The specified keywords for order are 'ASC' or 'DES' (case-insensitive) and represent the order "
            + "that the restaurants will be listed in.\n"
            + "Parameters: [ASC] or [DES]\n"
            + "Example: " + COMMAND_WORD + " asc";

    public static final String MESSAGE_SUCCESS = "Sorted all restaurants in %1$s order";

    private Order order;

    public SortCommand(Order order) {
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        model.updateFilteredRestaurantListAndSort(PREDICATE_SHOW_ALL_RESTAURANTS, new SortRating(order));
        model.commitFoodDiary();

        if (order.equals(new Order(ORDER_ASC))) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, FEEDBACK_ASC));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, FEEDBACK_DES));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && order.equals(((SortCommand) other).order)); // state check
    }


    /**
     * Represents the {@code Order} in a {@code SortCommand}
     */
    public static class Order {
        public static final String MESSAGE_CONSTRAINTS = "Order should be either 'ASC' or 'DES', case insensitive.";

        public final String value;

        /**
         * Constructs an {@code Order}, which is either 'ASC' or 'DES' in uppercase.
         *
         * @param order
         */
        public Order(String order) {
            requireNonNull(order);
            checkArgument(isValidOrder(order), MESSAGE_CONSTRAINTS);
            value = order.toUpperCase();
        }

        /**
         * Returns if a given string is a valid order
         */
        public static boolean isValidOrder(String test) {
            return test.matches(VALIDATION_REGEX);
        }

        /**
         * Checks if two Orders are the same.
         */
        public boolean equals(Order other) {
            return value.equals(other.value);
        }
    }
}
