package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.comparators.SortRating;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all restaurants in ascending or descending order of its aggregate ratings.\n"
            + "The list of restaurants will be sorted in descending order of aggregate ratings by default, unless "
            + "its order (ASC/DES) is specified. You can limit the number of rankings shown by specifying the limit.\n"
            + "The specified keywords for order are 'ASC' or 'DES' (case-insensitive) and represent the order "
            + "that the restaurants will be listed in.\n"
            + "Parameters: [or/ASC] or [or/DES] and [l/LIMIT]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ORDER + "asc " + PREFIX_LIMIT + "3";

    public static final String MESSAGE_SUCCESS_ALL = "Sorted all restaurants in %1$s order";
    public static final String MESSAGE_SUCCESS_LIMIT = "Sorted restaurant(s) with %1$s ranking(s) in %2$s order";

    private Order order;
    private Optional<Limit> limit;
    private String commandMessage;

    /**
     * Constructs a SortCommand with optional limit.
     */
    public SortCommand(Order order, Optional<Limit> limit) {
        this.order = order;
        boolean isLimitPresent = limit.isPresent();
        boolean isOrderAsc = order.equals(new Order(ORDER_ASC));
        boolean isOrderDes = order.equals(new Order(ORDER_DES));

        if (isLimitPresent) {
            this.limit = Optional.ofNullable(limit.get());

            if (isOrderAsc) {
                this.commandMessage = String.format(MESSAGE_SUCCESS_LIMIT,
                        "the bottom " + limit.get().toInteger(), FEEDBACK_ASC);
            } else if (isOrderDes) {
                this.commandMessage = String.format(MESSAGE_SUCCESS_LIMIT,
                        "the top " + limit.get().toInteger(), FEEDBACK_DES);
            }
        } else {
            if (isOrderAsc) {
                this.commandMessage = String.format(MESSAGE_SUCCESS_ALL, FEEDBACK_ASC);
            } else if (isOrderDes) {
                this.commandMessage = String.format(MESSAGE_SUCCESS_ALL, FEEDBACK_DES);
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        boolean isLimitPresent = limit != null;

        requireNonNull(model);
        model.sortRestaurantList(new SortRating(order));

        // Filter the sorted list to only show those within the limited range
        if (!isLimitPresent) {
            model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        } else {
            filterToLimit(model, limit, order);
        }

        model.commitFoodDiary();

        return new CommandResult(this.commandMessage);
    }

    /**
     * Creates a Predicate to sort and then filter the restaurant list to the
     * restaurants with the top {@code limit} number of ratings, ordered in
     * {@code order}.
     */
    private void filterToLimit(Model model, Optional<Limit> limit, Order order) {
        boolean isOrderAsc = order.equals(new Order(ORDER_ASC));
        boolean isOrderDes = order.equals(new Order(ORDER_DES));

        int limitInt = limit.get().toInteger();
        ArrayList<Float> uniqueRatings = model.getUniqueRatings();

        // If limit < number of Restaurants, need to filter
        if (limitInt < uniqueRatings.size()) {
            float ratingBorder = model.getUniqueRatings().get(limitInt);

            if (isOrderAsc) {
                Predicate<Restaurant> predicateShowLimitedAsc = (r) ->
                        r.getSummary().getAvgRating() < ratingBorder;
                model.updateFilteredRestaurantList(predicateShowLimitedAsc);
            } else if (isOrderDes) {
                Predicate<Restaurant> predicateShowLimitedDes = (r) ->
                        r.getSummary().getAvgRating() > ratingBorder;
                model.updateFilteredRestaurantList(predicateShowLimitedDes);
            }
        } else {
            // Else, just show all
            model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        }


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
        public static final String VALIDATION_REGEX = "(?i:^(ASC)$|^(DES)$)";

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


    /**
     * Represents the {@code Limit} in a {@code SortCommand}
     */
    public static class Limit {
        public static final String MESSAGE_CONSTRAINTS = "Limit must be a positive integer.\n"
                + "If greater than number of Restaurants in The Food Diary, a sorted list of all "
                + "Restaurants will be shown.";

        public final int value;

        /**
         * Constructs a {@code Limit}
         *
         * @param limit
         */
        public Limit(String limit) {
            requireNonNull(limit);
            checkArgument(isValidLimit(limit), MESSAGE_CONSTRAINTS);
            value = Integer.parseInt(limit);
        }

        /**
         * Returns if a given string is a valid order
         */
        public static boolean isValidLimit(String test) {
            int testInt = Integer.parseInt(test);
            return testInt > 0 && testInt <= Integer.MAX_VALUE;
        }

        public int toInteger() {
            return value;
        }

        /**
         * Checks if two Limits are the same.
         */
        public boolean equals(Limit other) {
            return value == other.value;
        }
    }
}
