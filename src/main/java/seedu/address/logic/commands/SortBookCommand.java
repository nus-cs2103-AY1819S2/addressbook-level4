package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import java.util.List;
import java.util.Map;

/**
 * Sorts all books in provided order.
 */
public class SortBookCommand extends Command {

    public static final String COMMAND_WORD = "sortBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort all books in certain order"
        + "the specified keywords (case-insensitive).\n"
        + "Parameters: [st/TYPE]...[o/ORDER] [o1/ORDER]...\n"
        + "TYPE can only be either author, name or rating, "
        + "you can specify all three TYPEs, then it will sort in specify order\n"
        + "ORDER can only be asc or des, sub Order o1 is corresponding first Order\n"
        + "Example: " + COMMAND_WORD + " st/rating o/asc";

    public static final String MESSAGE_SUCCESS = "Sorted successfully";

    private final List<String> types;

    private final String mainOrder;
    private final Map<String, String> subOrders;

    public SortBookCommand(List<String> types, String mainOrder, Map<String, String> subOrders) {
        requireAllNonNull(types);
        this.types = types;
        this.mainOrder = mainOrder;
        this.subOrders = subOrders;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.sortBook(types, mainOrder, subOrders);
        model.commitBookShelf();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortBookCommand // instanceof handles nulls
            && this.mainOrder.equals(((SortBookCommand) other).mainOrder)
            && this.subOrders.equals(((SortBookCommand) other).subOrders)
            && this.types.equals(((SortBookCommand) other).types));
    }
}
