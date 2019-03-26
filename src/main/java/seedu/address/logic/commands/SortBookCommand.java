package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sort all books in provided order.
 */
public class SortBookCommand extends Command {

    public static final String COMMAND_WORD = "sortBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort all books in certain order"
        + "the specified keywords (case-insensitive).\n"
        + "Parameters: [st/TYPE]...[o/ORDER]...\n"
        + "Example: " + COMMAND_WORD + " st/rating o/asc";

    public static final String MESSAGE_SUCCESS = "sorted successfully";

    private final String type;

    private final String order;

    public SortBookCommand(String type, String order) {
        this.type = type;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.sortBook(type, order);
        model.commitBookShelf();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
